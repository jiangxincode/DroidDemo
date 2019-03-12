package edu.jiangxin.droiddemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import edu.jiangxin.droiddemo.ApplicationExt;
import edu.jiangxin.droiddemo.R;

public class ThemeActivity extends Activity {

    private boolean isNight = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ApplicationExt.getAppConfig().isNighTheme()) {
            this.setTheme(R.style.NightTheme);
            isNight = true;
        } else {
            this.setTheme(R.style.DayTheme);
            isNight = false;
        }
        setContentView(R.layout.activity_theme);

    }


    public void changeTheme(View view) {

        Toast.makeText(this, "正在切换主题", Toast.LENGTH_SHORT).show();

        if (isNight) {
            ApplicationExt.getAppConfig().setNightTheme(false);
        } else {
            ApplicationExt.getAppConfig().setNightTheme(true);
        }

        Intent intent = getIntent();
        overridePendingTransition(0, R.anim.out_anim);
        finish();
        overridePendingTransition(R.anim.in_anim, 0);
        startActivity(intent);

    }
}
