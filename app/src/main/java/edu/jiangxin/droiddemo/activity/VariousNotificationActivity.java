package edu.jiangxin.droiddemo.activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;

import edu.jiangxin.droiddemo.R;

public class VariousNotificationActivity extends Activity {

    private Button mBtnNoneImportance, mBtnMinImportance, mBtnLowImportance, mBtnDefaultImportance, mBtnHighImportance;
    private Button mBtnEnableVibrate, mBtnDisableVibrate, mBtnOthers;

    private static final String IMPORTANCE_NONE = "IMPORTANCE_NONE";
    private static final String IMPORTANCE_MIN = "IMPORTANCE_MIN";
    private static final String IMPORTANCE_LOW = "IMPORTANCE_LOW";
    private static final String IMPORTANCE_DEFAULT = "IMPORTANCE_DEFAULT";
    private static final String IMPORTANCE_HIGH = "IMPORTANCE_HIGH";

    private static final String ENABLE_VIBRATE = "ENABLE_VIBRATE";
    private static final String DISABLE_VIBRATE = "DISABLE_VIBRATE";

    private static final String OTHERS = "OTHERS";

    private static final int NOTIFICATION_ID_NONE = 1001;
    private static final int NOTIFICATION_ID_MIN = 1002;
    private static final int NOTIFICATION_ID_LOW = 1003;
    private static final int NOTIFICATION_ID_DEFAULT = 1004;
    private static final int NOTIFICATION_ID_HIGH = 1005;

    private static final int NOTIFICATION_ID_ENABLE_VIBRATE = 1010;
    private static final int NOTIFICATION_ID_DISABLE_VIBRATE = 1011;

    private static final int NOTIFICATION_ID_OTHERS = 1020;

