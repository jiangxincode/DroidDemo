package edu.jiangxin.easymarry.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.IntRange;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import edu.jiangxin.easymarry.R;

public class BlurActivity extends Activity {

    private RenderScript renderScript;

    private ImageView imageView;
    private SeekBar seekBar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        renderScript = RenderScript.create(BlurActivity.this);

        setContentView(R.layout.activity_blur);

        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(25);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int radius = seekBar.getProgress();
                if (radius < 1) {
                    radius = 1;
                }
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.blur_picture);
                imageView.setImageBitmap(gaussianBlur(radius, bitmap));

                // 如果需要对整个layout进行高斯模糊，可能会用到Layout类中的setDrawingCacheEnabled和setDrawingCacheQuality方法：
            }
        });

    }

    public Bitmap gaussianBlur(@IntRange(from = 1, to = 25) int radius, Bitmap original) {
        Allocation input = Allocation.createFromBitmap(renderScript, original);
        Allocation output = Allocation.createTyped(renderScript, input.getType());
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        scriptIntrinsicBlur.setRadius(radius);
        scriptIntrinsicBlur.setInput(input);
        scriptIntrinsicBlur.forEach(output);
        output.copyTo(original);
        return original;
    }
}
