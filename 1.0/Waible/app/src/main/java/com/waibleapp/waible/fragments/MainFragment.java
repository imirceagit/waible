package com.waibleapp.waible.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.waibleapp.waible.R;
import com.waibleapp.waible.activities.MainActivity;

public class MainFragment extends Fragment {

    private final String LOG_TAG = "MainFragment";

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        Button callWaiterButton = (Button) view.findViewById(R.id.call_waiter_button);
        callWaiterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.mainActivity, "CALL WAITER", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
