package com.example.api.common.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;

public class NetworkStates {
    static boolean isOnline;
    public static boolean checkInternetConnection(Context context ) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());  // need ACCESS_NETWORK_STATE permission
//        isOnline = capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if(info != null){
            if(info.isConnected()){
                return true;
            }
        }
        else {
            return false;
        }
        return false;
    }
    public static boolean checkInternetConnecting(Context context ) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        WifiManager manager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        if(info != null){
            if(info.isConnected()){
                return true;
            }
        }
        return false;
    }
    public static void showDialog(Context context){
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                context);
        builder.setTitle("No Internet Connection");
        builder.setMessage("Please Connect To Wifi Or Network");
        builder.setPositiveButton("Connect to internet",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, final int i) {
                        context.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                    }
                });
        builder.setNegativeButton("Continue ", null);
        builder.create();
        builder.show();

    }
}
