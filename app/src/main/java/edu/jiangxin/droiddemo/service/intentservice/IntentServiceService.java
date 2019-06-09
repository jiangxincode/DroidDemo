package edu.jiangxin.droiddemo.service.intentservice;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class IntentServiceService extends IntentService {

    private static final String TAG = "IntentServiceService";

    public IntentServiceService() {
        super(TAG);
        //构造方法
    }

    public IntentServiceService(String name) {
        super(name);
        //构造方法
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "onHandleIntent is invoked");
    }
}