package com.waibleapp.waible.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.waibleapp.waible.R;
import com.waibleapp.waible.fragments.AuthFragment;
import com.waibleapp.waible.fragments.ScannerFragment;
import com.waibleapp.waible.model.Constants;
import com.waibleapp.waible.model.User;
import com.waibleapp.waible.services.DatabaseService;
import com.waibleapp.waible.services.LoginHandler;

public class MainActivity extends AppCompatActivity implements AuthFragment.OnAuthFragmentInteractionListener, ScannerFragment.OnScannerFragmentInteractionListener{

    private final String LOG_TAG = "MainActivity";

    public static MainActivity mainActivity;

    //FRAGMENTS
    private FragmentManager fragmentManager;
    private AuthFragment authFragment;
    private ScannerFragment scannerFragment;

    //AUTH
    private LoginHandler loginHandler;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private DatabaseService databaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;

        authFragment = AuthFragment.newInstance();
        scannerFragment = ScannerFragment.newInstance();

        fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);

        if(fragment == null){
            fragment = authFragment;
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }

        loginHandler = LoginHandler.getInstance();
        databaseService = new DatabaseService(loginHandler);
        mAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(LOG_TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    if (loginHandler.isFirstLogin()){
                        User loggedUser = loginHandler.getLoggedUser();
                        loggedUser.setUid(user.getUid());
                        loginHandler.setLoggedUser(loggedUser);
                        Log.d(LOG_TAG, loginHandler.getLoggedUser().getUid() + " " + loginHandler.getLoggedUser().getName());
                        databaseService.inserUser(loginHandler.getLoggedUser());
                    } else {
                        databaseService.queryUser(user.getUid());
                    }
                    openScannerFragment();
                } else {
                    // User is signed out
                    Log.d(LOG_TAG, "onAuthStateChanged:signed_out");
                    openAuthFragment();
                }
            }
        };

    }

    private void openScannerFragment(){
        fragmentManager.beginTransaction().replace(R.id.fragment_container, scannerFragment).commit();
    }

    private void openAuthFragment(){
        fragmentManager.beginTransaction().replace(R.id.fragment_container, authFragment).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                scannerFragment.updateResult("CANCELED");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d("MainActivity", "Scanned " + result.getContents());
                scannerFragment.updateResult(result.getContents());
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void updateUI(){
        scannerFragment.updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.main_menu_sign_out) {
            loginHandler.signOut();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void shortMessage(int messageId){
        Toast.makeText(mainActivity, messageId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            mAuth.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    public void onAuthFragmentInteractionSignIn(String email, String password) {
        loginHandler.signInWithEmailAndPassword(email, password);
    }

    @Override
    public void onAuthFragmentInteractionSignUp(String name, String email, String password) {
        loginHandler.createUserWithEmailAndPassword(name, email, password);
    }

    @Override
    public void onScannerFragmentInteraction(Uri uri) {

    }
}
