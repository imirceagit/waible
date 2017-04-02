package com.waibleapp.waible.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.waibleapp.waible.fragments.CategoriesFragment;
import com.waibleapp.waible.fragments.RestaurantFragment;
import com.waibleapp.waible.listeners.OnFirebaseCompleteListener;
import com.waibleapp.waible.model.Restaurant;
import com.waibleapp.waible.model.SessionEntity;
import com.waibleapp.waible.model.User;
import com.waibleapp.waible.service.DatabaseService;
import com.waibleapp.waible.service.LoginHandler;

public class MainActivity extends AppCompatActivity implements RestaurantFragment.OnRestaurantFragmentInteractionListener, CategoriesFragment.OnCategoriesFragmentInteractionListener{

    private final String LOG_TAG = MainActivity.class.getSimpleName();

    private LoginHandler loginHandler;
    private DatabaseService databaseService;
    private SessionEntity sessionEntity;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.v(LOG_TAG, "============================= onCreate ===========================");

        mAuth = FirebaseAuth.getInstance();
        loginHandler = LoginHandler.getInstance(this);
        databaseService = DatabaseService.getInstance();
        sessionEntity = SessionEntity.getInstance();
        fragmentManager = getSupportFragmentManager();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fragment fragment = fragmentManager.findFragmentById(R.id.main_activity_fragment_container);

        if (!sessionEntity.isScanned()){
            startBarcodeScanner();
        }

        if(fragment == null){
            fragment = new RestaurantFragment();
            fragmentManager.beginTransaction().add(R.id.main_activity_fragment_container, fragment).commit();
        }

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser == null){
                    openAuthActivity();
                }else {
                    User user = new User(firebaseUser.getUid());
                    sessionEntity.setUser(user);
                    getUserFromDatabase(user.getUserId());
                }
            }
        };
    }

    private void openAuthActivity(){
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();
    }

    private void openRestaurantFragment(){
        fragmentManager.beginTransaction().replace(R.id.main_activity_fragment_container, RestaurantFragment.newInstance()).commit();
    }

    private void openCategoriesFragment(){
        Log.v(LOG_TAG, " ==== " + sessionEntity.getRestaurant().toString());
        fragmentManager.beginTransaction().replace(R.id.main_activity_fragment_container, CategoriesFragment.newInstance(sessionEntity.getRestaurant().getRestaurantId())).addToBackStack(null).commit();
    }

    private void startBarcodeScanner(){
        new IntentIntegrator(this).initiateScan();
    }

    private void getUserFromDatabase(String userId){
        databaseService.getUserFromDatabase(userId, new OnFirebaseCompleteListener() {
            @Override
            public void onCompleteSuccessCalback(Object result) {
                sessionEntity.setUser((User) result);
                sessionEntity.setScanned(true);
            }

            @Override
            public void onCompleteErrorCalback(String message) {
                Log.d(LOG_TAG, message);
            }
        });
    }

    private void getRestaurantFromDatabase(String restaurantUserId, String restaurantId){
        databaseService.getRestaurantFromDatabase(restaurantUserId, restaurantId, new OnFirebaseCompleteListener() {
            @Override
            public void onCompleteSuccessCalback(Object result) {
                sessionEntity.setRestaurant((Restaurant) result);
                Log.v(LOG_TAG, " ==== " + sessionEntity.getRestaurant().toString());
                openRestaurantFragment();
            }

            @Override
            public void onCompleteErrorCalback(String message) {
                Log.d(LOG_TAG, message);
                Toast.makeText(getApplicationContext(), R.string.restaurant_not_found, Toast.LENGTH_SHORT).show();
                startBarcodeScanner();
            }
        });
    }

    private void processScannerResult(String content){
        String webPage = "http://waibleapp.com/restaurants/";
        if (content == null || content.length() <= 0 || !content.startsWith(webPage)){
            Toast.makeText(this, R.string.qr_code_no_match, Toast.LENGTH_SHORT).show();
            startBarcodeScanner();
        }else {
            String procesedString = content.replace(webPage, "");
            String[] uri = procesedString.split("/");
            sessionEntity.setTableNo(uri[2]);
            getRestaurantFromDatabase(uri[0], uri[1]);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
//                Toast.makeText(this, R.string.cancelled, Toast.LENGTH_LONG).show();
                startBarcodeScanner();
            } else {
                processScannerResult(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onStart() {
        Log.v(LOG_TAG, "============================= onStart ===========================");
        mAuth.addAuthStateListener(mAuthStateListener);
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.v(LOG_TAG, "============================= onResume ===========================");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.v(LOG_TAG, "============================= onPause ===========================");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.v(LOG_TAG, "============================= onStop ===========================");
        if (mAuthStateListener != null) {
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_action_settings) {
            return true;
        }
        if (id == R.id.menu_action_sign_out) {
            loginHandler.signOut();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRestaurantFragmentOpenMenuInteraction() {
        openCategoriesFragment();
    }

    @Override
    public void onRestaurantFragmentCallWaiterInteraction() {

    }

    @Override
    public void onRestaurantFragmentGetCheckInteraction() {

    }

    @Override
    public void onCategoriesFragmentItemSelectedInteraction(String categoryId) {

    }
}
