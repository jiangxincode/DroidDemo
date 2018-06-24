package com.itheima.googleplay_8.holder;

import android.view.View;

import com.itheima.googleplay_8.R;
import com.itheima.googleplay_8.base.BaseHolder;
import com.itheima.googleplay_8.utils.UIUtils;

/**
 * @author  Administrator
 * @time 	2015-7-20 下午4:18:10
 * @des	TODO
 *
 * @version $Rev: 51 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2015-07-20 16:22:06 +0800 (星期一, 20 七月 2015) $
 * @updateDes TODO
 */
public class MenuHolder extends BaseHolder<Object> {

	@Override
	public View initHolderView() {
		View view = View.inflate(UIUtils.getContext(), R.layout.menu_view, null);
		return view;
	}

	@Override
	public void refreshHolderView(Object data) {
		// TODO
		
	}

}
