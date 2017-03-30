package com.waibleapp.waible.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.waibleapp.waible.R;
import com.waibleapp.waible.activities.MainActivity;
import com.waibleapp.waible.listeners.OnUpdateUIListener;
import com.waibleapp.waible.model.Constants;
import com.waibleapp.waible.model.MenuCategory;

public class MenuCategoryFragment extends Fragment implements OnUpdateUIListener {

    private Gson gson;
    private MenuCategory menuCategory;
    private int position;

    private OnMenuCategoryFragmentInteractionListener mListener;

    public MenuCategoryFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson = new Gson();
        if (getArguments() != null) {
            menuCategory = gson.fromJson(getArguments().getString(Constants.MenuCategoryBundleParams.menuCategoryParam), MenuCategory.class);
            position = getArguments().getInt(Constants.MenuCategoryBundleParams.positionParam);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_category, container, false);
        return view;
    }

    private void updateUI(){

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onMenuCategoryFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MainActivity.mainActivity.addOnUpdateUIListeners(this);
        if (context instanceof OnMenuCategoryFragmentInteractionListener) {
            mListener = (OnMenuCategoryFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        MainActivity.mainActivity.removeOnUpdateUIListeners(this);
        mListener = null;
    }

    @Override
    public void onUpdateUIListener() {
        updateUI();
    }

    public interface OnMenuCategoryFragmentInteractionListener {
        void onMenuCategoryFragmentInteraction(Uri uri);
    }
}
