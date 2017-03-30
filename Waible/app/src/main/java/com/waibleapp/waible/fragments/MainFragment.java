package com.waibleapp.waible.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.waibleapp.waible.R;
import com.waibleapp.waible.activities.MainActivity;
import com.waibleapp.waible.listeners.OnCompleteCallback;
import com.waibleapp.waible.listeners.OnUpdateUIListener;
import com.waibleapp.waible.model.Restaurant;
import com.waibleapp.waible.model.SessionEntity;
import com.waibleapp.waible.services.DatabaseService;

public class MainFragment extends Fragment implements OnUpdateUIListener{

    private final String TAG = "MainFragment";

    private OnMainFragmentInteractionListener mListener;
    private DatabaseService databaseService;
    private SessionEntity sessionEntity;

    private TextView mainRestaurantName;

    public MainFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseService = DatabaseService.getInstance();
        sessionEntity = SessionEntity.getInstance();
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        if (actionBar != null && actionBar.isShowing()){
            actionBar.hide();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mainRestaurantName = (TextView) view.findViewById(R.id.main_restaurant_name);
        Button seeMenuButton = (Button) view.findViewById(R.id.see_menu_button);
        seeMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onseeMenuButtonPressed();
            }
        });
        Button callWaiterButton = (Button) view.findViewById(R.id.call_waiter_button);
        callWaiterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callWaiterButtonPressed();
            }
        });
        Button getCheckButton = (Button) view.findViewById(R.id.get_check_button);
        getCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCheckButtonPressed();
            }
        });

        databaseService.getRestaurantDetails(sessionEntity.getRestaurantPath(), new OnCompleteCallback() {
            @Override
            public void onCompleteSuccessCallback(Object result) {
                Restaurant restaurant = (Restaurant) result;
                mainRestaurantName.setText(restaurant.getName());
            }

            @Override
            public void onCompleteErrorCallback(String result) {
                MainActivity.makeToast(result);
            }
        });

        return view;
    }

    private void updateMainFragmentUI(){

    }

    public void onseeMenuButtonPressed() {
        if (mListener != null) {
            mListener.onMainFragmentInteractionMenuButton();
        }
    }

    public void callWaiterButtonPressed() {
        if (mListener != null) {
            mListener.onMainFragmentInteractionWaiterButton();
        }
    }

    public void getCheckButtonPressed() {
        if (mListener != null) {
            mListener.onMainFragmentInteractionCheckButton();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MainActivity.mainActivity.addOnUpdateUIListeners(this);
        if (context instanceof OnMainFragmentInteractionListener) {
            mListener = (OnMainFragmentInteractionListener) context;
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
        updateMainFragmentUI();
    }

    public interface OnMainFragmentInteractionListener {
        void onMainFragmentInteractionMenuButton();
        void onMainFragmentInteractionWaiterButton();
        void onMainFragmentInteractionCheckButton();
    }
}
