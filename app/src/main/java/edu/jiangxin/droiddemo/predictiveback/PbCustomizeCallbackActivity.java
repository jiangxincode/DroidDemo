package edu.jiangxin.droiddemo.predictiveback;

import android.os.Bundle;
import android.util.Log;
import android.window.BackEvent;
import android.window.OnBackAnimationCallback;

import androidx.appcompat.app.AppCompatActivity;

import edu.jiangxin.droiddemo.R;

public class PbCustomizeCallbackActivity extends AppCompatActivity {

    private static final String TAG = PbCustomizeCallbackActivity.class.getName();
    private final OnBackAnimationCallback mAnimationCallback = new OnBackAnimationCallback() {
        public void onBackStarted(BackEvent e) {
            Log.i(TAG, " onBackStarted");
        }

        public void onBackInvoked() {
            Log.i(TAG, " onBackInvoked");
            onBackPressed();
        }

        public void onBackCancelled() {
            Log.i(TAG, " onBackCancelled");
        }

        public void onBackProgressed(BackEvent e) {
            Log.i(TAG, " onBackProgressed");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pb_customize_callback);
        getOnBackInvokedDispatcher().registerOnBackInvokedCallback(0, this.mAnimationCallback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.mAnimationCallback != null) {
            getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(this.mAnimationCallback);
        }
    }

}