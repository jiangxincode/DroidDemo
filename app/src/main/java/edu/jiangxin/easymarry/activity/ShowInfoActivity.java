package edu.jiangxin.easymarry.activity;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Process;
import android.os.UserHandle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import edu.jiangxin.easymarry.R;
import edu.jiangxin.easymarry.view.PullDownTextView;

public class ShowInfoActivity extends AppCompatActivity {
    private static final String TAG = "ShowInfoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info);


        StringBuilder sb01 = new StringBuilder();
        TextView tv01 = findViewById(R.id.expand_text_view_title01);
        tv01.setText("显示屏幕状态");
        PullDownTextView pdtv01 = (PullDownTextView) findViewById(R.id.expand_text_view01);
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        //如果为true，则表示屏幕“亮”了，否则屏幕“暗”了
        sb01.append("isScreenOn: " + pm.isScreenOn()).append("\n");
        //如果flag为true，表示有两种状态：a、屏幕是黑的 b、目前正处于解锁状态 。
        // 如果flag为false，表示目前未锁屏
        KeyguardManager mKeyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        sb01.append("inKeyguardRestrictedInputMode: " + mKeyguardManager.inKeyguardRestrictedInputMode()).append("\n");
        pdtv01.setText(sb01.toString());

        StringBuilder sb02 = new StringBuilder();
        TextView tv02 = findViewById(R.id.expand_text_view_title02);
        tv02.setText("显示用户信息");
        PullDownTextView pdtv02 = (PullDownTextView) findViewById(R.id.expand_text_view02);
        sb02.append("Process.myUid(): " + Process.myUid()).append("\n");
        sb02.append("Process.myPid(): " + Process.myPid()).append("\n");
        sb02.append("Process.myTid(): " + Process.myTid()).append("\n");

        try {
            Method getUserId = UserHandle.class.getMethod("getUserId", int.class);
            int userUserId = (Integer) getUserId.invoke(null, Process.myUid());
            sb02.append("UserHandle.getUserId(int uid): " + userUserId).append("\n");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        pdtv02.setText(sb02.toString());

        StringBuilder sb03 = new StringBuilder();
        TextView tv03 = findViewById(R.id.expand_text_view_title03);
        tv03.setText("显示屏幕信息");
        PullDownTextView pdtv03 = (PullDownTextView) findViewById(R.id.expand_text_view03);

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);

        sb03.append("Screen width(px): " + metric.widthPixels).append("\n"); // 屏幕宽度（像素）
        sb03.append("Screen height(px): " + metric.heightPixels).append("\n"); // 屏幕高度（像素）
        sb03.append("Screen density: " + metric.density).append("\n"); // 屏幕密度（0.75 / 1.0 / 1.5）
        sb03.append("Screen densityDpi(dpi): " + metric.densityDpi).append("\n"); // 屏幕密度DPI（120 / 160 / 240）
        sb03.append("Screen width(inch): " + (float) metric.widthPixels / metric.densityDpi).append("\n");
        sb03.append("Screen height(inch): " + (float) metric.heightPixels / metric.densityDpi).append("\n");

        pdtv03.setText(sb03.toString());
    }
}
