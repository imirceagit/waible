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

public class SignUpFragment extends Fragment {

    private MainActivity mainActivity;
    private LoginHandler loginHandler;

    private TextView signInTextView;

    //INTERFACE
    private EditText signUpFullNameEditText;
    private EditText signUpEmailEditText;
    private EditText signUpPsswordEditText;

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

        loginHandler = LoginHandler.getInstance();
        mainActivity = MainActivity.mainActivity;

        signInTextView = (TextView) view.findViewById(R.id.sign_in_text_view);
        signUpFullNameEditText = (EditText) view.findViewById(R.id.sign_up_full_name_edit_text);
        signUpEmailEditText = (EditText) view.findViewById(R.id.sign_up_email_edit_text);
        signUpPsswordEditText = (EditText) view.findViewById(R.id.sign_up_password_edit_text);

        Button signUpButton = (Button) view.findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginHandler.doFirebaseMailSignUp(signUpFullNameEditText.getText().toString() ,signUpEmailEditText.getText().toString(), signUpPsswordEditText.getText().toString());
            }
        });

        signInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            mainActivity.openSignInFragment();
            }
        });

        return view;
    }

    public void clearFields(){
        signUpEmailEditText.setText("");
        signUpPsswordEditText.setText("");
        signUpFullNameEditText.setText("");
    }

}
