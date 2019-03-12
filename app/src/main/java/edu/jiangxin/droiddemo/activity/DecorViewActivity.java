package edu.jiangxin.droiddemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.jiangxin.droiddemo.R;

public class DecorViewActivity extends Activity {

    private Button mBtn1, mBtn2, mBtn3, mBtn4, mBtn5, mBtn6, mBtn7, mBtn8;
    private View decorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decor_view);

        decorView = getWindow().getDecorView();

        //显示状态栏，Activity不全屏显示(恢复到有状态的正常情况)
        mBtn1 = findViewById(R.id.btn1);
        mBtn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        });


        //隐藏状态栏，同时Activity会伸展全屏显示
        mBtn2 = findViewById(R.id.btn2);
        mBtn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                decorView.setSystemUiVisibility(View.INVISIBLE);
            }
        });


        //Activity全屏显示，且状态栏被隐藏覆盖掉
        mBtn3 = findViewById(R.id.btn3);
        mBtn3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
            }
        });


        //Activity全屏显示，但状态栏不会被隐藏覆盖，状态栏依然可见，Activity顶端布局部分会被状态遮住
        mBtn4 = findViewById(R.id.btn4);
        mBtn4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
        });


        //同mRLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        mBtn5 = findViewById(R.id.btn5);
        mBtn5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
            }
        });


        //同mRLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        mBtn6 = findViewById(R.id.btn6);
        mBtn6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_LAYOUT_FLAGS);
            }
        });


        //隐藏虚拟按键(导航栏)
        mBtn7 = findViewById(R.id.btn7);
        mBtn7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            }
        });


        //状态栏显示处于低能显示状态(low profile模式)，状态栏上一些图标显示会被隐藏。
        mBtn8 = findViewById(R.id.btn8);
        mBtn8.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
            }
        });
    }
}
