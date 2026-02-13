package edu.jiangxin.droiddemo.service.intentservice;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

@SuppressWarnings("deprecation")
public class IntentServiceImpl extends IntentService {

    private static final String TAG = "IntentServiceImpl";

    public IntentServiceImpl() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "onHandleIntent is invoked");
    }
}