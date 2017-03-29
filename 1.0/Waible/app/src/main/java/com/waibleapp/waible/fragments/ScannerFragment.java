package com.waibleapp.waible.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.waibleapp.waible.R;
import com.waibleapp.waible.activities.MainActivity;
import com.waibleapp.waible.services.LoginHandler;

public class ScannerFragment extends Fragment {

    private OnScannerFragmentInteractionListener mListener;

    private TextView scannerResult;
    private TextView userName;

    private LoginHandler loginHandler;

    public ScannerFragment() {

    }

    public static ScannerFragment newInstance() {
        ScannerFragment fragment = new ScannerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scanner, container, false);

        loginHandler = LoginHandler.getInstance();

        scannerResult = (TextView) view.findViewById(R.id.scanner_result);
        userName = (TextView) view.findViewById(R.id.user_name);

        userName.setText(loginHandler.getLoggedUser().getName());

        Button scannerButton = (Button) view.findViewById(R.id.scanner_button);
        scannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.mainActivity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });
        return view;
    }

    public void updateUI(){
        userName.setText(loginHandler.getLoggedUser().getName());
    }

    public void updateResult(String result){
        scannerResult.setText(result);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onScannerFragmentInteraction(uri);
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
        void onScannerFragmentInteraction(Uri uri);
    }
}
