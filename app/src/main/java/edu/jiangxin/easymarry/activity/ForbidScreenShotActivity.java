package edu.jiangxin.easymarry.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager.LayoutParams;

import edu.jiangxin.easymarry.R;

public class ForbidScreenShotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //阻止屏幕截图
        //在Recent apps(任务切换界面)中只显示应用名字和图标, 不显示内容
        //Google App的Now on tap功能不会去分析你的页面的内容
        getWindow().setFlags(LayoutParams.FLAG_SECURE, LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_forbid_screen_shot);
    }
}
