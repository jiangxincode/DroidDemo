package edu.jiangxin.droiddemo.predictiveback;

import android.os.Bundle;
import android.util.Log;
import android.window.BackEvent;
import android.window.OnBackAnimationCallback;
import android.window.OnBackInvokedDispatcher;

import androidx.appcompat.app.AppCompatActivity;

import edu.jiangxin.droiddemo.R;

public class PbSystemNavigationObserverActivity extends AppCompatActivity {
    private static final String TAG = "PbSystemNavigationObserverActivity";

    private final OnBackAnimationCallback mAnimationCallback = new OnBackAnimationCallback() {
        @Override
        public void onBackStarted(BackEvent backEvent) {
            Log.i(TAG, "onBackStarted");
        }

        @Override
        public void onBackProgressed(BackEvent backEvent) {
            Log.i(TAG, "onBackProgressed: " + backEvent.getProgress());
        }

        @Override
        public void onBackInvoked() {
            Log.i(TAG, "onBackInvoked");
            finish();
        }

        @Override
        public void onBackCancelled() {
            Log.i(TAG, "onBackCancelled");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pb_system_navigation_observer);
        
        getOnBackInvokedDispatcher().registerOnBackInvokedCallback(
            OnBackInvokedDispatcher.PRIORITY_SYSTEM_NAVIGATION_OBSERVER, 
            mAnimationCallback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAnimationCallback != null) {
            getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(mAnimationCallback);
        }
    }
} 