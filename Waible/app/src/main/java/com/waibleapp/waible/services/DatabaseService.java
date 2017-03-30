package com.waibleapp.waible.services;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.waibleapp.waible.listeners.OnCompleteCallback;
import com.waibleapp.waible.model.Restaurant;
import com.waibleapp.waible.model.RestaurantMenu;
import com.waibleapp.waible.model.SessionEntity;

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

    public void getMenuForRestaurant(String restaurantId, final OnCompleteCallback callback){
        DatabaseReference menusReference = databaseReference.child("menus");
        menusReference.child(restaurantId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                RestaurantMenu restaurantMenu = dataSnapshot.getValue(RestaurantMenu.class);
                callback.onCompleteSuccessCallback(restaurantMenu);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onCompleteErrorCallback(databaseError.getMessage());
            }
        });
    }

    public void getRestaurantDetails(String path, final OnCompleteCallback callback){
        DatabaseReference restaurantReference = databaseReference.child(path);
        restaurantReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Restaurant restaurant = dataSnapshot.getValue(Restaurant.class);
                Restaurant sessionRestaurant = sessionEntity.getRestaurant();
                sessionRestaurant.cloneRestaurant(restaurant);
                callback.onCompleteSuccessCallback(restaurant);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onCompleteErrorCallback(databaseError.getMessage());
            }
        });
    }
}
