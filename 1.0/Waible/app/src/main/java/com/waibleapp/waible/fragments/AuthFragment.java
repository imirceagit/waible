package com.waibleapp.waible.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.waibleapp.waible.R;
import com.waibleapp.waible.model.Constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthFragment extends Fragment {

    private final String LOG_TAG = "AuthFragment";

    private OnAuthFragmentInteractionListener mListener;

    private EditText fullNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private TextView signInSignUp;
    private Button signInButton;
    private Button signUpButton;

    private boolean isSigningUp = false;

    public AuthFragment() {

    }

    public static AuthFragment newInstance() {
        AuthFragment fragment = new AuthFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_auth, container, false);
        fullNameEditText = (EditText) view.findViewById(R.id.full_name_edit_text);
        emailEditText = (EditText) view.findViewById(R.id.email_edit_text);
        passwordEditText = (EditText) view.findViewById(R.id.password_edit_text);
        signInSignUp = (TextView) view.findViewById(R.id.sign_in_sign_up);

        signInSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSigningUp = !isSigningUp;
                updateUI();
            }
        });

        signInButton = (Button) view.findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailString = emailEditText.getText().toString();
                String passwordString = passwordEditText.getText().toString();

                if (validateSignInForm(emailString, passwordString)){
                    onSignInButtonPressed(emailString, passwordString);
                }
            }
        });

        signUpButton = (Button) view.findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(LOG_TAG, "SIGN UP");
                String fullNameString = fullNameEditText.getText().toString();
                String emailString = emailEditText.getText().toString();
                String passwordString = passwordEditText.getText().toString();
                onSignUpButtonPressed(fullNameString, emailString, passwordString);
                if (validateSignUpForm(fullNameString, emailString, passwordString)){
                    onSignUpButtonPressed(fullNameString, emailString, passwordString);
                }
            }
        });

        updateUI();

        return view;
    }

    private boolean validateSignUpForm(String fullName, String email, String password){
        if (fullName == null || fullName.length() < 3 || !fullName.matches("[a-zA-Z]+")){
            return false;
        }
        if (validateSignInForm(email, password)){
            return true;
        }else {
            return false;
        }
    }

    private boolean validateSignInForm(String email, String password){
        if (email == null || email.length() < 3 || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return false;
        }
        if (password == null || password.length() < 5){
            return false;
        }
        return true;
    }

    public void onSignInButtonPressed(String email, String password) {
        if (mListener != null) {
            mListener.onAuthFragmentInteractionSignIn(email, password);
        }
    }

    public void onSignUpButtonPressed(String name, String email, String password) {
        if (mListener != null) {
            mListener.onAuthFragmentInteractionSignUp(name, email, password);
        }
    }

    private void updateUI(){
        if (isSigningUp){
            fullNameEditText.setVisibility(View.VISIBLE);
            signUpButton.setVisibility(View.VISIBLE);
            signInButton.setVisibility(View.GONE);
            signInSignUp.setText(R.string.sign_in_text_view);
        }else{
            fullNameEditText.setVisibility(View.GONE);
            signUpButton.setVisibility(View.GONE);
            signInSignUp.setText(R.string.sign_up_text_view);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAuthFragmentInteractionListener) {
            mListener = (OnAuthFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnAuthFragmentInteractionListener {
        void onAuthFragmentInteractionSignIn(String email, String password);
        void onAuthFragmentInteractionSignUp(String name, String email, String password);
    }
}
