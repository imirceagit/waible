package com.waibleapp.waible.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.waibleapp.waible.R;
import com.waibleapp.waible.fragments.AuthFragment;
import com.waibleapp.waible.fragments.MainFragment;
import com.waibleapp.waible.fragments.RegisterFragment;
import com.waibleapp.waible.listeners.OnCompleteCallback;
import com.waibleapp.waible.model.Constants;
import com.waibleapp.waible.model.SessionEntity;
import com.waibleapp.waible.services.LoginHandler;

public class AuthActivity extends AppCompatActivity implements AuthFragment.OnAuthFragmentInteractionListener, RegisterFragment.OnRegisterFragmentInteractionListener{

    private final String TAG = "AuthActivity";

    private FragmentManager fragmentManager;
    private LoginHandler loginHandler;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        mAuth = FirebaseAuth.getInstance();
        loginHandler = LoginHandler.getInstance();

        fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.auth_fragment_container);

        if(fragment == null){
            fragment = new AuthFragment();
            fragmentManager.beginTransaction().add(R.id.auth_fragment_container, fragment).commit();
        }

        if (mAuth.getCurrentUser() != null) {
            openMainActivity();
        }
    }

    private void openAuthFragment(){
        fragmentManager.beginTransaction().replace(R.id.auth_fragment_container, new AuthFragment()).commit();
    }

    private void openRegisterFragment(){
        fragmentManager.beginTransaction().replace(R.id.auth_fragment_container, new RegisterFragment()).commit();
    }

    private void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onLoginButtonPressed(String email, String password) {
        loginHandler.signInWithEmailAndPassword(email, password, new OnCompleteCallback() {
            @Override
            public void onCompleteSuccessCallback(Object result) {
                openMainActivity();
            }

            @Override
            public void onCompleteErrorCallback(String result) {

            }
        });
    }

    @Override
    public void onGoToRegisterButtonPressed() {

    }

    @Override
    public void onRegisterButtonPressed(String email, String password) {

    }

    @Override
    public void onGoToLoginButtonPressed() {

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
