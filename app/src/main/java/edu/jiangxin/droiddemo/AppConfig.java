package edu.jiangxin.droiddemo;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class AppConfig {


    private final SharedPreferences innerConfig;

    private static final String KEY_NIGHT_MODE_SWITCH="night_theme";


    public AppConfig(final Context context) {
        innerConfig = context.getSharedPreferences("app_config", Application.MODE_PRIVATE);
    }

    //夜间模式
    public boolean isNighTheme() {
        return innerConfig.getBoolean(KEY_NIGHT_MODE_SWITCH, false);
    }

    public void setNightTheme(boolean on) {
        SharedPreferences.Editor editor = innerConfig.edit();
        editor.putBoolean(KEY_NIGHT_MODE_SWITCH, on);
        editor.commit();
    }

    /**
     * 清空
     */
    public void clear() {
        SharedPreferences.Editor editor = innerConfig.edit();
        editor.clear();
        editor.commit();
    }


}
