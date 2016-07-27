package com.example.android.newsappproject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by da7th on 7/27/2016.
 */
public class Helpers {

    public Boolean checkConnectivity() {

        ConnectivityManager connMgr = (ConnectivityManager) MainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public void establishConnection() {

    }


}
