package com.waibleapp.waible.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.waibleapp.waible.R;
import com.waibleapp.waible.activities.MainActivity;
import com.waibleapp.waible.model.SessionEntity;
import com.waibleapp.waible.utils.Constants;

public class RestaurantFragment extends Fragment {

    private final String LOG_TAG = RestaurantFragment.class.getSimpleName();

    private OnRestaurantFragmentInteractionListener mListener;

    private RelativeLayout mainFragmentLoadingPanel;
    private TextView mainTitleOne;
    private TextView mainRestaurantName;
    private Button seeMenuButton;
    private Button callWaiterButton;
    private Button getCheckButton;
    private ActionBar actionBar;

    private boolean isLoading = true;
    private String restaurantName;

    public RestaurantFragment() {

    }

    public static RestaurantFragment newInstance(String restaurantName){
        RestaurantFragment fragment = new RestaurantFragment();
        Bundle args = new Bundle();
        args.putString(Constants.FragmentArgumentParams.restaurantFragmentRestaurantNameParam, restaurantName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            restaurantName = getArguments().getString(Constants.FragmentArgumentParams.restaurantFragmentRestaurantNameParam);
        }else {
            restaurantName = "";
        }
        actionBar = ((MainActivity) getActivity()).getSupportActionBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant, container, false);

        mainFragmentLoadingPanel = (RelativeLayout) view.findViewById(R.id.main_fragment_loading_panel);

        mainRestaurantName = (TextView) view.findViewById(R.id.main_restaurant_name);
        mainRestaurantName.setText(restaurantName);

        seeMenuButton = (Button) view.findViewById(R.id.see_menu_button);
        seeMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOpenMenuButtonPressed();
            }
        });

        callWaiterButton = (Button) view.findViewById(R.id.call_waiter_button);
        callWaiterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        getCheckButton = (Button) view.findViewById(R.id.get_check_button);
        getCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        mainFragmentLoadingPanel.setVisibility(View.GONE);
        return view;
    }

    private void updateUI(){
        if (isLoading){
            mainTitleOne.setVisibility(View.GONE);
            seeMenuButton.setVisibility(View.GONE);
            callWaiterButton.setVisibility(View.GONE);
            getCheckButton.setVisibility(View.GONE);
        }else {
            mainTitleOne.setVisibility(View.VISIBLE);
            mainFragmentLoadingPanel.setVisibility(View.GONE);
            seeMenuButton.setVisibility(View.VISIBLE);
            callWaiterButton.setVisibility(View.VISIBLE);
            getCheckButton.setVisibility(View.VISIBLE);
        }
    }

    public void onOpenMenuButtonPressed() {
        if (mListener != null) {
            mListener.onRestaurantFragmentOpenMenuInteraction();
        }
    }

    public void onCallWaiterButtonPressed() {
        if (mListener != null) {
            mListener.onRestaurantFragmentCallWaiterInteraction();
        }
    }

    public void onGetCheckButtonPressed() {
        if (mListener != null) {
            mListener.onRestaurantFragmentGetCheckInteraction();
        }
    }

    @Override
    public void onStart() {
        if (actionBar != null && actionBar.isShowing()){
            actionBar.hide();
        }
        super.onStart();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRestaurantFragmentInteractionListener) {
            mListener = (OnRestaurantFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRestaurantFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnRestaurantFragmentInteractionListener {
        void onRestaurantFragmentOpenMenuInteraction();
        void onRestaurantFragmentCallWaiterInteraction();
        void onRestaurantFragmentGetCheckInteraction();
    }
}
