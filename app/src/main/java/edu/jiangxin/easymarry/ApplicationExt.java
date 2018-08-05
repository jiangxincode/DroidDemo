package edu.jiangxin.easymarry;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;

public class ApplicationExt extends Application {

    private static Context mContext;

    private static AppConfig appConfig;

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

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
