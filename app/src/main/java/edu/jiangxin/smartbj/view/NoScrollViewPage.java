package edu.jiangxin.smartbj.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 不能滑动,懒加载
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
