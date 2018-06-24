package com.itheima.googleplay_8.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.googleplay_8.R;
import com.itheima.googleplay_8.base.BaseHolder;
import com.itheima.googleplay_8.bean.SubjectInfoBean;
import com.itheima.googleplay_8.conf.Constants.URLS;
import com.itheima.googleplay_8.utils.BitmapHelper;
import com.itheima.googleplay_8.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @author  Administrator
 * @time 	2015-7-18 上午11:08:43
 * @des	TODO
 *
 * @version $Rev: 30 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2015-07-18 11:15:47 +0800 (星期六, 18 七月 2015) $
 * @updateDes TODO
 */
public class SubjectHolder extends BaseHolder<SubjectInfoBean> {
	@ViewInject(R.id.item_subject_iv_icon)
	ImageView	mIvIcon;

	@ViewInject(R.id.item_subject_tv_title)
	TextView	mTvTitle;

	@Override
	public View initHolderView() {
		View view = View.inflate(UIUtils.getContext(), R.layout.item_subject, null);

		ViewUtils.inject(this, view);

		return view;
	}

	@Override
	public void refreshHolderView(SubjectInfoBean data) {
		mTvTitle.setText(data.des);
		mIvIcon.setImageResource(R.drawable.ic_default);
		BitmapHelper.display(mIvIcon, URLS.IMAGEBASEURL + data.url);
	}

}
