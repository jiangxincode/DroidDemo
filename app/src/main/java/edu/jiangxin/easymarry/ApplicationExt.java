package edu.jiangxin.easymarry;

import android.app.Application;
import android.content.Context;

public class ApplicationExt extends Application {

    private static Context mContext;

    private static AppConfig appConfig;

    @Override
    public void onCreate() {
        super.onCreate();
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
