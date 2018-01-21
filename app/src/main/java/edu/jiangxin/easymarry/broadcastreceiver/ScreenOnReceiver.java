package edu.jiangxin.easymarry.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by jiang on 2018/1/21.
 */

public class ScreenOnReceiver extends BroadcastReceiver {
    private  static  final String TAG = "ScreenOnReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Screen On");
    }
}
