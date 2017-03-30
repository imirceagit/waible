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
import com.waibleapp.waible.model.Constants;
import com.waibleapp.waible.model.SessionEntity;
import com.waibleapp.waible.services.LoginHandler;

public class AuthActivity extends AppCompatActivity implements AuthFragment.OnAuthFragmentInteractionListener, RegisterFragment.OnRegisterFragmentInteractionListener{

    private final String TAG = "AuthActivity";

    private FragmentManager fragmentManager;
    private LoginHandler loginHandler;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private SessionEntity sessionEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        mAuth = FirebaseAuth.getInstance();
        loginHandler = LoginHandler.getInstance();
        sessionEntity = SessionEntity.getInstance();

        fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.auth_fragment_container);

        if(fragment == null){
            fragment = new AuthFragment();
            fragmentManager.beginTransaction().add(R.id.auth_fragment_container, fragment).commit();
        }

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null){

                }else {
                    Log.v(TAG, "loggedIn" + user.getUid());
                    openMainActivity(user.getUid());
                }
            }
        };
    }

    private void openAuthFragment(){
        fragmentManager.beginTransaction().replace(R.id.auth_fragment_container, new AuthFragment()).commit();
    }

    private void openRegisterFragment(){
        fragmentManager.beginTransaction().replace(R.id.auth_fragment_container, new RegisterFragment()).commit();
    }

    private void openMainActivity(String uid){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Constants.AuthActivityExtras.userIdExtra, uid);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onLoginButtonPressed(String email, String password) {
        loginHandler.signInWithEmailAndPassword(email, password);
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
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        if (mAuthStateListener != null) {
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
        super.onStop();
    }
}
