package edu.jiangxin.droiddemo.opengl.transform;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

import edu.jiangxin.droiddemo.R;
import edu.jiangxin.droiddemo.opengl.Utils;

public class TransformActivity extends Activity {

    private GLSurfaceView mGLSurfaceViewLeft;
    private GLSurfaceView mGLSurfaceViewRight;
    private Button mBtnScale;
    private Button mBtnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transform);
        mGLSurfaceViewLeft = findViewById(R.id.glSurfaceViewLeft);
        mGLSurfaceViewRight = findViewById(R.id.glSurfaceViewRight);
        mBtnScale = findViewById(R.id.scaleAnimation);
        mBtnReset = findViewById(R.id.reset);

        mGLSurfaceViewLeft.setEGLContextClientVersion(Utils.OPENGL_ES_VERSION);
        mGLSurfaceViewLeft.setRenderer(new TransformRenderer(this, "image_0.jpg"));

        mGLSurfaceViewRight.setEGLContextClientVersion(Utils.OPENGL_ES_VERSION);
        mGLSurfaceViewRight.setRenderer(new TransformRenderer(this, "image_1.jpg"));

        mBtnScale.setOnClickListener(v -> {
            mGLSurfaceViewLeft.setPivotX(0f);
            mGLSurfaceViewLeft.setPivotY(0.5f);
            ObjectAnimator scaleLeftScaleX = ObjectAnimator.ofFloat(mGLSurfaceViewLeft, "scaleX", 1.0f, 0);

            mGLSurfaceViewRight.setPivotX(0.5f);
            mGLSurfaceViewRight.setPivotY(0.5f);
            ObjectAnimator scaleRightScaleX = ObjectAnimator.ofFloat(mGLSurfaceViewRight, "scaleX", 1.0f, 2.0f);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(2000L);
            animatorSet.setInterpolator(new LinearInterpolator());
            animatorSet.playTogether(scaleLeftScaleX, scaleRightScaleX);
            animatorSet.start();
        });

        mBtnReset.setOnClickListener(v -> {
            mGLSurfaceViewLeft.setScaleX(1.0f);
            mGLSurfaceViewRight.setScaleX(1.0f);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mGLSurfaceViewLeft.onResume();
        mGLSurfaceViewRight.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGLSurfaceViewLeft.onPause();
        mGLSurfaceViewRight.onPause();
    }
}
