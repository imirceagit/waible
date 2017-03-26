package com.waibleapp.waible.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.waibleapp.waible.R;
import com.waibleapp.waible.fragments.SignInFragment;

public class AuthActivity extends AppCompatActivity {

    TextView signUpTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.auth_fragment_container);

        if(fragment == null){
            fragment = new SignInFragment();
            fragmentManager.beginTransaction().add(R.id.auth_fragment_container, fragment).commit();
        }

        signUpTextView = (TextView) findViewById(R.id.sign_up_text_view);

        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });
    }
}
