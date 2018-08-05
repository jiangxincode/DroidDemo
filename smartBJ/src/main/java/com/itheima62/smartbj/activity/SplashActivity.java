

package com.itheima62.smartbj.activity;

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

import com.itheima62.smartbj.R;
import com.itheima62.smartbj.utils.MyConstants;
import com.itheima62.smartbj.utils.SpTools;

/**
 * @author Administrator
 * @创建时间 2015-7-4 上午10:20:29
 * @描述 智慧北京的splash界面 ：主要实现了动画的效果
 * 
 *     @ svn提交者：$Author: gd $ @ 提交时间: $Date: 2015-07-04 14:16:48 +0800 (Sat, 04 Jul 2015) $ @ 当前版本: $Rev: 7 $
 */
public class SplashActivity extends Activity 
{
	private ImageView		iv_mainview;
	private AnimationSet	as;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//去掉标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		initView();// 初始化界面

		startAnimation();// 开始播放动画

		initEvent();// 初始化事件
	}

	private void initEvent() {
		// 1.监听动画播完的事件, 只是一处用的的事件，匿名类对象, 多处声明成成员变量
		as.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				//监听动画播完
				// 2.判断进入向导界面还是主界面
				if (SpTools.getBoolean(getApplicationContext(), MyConstants.ISSETUP, false)){
					//true，设置过 ，直接进入主界面
					//进入主界面
					Intent main = new Intent(SplashActivity.this,MainActivity.class);
					startActivity(main);//启动主界面
				} else {
					//false 没设置过，进入设置向导界面

					Intent intent = new Intent(SplashActivity.this,GuideActivity.class);
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
		as = new AnimationSet(false);

		// 旋转动画,锚点
		RotateAnimation ra = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);// 设置锚点为图片的中心点
		// 设置动画播放时间
		ra.setDuration(2000);
		ra.setFillAfter(true);// 动画播放完之后，停留在当前状态

		// 添加到动画集
		as.addAnimation(ra);

		// 渐变动画
		AlphaAnimation aa = new AlphaAnimation(0, 1);// 由完全透明到不透明
		// 设置动画播放时间
		aa.setDuration(2000);
		aa.setFillAfter(true);// 动画播放完之后，停留在当前状态

		// 添加到动画集
		as.addAnimation(aa);

		// 缩放动画

		ScaleAnimation sa = new ScaleAnimation(0, 1, 0, 1,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		// 设置动画播放时间
		sa.setDuration(2000);
		sa.setFillAfter(true);// 动画播放完之后，停留在当前状态

		// 添加到动画集
		as.addAnimation(sa);

		// 播放动画
		iv_mainview.startAnimation(as);

		// 动画播完进入下一个界面 向导界面和主界面

		

	}

	private void initView() {
		// 设置主界面
		setContentView(R.layout.activity_splash);

		// 获取背景图片
		iv_mainview = (ImageView) findViewById(R.id.iv_splash_mainview);

	}
}
