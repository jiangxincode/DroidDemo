

package com.itheima62.smartbj.newscenterpage;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.itheima62.smartbj.activity.MainActivity;

/**
 * @author Administrator
 * @创建时间 2015-7-6 下午2:20:16
 * @描述 TODO
 * 
 *     @ svn提交者：$Author: gd $ @ 提交时间: $Date: 2015-07-06 14:57:36 +0800 (Mon, 06 Jul 2015) $ @ 当前版本: $Rev: 28 $
 */
public class InteractBaseNewsCenterPage extends BaseNewsCenterPage
{

	public InteractBaseNewsCenterPage(MainActivity mainActivity) {
		super(mainActivity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View initView() {
		// TODO Auto-generated method stub
		// 要展示的内容，
		TextView tv = new TextView(mainActivity);
		tv.setText("互动的内容");
		tv.setTextSize(25);
		tv.setGravity(Gravity.CENTER);
		return tv;
	}

}
