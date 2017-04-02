package com.waibleapp.waible.listeners;

/**
 * Created by mircea.ionita on 3/30/2017.
 */

public interface OnCompleteCallback {

    void onCompleteSuccessCallback(Object result);
    void onCompleteErrorCallback(String result);
}
