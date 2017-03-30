package com.waibleapp.waible.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.waibleapp.waible.R;
import com.waibleapp.waible.fragments.MainFragment;
import com.waibleapp.waible.fragments.MenuCategoryFragment;
import com.waibleapp.waible.fragments.MenuFragment;
import com.waibleapp.waible.fragments.RegisterFragment;
import com.waibleapp.waible.fragments.ScannerFragment;
import com.waibleapp.waible.model.Constants;
import com.waibleapp.waible.listeners.OnUpdateUIListener;
import com.waibleapp.waible.model.MenuCategory;
import com.waibleapp.waible.model.Restaurant;
import com.waibleapp.waible.model.SessionEntity;
import com.waibleapp.waible.model.User;
import com.waibleapp.waible.services.LoginHandler;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainFragment.OnMainFragmentInteractionListener,
        MenuFragment.OnMenuFragmentInteractionListener, MenuCategoryFragment.OnMenuCategoryFragmentInteractionListener{

    private final String TAG = "MainActivity";

    public static MainActivity mainActivity;

    private List<OnUpdateUIListener> onUpdateUIListeners;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private LoginHandler loginHandler;

    private FragmentManager fragmentManager;
    private Gson gson;

    private SessionEntity sessionEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;

        sessionEntity = SessionEntity.getInstance();

//        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras != null) {
                Log.v(TAG, "EXTRAS +++ " + extras.getString(Constants.AuthActivityExtras.userIdExtra));
                setUserToSession(extras.getString(Constants.AuthActivityExtras.userIdExtra));
            }
//        } else {
//            Log.v(TAG, "savedInstanceState +++ " + (String) savedInstanceState.getSerializable(Constants.AuthActivityExtras.userIdExtra));
//            setUserToSession((String) savedInstanceState.getSerializable(Constants.AuthActivityExtras.userIdExtra));
//        }

        mAuth = FirebaseAuth.getInstance();
        loginHandler = LoginHandler.getInstance();
        gson = new Gson();
        onUpdateUIListeners = new ArrayList<>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.main_fragment_container);

        if(fragment == null){
            fragment = new ScannerFragment();
            fragmentManager.beginTransaction().add(R.id.main_fragment_container, fragment).commit();
        }

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null){
                    Log.v(TAG, "loggedOut");
                    openAuthActivity();
                }else {

                }
            }
        };
    }

    private void openAuthActivity(){
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
    }

    private void openRegisterFragment(){
        fragmentManager.beginTransaction().replace(R.id.main_fragment_container, new RegisterFragment()).commit();
    }

    private void openMainFragment(){
        fragmentManager.beginTransaction().replace(R.id.main_fragment_container, new MainFragment()).commit();
    }

    private void openMenuFragment(){
        fragmentManager.beginTransaction().replace(R.id.main_fragment_container, new MenuFragment()).addToBackStack(null).commit();
    }

    private void openMenuCategoryFragment(MenuCategory menuCategory, int position){
        MenuCategoryFragment fragment = new MenuCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.MenuCategoryBundleParams.menuCategoryParam, gson.toJson(menuCategory));
        bundle.putInt(Constants.MenuCategoryBundleParams.positionParam, position);
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.main_fragment_container, fragment).addToBackStack(null).commit();
    }

    private void openScannerFragment(){
        fragmentManager.beginTransaction().replace(R.id.main_fragment_container, new ScannerFragment()).commit();
    }

    private void setUserToSession(String uid){
        User user;
        if (sessionEntity.getUser() == null){
            user = new User();
            user.setUid(uid);
            sessionEntity.setUser(user);
        }else {
            user = sessionEntity.getUser();
            user.setUid(uid);
            sessionEntity.setUser(user);
        }
    }

    public static void makeToast(int resId){
        Toast.makeText(mainActivity, resId, Toast.LENGTH_SHORT).show();
    }

    public static void makeToast(String message){
        Toast.makeText(mainActivity, message, Toast.LENGTH_SHORT).show();
    }

    private void processScan(String scan){
        sessionEntity.setScanned(true);
        String path = scan;
        path = path.replace("http://waibleapp.com/restaurants/", "");
        String[] uri = path.split("/");
        sessionEntity.setRestaurantUserId(uri[0]);
        Restaurant restaurant = new Restaurant(uri[1]);
        sessionEntity.setRestaurant(restaurant);
        sessionEntity.setTableNo(uri[2]);
        openMainFragment();
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
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }else {
                processScan(scannerResult);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
    public void onMenuFragmentInteraction(MenuCategory menuCategory, int position) {
        openMenuCategoryFragment(menuCategory, position);
    }

    @Override
    public void onMenuCategoryFragmentInteraction(Uri uri) {

    }
}
