package edu.jiangxin.droiddemo;

import android.app.Application;
import android.content.Context;

import com.google.android.material.color.DynamicColors;

public class ApplicationExt extends Application {

    private static Context mContext;

    private static AppConfig appConfig;

    @Override
    public void onCreate() {
        super.onCreate();
        DynamicColors.applyToActivitiesIfAvailable(this);

        mContext = getApplicationContext();
        appConfig = new AppConfig(getApplicationContext());
    }

    public static Context getContext() {
        return mContext;
    }

    public static AppConfig getAppConfig() {
        return appConfig;
    }
}
