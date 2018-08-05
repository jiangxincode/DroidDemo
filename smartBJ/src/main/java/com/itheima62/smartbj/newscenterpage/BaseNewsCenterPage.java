package com.itheima62.smartbj.newscenterpage;

import android.view.View;

import com.itheima62.smartbj.activity.MainActivity;

/**
 * @author Administrator
 * @创建时间 2015-7-6 下午2:18:57
 * @描述 TODO
 *
 * @ svn提交者：$Author: gd $
 * @ 提交时间: $Date: 2015-07-06 14:57:36 +0800 (Mon, 06 Jul 2015) $
 * @ 当前版本: $Rev: 28 $
 */
public abstract class BaseNewsCenterPage
{
	protected MainActivity mainActivity ;
	protected View root;//根布局
	public BaseNewsCenterPage(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
		root = initView();
		initEvent();
	}
	
	/**
	 * 子类覆盖此方法完成事件的处理
	 */
	public void initEvent(){
		
	}
	
	/**
	 * 子类覆盖此方法来显示自定义的View
	 * @return
	 */
	public abstract View initView();
	
	public View getRoot(){
		return root;
	}
	
	
	/**
	 * 子类覆盖此方法完成数据的显示
	 */
	public void initData(){
		
	}
	
	
}
