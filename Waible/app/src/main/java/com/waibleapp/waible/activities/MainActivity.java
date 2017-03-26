package com.waibleapp.waible.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.waibleapp.waible.R;
import com.waibleapp.waible.services.DatabaseService;

public class MainActivity extends AppCompatActivity {

    public static MainActivity mainActivity;

    //INTERFACE
    private Button callWaiterButton;

    //DATABASE
    DatabaseService dbService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivity = this;

        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);

        dbService = new DatabaseService();

        callWaiterButton = (Button) findViewById(R.id.call_waiter_button);

        callWaiterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
