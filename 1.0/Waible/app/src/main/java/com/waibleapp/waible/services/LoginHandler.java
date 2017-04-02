package com.waibleapp.waible.services;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.waibleapp.waible.R;
import com.waibleapp.waible.activities.MainActivity;
import com.waibleapp.waible.listeners.OnCompleteCallback;

/**
 * Created by mircea.ionita on 3/29/2017.
 */

public class LoginHandler {

    private final String TAG = "LoginHandler";

    private static LoginHandler instance;
    private Context context;

    private FirebaseAuth mAuth;

    private LoginHandler(Context context) {
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
    }

    public static LoginHandler getInstance(Context context){
        if (instance == null){
            instance = new LoginHandler(context);
        }
        return instance;
    }

    public void createUserWithEmailAndPassword(String email, String password, final OnCompleteCallback callback){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    callback.onCompleteSuccessCallback(new Object());
                }else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(context, R.string.auth_user_already_exists, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void signInWithEmailAndPassword(String email, String password, final OnCompleteCallback callback){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    callback.onCompleteSuccessCallback(new Object());
                }else {
                    Toast.makeText(context, R.string.auth_failed, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void signOut(){
        mAuth.signOut();
    }
}
