package edu.jiangxin.easymarry.runable;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;

import edu.jiangxin.easymarry.activity.ShowInfoActivity;

public class ScreenInfoRunnable implements Runnable {

    private Activity activity;

    private Handler handler;

    public ScreenInfoRunnable(Handler mHandler, Activity activity) {
        this.handler = mHandler;
        this.activity = activity;
    }

    @Override
    public void run() {
        StringBuilder stringBuilder = new StringBuilder();
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        stringBuilder.append("Screen width(px): " + metric.widthPixels).append("\n"); // 屏幕宽度（像素）
        stringBuilder.append("Screen height(px): " + metric.heightPixels).append("\n"); // 屏幕高度（像素）
        stringBuilder.append("Screen density: " + metric.density).append("\n"); // 屏幕密度（0.75 / 1.0 / 1.5）
        stringBuilder.append("Screen densityDpi(dpi): " + metric.densityDpi).append("\n"); // 屏幕密度DPI（120 / 160 / 240）
        stringBuilder.append("Screen width(inch): " + (float) metric.widthPixels / metric.densityDpi).append("\n");
        stringBuilder.append("Screen height(inch): " + (float) metric.heightPixels / metric.densityDpi).append("\n");
        stringBuilder.append("Screen width(dp): " + (float) metric.widthPixels / metric.density).append("\n");
        stringBuilder.append("Screen height(dp): " + (float) metric.heightPixels / metric.density).append("\n");

        Message message = new Message();
        message.what = ShowInfoActivity.UPDATE_MESSAGE;
        Bundle bundle = new Bundle();
        bundle.putString("title", "显示屏幕信息");
        bundle.putString("content", stringBuilder.toString());
        message.setData(bundle);
        handler.sendMessage(message);
    }
}
