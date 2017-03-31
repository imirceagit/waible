package com.waibleapp.waible.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.waibleapp.waible.R;
import com.waibleapp.waible.fragments.MainFragment;
import com.waibleapp.waible.fragments.MenuCategoryFragment;
import com.waibleapp.waible.fragments.MenuFragment;
import com.waibleapp.waible.fragments.ScannerFragment;
import com.waibleapp.waible.model.Constants;
import com.waibleapp.waible.listeners.OnUpdateUIListener;
import com.waibleapp.waible.model.MenuCategory;
import com.waibleapp.waible.model.SessionEntity;
import com.waibleapp.waible.model.User;
import com.waibleapp.waible.services.LoginHandler;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainFragment.OnMainFragmentInteractionListener, ScannerFragment.OnScannerFragmentInteractionListener,
        MenuFragment.OnMenuFragmentInteractionListener, MenuCategoryFragment.OnMenuCategoryFragmentInteractionListener{

    private final String TAG = "MainActivity";

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

        sessionEntity = SessionEntity.getInstance();
        mAuth = FirebaseAuth.getInstance();
        loginHandler = LoginHandler.getInstance(this);
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
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser == null){
                    openAuthActivity();
                }else {
                    User user = new User();
                    user.setUid(firebaseUser.getUid());
                    sessionEntity.setUser(user);
                }
            }
        };
    }

    private void openAuthActivity(){
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();
    }

    private void openMainFragment(String restaurantId){
        MainFragment fragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.MainBundleParams.restaurantIdParam, restaurantId);
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.main_fragment_container, fragment).commit();
    }

    private void openMenuFragment(){
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .replace(R.id.main_fragment_container, new MenuFragment()).addToBackStack(null).commit();
    }

    private void openMenuCategoryFragment(MenuCategory menuCategory, int position){
        MenuCategoryFragment fragment = new MenuCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.MenuCategoryBundleParams.menuCategoryParam, gson.toJson(menuCategory));
        bundle.putInt(Constants.MenuCategoryBundleParams.positionParam, position);
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .replace(R.id.main_fragment_container, fragment).addToBackStack(null).commit();
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
    public void onScannerFragmentInteraction(String restaurantId) {
        openMainFragment(restaurantId);
    }

    @Override
    public void onMenuFragmentInteraction(MenuCategory menuCategory, int position) {
        openMenuCategoryFragment(menuCategory, position);
    }

    @Override
    public void onMenuCategoryFragmentInteraction(Uri uri) {

    }
}
