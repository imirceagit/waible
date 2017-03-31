package com.waibleapp.waible.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.waibleapp.waible.R;
import com.waibleapp.waible.activities.MainActivity;
import com.waibleapp.waible.model.Restaurant;
import com.waibleapp.waible.model.SessionEntity;

public class ScannerFragment extends Fragment {

    private final String TAG = "ScannerFragment";

    private OnScannerFragmentInteractionListener mListener;
    private SessionEntity sessionEntity;

    public ScannerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        if (actionBar != null && actionBar.isShowing()){
            actionBar.hide();
        }
        sessionEntity = SessionEntity.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scanner, container, false);
        
        if (!sessionEntity.isScanned()){
            scanFromFragment();
        }

        return view;
    }

    public void scanFromFragment() {
        IntentIntegrator.forSupportFragment(this).initiateScan();
    }

    public void onScanComplete(String restaurantId) {
        if (sessionEntity.isScanned()) {
            if (mListener != null) {
                mListener.onScannerFragmentInteraction(restaurantId);
            }
        }
    }

    private void processBarcodeCode(String scan){
        if (scan.startsWith("http://waibleapp.com/restaurants/")){
            sessionEntity.setScanned(true);
        }
        String path = scan.replace("http://waibleapp.com/restaurants/", "");
        String[] uri = path.split("/");
        sessionEntity.setRestaurantAdminId(uri[0]);
        sessionEntity.setTableNo(uri[2]);
        onScanComplete(uri[1]);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            String scannerResult = result.getContents();
            Log.d(TAG, "scannerResult ============ " + scannerResult);
            if(scannerResult == null) {
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
            }else {
                processBarcodeCode(scannerResult);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnScannerFragmentInteractionListener) {
            mListener = (OnScannerFragmentInteractionListener) context;
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

    public interface OnScannerFragmentInteractionListener {
        void onScannerFragmentInteraction(String restaurantId);
    }
}
