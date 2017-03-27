package com.waibleapp.waible.activities;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.waibleapp.waible.R;
import com.waibleapp.waible.fragments.MainFragment;
import com.waibleapp.waible.fragments.SignInFragment;
import com.waibleapp.waible.fragments.SignUpFragment;
import com.waibleapp.waible.model.User;
import com.waibleapp.waible.services.DatabaseService;
import com.waibleapp.waible.services.LoginHandler;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = "MainActivity";

    public static MainActivity mainActivity;

    //LOGINHANDLER
    private LoginHandler loginHandler;

    //FRAGMENTS
    private FragmentManager fragmentManager;

    private SignInFragment signInFragment;
    private SignUpFragment signUpFragment;
    private MainFragment mainFragment;

    //FIREBASE
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    //DATABASE SERVICE
    private DatabaseService databaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;

        mAuth = FirebaseAuth.getInstance();
        loginHandler = LoginHandler.getInstance();
        databaseService = DatabaseService.getInstance();

        signInFragment = new SignInFragment();
        signUpFragment = new SignUpFragment();
        mainFragment = new MainFragment();

        fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);

        if(fragment == null){
            fragment = signInFragment;
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(LOG_TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                    loginHandler.getLoggedInUser().setUid(user.getUid());

                    databaseService.saveUserToDb(loginHandler.getLoggedInUser());

                    databaseService.getUserFromDb();

                    clearAuthFields();
                    openMainFragment();
                } else {
                    // User is signed out
                    Log.d(LOG_TAG, "onAuthStateChanged:signed_out");
                    loginHandler.clearLoggedInUser();
                    openSignInFragment();
                }
            }
        };
    }

    public void openSignInFragment(){
        fragmentManager.beginTransaction().replace(R.id.fragment_container, signInFragment).commit();
    }

    public void openSignUpFragment(){
        fragmentManager.beginTransaction().replace(R.id.fragment_container, signUpFragment).commit();
    }

    public void openMainFragment(){
        fragmentManager.beginTransaction().replace(R.id.fragment_container, mainFragment).commit();
    }

    private void clearAuthFields(){
        signInFragment.clearFields();
        signUpFragment.clearFields();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.sign_out_menu_button) {
            loginHandler.signOutFirebase();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
