package com.waibleapp.waible.services;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.waibleapp.waible.R;
import com.waibleapp.waible.activities.MainActivity;
import com.waibleapp.waible.model.User;

import java.util.concurrent.Executor;

/**
 * Created by mircea.ionita on 3/27/2017.
 */
public class LoginHandler {

    private final String LOG_TAG = "LoginHandler";

    private static LoginHandler ourInstance;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseService databaseService;

    private User loggedInUser;

    public static LoginHandler getInstance() {
        if (ourInstance == null){
            ourInstance = new LoginHandler();
        }
        return ourInstance;
    }

    private LoginHandler() {
        loggedInUser = new User();
        databaseService = DatabaseService.getInstance();
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public void clearLoggedInUser(){
        loggedInUser = new User();
    }

    public void doFirebaseMailSignIn(String email, String password){

        Log.d(LOG_TAG, "MAIL: " + email + " PASS: " + password);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.mainActivity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(LOG_TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                if (!task.isSuccessful()) {
                    Log.w(LOG_TAG, "signInWithEmail:failed", task.getException());
                    Toast.makeText(MainActivity.mainActivity, R.string.auth_failed, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void doFirebaseMailSignUp(String fullName, String email, String password){
        Log.d(LOG_TAG, "MAIL: " + email + " PASS: " + password);

        loggedInUser.setFullName(fullName);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.mainActivity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                Log.d(LOG_TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                if (!task.isSuccessful()) {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(MainActivity.mainActivity, R.string.user_already_exist, Toast.LENGTH_SHORT).show();
                        MainActivity.mainActivity.openSignInFragment();
                    }
                }
            }
        });
    }

    public void signOutFirebase(){
        mAuth.signOut();
    }

}
