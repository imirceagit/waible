package com.waibleapp.waible.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.waibleapp.waible.R;
import com.waibleapp.waible.activities.AuthActivity;
import com.waibleapp.waible.activities.MainActivity;

public class RegisterFragment extends Fragment {

    private final String TAG = "RegisterFragment";

    private OnRegisterFragmentInteractionListener mListener;
    ActionBar actionBar;

    public RegisterFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar = ((AuthActivity) getActivity()).getSupportActionBar();
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        if (actionBar != null && actionBar.isShowing()){
            actionBar.hide();
        }

        return view;
    }

    public void onButtonPressed(String email, String password) {
        if (mListener != null) {
            mListener.onRegisterButtonPressed(email, password);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRegisterFragmentInteractionListener) {
            mListener = (OnRegisterFragmentInteractionListener) context;
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

    public interface OnRegisterFragmentInteractionListener {
        void onRegisterButtonPressed(String email, String password);
        void onGoToLoginButtonPressed();
    }
}
