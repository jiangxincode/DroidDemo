package com.itheima.googleplay_8.factory;

import com.itheima.googleplay_8.utils.UIUtils;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ListView;

/**
 * @author  Administrator
 * @time 	2015-7-18 上午9:44:26
 * @des	TODO
 *
 * @version $Rev: 27 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2015-07-18 09:45:41 +0800 (星期六, 18 七月 2015) $
 * @updateDes TODO
 */
public class ListViewFactory {
	public static ListView createListView() {
		// 返回成功的视图
		ListView listView = new ListView(UIUtils.getContext());

		// 简单的设置
		listView.setCacheColorHint(Color.TRANSPARENT);
		listView.setFastScrollEnabled(true);

		listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		return listView;
	}
}
