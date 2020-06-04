package edu.jiangxin.droiddemo.quickshow.runable;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.text.format.Formatter;

import edu.jiangxin.droiddemo.ApplicationExt;
import edu.jiangxin.droiddemo.quickshow.activity.ShowInfoActivity;

public class StorageInfoRunnable implements Runnable {

    private Handler handler;

    public StorageInfoRunnable(Handler mHandler) {
        this.handler = mHandler;
    }

    @Override
    public void run() {
        StringBuilder stringBuilder = new StringBuilder();

        ActivityManager activityManager = (ActivityManager) ApplicationExt.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        stringBuilder.append("Memory Total: ").append(Formatter.formatFileSize(ApplicationExt.getContext(), memoryInfo.totalMem)).append("\n");
        stringBuilder.append("Memory Available: ").append(Formatter.formatFileSize(ApplicationExt.getContext(), memoryInfo.availMem)).append("\n");

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            StatFs exteranlStorageStatFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            //Android API18之前：exteranlStorageStatFs.getAvailableBlocks()*exteranlStorageStatFs.getBlockSize()
            stringBuilder.append("External Storage Total Size: ").append(Formatter.formatFileSize(ApplicationExt.getContext(), (exteranlStorageStatFs.getTotalBytes()))).append("\n");
            stringBuilder.append("External Storage Available Size: ").append(Formatter.formatFileSize(ApplicationExt.getContext(), (exteranlStorageStatFs.getAvailableBytes()))).append("\n");
        } else {
            stringBuilder.append("No External Storage").append("\n");
        }

        stringBuilder.append("DIRECTORY_PICTURES: ").append(ApplicationExt.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)).append("\n");
        stringBuilder.append("DIRECTORY_DCIM: ").append(ApplicationExt.getContext().getExternalFilesDir(Environment.DIRECTORY_DCIM)).append("\n");
        stringBuilder.append("DIRECTORY_DOWNLOADS: ").append(ApplicationExt.getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)).append("\n");
        stringBuilder.append("DIRECTORY_MUSIC: ").append(ApplicationExt.getContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC)).append("\n");
        stringBuilder.append("DIRECTORY_MOVIES: ").append(ApplicationExt.getContext().getExternalFilesDir(Environment.DIRECTORY_MOVIES)).append("\n");

        Message message = new Message();
        message.what = ShowInfoActivity.UPDATE_MESSAGE;
        Bundle bundle = new Bundle();
        bundle.putString("title", "显示存储信息");
        bundle.putString("content", stringBuilder.toString());
        message.setData(bundle);
        handler.sendMessage(message);
    }
}
