package com.waibleapp.waible.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.waibleapp.waible.R;
import com.waibleapp.waible.activities.MainActivity;
import com.waibleapp.waible.services.LoginHandler;

public class SignInFragment extends Fragment {

    private MainActivity mainActivity;
    private LoginHandler loginHandler;

    private TextView signUpTextView;

    //INTERFACE
    private EditText signInEmailEditText;
    private EditText signInPsswordEditText;

    //VARIABLES
    String signInEmail;
    String signInPassword;

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

        loginHandler = LoginHandler.getInstance();
        mainActivity = MainActivity.mainActivity;

        Button signInButton = (Button) view.findViewById(R.id.sign_in_button);
        signInEmailEditText = (EditText) view.findViewById(R.id.sign_in_email_edit_text);
        signInPsswordEditText = (EditText) view.findViewById(R.id.sign_in_password_edit_text);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginHandler.doFirebaseMailSignIn(signInEmailEditText.getText().toString(), signInPsswordEditText.getText().toString());
            }
        });

        signUpTextView = (TextView) view.findViewById(R.id.sign_up_text_view);
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.openSignUpFragment();
            }
        });

        return view;
    }

    public void clearFields(){
        signInEmailEditText.setText("");
        signInPsswordEditText.setText("");
    }

}
