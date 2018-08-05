

package com.itheima62.smartbj.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.itheima62.smartbj.R;
import com.itheima62.smartbj.view.LeftMenuFragment;
import com.itheima62.smartbj.view.MainContentFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * @author Administrator
 * @创建时间 2015-7-4 下午2:12:43
 * @描述 智慧北京的主界面
 * 
 *     @ svn提交者：$Author: gd $ @ 提交时间: $Date: 2015-07-04 15:55:43 +0800 (Sat, 04
 *     Jul 2015) $ @ 当前版本: $Rev: 26 $
 */
public class MainActivity extends SlidingFragmentActivity
{
	private static final String	LEFT_MUNE_TAG	= "LEFT_MUNE_TAG";
	private static final String	MAIN_MUNE_TAG	= "MAIN_MUNE_TAG";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
		initView();// 初始化界面
		initData();// 初始化数据
		
	}
	
	/**
	 * @return
	 *    返回左侧菜单的fragment
	 */
	public LeftMenuFragment getLeftMenuFragment(){
		FragmentManager fragmentManager = getSupportFragmentManager();
		LeftMenuFragment leftFragment = (LeftMenuFragment) fragmentManager.findFragmentByTag(LEFT_MUNE_TAG);
		return leftFragment;
	}
	
	/**
	 * @return
	 *    返回左侧菜单的fragment
	 */
	public MainContentFragment getMainMenuFragment(){
		FragmentManager fragmentManager = getSupportFragmentManager();
		MainContentFragment leftFragment = (MainContentFragment) fragmentManager.findFragmentByTag(MAIN_MUNE_TAG);
		return leftFragment;
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		FragmentManager fragmentManager = getSupportFragmentManager();
		// 1. 获取事物
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 2. 完成替换

		// 完成左侧菜单界面的替换
		transaction.replace(R.id.fl_left_menu, new LeftMenuFragment(),
				LEFT_MUNE_TAG);

		// 完成左侧菜单界面的替换
		transaction.replace(R.id.fl_main_menu, new MainContentFragment(),
				MAIN_MUNE_TAG);

		// 3.提交事物
		transaction.commit();
	}

	private void initView() {
		// 设置主界面
		setContentView(R.layout.fragment_content_tag);

		// 设置左侧菜单界面
		setBehindContentView(R.layout.fragment_left);

		// 设置滑动模式
		SlidingMenu sm = getSlidingMenu();
		sm.setMode(SlidingMenu.LEFT);// 只设置左侧可以滑动

		// 设置滑动位置为全屏
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		// 设置主界面左侧滑动后剩余的空间位置
		sm.setBehindOffset(200);// 设置主界面剩余的位置

	}
}
