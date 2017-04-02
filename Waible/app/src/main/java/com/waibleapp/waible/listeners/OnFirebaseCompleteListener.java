package com.waibleapp.waible.listeners;

/**
 * Created by Mircea-Ionel on 4/2/2017.
 */

public interface OnFirebaseCompleteListener {
    void onCompleteSuccessCalback(Object result);
    void onCompleteErrorCalback(String message);
}
