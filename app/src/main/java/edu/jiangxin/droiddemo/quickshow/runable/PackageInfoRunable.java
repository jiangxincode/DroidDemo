package edu.jiangxin.droiddemo.quickshow.runable;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

import java.util.List;

import edu.jiangxin.droiddemo.ApplicationExt;
import edu.jiangxin.droiddemo.quickshow.activity.ShowInfoActivity;

public class PackageInfoRunable implements Runnable {

    private Handler handler;

    public PackageInfoRunable(Handler mHandler) {
        this.handler = mHandler;
    }

    @Override
    public void run() {
        StringBuilder stringBuilder = new StringBuilder();
        PackageManager packageManager = ApplicationExt.getContext().getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(PackageManager.MATCH_UNINSTALLED_PACKAGES);
        for (PackageInfo packageInfo : packageInfos) {
            if (packageInfo.packageName.equals(ApplicationExt.getContext().getPackageName())) {
                ApplicationInfo applicationInfo = packageInfo.applicationInfo;
                stringBuilder.append(applicationInfo.packageName).append("\n");
                stringBuilder.append(applicationInfo.dataDir).append("\n");
                stringBuilder.append(applicationInfo.name).append("\n");
                break;
            }
        }
        stringBuilder.append("=============================================\n");
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resolveInfos) {
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            stringBuilder.append(activityInfo.name).append("\n");
        }
        stringBuilder.append("=============================================\n");

        intent = new Intent("edu.jiangxin.droiddemo.action.SHOWINFO", null);
        intent.addCategory("android.intent.category.SHOWINFO");
        ResolveInfo resolveInfo = packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        ActivityInfo activityInfo = resolveInfo.activityInfo;
        stringBuilder.append(activityInfo.name).append("\n");
        stringBuilder.append("=============================================\n");

        // difference between SystemClok#sleep and Thread#sleep
        SystemClock.sleep(1000L);

        Message message = new Message();
        message.what = ShowInfoActivity.UPDATE_MESSAGE;
        Bundle bundle = new Bundle();
        bundle.putString("title", "显示Package信息");
        bundle.putString("content", stringBuilder.toString());
        message.setData(bundle);
        handler.sendMessage(message);
    }
}
