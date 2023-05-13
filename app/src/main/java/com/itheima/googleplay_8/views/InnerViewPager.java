package com.itheima.googleplay_8.views;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author  Administrator
 * @time 	2015-7-18 上午10:01:07
 * @des	TODO
 *
 * @version $Rev: 30 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2015-07-18 11:15:47 +0800 (星期六, 18 七月 2015) $
 * @updateDes TODO
 */
public class InnerViewPager extends ViewPager {

	private float	mDownX;
	private float	mDownY;
	private float	mMoveX;
	private float	mMoveY;

	public InnerViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public InnerViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO
		// 左右滑动-->自己处理
		// 上下滑动-->父亲处理
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mDownX = ev.getRawX();
			mDownY = ev.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			mMoveX = ev.getRawX();
			mMoveY = ev.getRawY();

			int diffX = (int) (mMoveX - mDownX);
			int diffY = (int) (mMoveY - mDownY);
			// 左右滚动的绝对值 > 上下滚动的绝对值
			if (Math.abs(diffX) > Math.abs(diffY)) {// 左右
				// 左右滑动-->自己处理
				// getParent()(父亲).request(请求)Disallow(不)Intercept(拦截)TouchEvent(touch事件)(true(同意));
				// 请求父亲不拦截事件
				getParent().requestDisallowInterceptTouchEvent(true);
			} else {// 上下
				getParent().requestDisallowInterceptTouchEvent(false);
			}
			break;
		case MotionEvent.ACTION_UP:

			break;
		case MotionEvent.ACTION_CANCEL:

			break;

		default:
			break;
		}
		return super.onTouchEvent(ev);
	}

}
