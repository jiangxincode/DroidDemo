package edu.jiangxin.droiddemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.IntRange;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;

import edu.jiangxin.droiddemo.R;

public class BlurActivity extends Activity {

    private static final String TAG = "BlurActivity";

    private static final int REQUEST_CODE_GET_IMAGE = 1001;

    private RenderScript renderScript;

    private ImageView imageView;

    private SeekBar seekBar;

    private TextView textView;

    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
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
                imageView.setImageBitmap(gaussianBlur(radius, bitmap));

                // 如果需要对整个layout进行高斯模糊，可能会用到Layout类中的setDrawingCacheEnabled和setDrawingCacheQuality方法：
            }
        });

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_GET_IMAGE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i(TAG, "onConfigurationChanged");
    }

    //Activity生命周期中，onStart, onResume, onCreate都不是真正visible的时间点，真正的visible时间点是onWindowFocusChanged
    //从onWindowFocusChanged被执行起，用户可以与应用进行交互了，而这之前，对用户的操作需要做一点限制
    //使用一个view的getWidth/getHeight方法来获取该view的宽和高，返回的值为0
    //如果这个view的长宽很确定不为0的话，很可能是过早的调用这些方法，也就是说在这个view被加入到rootview之前你就调用了这些方法，返回的值自然为0
    //解决该问题的方法有很多，主要就是延后调用这些方法。可以试着在onWindowFocusChanged里面调用这些方法
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.i(TAG, "onWindowFocusChanged");
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.i(TAG, "onAttachedToWindow");
    }

    //在三星，OPPO等机型上，当用户触摸“返回键“时没有进入重写的onBackPressed方法
    //可以通过覆写dispatchKeyEvent方法，从该方法中拦截返回键再做处理
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i(TAG, "onBackPressed");
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Log.i(TAG, "dispatchKeyEvent: " + event.toString());
        return super.dispatchKeyEvent(event);
    }

    private Bitmap gaussianBlur(@IntRange(from = 1, to = 25) int radius, Bitmap original) {
        Bitmap.Config config = original.getConfig() != null ? original.getConfig() : Bitmap.Config.ARGB_8888;
        Bitmap result = original.copy(config, false);
        Allocation input = Allocation.createFromBitmap(renderScript, original);
        Allocation output = Allocation.createTyped(renderScript, input.getType());
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        scriptIntrinsicBlur.setRadius(radius);
        scriptIntrinsicBlur.setInput(input);
        scriptIntrinsicBlur.forEach(output);
        output.copyTo(result);
        return result;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        if (requestCode == REQUEST_CODE_GET_IMAGE && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                Uri uri = resultData.getData();
                ParcelFileDescriptor parcelFileDescriptor = null;
                try {
                    parcelFileDescriptor = getContentResolver()
                            .openFileDescriptor(uri, "r");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
