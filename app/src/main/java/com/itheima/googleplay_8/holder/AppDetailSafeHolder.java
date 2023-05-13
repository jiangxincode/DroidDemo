package com.itheima.googleplay_8.holder;

import java.util.List;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itheima.googleplay_8.R;
import com.itheima.googleplay_8.base.BaseHolder;
import com.itheima.googleplay_8.bean.AppInfoBean;
import com.itheima.googleplay_8.bean.AppInfoBean.AppInfoSafeBean;
import com.itheima.googleplay_8.conf.Constants.URLS;
import com.itheima.googleplay_8.utils.BitmapHelper;
import com.itheima.googleplay_8.utils.UIUtils;


/**
 * @author  Administrator
 * @time 	2015-7-19 上午11:05:30
 * @des	TODO
 *
 * @version $Rev: 45 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2015-07-19 17:01:18 +0800 (星期日, 19 七月 2015) $
 * @updateDes TODO
 */
public class AppDetailSafeHolder extends BaseHolder<AppInfoBean> implements OnClickListener {
	private LinearLayout	mContainerPic;

	private LinearLayout	mContainerDes;

	private ImageView		mIvArrow;

	private boolean	isOpen	= true;

	@Override
	public View initHolderView() {

		View view = View.inflate(UIUtils.getContext(), R.layout.item_app_detail_safe, null);
		mContainerPic = view.findViewById(R.id.app_detail_safe_pic_container);
		mContainerDes = view.findViewById(R.id.app_detail_safe_des_container);
		mIvArrow = view.findViewById(R.id.app_detail_safe_iv_arrow);
		view.setOnClickListener(this);
		return view;
	}

	@Override
	public void refreshHolderView(AppInfoBean data) {
		List<AppInfoSafeBean> safeBeans = data.safe;
		for (AppInfoSafeBean appInfoSafeBean : safeBeans) {
			ImageView ivIcon = new ImageView(UIUtils.getContext());
			BitmapHelper.display(ivIcon, URLS.IMAGEBASEURL + appInfoSafeBean.safeUrl);
			mContainerPic.addView(ivIcon);

			LinearLayout ll = new LinearLayout(UIUtils.getContext());

			// 描述图标
			ImageView ivDes = new ImageView(UIUtils.getContext());
			BitmapHelper.display(ivDes, URLS.IMAGEBASEURL + appInfoSafeBean.safeDesUrl);
			// 描述内容
			TextView tvDes = new TextView(UIUtils.getContext());
			tvDes.setText(appInfoSafeBean.safeDes);
			if (appInfoSafeBean.safeDesColor == 0) {
				tvDes.setTextColor(UIUtils.getColor(R.color.app_detail_safe_normal));
			} else {
				tvDes.setTextColor(UIUtils.getColor(R.color.app_detail_safe_warning));
			}
			
			tvDes.setGravity(Gravity.CENTER);
			// 加点间距
			int padding = UIUtils.dip2Px(5);
			ll.setPadding(padding, padding, padding, padding);

			ll.addView(ivDes);
			ll.addView(tvDes);

			mContainerDes.addView(ll);

		}
		// 默认折叠
		toggle(false);
	}

	@Override
	public void onClick(View v) {
		toggle(true);
	}

	private void toggle(boolean isAnimation) {
		if (isOpen) {// 折叠

			/**
			 mContainerDes高度发生变化
			 应有的高度-->0
			 */

			mContainerDes.measure(0, 0);
			int measuredHeight = mContainerDes.getMeasuredHeight();
			int start = measuredHeight;// 动画的开始高度
			int end = 0;// 动画的结束高度
			if (isAnimation) {
				doAnimation(start, end);
			} else {// 直接修改高度
				LayoutParams params = mContainerDes.getLayoutParams();
				params.height = end;
				mContainerDes.setLayoutParams(params);
			}
		} else {// 展开
			/**
			 mContainerDes高度发生变化
			 0-->应有的高度
			 */
			mContainerDes.measure(0, 0);
			int measuredHeight = mContainerDes.getMeasuredHeight();
			int end = measuredHeight;// 动画的开始高度
			int start = 0;// 动画的结束高度
			if (isAnimation) {
				doAnimation(start, end);
			} else {
				LayoutParams params = mContainerDes.getLayoutParams();
				params.height = end;
				mContainerDes.setLayoutParams(params);
			}
		}
		// 箭头的旋转动画
		if (isAnimation) {// 有折叠动画的时候
			if (isOpen) {
				ObjectAnimator.ofFloat(mIvArrow, "rotation", 180, 0).start();
			} else {
				ObjectAnimator.ofFloat(mIvArrow, "rotation", 0, 180).start();
			}
		}

		isOpen = !isOpen;
	}

	public void doAnimation(int start, int end) {
		ValueAnimator animator = ValueAnimator.ofInt(start, end);
		animator.start();// 开始动画
		animator.addUpdateListener(new AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator value) {
				// TODO
				int height = (Integer) value.getAnimatedValue();
				// 通过layoutParams,修改高度
				LayoutParams params = mContainerDes.getLayoutParams();
				params.height = height;
				mContainerDes.setLayoutParams(params);
			}
		});
	}

}
