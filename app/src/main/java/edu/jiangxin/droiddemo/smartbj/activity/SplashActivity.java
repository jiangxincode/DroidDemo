package edu.jiangxin.droiddemo.smartbj.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import edu.jiangxin.droiddemo.R;
import edu.jiangxin.droiddemo.smartbj.utils.MyConstants;
import edu.jiangxin.droiddemo.smartbj.utils.SpTools;

/**
 * 智慧北京的splash界面 ：主要实现了动画的效果
 */
public class SplashActivity extends Activity {
    private ImageView iv_mainview;
    private AnimationSet mAnimationSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //去掉标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initView();
        startAnimation();
        initEvent();
    }

    private void initEvent() {
        mAnimationSet.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (SpTools.getBoolean(getApplicationContext(), MyConstants.ISSETUP, false)) {
                    Intent main = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(main);
                } else {
                    Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        });
    }

    private void startAnimation() {
        // false 代表动画集中每种动画都采用各自的动画插入器(数学函数)
        mAnimationSet = new AnimationSet(false);

        RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        rotateAnimation.setDuration(2000);
        rotateAnimation.setFillAfter(true);// 动画播放完之后，停留在当前状态

        mAnimationSet.addAnimation(rotateAnimation);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setFillAfter(true);

        mAnimationSet.addAnimation(alphaAnimation);

        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        scaleAnimation.setDuration(2000);
        scaleAnimation.setFillAfter(true);

        mAnimationSet.addAnimation(scaleAnimation);

        iv_mainview.startAnimation(mAnimationSet);
    }

    private void initView() {
        setContentView(R.layout.activity_smartbj_splash);
        iv_mainview = findViewById(R.id.iv_splash_mainview);
    }
}
