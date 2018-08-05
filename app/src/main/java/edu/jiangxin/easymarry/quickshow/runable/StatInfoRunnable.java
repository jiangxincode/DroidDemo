package edu.jiangxin.easymarry.quickshow.runable;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.List;

import edu.jiangxin.easymarry.ApplicationExt;
import edu.jiangxin.easymarry.quickshow.activity.ShowInfoActivity;

public class StatInfoRunnable implements Runnable {

    private Handler handler;

    public StatInfoRunnable(Handler mHandler) {
        this.handler = mHandler;
    }

    @Override
    public void run() {
        // 设置->安全->有权查看使用情况的应用 设置权限
        StringBuilder stringBuilder = new StringBuilder();
        Calendar calendar = Calendar.getInstance();
        long endTime = calendar.getTimeInMillis();//结束时间
        calendar.add(Calendar.DAY_OF_MONTH, -1);//时间间隔为一个月
        long startTiem = calendar.getTimeInMillis();//开始时间
        UsageStatsManager usageStatsManager = (UsageStatsManager) ApplicationExt.getContext().getSystemService(Context.USAGE_STATS_SERVICE);
        // 获取一个月内的信息
        List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_MONTHLY, startTiem, endTime);

        if (queryUsageStats != null) {
            for (UsageStats usageStats : queryUsageStats) {
                stringBuilder.append(usageStats.getPackageName()).append(":").append(DateFormat.format("yyyy-MM-dd kk:mm", usageStats.getLastTimeUsed())).append("\n");
            }
        }

        Message message = new Message();
        message.what = ShowInfoActivity.UPDATE_MESSAGE;
        Bundle bundle = new Bundle();
        bundle.putString("title", "显示统计信息");
        bundle.putString("content", stringBuilder.toString());
        message.setData(bundle);
        handler.sendMessage(message);
    }
}
