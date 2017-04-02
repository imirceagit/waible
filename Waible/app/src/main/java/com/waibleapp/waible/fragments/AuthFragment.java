package com.waibleapp.waible.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.waibleapp.waible.R;
import com.waibleapp.waible.activities.AuthActivity;

public class AuthFragment extends Fragment {

    private final String LOG_TAG = AuthFragment.class.getSimpleName();

    private OnAuthFragmentInteractionListener mListener;

    public AuthFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = ((AuthActivity) getActivity()).getSupportActionBar();
        if (actionBar != null && actionBar.isShowing()){
            actionBar.hide();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auth, container, false);

        final EditText authEmailEditText = (EditText) view.findViewById(R.id.auth_email_edit_text);
        final EditText authPasswordEditText = (EditText) view.findViewById(R.id.auth_password_edit_text);

        view.findViewById(R.id.auth_login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = authEmailEditText.getText().toString();
                String password = authPasswordEditText.getText().toString();
                if (validateForm(email, password)){
                    onLoginButtonPressed(email, password);
                }
            }
        });

        return view;
    }

    public void onLoginButtonPressed(String email, String password) {
        if (mListener != null) {
            mListener.onAuthFragmentLoginInteraction(email, password);
        }
    }

    public boolean validateForm(String email, String password){
        if (email == null || email.length() < 3 || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return false;
        }
        if (password == null || password.length() < 6){
            return false;
        }
        return true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAuthFragmentInteractionListener) {
            mListener = (OnAuthFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAuthFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnAuthFragmentInteractionListener {
        void onAuthFragmentLoginInteraction(String email, String password);
    }
}
