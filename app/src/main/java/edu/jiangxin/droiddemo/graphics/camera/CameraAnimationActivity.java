package edu.jiangxin.droiddemo.graphics.camera;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import edu.jiangxin.droiddemo.R;

/**
 * Android 3D旋转动画——Rotate3dAnimation: https://blog.csdn.net/chenzhiqin20/article/details/19042423
 */
public class CameraAnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_animation);
        View view = findViewById(R.id.iv);
        Rotate3DAnimation rotate3DAnimation = new Rotate3DAnimation(0,180, 0f, 0f, 0f, true);
        rotate3DAnimation.setDuration(2000);
        rotate3DAnimation.setRepeatCount(10);
        view.startAnimation(rotate3DAnimation);
    }
}