package com.waibleapp.waible.services;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.waibleapp.waible.model.User;

/**
 * Created by Mircea-Ionel on 3/27/2017.
 */
public class DatabaseService {

    private final String TAG = "DatabaseService";

    private static DatabaseService ourInstance;

    private FirebaseDatabase firebaseDatabase;

    private LoginHandler loginHandler;

    //REFERENCES
    DatabaseReference userDatabaseReference;

    public static DatabaseService getInstance(LoginHandler lh) {
        if (ourInstance == null){
            ourInstance = new DatabaseService(lh);
        }
        return ourInstance;
    }

    private DatabaseService(LoginHandler lh) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        loginHandler = lh;
    }

    public void saveUserInfo(User user){
        userDatabaseReference = firebaseDatabase.getReference("users");
        userDatabaseReference.child(user.getUserId()).child("name").setValue(user.getFullName());
    }

    public void getUserInfo(User user){
        userDatabaseReference.child(user.getUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                loginHandler.setLoggedUser(user);
                Log.v(TAG, dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
