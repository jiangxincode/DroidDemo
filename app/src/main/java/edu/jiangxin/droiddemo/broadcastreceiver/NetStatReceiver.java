package edu.jiangxin.droiddemo.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by jiang on 2018/1/21.
 */

public class NetStatReceiver extends BroadcastReceiver {
    private  static  final String TAG = "NetStatReceiver";
    
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean isConnected = isNetworkConnected(cm);
        
        Log.i(TAG, "isConnected: " + isConnected);
        if (!isConnected) {
            return;
        }

        new Thread(networkTask).start();

        logNetworkType(cm);
    }
    
    private boolean isNetworkConnected(ConnectivityManager cm) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = cm.getActiveNetwork();
            NetworkCapabilities capabilities = network != null ? cm.getNetworkCapabilities(network) : null;
            return capabilities != null &&
                    capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                    capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
        } else {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
        }
    }
    
    private void logNetworkType(ConnectivityManager cm) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = cm.getActiveNetwork();
            NetworkCapabilities capabilities = network != null ? cm.getNetworkCapabilities(network) : null;
            if (capabilities == null) {
                return;
            }
            
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i(TAG, "type is: TYPE_WIFI");
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i(TAG, "type is: TYPE_MOBILE");
            }
        } else {
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info == null) {
                return;
            }
            
            switch (info.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                    Log.i(TAG, "type is: TYPE_WIFI");
                    break;
                case ConnectivityManager.TYPE_MOBILE:
                    Log.i(TAG, "type is: TYPE_MOBILE");
                    Log.i(TAG, "sub type is: " + info.getSubtype());
                    break;
                 default:
                    break;
            }
        }
    }

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Bundle data = msg.getData();
                    boolean result = data.getBoolean("result");
                    Log.i(TAG, "connected to baidu: " + result);
                    break;
                default:
                    break;
            }

        }
    };

    Runnable networkTask = new Runnable() {

        @Override
        public void run() {
            boolean result = false;
            URL url = null;
            try {
                url = new URL("http://www.baidu.com");
                HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                urlc.setConnectTimeout(3000);
                urlc.connect();
                if (urlc.getResponseCode() == 200) {
                    result = true;
                }
            } catch (MalformedURLException e) {
            } catch (IOException e) {
            }
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putBoolean("result", result);
            msg.setData(data);
            msg.what = 0;
            handler.removeMessages(0);
            handler.sendMessageDelayed(msg, 3000);
        }
    };
}
