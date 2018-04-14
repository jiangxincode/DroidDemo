package edu.jiangxin.easymarry.activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;

import edu.jiangxin.easymarry.R;

import static android.app.Notification.VISIBILITY_PUBLIC;

public class VariousNotificationActivity extends Activity {

    private Button mBtn1, mBtn2;

    private static final String channelIdCommon = "1000";
    private static final String channelNameCommon = "普通通知";
    private static final int notificationIdOfCommonChannel = Integer.parseInt(channelIdCommon) + 1;

    private static final String channelIdSpecial = "2000";
    private static final String channelNameSpecial = "特殊通知";
    private static final int notificationIdOfSpecialChannel = Integer.parseInt(channelIdSpecial) + 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_various_notification);

        mBtn1 = findViewById(R.id.btn1);
        mBtn1.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                NotificationChannel chan = new NotificationChannel(channelIdCommon, channelNameCommon, NotificationManager.IMPORTANCE_DEFAULT);
                chan.enableVibration(true);
                chan.setLockscreenVisibility(VISIBILITY_PUBLIC);

                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.createNotificationChannel(chan);

                Notification.Builder builder = new Notification.Builder(VariousNotificationActivity.this)
                        .setContentTitle("Attention!")
                        .setContentText("This is an urgent notification message")
                        .setChannelId(channelIdCommon)
                        .setSmallIcon(R.mipmap.ic_launcher);

                notificationManager.notify(notificationIdOfCommonChannel, builder.build());
            }
        });

        mBtn2 = findViewById(R.id.btn2);
        mBtn2.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                NotificationChannel chan = new NotificationChannel(channelIdSpecial, channelNameSpecial, NotificationManager.IMPORTANCE_DEFAULT);
                chan.enableVibration(true);
                chan.setLockscreenVisibility(VISIBILITY_PUBLIC);

                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.createNotificationChannel(chan);

                Notification.Builder builder = new Notification.Builder(VariousNotificationActivity.this)
                        .setContentTitle("Attention!")
                        .setContentText("This is an urgent notification message")
                        .setChannelId(channelIdSpecial)
                        .setSmallIcon(R.mipmap.ic_launcher);

                notificationManager.notify(notificationIdOfSpecialChannel, builder.build());
            }
        });
    }
}
