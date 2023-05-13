package com.itheima.googleplay_8.holder;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.jiangxin.droiddemo.R;
import com.itheima.googleplay_8.base.BaseHolder;
import com.itheima.googleplay_8.bean.AppInfoBean;
import com.itheima.googleplay_8.conf.Constants.URLS;
import com.itheima.googleplay_8.utils.BitmapHelper;
import com.itheima.googleplay_8.utils.UIUtils;
import com.itheima.googleplay_8.views.RatioLayout;

/**
 * @author  Administrator
 * @time 	2015-7-19 上午11:05:30
 * @des	TODO
 *
 * @version $Rev: 43 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2015-07-19 15:47:06 +0800 (星期日, 19 七月 2015) $
 * @updateDes TODO
 */
public class AppDetailPicHolder extends BaseHolder<AppInfoBean> {
	private LinearLayout	mContainerPic;

	@Override
	public View initHolderView() {
		View view = View.inflate(UIUtils.getContext(), R.layout.item_app_detail_pic, null);
		mContainerPic = view.findViewById(R.id.app_detail_pic_iv_container);
		return view;
	}

	@Override
	public void refreshHolderView(AppInfoBean data) {
		// TODO
		List<String> picUrls = data.screen;
		for (int i = 0; i < picUrls.size(); i++) {
			String url = picUrls.get(i);
			ImageView ivPic = new ImageView(UIUtils.getContext());
			ivPic.setImageResource(R.drawable.ic_default);// 默认图片
			BitmapHelper.display(ivPic, URLS.IMAGEBASEURL + url);
			
			// 控件宽度等于屏幕的1/3
			int widthPixels = UIUtils.getResource().getDisplayMetrics().widthPixels;
			widthPixels = widthPixels - mContainerPic.getPaddingLeft() - mContainerPic.getPaddingRight();

			int childWidth = widthPixels / 3;
			// 已知控件的宽度,和图片的宽高比,去动态的计算控件的高度
			RatioLayout rl = new RatioLayout(UIUtils.getContext());
			rl.setPicRatio(150 / 250);// 图片的宽高比
			rl.setRelative(RatioLayout.RELATIVE_WIDTH);

			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(childWidth, LayoutParams.WRAP_CONTENT);

			rl.addView(ivPic);

			if (i != 0) {// 不处理第一张图片
				params.leftMargin = UIUtils.dip2Px(5);
			}

			mContainerPic.addView(rl, params);
			
		}
	}
}
