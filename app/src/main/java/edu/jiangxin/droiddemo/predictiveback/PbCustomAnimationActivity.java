package edu.jiangxin.droiddemo.predictiveback;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.window.BackEvent;
import android.window.OnBackAnimationCallback;
import android.window.OnBackInvokedDispatcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import edu.jiangxin.droiddemo.R;

public class PbCustomAnimationActivity extends AppCompatActivity {
    private static final String TAG = "PbCustomAnimationActivity";
    private ConstraintLayout rootLayout;
    private TextView textView;
    private float initialScale = 1.0f;
    
    private final OnBackAnimationCallback mAnimationCallback = new OnBackAnimationCallback() {
        @Override
        public void onBackStarted(BackEvent backEvent) {
            // 开始返回手势时的初始化
            initialScale = 1.0f;
        }

        @Override
        public void onBackProgressed(BackEvent backEvent) {
            Log.i(TAG, "onBackProgressed: " + backEvent.getProgress());
            // 根据返回手势的进度更新动画
            float progress = backEvent.getProgress();
            
            // 缩放效果
            float scale = 1.0f - (0.2f * progress);
            rootLayout.setScaleX(scale);
            rootLayout.setScaleY(scale);
            
            // 透明度效果
            float alpha = 1.0f - (0.5f * progress);
            rootLayout.setAlpha(alpha);
            
            // 旋转效果
            float rotation = 10 * progress;
            rootLayout.setRotation(rotation);
            
            // 文本动画
            textView.setText("返回进度: " + String.format("%.1f%%", progress * 100));
        }

        @Override
        public void onBackInvoked() {
            // 完成返回手势时调用
            finish();
        }

        @Override
        public void onBackCancelled() {
            // 取消返回手势时重置动画
            rootLayout.setScaleX(initialScale);
            rootLayout.setScaleY(initialScale);
            rootLayout.setAlpha(1.0f);
            rootLayout.setRotation(0);
            textView.setText("自定义返回动画效果");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pb_custom_animation);
        
        rootLayout = findViewById(R.id.rootLayout);
        textView = findViewById(R.id.textView);
        
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