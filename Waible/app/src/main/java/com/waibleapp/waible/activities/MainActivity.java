package com.waibleapp.waible.activities;

import android.net.Uri;
import android.os.Bundle;
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

import com.waibleapp.waible.R;
import com.waibleapp.waible.fragments.AuthFragment;
import com.waibleapp.waible.fragments.MainFragment;
import com.waibleapp.waible.services.LoginHandler;

public class MainActivity extends AppCompatActivity implements AuthFragment.OnFragmentInteractionListener {

    private final String TAG = "MainActivity";

    private LoginHandler loginHandler;

    //FRAGMENTS
    private FragmentManager fragmentManager;
    private MainFragment mainFragment;
    private AuthFragment authFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        loginHandler = new LoginHandler(this);
        fragmentManager = getSupportFragmentManager();
        mainFragment = new MainFragment();
        authFragment = new AuthFragment();

        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (fragment == null){
            if (loginHandler.isLoggedIn()){
                fragment = mainFragment;
                Log.v(TAG, "MAIN");
            }else {
                fragment = authFragment;
                Log.v(TAG, "AUTH");
            }
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
