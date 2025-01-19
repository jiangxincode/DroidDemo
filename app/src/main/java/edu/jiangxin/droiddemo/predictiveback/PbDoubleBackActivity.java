package edu.jiangxin.droiddemo.predictiveback;

import android.os.Bundle;
import android.widget.Toast;
import android.window.BackEvent;
import android.window.OnBackAnimationCallback;
import android.window.OnBackInvokedDispatcher;

import androidx.appcompat.app.AppCompatActivity;

import edu.jiangxin.droiddemo.R;

public class PbDoubleBackActivity extends AppCompatActivity {
    private long lastBackTime = 0;
    private static final long BACK_INTERVAL = 2000;

    private final OnBackAnimationCallback mAnimationCallback = new OnBackAnimationCallback() {
        @Override
        public void onBackInvoked() {
        }

        @Override
        public void onBackStarted(BackEvent backEvent) {
            long currentTime = System.currentTimeMillis();
            if (lastBackTime == 0 || (currentTime - lastBackTime) > BACK_INTERVAL) {
                lastBackTime = currentTime;
                Toast.makeText(PbDoubleBackActivity.this, 
                    "再次返回退出界面", Toast.LENGTH_SHORT).show();
            } else {
                finish();
            }
        }

        @Override
        public void onBackProgressed(BackEvent backEvent) {
        }

        @Override
        public void onBackCancelled() {
            lastBackTime = 0;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pb_double_back);
        getOnBackInvokedDispatcher().registerOnBackInvokedCallback(OnBackInvokedDispatcher.PRIORITY_DEFAULT, mAnimationCallback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAnimationCallback != null) {
            getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(mAnimationCallback);
        }
    }
} 