package edu.jiangxin.droiddemo.usagestats;

import androidx.appcompat.app.AppCompatActivity;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import edu.jiangxin.droiddemo.R;

public class UsageStatsActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usage_stats);
        mTextView = findViewById(R.id.usage_stats);
    }

    private boolean hasUsageAccessPermission() {
        UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        if (usageStatsManager == null) {
            return false;
        }
        long currentTime = System.currentTimeMillis();
        List<UsageStats> stats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, currentTime - 60 * 1000, currentTime);
        return stats.size() != 0;
    }

    private void requestUsageAccessPermission() {
        Intent intent = new Intent(android.provider.Settings.ACTION_USAGE_ACCESS_SETTINGS);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!hasUsageAccessPermission()) {
            requestUsageAccessPermission();
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            Calendar calendar = Calendar.getInstance();
            long endTime = calendar.getTimeInMillis();//结束时间
            calendar.add(Calendar.DAY_OF_MONTH, -1);//时间间隔为一个月
            long startTime = calendar.getTimeInMillis();//开始时间
            UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
            List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_MONTHLY, startTime, endTime);

            if (queryUsageStats != null) {
                for (UsageStats usageStats : queryUsageStats) {
                    stringBuilder.append(usageStats.getPackageName()).append(":").append(DateFormat.format("yyyy-MM-dd kk:mm", usageStats.getLastTimeUsed())).append("\n");
                }
            }
            mTextView.setText(stringBuilder.toString());
        }
    }
}