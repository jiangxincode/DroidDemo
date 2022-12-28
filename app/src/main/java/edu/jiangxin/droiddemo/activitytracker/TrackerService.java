package edu.jiangxin.droiddemo.activitytracker;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;

public class TrackerService extends AccessibilityService {
    public static final String TAG = "TrackerService";
    public static final String KEY_ACTION = "action";
    public static final String ACTION_OPEN = "action_open";
    public static final String ACTION_CLOSE = "action_close";
    private static final WindowManager.LayoutParams LAYOUT_PARAMS;

    private WindowManager mWindowManager;

    private FloatingView mFloatingView;

    static {
        LAYOUT_PARAMS = new WindowManager.LayoutParams();
        LAYOUT_PARAMS.x = 0;
        LAYOUT_PARAMS.y = 0;
        LAYOUT_PARAMS.width = WindowManager.LayoutParams.WRAP_CONTENT;
        LAYOUT_PARAMS.height = WindowManager.LayoutParams.WRAP_CONTENT;
        LAYOUT_PARAMS.gravity = Gravity.LEFT | Gravity.TOP;
        LAYOUT_PARAMS.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        LAYOUT_PARAMS.format = PixelFormat.RGBA_8888;
        LAYOUT_PARAMS.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        }
        String action = intent.getStringExtra(KEY_ACTION);
        if (action != null) {
            if (action.equals(ACTION_OPEN)) {
                addFloatingView();
            } else if (action.equals(ACTION_CLOSE)) {
                removeFloatingView();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d(TAG, "onAccessibilityEvent: " + event.getPackageName());
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {

            if (mFloatingView != null) {
                String packageName = event.getPackageName().toString();
                String className = event.getClassName().toString();
                mFloatingView.updateContent(packageName, className);
            }
        }
    }

    private void addFloatingView() {
        if (mFloatingView == null) {
            mFloatingView = new FloatingView(this);
            mFloatingView.setLayoutParams(LAYOUT_PARAMS);
            mWindowManager.addView(mFloatingView, LAYOUT_PARAMS);
        }
    }

    private void removeFloatingView() {
        if (mFloatingView != null) {
            mWindowManager.removeView(mFloatingView);
            mFloatingView = null;
        }
    }
}
