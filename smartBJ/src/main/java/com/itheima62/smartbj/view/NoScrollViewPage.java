package com.itheima62.smartbj.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author Administrator
 * @创建时间 2015-7-6 上午8:52:25
 * @描述 不能滑动,懒加载
 *
 * @ svn提交者：$Author: gd $
 * @ 提交时间: $Date: 2015-07-06 10:17:14 +0800 (Mon, 06 Jul 2015) $
 * @ 当前版本: $Rev: 24 $
 */
public class NoScrollViewPage extends MyViewPager
{

	public NoScrollViewPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public NoScrollViewPage(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 不让自己拦截
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return false;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return false;
	}

}
