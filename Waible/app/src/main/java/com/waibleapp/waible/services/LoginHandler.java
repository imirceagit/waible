package com.waibleapp.waible.services;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.waibleapp.waible.activities.MainActivity;
import com.waibleapp.waible.model.User;

/**
 * Created by Mircea-Ionel on 3/27/2017.
 */

public class LoginHandler {

    private final String TAG = "LoginHandler";

    private User loggedUser;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private boolean firstLogin;

    private MainActivity mainActivity;

    private DatabaseService databaseService;

    public LoginHandler(MainActivity activity) {
        mainActivity = activity;
        loggedUser = new User();
        mAuth = FirebaseAuth.getInstance();

        firstLogin = false;

        databaseService = DatabaseService.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    loggedUser.setUserId(user.getUid());
                    if (firstLogin) {
                        databaseService.saveUserInfo(loggedUser);
                    }
                }else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }

            }
        };
    }

    public void signInWithEmailAndPassword(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(mainActivity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Log.w(TAG, "signInWithEmailAndPassword", task.getException());
                        Toast.makeText(mainActivity, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                }else {
                    firstLogin = false;
                }
            }
        });
    }

    public void createUserWithEmailAndPassword(String fullName, String email, String password){
        loggedUser.setFullName(fullName);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(mainActivity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Log.w(TAG, "createUserWithEmailAndPassword", task.getException());
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(mainActivity, "Authentication failed. User laready exists.",
                                Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(mainActivity, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                }else {
                    firstLogin = true;
                }
            }
        });
    }

    public void saveUserInfo(User user){

    }

    public User getUserInfo(){
        return null;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User user) {
        this.loggedUser = user;
    }

    public void onStart(){
        mAuth.addAuthStateListener(mAuthListener);
    }

    public void onStop(){
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
