package edu.jiangxin.easymarry.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import edu.jiangxin.easymarry.R;

/**
 * Setting sound of ringtone/alarm/notification
 */
public class RingtoneSettingActivity extends Activity {
    private static final String TAG = "RingtoneSettingActivity";

    private static final int REQUEST_CODE_PERMISSIONS = 1;

    private Button mButtonRingtone;
    private Button mButtonAlarm;
    private Button mButtonNotification;

    public static final int REQUEST_CODE_RINGSTONE = 0;
    public static final int REQUEST_CODE_ALARM = 1;
    public static final int REQUEST_CODE_NOTIFICATION = 2;

    private static final String systemRingtonePath = "/system/media/audio/ringtones"; //system
    private static final String userRingtonePath = "/sdcard/music/ringtones"; //user-defined

    private static final String systemAlarmPath = "/system/media/audio/alarms"; //system
    private static final String userAlarmPath = "/sdcard/music/alarms"; //user-defined

    private static final String systemNotificationPath = "/system/media/audio/notifications"; //system
    private static final String userNotificationPath = "/sdcard/music/notifications"; //user-defined

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ringtone_setting);
        checkWriteSettingPermission();
        mButtonRingtone = (Button) findViewById(R.id.buttonRingtone);
        mButtonAlarm = (Button) findViewById(R.id.buttonAlarm);
        mButtonNotification = (Button) findViewById(R.id.buttonNotification);

        mButtonRingtone.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (exist(systemRingtonePath) || exist(userRingtonePath)) {
                    Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_RINGTONE);
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "设置来电铃声");
                    startActivityForResult(intent, REQUEST_CODE_RINGSTONE);
                }
            }
        });
        mButtonAlarm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (exist(systemAlarmPath) || exist(userAlarmPath)) {
                    Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "设置闹钟铃声");
                    startActivityForResult(intent, REQUEST_CODE_ALARM);
                }
            }
        });
        mButtonNotification.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (exist(systemNotificationPath) || exist(userNotificationPath)) {
                    Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "设置通知铃声");
                    startActivityForResult(intent, REQUEST_CODE_NOTIFICATION);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        // get picked ringtone uri
        Uri pickedUri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
        if (pickedUri != null) {
            try {
                switch (requestCode) {
                    case REQUEST_CODE_RINGSTONE:
                        RingtoneManager.setActualDefaultRingtoneUri(this,
                                RingtoneManager.TYPE_RINGTONE, pickedUri);
                        break;
                    case REQUEST_CODE_ALARM:
                        RingtoneManager.setActualDefaultRingtoneUri(this,
                                RingtoneManager.TYPE_ALARM, pickedUri);
                        break;
                    case REQUEST_CODE_NOTIFICATION:
                        RingtoneManager.setActualDefaultRingtoneUri(this,
                                RingtoneManager.TYPE_NOTIFICATION, pickedUri);
                        break;
                }
            } catch (SecurityException ex) {
                Toast toast = Toast.makeText(this, ex.getLocalizedMessage(), Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    private boolean exist(String path) {
        File file = new File(path);
        if (file.exists()) {
            return true;
        }
        return false;
    }

    private void checkWriteSettingPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 判断是否有WRITE_SETTINGS权限if(!Settings.System.canWrite(this))
            if (!Settings.System.canWrite(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, REQUEST_CODE_PERMISSIONS);
            }
        }
    }


}