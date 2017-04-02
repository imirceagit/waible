package com.waibleapp.waible.service;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.waibleapp.waible.listeners.OnFirebaseCompleteListener;
import com.waibleapp.waible.model.MenuCategory;
import com.waibleapp.waible.model.MenuItem;
import com.waibleapp.waible.model.Restaurant;
import com.waibleapp.waible.model.RestaurantMenu;
import com.waibleapp.waible.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mircea-Ionel on 4/2/2017.
 */

public class DatabaseService {

    private final String LOG_TAG = DatabaseService.class.getSimpleName();

    private static DatabaseService instance;

    private FirebaseDatabase firebaseDatabase;

    private DatabaseService() {
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public static DatabaseService getInstance(){
        if (instance == null){
            instance = new DatabaseService();
        }
        return instance;
    }

    public void getUserFromDatabase(String userId, final OnFirebaseCompleteListener callback){
        firebaseDatabase.getReference().child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                user.setUserId(dataSnapshot.getKey());
                callback.onCompleteSuccessCalback(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onCompleteErrorCalback(databaseError.getMessage());
            }
        });
    }

    public void getRestaurantFromDatabase(String restaurantUserId, String restaurantId, final OnFirebaseCompleteListener callback){
        firebaseDatabase.getReference().child("restaurants").child(restaurantUserId).child(restaurantId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Restaurant restaurant = dataSnapshot.getValue(Restaurant.class);
                restaurant.setRestaurantId(dataSnapshot.getKey());
                callback.onCompleteSuccessCalback(restaurant);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onCompleteErrorCalback(databaseError.getMessage());
            }
        });
    }

    public void getRestaurantMenuFromDatabase(String restaurantId, final OnFirebaseCompleteListener callback){
        firebaseDatabase.getReference().child("menus").child(restaurantId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                RestaurantMenu restaurantMenu = dataSnapshot.getValue(RestaurantMenu.class);
                Log.v(LOG_TAG, "getRestaurantMenuFromDatabase ====" + restaurantMenu.toString());
                restaurantMenu.setCategoriesId();
                callback.onCompleteSuccessCalback(restaurantMenu);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onCompleteErrorCalback(databaseError.getMessage());
            }
        });
    }

    public void getMenuItemsFromDatabase(String restaurantId, String categoryId, final OnFirebaseCompleteListener callback){
        firebaseDatabase.getReference().child("menuItems").child(restaurantId).child(categoryId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<MenuItem> itemList = new ArrayList<>();
                for (DataSnapshot d : dataSnapshot.getChildren()){
                    MenuItem menuItem = d.getValue(MenuItem.class);
                    menuItem.setMenuItemId(d.getKey());
                    itemList.add(menuItem);
                }
                callback.onCompleteSuccessCalback(itemList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onCompleteErrorCalback(databaseError.getMessage());
            }
        });
    }
}
