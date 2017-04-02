package com.waibleapp.waible.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.waibleapp.waible.R;
import com.waibleapp.waible.fragments.AuthFragment;
import com.waibleapp.waible.fragments.RegisterFragment;
import com.waibleapp.waible.listeners.OnFirebaseCompleteListener;
import com.waibleapp.waible.service.LoginHandler;

public class AuthActivity extends AppCompatActivity implements AuthFragment.OnAuthFragmentInteractionListener, RegisterFragment.OnRegisterFragmentInteractionListener{

    private final String LOG_TAG = AuthActivity.class.getSimpleName();

    private FragmentManager fragmentManager;
    private LoginHandler loginHandler;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        mAuth = FirebaseAuth.getInstance();
        loginHandler = LoginHandler.getInstance(this);

        fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.auth_activity_fragment_container);

        if (mAuth.getCurrentUser() != null) {
            openMainActivity();
        }else {
            if(fragment == null){
                fragment = new AuthFragment();
                fragmentManager.beginTransaction().add(R.id.auth_activity_fragment_container, fragment).commit();
            }
        }
    }

    private void openAuthFragment(){
        fragmentManager.beginTransaction().replace(R.id.auth_activity_fragment_container, new AuthFragment()).commit();
    }

    private void openRegisterFragment(){
        fragmentManager.beginTransaction().replace(R.id.auth_activity_fragment_container, new RegisterFragment()).commit();
    }

    private void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onAuthFragmentLoginInteraction(String email, String password) {
        loginHandler.signInWithEmailAndPassword(email, password, new OnFirebaseCompleteListener() {
            @Override
            public void onCompleteSuccessCalback(Object result) {
                openMainActivity();
            }

            @Override
            public void onCompleteErrorCalback(String message) {
                Log.e(LOG_TAG, message);
            }
        });
    }

    @Override
    public void onRegisterFragmentRegisterInteraction(String email, String password) {

    }
}
