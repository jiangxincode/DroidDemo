package edu.jiangxin.easymarry.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;

import edu.jiangxin.easymarry.R;

public class DialogActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        TextView tv = findViewById(R.id.tv_tint);
        Drawable bg = tv.getBackground();
        // 修改Drawable对象颜色
        bg.setTint(Color.BLUE);
    }
}
