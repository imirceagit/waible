package com.waibleapp.waible.services;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.waibleapp.waible.activities.MainActivity;
import com.waibleapp.waible.model.User;
import com.waibleapp.waible.R;

/**
 * Created by mircea.ionita on 3/28/2017.
 */

public class LoginHandler {

    private final String LOG_TAG = "LoginHandler";

    private static LoginHandler instance;

    private User loggedUser;
    private boolean firstLogin = false;

    private FirebaseAuth mAuth;

    public static LoginHandler getInstance(){
        if (instance == null){
            instance = new LoginHandler();
        }
        return instance;
    }

    private LoginHandler() {
        loggedUser = new User();
        mAuth = FirebaseAuth.getInstance();
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public void clearLoggedUser(){
        loggedUser = new User();
    }

    public void createUserWithEmailAndPassword(String name, String email, String password){
        User user = loggedUser;
        user.setName(name);
        setLoggedUser(user);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    firstLogin = false;
                    if (task.getException() instanceof FirebaseAuthUserCollisionException){
                        MainActivity.shortMessage(R.string.user_already_exist);
                    }else {
                        MainActivity.shortMessage(R.string.auth_failed);
                    }
                }else {
                    firstLogin = true;
                }
            }
        });
    }

    public void signInWithEmailAndPassword(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    MainActivity.shortMessage(R.string.auth_failed);
                }
                firstLogin = false;
            }
        });
    }

    public void signOut() {
        mAuth.signOut();
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }
}
