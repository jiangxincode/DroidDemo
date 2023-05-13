package edu.jiangxin.smartbj.activity;

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

import edu.jiangxin.smartbj.R;
import edu.jiangxin.smartbj.utils.MyConstants;
import edu.jiangxin.smartbj.utils.SpTools;

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
        initView();// 初始化界面
        startAnimation();// 开始播放动画
        initEvent();// 初始化事件
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
                // 2.判断进入向导界面还是主界面
                if (SpTools.getBoolean(getApplicationContext(), MyConstants.ISSETUP, false)) {
                    //true，设置过 ，直接进入主界面
                    Intent main = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(main);//启动主界面
                } else {
                    //false 没设置过，进入设置向导界面
                    Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
                    startActivity(intent);//启动设置向导界面
                }
                //关闭自己
                finish();
            }
        });
    }

    /**
     * 开始播放动画：旋转，缩放，渐变
     */
    private void startAnimation() {
        // false 代表动画集中每种动画都采用各自的动画插入器(数学函数)
        mAnimationSet = new AnimationSet(false);

        // 旋转动画,锚点
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);// 设置锚点为图片的中心点
        rotateAnimation.setDuration(2000);
        rotateAnimation.setFillAfter(true);// 动画播放完之后，停留在当前状态

        mAnimationSet.addAnimation(rotateAnimation);

        // 渐变动画
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);// 由完全透明到不透明
        alphaAnimation.setDuration(2000);
        alphaAnimation.setFillAfter(true);// 动画播放完之后，停留在当前状态

        mAnimationSet.addAnimation(alphaAnimation);

        // 缩放动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        scaleAnimation.setDuration(2000);
        scaleAnimation.setFillAfter(true);// 动画播放完之后，停留在当前状态

        mAnimationSet.addAnimation(scaleAnimation);

        // 播放动画
        iv_mainview.startAnimation(mAnimationSet);
    }

    private void initView() {
        // 设置主界面
        setContentView(R.layout.activity_splash);
        // 获取背景图片
        iv_mainview = (ImageView) findViewById(R.id.iv_splash_mainview);
    }
}
