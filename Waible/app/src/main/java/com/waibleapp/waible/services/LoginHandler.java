package com.waibleapp.waible.services;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.waibleapp.waible.R;
import com.waibleapp.waible.activities.MainActivity;
import com.waibleapp.waible.model.LoginEntity;

/**
 * Created by mircea.ionita on 3/29/2017.
 */

public class LoginHandler {

    private final String TAG = "LoginHandler";

    private FirebaseAuth mAuth;

    private LoginEntity loginEntity;

    public LoginHandler(LoginEntity loginEntity) {
        mAuth = FirebaseAuth.getInstance();
        this.loginEntity = loginEntity;
    }

    public void createUserWithEmailAndPassword(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                }else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException){
                        MainActivity.makeToast(R.string.auth_user_already_exists);
                    }
                }
            }
        });
    }

    public void signInWithEmailAndPassword(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                }else {
                    MainActivity.makeToast(R.string.auth_failed);
                }
            }
        });
    }

    public void signOut(){
        mAuth.signOut();
    }
}
