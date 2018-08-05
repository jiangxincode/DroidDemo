package com.itheima62.smartbj.newscenterpage;

import com.itheima62.smartbj.activity.MainActivity;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;


/**
 * @author Administrator
 * @创建时间 2015-7-6 下午2:20:16
 * @描述 TODO
 *
 * @ svn提交者：$Author: gd $
 * @ 提交时间: $Date: 2015-07-06 14:57:36 +0800 (Mon, 06 Jul 2015) $
 * @ 当前版本: $Rev: 28 $
 */
public class TopicBaseNewsCenterPage extends BaseNewsCenterPage
{

	public TopicBaseNewsCenterPage(MainActivity mainActivity) {
		super(mainActivity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View initView() {
		TextView tv = new TextView(mainActivity);
		tv.setText("专题的内容");
		tv.setTextSize(25);
		tv.setGravity(Gravity.CENTER);
		return tv;
	}

}
