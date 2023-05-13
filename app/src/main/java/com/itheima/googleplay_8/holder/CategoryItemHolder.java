package com.itheima.googleplay_8.holder;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import edu.jiangxin.droiddemo.R;
import com.itheima.googleplay_8.base.BaseHolder;
import com.itheima.googleplay_8.bean.CategoryInfoBean;
import com.itheima.googleplay_8.conf.Constants.URLS;
import com.itheima.googleplay_8.utils.BitmapHelper;
import com.itheima.googleplay_8.utils.StringUtils;
import com.itheima.googleplay_8.utils.UIUtils;

/**
 * @author  Administrator
 * @time 	2015-7-18 下午4:23:26
 * @des	TODO
 *
 * @version $Rev: 37 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2015-07-18 16:55:48 +0800 (星期六, 18 七月 2015) $
 * @updateDes TODO
 */
public class CategoryItemHolder extends BaseHolder<CategoryInfoBean> {
	private LinearLayout	mContainerItem1;
	private LinearLayout	mContainerItem2;
	private LinearLayout	mContainerItem3;
	private ImageView		mIvIcon1;
	private ImageView		mIvIcon2;
	private ImageView		mIvIcon3;
	private TextView		mTvName1;
	private TextView		mTvName2;
	private TextView		mTvName3;

	@Override
	public View initHolderView() {
		View view = View.inflate(UIUtils.getContext(), R.layout.item_category_info, null);

		mContainerItem1 = view.findViewById(R.id.item_category_item_1);
		mContainerItem2 = view.findViewById(R.id.item_category_item_2);
		mContainerItem3 = view.findViewById(R.id.item_category_item_3);
		mIvIcon1 = view.findViewById(R.id.item_category_icon_1);
		mIvIcon2 = view.findViewById(R.id.item_category_icon_2);
		mIvIcon3 = view.findViewById(R.id.item_category_icon_3);
		mTvName1 = view.findViewById(R.id.item_category_name_1);
		mTvName2 = view.findViewById(R.id.item_category_name_2);
		mTvName3 = view.findViewById(R.id.item_category_name_3);

		return view;
	}

	@Override
	public void refreshHolderView(CategoryInfoBean data) {
		setData(data.name1, data.url1, mTvName1, mIvIcon1);
		setData(data.name2, data.url2, mTvName2, mIvIcon2);
		setData(data.name3, data.url3, mTvName3, mIvIcon3);

	}

	public void setData(final String name, String url, TextView tvName, ImageView ivIcon) {
		if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(url)) {
			tvName.setText(name);
			ivIcon.setImageResource(R.drawable.ic_default);
			BitmapHelper.display(ivIcon, URLS.IMAGEBASEURL + url);
			((ViewGroup) tvName.getParent()).setVisibility(View.VISIBLE);

			((ViewGroup) tvName.getParent()).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO
					Toast.makeText(UIUtils.getContext(), name, 0).show();
				}
			});
		} else {
			((ViewGroup) tvName.getParent()).setVisibility(View.INVISIBLE);
		}
	}

}
