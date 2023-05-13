package com.itheima.googleplay_8.holder;

import android.view.View;
import android.widget.LinearLayout;

import edu.jiangxin.droiddemo.R;
import com.itheima.googleplay_8.base.BaseHolder;
import com.itheima.googleplay_8.utils.UIUtils;

/**
 * @author  Administrator
 * @time 	2015-7-16 下午3:01:45
 * @des	TODO
 *
 * @version $Rev: 21 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2015-07-16 15:47:18 +0800 (星期四, 16 七月 2015) $
 * @updateDes TODO
 */
public class LoadMoreHolder extends BaseHolder<Integer> {
	private LinearLayout			mContainerLoading;

	private LinearLayout			mContainerRetry;

	public static final int	STATE_LOADING	= 0;
	public static final int	STATE_RETRY		= 1;
	public static final int	STATE_NONE		= 2;

	@Override
	public View initHolderView() {
		View view = View.inflate(UIUtils.getContext(), R.layout.item_loadmore, null);

		mContainerLoading = view.findViewById(R.id.item_loadmore_container_loading);
		mContainerRetry = view.findViewById(R.id.item_loadmore_container_retry);

		return view;
	}

	@Override
	public void refreshHolderView(Integer state) {
		mContainerLoading.setVisibility(8);
		mContainerRetry.setVisibility(8);
		switch (state) {
		case STATE_LOADING://正在加载更多中...
			mContainerLoading.setVisibility(0);
			break;
		case STATE_RETRY://加载更多失败
			mContainerRetry.setVisibility(0);
			break;
		case STATE_NONE:
			break;

		default:
			break;
		}
	}

}
