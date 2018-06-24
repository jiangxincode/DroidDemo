package com.itheima.googleplay_8.holder;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itheima.googleplay_8.R;
import com.itheima.googleplay_8.base.BaseHolder;
import com.itheima.googleplay_8.bean.CategoryInfoBean;
import com.itheima.googleplay_8.utils.UIUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @author  Administrator
 * @time 	2015-7-18 下午4:23:16
 * @des	TODO
 *
 * @version $Rev: 37 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2015-07-18 16:55:48 +0800 (星期六, 18 七月 2015) $
 * @updateDes TODO
 */
public class CategoryTitleHolder extends BaseHolder<CategoryInfoBean> {

	private TextView	mTvTitle;

	@Override
	public View initHolderView() {
		mTvTitle = new TextView(UIUtils.getContext());
		mTvTitle.setTextSize(15);
		int padding = UIUtils.dip2Px(5);
		mTvTitle.setPadding(padding, padding, padding, padding);
		mTvTitle.setBackgroundColor(Color.WHITE);

		AbsListView.LayoutParams params = new AbsListView.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		mTvTitle.setLayoutParams(params);

		return mTvTitle;
	}

	@Override
	public void refreshHolderView(CategoryInfoBean data) {
		mTvTitle.setText(data.title);
	}

}
