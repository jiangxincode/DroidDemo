package edu.jiangxin.droiddemo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.core.widget.TextViewCompat;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import edu.jiangxin.droiddemo.R;
import edu.jiangxin.droiddemo.Utils;

/**
 * 想对TextView的Autosizeing属性有更多了解建议阅读：
 * 文字太多？控件太小？试试 TextView 的新特性 Autosizeing 吧！https://www.cnblogs.com/plokmju/p/8268005.html
 */
public class ScaleTextActivity extends Activity {

    private TextView tv_scale;
    private SeekBar sb_width;
    private SeekBar sb_height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale_text);

        tv_scale = findViewById(R.id.tv_scale);
        sb_width = findViewById(R.id.sb_width);
        sb_height = findViewById(R.id.sb_height);

        TextViewCompat.setAutoSizeTextTypeWithDefaults(tv_scale, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tv_scale,
                12, 48, 2, TypedValue.COMPLEX_UNIT_SP);

        int[] autoTextSize = getResources().getIntArray(R.array.autosize_text_sizes);
        TextViewCompat.setAutoSizeTextTypeUniformWithPresetSizes(tv_scale, autoTextSize, TypedValue.COMPLEX_UNIT_SP);

        tv_scale.setText("Hello Android!");
        final int defaultWidth = 100;
        final int defaultHeight = 20;
        /**
         * height min 20 (+ 0~100)
         * width min 100 (+ 0~200)
         */
        sb_width.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ViewGroup.LayoutParams layoutParams = tv_scale.getLayoutParams();
                layoutParams.width = Utils.sp2px(ScaleTextActivity.this, progress + defaultWidth);
                tv_scale.setLayoutParams(layoutParams);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sb_height.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ViewGroup.LayoutParams layoutParams = tv_scale.getLayoutParams();
                layoutParams.height = Utils.sp2px(ScaleTextActivity.this, defaultHeight + progress);
                tv_scale.setLayoutParams(layoutParams);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}