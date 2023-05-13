package com.itheima.googleplay_8.views;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author  Administrator
 * @time 	2015-7-18 上午10:27:50
 * @des	TODO
 *
 * @version $Rev: 30 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2015-07-18 11:15:47 +0800 (星期六, 18 七月 2015) $
 * @updateDes TODO
 */
public class ParentViewPager extends ViewPager {

	public ParentViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public ParentViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:

			break;
		case MotionEvent.ACTION_MOVE:

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
