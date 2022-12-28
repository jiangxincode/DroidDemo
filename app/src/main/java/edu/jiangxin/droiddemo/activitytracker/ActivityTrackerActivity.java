package edu.jiangxin.droiddemo.activitytracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import edu.jiangxin.droiddemo.R;

public class ActivityTrackerActivity extends Activity {
    private static final String TAG = "ActivityTrackerActivity";

    private static final int REQUEST_CODE_OVERLAY = 10002;
    private static final int REQUEST_CODE_ACCESSIBILITY = 10003;

    private Button mBtnStart;

    private Button mBtnOverlay;

    private Button mBtnAccessibility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);

        mBtnStart = findViewById(R.id.startBtn);
        mBtnStart.setOnClickListener(v -> {
            Intent service = new Intent(this, TrackerService.class);
            service.putExtra(TrackerService.KEY_ACTION, TrackerService.ACTION_OPEN);
            startService(service);
            finish();
        });

        mBtnOverlay = findViewById(R.id.overlayBtn);
        mBtnOverlay.setOnClickListener(v -> {
            startActivityForResult(
                    new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()))
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK), REQUEST_CODE_OVERLAY
            );
        });


        mBtnAccessibility = findViewById(R.id.accessibilityBtn);
        mBtnAccessibility.setOnClickListener(v -> {
            startActivityForResult(
                    new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK), REQUEST_CODE_ACCESSIBILITY
            );
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateButtonStatus();
    }

    private void updateButtonStatus() {
        boolean canDrawOverlays = Settings.canDrawOverlays(this);
        boolean isAccessibilitySettingsOn = isAccessibilitySettingsOn(this);
        mBtnOverlay.setEnabled(!canDrawOverlays);
        mBtnAccessibility.setEnabled(!isAccessibilitySettingsOn);
        mBtnStart.setEnabled(canDrawOverlays && isAccessibilitySettingsOn);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_OVERLAY) {
            if (Settings.canDrawOverlays(this)) {
                updateButtonStatus();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == REQUEST_CODE_ACCESSIBILITY) {
            if (isAccessibilitySettingsOn(this)) {
                updateButtonStatus();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    private static boolean isAccessibilitySettingsOn(Context context) {
        int accessibilityEnabled = 0;
        try {
            accessibilityEnabled = Settings.Secure.getInt(context.getContentResolver(),
                    Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            Log.i(TAG, "get setting failed: " + Settings.Secure.ACCESSIBILITY_ENABLED);
            return true;
        }

        Log.i(TAG, "accessibilityEnabled : " + accessibilityEnabled);

        if (accessibilityEnabled != 1) {
            return false;
        }

        String services = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);

        if (services == null) {
            Log.e(TAG, "services is null");
            return false;
        }
        return services.toLowerCase().contains(context.getPackageName().toLowerCase());
    }
}
