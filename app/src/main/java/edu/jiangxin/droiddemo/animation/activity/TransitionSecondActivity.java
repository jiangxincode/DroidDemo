package edu.jiangxin.droiddemo.animation.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import edu.jiangxin.droiddemo.R;

public class TransitionSecondActivity extends Activity {

    TextView mTargetTextView;
    ImageView mTargetImageView;
    ImageView mTargetChromeImageView;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityoptions_layout_target);
        mTargetTextView = findViewById(R.id.target_textView);
        mTargetImageView = findViewById(R.id.target_imageView);
        mTargetChromeImageView = findViewById(R.id.target_chrome_imageView);
    }


}
