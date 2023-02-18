package com.example.api.common.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.example.api.common.helper.Utils;
import com.example.api.ui.layout.ActivityFavourite;
import com.example.api.ui.layout.MainActivity;
import com.example.api.ui.layout.MainDetails;
import com.example.api.ui.layout.MoviesFragment;
import com.example.api.ui.layout.Search;

public class NetworkStatesReceiver extends BroadcastReceiver {
    private MainReceiver mainReceiver;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)){
            if(NetworkStates.checkInternetConnection(context)){
                // connected
                if(context.getClass().getSimpleName().equals(MainActivity.class.getSimpleName())){
                    // Main Activity Callback
                    this.mainReceiver.mainReceiver();
                }
                else if(context.getClass().getSimpleName().equals(MainDetails.class.getSimpleName())){
                    // Main Details Callback


                }
                else if(context.getClass().getSimpleName().equals(ActivityFavourite.class.getSimpleName())){
                    // Favorite Callback
                    this.mainReceiver.mainReceiver();


                }
                else if(context.getClass().getSimpleName().equals(Search.class.getSimpleName())){
                    // search Callback
                    this.mainReceiver.mainReceiver();


                }
            }
//            else if(NetworkStates.checkInternetConnection(context)){
//                Toast.makeText(context, "Connecting", Toast.LENGTH_SHORT).show();
//                //connecting
//            }
            else {
                //not connected
                NetworkStates.showDialog(context);

            }
        }
    }

    public void setMainReceiver(MainReceiver mainReceiver) {
        this.mainReceiver=  mainReceiver;
    }

    public interface MainReceiver{
        void mainReceiver();
    }
}
