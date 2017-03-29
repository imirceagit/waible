package com.waibleapp.waible.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.waibleapp.waible.R;
import com.waibleapp.waible.fragments.AuthFragment;
import com.waibleapp.waible.fragments.MainFragment;
import com.waibleapp.waible.fragments.MenuFragment;
import com.waibleapp.waible.model.LoginEntity;
import com.waibleapp.waible.model.OnUpdateUIListener;
import com.waibleapp.waible.model.Restaurant;
import com.waibleapp.waible.services.LoginHandler;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainFragment.OnMainFragmentInteractionListener, AuthFragment.OnAuthFragmentInteractionListener, MenuFragment.OnMenuFragmentInteractionListener{

    private final String TAG = "MainActivity";
    public static MainActivity mainActivity;

    private List<OnUpdateUIListener> onUpdateUIListeners;

    //FRAGMENTS
    private FragmentManager fragmentManager;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    public static LoginEntity loginEntity;
    private LoginHandler loginHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);

        if(fragment == null){
            fragment = new AuthFragment();
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }

        mainActivity = this;
        mAuth = FirebaseAuth.getInstance();
        loginEntity = new LoginEntity();
        loginHandler = new LoginHandler();

        onUpdateUIListeners = new ArrayList<>();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null){
                    Log.v(TAG, "loggedOut");
                    openAuthFragment();
                }else {
                    Log.v(TAG, "loggedIn" + user.getUid());
                    loginEntity.setUid(user.getUid());
                    openMainFragment();
                }
            }
        };
    }

    private void openAuthFragment(){
        fragmentManager.beginTransaction().replace(R.id.fragment_container, new AuthFragment()).commit();
    }

    private void openMainFragment(){
        fragmentManager.beginTransaction().replace(R.id.fragment_container, new MainFragment()).commit();
    }

    private void openMenuFragment(){
        fragmentManager.beginTransaction().replace(R.id.fragment_container, new MenuFragment()).addToBackStack(null).commit();
    }

    public static void makeToast(int resId){
        Toast.makeText(mainActivity, resId, Toast.LENGTH_SHORT).show();
    }

    private void processScan(String scan){
        String restaurantName = scan;
        restaurantName = restaurantName.replace("http://waibleapp.com/", "");
        restaurantName = restaurantName.replace("_", " ");
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantName);
        loginEntity.setRestaurant(restaurant);
    }

    private void updateUI(){
        for (OnUpdateUIListener listener : onUpdateUIListeners){
            listener.onUpdateUIListener();
        }
    }

    public void addOnUpdateUIListeners(OnUpdateUIListener listener){
        onUpdateUIListeners.add(listener);
    }

    public void removeOnUpdateUIListeners(OnUpdateUIListener listener){
        onUpdateUIListeners.remove(listener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null){
            String scannerResult = result.getContents();
            Log.d(TAG, "scannerResult ============ " + scannerResult);
            if(scannerResult == null) {
                Log.d(TAG, "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }else {
                Log.d(TAG, "Scanned " + scannerResult);
                processScan(scannerResult);
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_main_action_settings) {
            return true;
        }

        if (id == R.id.menu_main_action_sign_out) {
            loginHandler.signOut();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        mAuth.addAuthStateListener(mAuthStateListener);
        super.onStart();
    }

    @Override
    protected void onStop() {
        if (mAuthStateListener != null) {
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void onAuthFragmentInteraction(String email, String password) {
        loginHandler.signInWithEmailAndPassword(email, password);
    }

    @Override
    public void onMainFragmentInteractionMenuButton() {
        openMenuFragment();
    }

    @Override
    public void onMainFragmentInteractionWaiterButton() {

    }

    @Override
    public void onMainFragmentInteractionCheckButton() {

    }

    @Override
    public void onMenuFragmentInteraction(Uri uri) {

    }
}
