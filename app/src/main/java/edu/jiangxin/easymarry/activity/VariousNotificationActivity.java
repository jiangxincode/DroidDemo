package edu.jiangxin.easymarry.activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;

import edu.jiangxin.easymarry.R;

public class VariousNotificationActivity extends Activity {

    private Button mBtn1, mBtn2, mBtn3, mBtn4, mBtn5;

    private static final String IMPORTANCE_NONE = "IMPORTANCE_NONE";
    private static final String IMPORTANCE_MIN = "IMPORTANCE_MIN";
    private static final String IMPORTANCE_LOW = "IMPORTANCE_LOW";
    private static final String IMPORTANCE_DEFAULT = "IMPORTANCE_DEFAULT";
    private static final String IMPORTANCE_HIGH = "IMPORTANCE_HIGH";

    private static final int NOTIFICATION_ID_NONE = 1001;
    private static final int NOTIFICATION_ID_MIN = 1002;
    private static final int NOTIFICATION_ID_LOW = 1003;
    private static final int NOTIFICATION_ID_DEFAULT = 1004;
    private static final int NOTIFICATION_ID_HIGH = 1005;

    private NotificationManager notificationManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_various_notification);
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        createNotificationChannel();

        mBtn1 = findViewById(R.id.btn1);
        mBtn1.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Notification.Builder builder = new Notification.Builder(VariousNotificationActivity.this)
                        .setContentTitle(IMPORTANCE_NONE)
                        .setContentText(IMPORTANCE_NONE)
                        .setChannelId(IMPORTANCE_NONE)
                        .setSmallIcon(R.mipmap.ic_launcher);

                notificationManager.notify(NOTIFICATION_ID_NONE, builder.build());
            }
        });

        mBtn2 = findViewById(R.id.btn2);
        mBtn2.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Notification.Builder builder = new Notification.Builder(VariousNotificationActivity.this)
                        .setContentTitle(IMPORTANCE_MIN)
                        .setContentText(IMPORTANCE_MIN)
                        .setChannelId(IMPORTANCE_MIN)
                        .setSmallIcon(R.mipmap.ic_launcher);

                notificationManager.notify(NOTIFICATION_ID_MIN, builder.build());
            }
        });

        mBtn3 = findViewById(R.id.btn3);
        mBtn3.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Notification.Builder builder = new Notification.Builder(VariousNotificationActivity.this)
                        .setContentTitle(IMPORTANCE_LOW)
                        .setContentText(IMPORTANCE_LOW)
                        .setChannelId(IMPORTANCE_LOW)
                        .setSmallIcon(R.mipmap.ic_launcher);

                notificationManager.notify(NOTIFICATION_ID_LOW, builder.build());
            }
        });

        mBtn4 = findViewById(R.id.btn4);
        mBtn4.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Notification.Builder builder = new Notification.Builder(VariousNotificationActivity.this)
                        .setContentTitle(IMPORTANCE_DEFAULT)
                        .setContentText(IMPORTANCE_DEFAULT)
                        .setChannelId(IMPORTANCE_DEFAULT)
                        .setSmallIcon(R.mipmap.ic_launcher);

                notificationManager.notify(NOTIFICATION_ID_DEFAULT, builder.build());
            }
        });

        mBtn5 = findViewById(R.id.btn5);
        mBtn5.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Notification.Builder builder = new Notification.Builder(VariousNotificationActivity.this)
                        .setContentTitle(IMPORTANCE_HIGH)
                        .setContentText(IMPORTANCE_HIGH)
                        .setChannelId(IMPORTANCE_HIGH)
                        .setSmallIcon(R.mipmap.ic_launcher);

                /*PendingIntent pendingIntent1 = PendingIntent.getActivity(VariousNotificationActivity.this, 1,// requestCode是0的时候三星手机点击通知栏通知不起作用
                        new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setVisibility(Notification.VISIBILITY_PUBLIC);
                builder.setFullScreenIntent(pendingIntent1, false);*/

                notificationManager.notify(NOTIFICATION_ID_HIGH, builder.build());
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        NotificationChannel notificationChannelNone = new NotificationChannel(IMPORTANCE_NONE, IMPORTANCE_NONE, NotificationManager.IMPORTANCE_NONE);
        notificationManager.createNotificationChannel(notificationChannelNone);
        NotificationChannel notificationChannelMin = new NotificationChannel(IMPORTANCE_MIN, IMPORTANCE_MIN, NotificationManager.IMPORTANCE_MIN);
        notificationManager.createNotificationChannel(notificationChannelMin);
        NotificationChannel notificationChannelLow = new NotificationChannel(IMPORTANCE_LOW, IMPORTANCE_LOW, NotificationManager.IMPORTANCE_LOW);
        notificationManager.createNotificationChannel(notificationChannelLow);
        NotificationChannel notificationChannelDefault = new NotificationChannel(IMPORTANCE_DEFAULT, IMPORTANCE_DEFAULT, NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(notificationChannelDefault);
        NotificationChannel notificationChannelHigh = new NotificationChannel(IMPORTANCE_HIGH, IMPORTANCE_HIGH, NotificationManager.IMPORTANCE_HIGH);
        notificationChannelHigh.enableVibration(true);
        notificationChannelHigh.setSound(Settings.System.DEFAULT_NOTIFICATION_URI, Notification.AUDIO_ATTRIBUTES_DEFAULT);
        /*notificationChannelHigh.setShowBadge(false);
        notificationChannelHigh.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        notificationChannelHigh.setBypassDnd(false);
        notificationChannelHigh.setDescription("IMPORTANCE_HIGH_DESCRIPTION");
        long[] pattern = {3000, 1000, 3000,1000}; // {间隔时间，震动持续时间，间隔时间，震动持续时间，间隔时间，震动持续时间}
        notificationChannelHigh.setVibrationPattern(pattern);*/


        notificationManager.createNotificationChannel(notificationChannelHigh);

    }
}
