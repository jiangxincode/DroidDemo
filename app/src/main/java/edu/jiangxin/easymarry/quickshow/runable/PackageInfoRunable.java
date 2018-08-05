package edu.jiangxin.easymarry.quickshow.runable;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

import java.util.List;

import edu.jiangxin.easymarry.ApplicationExt;
import edu.jiangxin.easymarry.quickshow.activity.ShowInfoActivity;

public class PackageInfoRunable implements Runnable {

    private Handler handler;

    public PackageInfoRunable(Handler mHandler) {
        this.handler = mHandler;
    }

    @Override
    public void run() {
        StringBuilder stringBuilder = new StringBuilder();
        PackageManager packageManager = ApplicationExt.getContext().getPackageManager();
        List<android.content.pm.PackageInfo> packageInfos = packageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        android.content.pm.PackageInfo packageInfo = packageInfos.get(0);
        ApplicationInfo applicationInfo = packageInfo.applicationInfo;
        stringBuilder.append(applicationInfo.packageName).append("\n");
        stringBuilder.append(applicationInfo.dataDir).append("\n");
        stringBuilder.append(applicationInfo.name).append("\n");
        Intent startIntent = new Intent(Intent.ACTION_MAIN, null);
        startIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(startIntent, 0);
        ResolveInfo resolveInfo = resolveInfos.get(0);
        ActivityInfo activityInfo = resolveInfo.activityInfo;

        // difference between SystemClok#sleep and Thread#sleep
        SystemClock.sleep(6000l);

        Message message = new Message();
        message.what = ShowInfoActivity.UPDATE_MESSAGE;
        Bundle bundle = new Bundle();
        bundle.putString("title", "显示Package信息");
        bundle.putString("content", stringBuilder.toString());
        message.setData(bundle);
        handler.sendMessage(message);
    }
}