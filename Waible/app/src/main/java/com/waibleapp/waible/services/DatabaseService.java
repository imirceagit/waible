package com.waibleapp.waible.services;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.waibleapp.waible.listeners.OnCompleteCallback;
import com.waibleapp.waible.model.Localization;
import com.waibleapp.waible.model.MenuItem;
import com.waibleapp.waible.model.MenuItems;
import com.waibleapp.waible.model.Restaurant;
import com.waibleapp.waible.model.RestaurantMenu;
import com.waibleapp.waible.model.SessionEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by mircea.ionita on 3/30/2017.
 */

public class DatabaseService {

    private final String TAG = "DatabaseService";
    private static DatabaseService instance;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private SessionEntity sessionEntity;

    private DatabaseService() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        sessionEntity = SessionEntity.getInstance();
    }

    public static DatabaseService getInstance(){
        if (instance == null){
            instance = new DatabaseService();
        }
        return instance;
    }

    public void getRestaurantDetails(String restaurantAdminId, String restaurantId, final OnCompleteCallback callback){
        DatabaseReference restaurantReference = databaseReference.child("restaurants").child(restaurantAdminId).child(restaurantId);
        restaurantReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Restaurant restaurant = dataSnapshot.getValue(Restaurant.class);
                restaurant.setRestaurantId(dataSnapshot.getKey());
                sessionEntity.setRestaurant(restaurant);
                sessionEntity.setRestaurantLoaded(true);
                callback.onCompleteSuccessCallback(restaurant);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onCompleteErrorCallback(databaseError.getMessage());
            }
        });
    }

    public void getMenuCategoriesForRestaurant(String restaurantId, final OnCompleteCallback callback){
        DatabaseReference menusReference = databaseReference.child("menus").child(restaurantId);
        menusReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                RestaurantMenu restaurantMenu = dataSnapshot.getValue(RestaurantMenu.class);
                restaurantMenu.setCategoriesId();
                Log.v(TAG, "restaurantMenu " + restaurantMenu.toString());
                Localization.defaultLanguage = restaurantMenu.getDefaultLanguage();
                callback.onCompleteSuccessCallback(restaurantMenu);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onCompleteErrorCallback(databaseError.getMessage());
            }
        });
    }

    public void getMenuItemsForCategory(String restaurantId, String categoryId, final OnCompleteCallback callback){
        DatabaseReference menuItemsReference = databaseReference.child("menuItems").child(restaurantId).child(categoryId);
        menuItemsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, MenuItem> items = (HashMap<String, MenuItem>) dataSnapshot.getValue();
                Set<String> keySet = items.keySet();
                List<MenuItem> list = new ArrayList<>();
                for (String key : keySet){
                    list.add(items.get(key));
                }
                MenuItems menuItems = new MenuItems();
                menuItems.setMenuItems(list);
                callback.onCompleteSuccessCallback(menuItems);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onCompleteErrorCallback(databaseError.getMessage());
            }
        });
    }
}
