package edu.jiangxin.droiddemo.animation;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import edu.jiangxin.droiddemo.R;

public class FrameAnimationActivity extends Activity {

    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_animation);
        LinearLayout linearLayout = findViewById(R.id.linearLayout); //获取布局管理器
        //获取AnimationDrawable对象
        final AnimationDrawable anim = (AnimationDrawable) linearLayout.getBackground();
        linearLayout.setOnClickListener(new View.OnClickListener() {  //为布局管理器添加单击事件
            @Override
            public void onClick(View v) {
                if (flag) {
                    anim.start(); //开始播放动画
                    flag = false;
                } else {
                    anim.stop();  //停止播放动画
                    flag = true;
                }
            }
        });
    }
}