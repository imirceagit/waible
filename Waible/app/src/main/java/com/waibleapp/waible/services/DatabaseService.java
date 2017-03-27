package com.waibleapp.waible.services;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.waibleapp.waible.model.User;

/**
 * Created by mircea.ionita on 3/27/2017.
 */
public class DatabaseService {

    private static DatabaseService ourInstance;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference usersReference;

    private LoginHandler loginHandler;

    public static DatabaseService getInstance() {
        if (ourInstance == null){
            ourInstance = new DatabaseService();
        }
        return ourInstance;
    }

    private DatabaseService() {
        loginHandler = LoginHandler.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        usersReference = firebaseDatabase.getReference("users");
    }

    public void saveUserToDb(User user){
        usersReference.child(user.getUid()).child("name").setValue(user.getFullName());
    }

    public void getUserFromDb(){
        usersReference.child(loginHandler.getLoggedInUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = new User();
                user.setFullName(dataSnapshot.child("name").getValue(String.class));
                loginHandler.setLoggedInUser(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
