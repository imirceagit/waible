package com.waibleapp.waible.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.waibleapp.waible.R;
import com.waibleapp.waible.fragments.SignInFragment;

public class AuthActivity extends AppCompatActivity{

    public static AuthActivity authActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        authActivity = this;

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.auth_fragment_container);

        if(fragment == null){
            fragment = new SignInFragment();
            fragmentManager.beginTransaction().add(R.id.auth_fragment_container, fragment).commit();
        }
    }
}
