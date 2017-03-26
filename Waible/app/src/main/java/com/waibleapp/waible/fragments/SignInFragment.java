package com.waibleapp.waible.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.waibleapp.waible.R;
import com.waibleapp.waible.activities.AuthActivity;

public class SignInFragment extends Fragment {

    private SignUpFragment signUpFragment;

    private TextView signUpTextView;

    public SignInFragment() {

    }

    public static SignInFragment newInstance() {
        SignInFragment fragment = new SignInFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        signUpFragment = SignUpFragment.newInstance();

        signUpTextView = (TextView) view.findViewById(R.id.sign_up_text_view);

        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthActivity.authActivity.getSupportFragmentManager().beginTransaction().replace(R.id.auth_fragment_container, signUpFragment)
                        .addToBackStack(null).commit();
            }
        });

        return view;
    }

}
