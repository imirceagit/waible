package com.waibleapp.waible.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.waibleapp.waible.R;
import com.waibleapp.waible.activities.AuthActivity;

public class SignUpFragment extends Fragment {

    SignInFragment signInFragment;

    private TextView signInTextView;

    public SignUpFragment() {

    }

    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        signInFragment = SignInFragment.newInstance();

        signInTextView = (TextView) view.findViewById(R.id.sign_in_text_view);

        signInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthActivity.authActivity.getSupportFragmentManager().beginTransaction().replace(R.id.auth_fragment_container, signInFragment)
                        .addToBackStack(null).commit();
            }
        });

        return view;
    }

}
