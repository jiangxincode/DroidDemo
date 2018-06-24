package com.itheima.googleplay_8.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.itheima.googleplay_8.R;
import com.itheima.googleplay_8.base.BaseHolder;
import com.itheima.googleplay_8.bean.AppInfoBean;
import com.itheima.googleplay_8.conf.Constants.URLS;
import com.itheima.googleplay_8.utils.BitmapHelper;
import com.itheima.googleplay_8.utils.StringUtils;
import com.itheima.googleplay_8.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @author  Administrator
 * @time 	2015-7-19 上午11:05:30
 * @des	TODO
 *
 * @version $Rev: 40 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2015-07-19 11:26:02 +0800 (星期日, 19 七月 2015) $
 * @updateDes TODO
 */
public class AppDetailInfoHolder extends BaseHolder<AppInfoBean> {
	@ViewInject(R.id.app_detail_info_iv_icon)
	ImageView	mIvIcon;

	@ViewInject(R.id.app_detail_info_rb_star)
	RatingBar	mRbStar;

	@ViewInject(R.id.app_detail_info_tv_downloadnum)
	TextView	mTvDownLoadNum;

	@ViewInject(R.id.app_detail_info_tv_name)
	TextView	mTvName;

	@ViewInject(R.id.app_detail_info_tv_time)
	TextView	mTvTime;

	@ViewInject(R.id.app_detail_info_tv_version)
	TextView	mTvVersion;

	@ViewInject(R.id.app_detail_info_tv_size)
	TextView	mTvSize;

	@Override
	public View initHolderView() {
		View view = View.inflate(UIUtils.getContext(), R.layout.item_app_detail_info, null);
		// 注入
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void refreshHolderView(AppInfoBean data) {
		String date = UIUtils.getString(R.string.detail_date, data.date);
		String downloadNum = UIUtils.getString(R.string.detail_downloadnum, data.downloadNum);
		String size = UIUtils.getString(R.string.detail_size, StringUtils.formatFileSize(data.size));
		String version = UIUtils.getString(R.string.detail_version, data.version);

		mTvName.setText(data.name);
		mTvDownLoadNum.setText(downloadNum);
		mTvTime.setText(date);
		mTvVersion.setText(version);
		mTvSize.setText(size);

		mIvIcon.setImageResource(R.drawable.ic_default);
		BitmapHelper.display(mIvIcon, URLS.IMAGEBASEURL + data.iconUrl);

		mRbStar.setRating(data.stars);

	}

}
