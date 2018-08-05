

package com.itheima62.smartbj.basepage;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.itheima62.smartbj.activity.MainActivity;

/**
 * @author Administrator
 * @创建时间 2015-7-4 下午5:30:22
 * @描述 TODO
 * 
 *     @ svn提交者：$Author: gd $ @ 提交时间: $Date: 2015-07-04 17:38:44 +0800 (Sat, 04
 *     Jul 2015) $ @ 当前版本: $Rev: 23 $
 */
public class HomeBaseTagPager extends BaseTagPage
{

	public HomeBaseTagPager(MainActivity context) {
		super(context);
	}

	@Override
	public void initData() {
		
		//屏蔽菜单按钮
		ib_menu.setVisibility(View.GONE);
		
		// 设置本page的标题
		tv_title.setText("主页");

		// 要展示的内容，
		TextView tv = new TextView(mainActivity);
		tv.setText("主页的内容");
		tv.setTextSize(25);
		tv.setGravity(Gravity.CENTER);

		// 替换掉白纸
		fl_content.addView(tv);// 添加自己的内容到白纸上
		super.initData();
	}
}
