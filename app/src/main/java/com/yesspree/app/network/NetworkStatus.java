/*********************************************************
 * NetworkStatus helps us to check the Internet connection
 * state of our handheld device.**************************
 ********************************************************/
package com.yesspree.app.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class NetworkStatus {
    private static NetworkStatus instance;

    public static NetworkStatus getInstance() {
        if (instance == null)
            instance = new NetworkStatus();
        return instance;
    }

    public boolean isOnline(Context context) {
        boolean connected = false;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
            if (!connected)
                Toast.makeText(context, "Check Network Connection", Toast.LENGTH_SHORT).show();
            context = null;
            return connected;
        } catch (Exception e) {
            Toast.makeText(context, "Check Connectivity Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.v("connectivity", e.toString());
            return connected;
        }

    }

    public boolean isOnline2(Context context) {
        boolean connected = false;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
            context = null;
            return connected;
        } catch (Exception e) {
            Log.v("connectivity", e.toString());
        }
        return connected;
    }
}
