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

public class RegisterFragment extends Fragment {

    private OnRegisterFragmentInteractionListener mListener;

    public RegisterFragment() {

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
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    public void onRegisterButtonPressed(String email, String password) {
        if (mListener != null) {
            mListener.onRegisterFragmentRegisterInteraction(email, password);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRegisterFragmentInteractionListener) {
            mListener = (OnRegisterFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRegisterFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnRegisterFragmentInteractionListener {
        void onRegisterFragmentRegisterInteraction(String email, String password);
    }
}
