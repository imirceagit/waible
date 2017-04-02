package com.waibleapp.waible.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int $sa = 2;

        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();
    }
}
