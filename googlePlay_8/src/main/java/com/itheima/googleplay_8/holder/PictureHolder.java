package com.itheima.googleplay_8.holder;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.itheima.googleplay_8.R;
import com.itheima.googleplay_8.base.BaseHolder;
import com.itheima.googleplay_8.conf.Constants.URLS;
import com.itheima.googleplay_8.utils.BitmapHelper;
import com.itheima.googleplay_8.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @author  Administrator
 * @time 	2015-7-18 上午9:47:44
 * @des	TODO
 *
 * @version $Rev: 30 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2015-07-18 11:15:47 +0800 (星期六, 18 七月 2015) $
 * @updateDes TODO
 */
public class PictureHolder extends BaseHolder<List<String>> {

	@ViewInject(R.id.item_home_picture_pager)
	ViewPager				mViewPager;

	@ViewInject(R.id.item_home_picture_container_indicator)
	LinearLayout			mContainerIndicator;

	private List<String>	mDatas;

	@Override
	public View initHolderView() {
		View view = View.inflate(UIUtils.getContext(), R.layout.item_home_picture, null);
		// 注入找孩子
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void refreshHolderView(List<String> datas) {
		mDatas = datas;
		mViewPager.setAdapter(new PictureAdapter());

		// 添加小点
		for (int i = 0; i < datas.size(); i++) {
			View indicatorView = new View(UIUtils.getContext());
			indicatorView.setBackgroundResource(R.drawable.indicator_normal);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(UIUtils.dip2Px(5), UIUtils.dip2Px(5));// dp-->px
			// 左边距
			params.leftMargin = UIUtils.dip2Px(5);
			// 下边距
			params.bottomMargin = UIUtils.dip2Px(5);
			mContainerIndicator.addView(indicatorView, params);

			// 默认选中效果
			if (i == 0) {
				indicatorView.setBackgroundResource(R.drawable.indicator_selected);
			}
		}
		// 监听滑动事件
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {

				position = position % mDatas.size();

				for (int i = 0; i < mDatas.size(); i++) {
					View indicatorView = mContainerIndicator.getChildAt(i);
					// 还原背景
					indicatorView.setBackgroundResource(R.drawable.indicator_normal);

					if (i == position) {
						indicatorView.setBackgroundResource(R.drawable.indicator_selected);
					}
				}
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				// TODO

			}

			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO

			}
		});

		// 设置curItem为count/2
		int diff = Integer.MAX_VALUE / 2 % mDatas.size();
		int index = Integer.MAX_VALUE / 2;
		mViewPager.setCurrentItem(index - diff);

		// 自动轮播
		final AutoScrollTask autoScrollTask = new AutoScrollTask();
		autoScrollTask.start();
		// 用户触摸的时候移除自动轮播的任务
		mViewPager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					autoScrollTask.stop();
					break;
				case MotionEvent.ACTION_MOVE:

					break;
				case MotionEvent.ACTION_UP:
					autoScrollTask.start();
					break;

				default:
					break;
				}
				return false;
			}
		});
	}

	class AutoScrollTask implements Runnable {
		/**开始轮播*/
		public void start() {
			UIUtils.postTaskDelay(this, 2000);
		}

		public void stop() {
			UIUtils.removeTask(this);
		}

		/**结束轮播*/
		@Override
		public void run() {
			int item = mViewPager.getCurrentItem();
			item++;
			mViewPager.setCurrentItem(item);
			// 结束-->再次开始
			start();
		}
	}

	class PictureAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			if (mDatas != null) {
				return Integer.MAX_VALUE;
				// return mDatas.size();
			}
			return 0;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			position = position % mDatas.size();// 0

			ImageView iv = new ImageView(UIUtils.getContext());
			iv.setScaleType(ScaleType.FIT_XY);

			iv.setImageResource(R.drawable.ic_default);

			BitmapHelper.display(iv, URLS.IMAGEBASEURL + mDatas.get(position));

			container.addView(iv);
			return iv;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

	}

}
