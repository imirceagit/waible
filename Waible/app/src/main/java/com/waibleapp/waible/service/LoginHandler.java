package com.waibleapp.waible.service;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.waibleapp.waible.R;
import com.waibleapp.waible.listeners.OnFirebaseCompleteListener;

/**
 * Created by Mircea-Ionel on 4/2/2017.
 */

public class LoginHandler {

    private final String LOG_TAG = LoginHandler.class.getSimpleName();

    private static LoginHandler instance;

    private Context context;
    private FirebaseAuth mAuth;

    private LoginHandler(Context context){
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
    }

    public static LoginHandler getInstance(Context context){
        if (instance == null){
            instance = new LoginHandler(context);
        }
        return instance;
    }

    public void signInWithEmailAndPassword(String email, String password, final OnFirebaseCompleteListener callback){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    callback.onCompleteSuccessCalback(null);
                }else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(context, R.string.auth_user_already_exists, Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(context, R.string.auth_failed, Toast.LENGTH_SHORT).show();
                    callback.onCompleteErrorCalback(task.getException().getMessage());
                }
            }
        });
    }

    public void createUserWithEmailAndPassword(String email, String password, final OnFirebaseCompleteListener callback){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    callback.onCompleteSuccessCalback(null);
                }else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(context, R.string.auth_user_already_exists, Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(context, R.string.auth_failed, Toast.LENGTH_SHORT).show();
                    callback.onCompleteErrorCalback(task.getException().getMessage());
                }
            }
        });
    }

    public void signOut(){
        mAuth.signOut();
    }
}
