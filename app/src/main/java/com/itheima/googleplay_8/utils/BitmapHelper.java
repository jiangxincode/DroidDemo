package com.itheima.googleplay_8.utils;

import android.view.View;

import com.lidroid.xutils.BitmapUtils;

/**
 * @author  Administrator
 * @time 	2015-7-16 下午2:31:07
 * @des	TODO
 *
 * @version $Rev: 20 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2015-07-16 14:38:48 +0800 (星期四, 16 七月 2015) $
 * @updateDes TODO
 */
public class BitmapHelper {
	public static BitmapUtils	mBitmapUtils;
	static {
		mBitmapUtils = new BitmapUtils(UIUtils.getContext());
	}

	public static <T extends View> void display(T container, String uri) {
		mBitmapUtils.display(container, uri);
	}
}