    private NotificationManager notificationManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_various_notification);
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        mBtnNoneImportance = findViewById(R.id.btnNoneImportance);
        mBtnNoneImportance.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                NotificationChannel notificationChannel = new NotificationChannel(IMPORTANCE_NONE, IMPORTANCE_NONE, NotificationManager.IMPORTANCE_NONE);
                notificationManager.createNotificationChannel(notificationChannel);
                Notification.Builder builder = new Notification.Builder(VariousNotificationActivity.this, IMPORTANCE_NONE)
                        .setContentTitle(IMPORTANCE_NONE)
                        .setContentText(IMPORTANCE_NONE)
                        .setSmallIcon(R.mipmap.ic_launcher);
                notificationManager.notify(NOTIFICATION_ID_NONE, builder.build());
            }
        });

        mBtnMinImportance = findViewById(R.id.btnMinImportance);
        mBtnMinImportance.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                NotificationChannel notificationChannel = new NotificationChannel(IMPORTANCE_MIN, IMPORTANCE_MIN, NotificationManager.IMPORTANCE_MIN);
                notificationManager.createNotificationChannel(notificationChannel);
                Notification.Builder builder = new Notification.Builder(VariousNotificationActivity.this, IMPORTANCE_MIN)
                        .setContentTitle(IMPORTANCE_MIN)
                        .setContentText(IMPORTANCE_MIN)
                        .setSmallIcon(R.mipmap.ic_launcher);
                notificationManager.notify(NOTIFICATION_ID_MIN, builder.build());
            }
        });

        mBtnLowImportance = findViewById(R.id.btnLowImportance);
        mBtnLowImportance.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                NotificationChannel notificationChannel = new NotificationChannel(IMPORTANCE_LOW, IMPORTANCE_LOW, NotificationManager.IMPORTANCE_LOW);
                notificationManager.createNotificationChannel(notificationChannel);
                Notification.Builder builder = new Notification.Builder(VariousNotificationActivity.this, IMPORTANCE_LOW)
                        .setContentTitle(IMPORTANCE_LOW)
                        .setContentText(IMPORTANCE_LOW)
                        .setSmallIcon(R.mipmap.ic_launcher);

                notificationManager.notify(NOTIFICATION_ID_LOW, builder.build());
            }
        });
        mBtnDefaultImportance = findViewById(R.id.btnDefaultImportance);
        mBtnDefaultImportance.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                NotificationChannel notificationChannel = new NotificationChannel(IMPORTANCE_DEFAULT, IMPORTANCE_DEFAULT, NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(notificationChannel);
                Notification.Builder builder = new Notification.Builder(VariousNotificationActivity.this, IMPORTANCE_DEFAULT)
                        .setContentTitle(IMPORTANCE_DEFAULT)
                        .setContentText(IMPORTANCE_DEFAULT)
                        .setSmallIcon(R.mipmap.ic_launcher);
                notificationManager.notify(NOTIFICATION_ID_DEFAULT, builder.build());
            }
        });

        mBtnHighImportance = findViewById(R.id.btnHighImportance);
        mBtnHighImportance.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                NotificationChannel notificationChannel = new NotificationChannel(IMPORTANCE_HIGH, IMPORTANCE_HIGH, NotificationManager.IMPORTANCE_HIGH);
                notificationManager.createNotificationChannel(notificationChannel);
                Notification.Builder builder = new Notification.Builder(VariousNotificationActivity.this, IMPORTANCE_HIGH)
                        .setContentTitle(IMPORTANCE_HIGH)
                        .setContentText(IMPORTANCE_HIGH)
                        .setSmallIcon(R.mipmap.ic_launcher);
                notificationManager.notify(NOTIFICATION_ID_HIGH, builder.build());
            }
        });

        mBtnEnableVibrate = findViewById(R.id.btnEnableVibrate);
        mBtnEnableVibrate.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                NotificationChannel notificationChannel = new NotificationChannel(ENABLE_VIBRATE, ENABLE_VIBRATE, NotificationManager.IMPORTANCE_HIGH);
                notificationManager.createNotificationChannel(notificationChannel);
                Notification.Builder builder = new Notification.Builder(VariousNotificationActivity.this, ENABLE_VIBRATE)
                        .setContentTitle(ENABLE_VIBRATE)
                        .setContentText(ENABLE_VIBRATE)
                        .setSmallIcon(R.mipmap.ic_launcher);
                notificationManager.notify(NOTIFICATION_ID_ENABLE_VIBRATE, builder.build());
            }
        });

        mBtnDisableVibrate = findViewById(R.id.btnDisableVibrate);
        mBtnDisableVibrate.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                NotificationChannel notificationChannel = new NotificationChannel(DISABLE_VIBRATE, DISABLE_VIBRATE, NotificationManager.IMPORTANCE_HIGH);
                notificationManager.createNotificationChannel(notificationChannel);
                Notification.Builder builder = new Notification.Builder(VariousNotificationActivity.this, DISABLE_VIBRATE)
                        .setContentTitle(DISABLE_VIBRATE)
                        .setContentText(DISABLE_VIBRATE)
                        .setSmallIcon(R.mipmap.ic_launcher);
                notificationManager.notify(NOTIFICATION_ID_DISABLE_VIBRATE, builder.build());
            }
        });

        mBtnOthers = findViewById(R.id.btnOthers);
        mBtnOthers.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                NotificationChannel notificationChannel = new NotificationChannel(OTHERS, OTHERS, NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.enableVibration(true);
                notificationChannel.setSound(Settings.System.DEFAULT_NOTIFICATION_URI, Notification.AUDIO_ATTRIBUTES_DEFAULT);
                notificationChannel.setShowBadge(false);
                notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                notificationChannel.setBypassDnd(false);
                notificationChannel.setDescription("OTHERS_DESCRIPTION");
                long[] pattern = {3000, 1000, 3000, 1000}; // {间隔时间，震动持续时间，间隔时间，震动持续时间，间隔时间，震动持续时间}
                notificationChannel.setVibrationPattern(pattern);
                notificationManager.createNotificationChannel(notificationChannel);
                Notification.Builder builder = new Notification.Builder(VariousNotificationActivity.this, OTHERS)
                        .setContentTitle(OTHERS)
                        .setContentText(OTHERS)
                        .setSmallIcon(R.mipmap.ic_launcher);

                /*PendingIntent pendingIntent1 = PendingIntent.getActivity(VariousNotificationActivity.this, 1,// requestCode是0的时候三星手机点击通知栏通知不起作用
                        new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setVisibility(Notification.VISIBILITY_PUBLIC);
                builder.setFullScreenIntent(pendingIntent1, false);*/

                notificationManager.notify(NOTIFICATION_ID_OTHERS, builder.build());
            }
        });
    }
}
