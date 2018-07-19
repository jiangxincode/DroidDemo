package edu.jiangxin.easymarry;

import android.app.Application;
import android.content.Context;

public class ApplicationExt extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
