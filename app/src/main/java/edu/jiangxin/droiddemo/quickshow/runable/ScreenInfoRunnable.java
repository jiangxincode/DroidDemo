package edu.jiangxin.droiddemo.quickshow.runable;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import edu.jiangxin.droiddemo.ApplicationExt;
import edu.jiangxin.droiddemo.quickshow.activity.ShowInfoActivity;

public class ScreenInfoRunnable implements Runnable {

    private Handler handler;

    public ScreenInfoRunnable(Handler mHandler) {
        this.handler = mHandler;
    }

    @Override
    public void run() {
        StringBuilder stringBuilder = new StringBuilder();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager)ApplicationExt.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        // 不同方式获取的WindowManager实例则displayMetrics有所不同。
        // 使用Activity获取的WindowManager则此处的displayMetrics对应的大小在多窗口情况下会小于屏幕大小
        // 详见Display#getMetrics
        display.getMetrics(displayMetrics);

        // Display#getMetrics获取的尺寸可能会小于实际尺寸，如果要获得真实尺寸需要使用Display#getRealMetrics
        // Display#getSize类似于Display#getMetrics，但是只有宽、高对应的像素数
        // Display#getRealSize类似于Display#getRealMetrics，但是只有实际宽、高对应的像素数

        stringBuilder.append("Screen width(px): " + displayMetrics.widthPixels).append("\n");
        stringBuilder.append("Screen height(px): " + displayMetrics.heightPixels).append("\n");
        stringBuilder.append("Screen density: " + displayMetrics.density).append("\n");
        stringBuilder.append("Screen densityDpi(dpi): " + displayMetrics.densityDpi).append("\n");
        stringBuilder.append("Screen width(inch): " + (float) displayMetrics.widthPixels / displayMetrics.densityDpi).append("\n");
        stringBuilder.append("Screen height(inch): " + (float) displayMetrics.heightPixels / displayMetrics.densityDpi).append("\n");
        stringBuilder.append("Screen width(dp): " + (float) displayMetrics.widthPixels / displayMetrics.density).append("\n");
        stringBuilder.append("Screen height(dp): " + (float) displayMetrics.heightPixels / displayMetrics.density).append("\n");

        Message message = new Message();
        message.what = ShowInfoActivity.UPDATE_MESSAGE;
        Bundle bundle = new Bundle();
        bundle.putString("title", "显示屏幕信息");
        bundle.putString("content", stringBuilder.toString());
        message.setData(bundle);
        handler.sendMessage(message);
    }
}
