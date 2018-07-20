package edu.jiangxin.easymarry.activity;

import android.app.Activity;
import android.app.usage.UsageEvents;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import edu.jiangxin.easymarry.ApplicationExt;
import edu.jiangxin.easymarry.R;

public class ThemeActivity extends Activity {

    private boolean isNight = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(ApplicationExt.getAppConfig().isNighTheme()){
            this.setTheme(R.style.NightTheme);
            isNight =  true;
        }else{
            this.setTheme(R.style.DayTheme);
            isNight = false;
        }
        setContentView(R.layout.activity_theme);

    }


    public void changeTheme(View view) {

        Toast.makeText(this,"正在切换主题",Toast.LENGTH_SHORT).show();

        if(isNight){
            ApplicationExt.getAppConfig().setNightTheme(false);
        }else{
            ApplicationExt.getAppConfig().setNightTheme(true);
        }

        Intent intent = getIntent();
        overridePendingTransition(0, R.anim.out_anim);
        finish();
        overridePendingTransition(R.anim.in_anim, 0);
        startActivity(intent);

    }
}
