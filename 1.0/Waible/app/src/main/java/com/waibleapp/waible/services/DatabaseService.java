package com.waibleapp.waible.services;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.waibleapp.waible.activities.MainActivity;
import com.waibleapp.waible.model.User;
import com.waibleapp.waible.model.UserDb;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by mircea.ionita on 3/28/2017.
 */

public class DatabaseService {

    private final String LOG_TAG = "DatabaseService";

    private FirebaseDatabase firebaseDatabase;
    private LoginHandler loginHandler;

    //REFERENCES
    private DatabaseReference databaseReference;
    private DatabaseReference userReference;

    public DatabaseService(LoginHandler lh) {
        loginHandler = lh;
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        userReference = firebaseDatabase.getReference("users");
    }

    public void inserUser(User user){
        Log.v(LOG_TAG, "INSERT " + user.getUid() + " - " + user.getName());
        UserDb saveObj = new UserDb();
        saveObj.setName(user.getName());
        Log.v(LOG_TAG, "INSERT " + user.getUid() + " - " + saveObj.getName());
        databaseReference.child("users").child(user.getUid()).setValue(saveObj);
    }

    public void queryUser(String userId){
        final User user = new User();
        user.setUid(userId);
        userReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDb userDb = dataSnapshot.getValue(UserDb.class);
                Log.v(LOG_TAG, "QUERY == FINISHED ===" + user.getUid() + " - " + userDb.getName());
                user.setName(userDb.getName());
                loginHandler.setLoggedUser(user);
                MainActivity.mainActivity.updateUI();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
