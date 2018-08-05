package com.itheima62.smartbj.basepage;

import android.view.Gravity;
import android.widget.TextView;

import com.itheima62.smartbj.activity.MainActivity;

/**
 * @author Administrator
 * @创建时间 2015-7-4 下午5:30:22
 * @描述 TODO
 *
 * @ svn提交者：$Author: gd $
 * @ 提交时间: $Date: 2015-07-06 09:25:32 +0800 (Mon, 06 Jul 2015) $
 * @ 当前版本: $Rev: 23 $
 */
public class SmartServiceBaseTagPager extends BaseTagPage
{

	public SmartServiceBaseTagPager(MainActivity context) {
		super(context);
	}
	@Override
	public void initData() {
		//设置当前page的标题头
		tv_title.setText("智慧服务");
		
		//当前page要展示的内容
		TextView tv = new TextView(mainActivity);
		tv.setText("智慧服务的内容");
		tv.setTextSize(25);
		tv.setGravity(Gravity.CENTER);
		
		//添加到白纸中
		fl_content.addView(tv);
		super.initData();
	}

}
