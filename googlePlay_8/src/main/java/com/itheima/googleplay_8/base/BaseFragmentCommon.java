package com.itheima.googleplay_8.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author  Administrator
 * @time 	2015-7-15 下午3:52:25
 * @des	TODO
 *
 * @version $Rev: 7 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2015-07-15 16:00:22 +0800 (星期三, 15 七月 2015) $
 * @updateDes TODO
 */
public abstract class BaseFragmentCommon extends Fragment {

	//共有的属性
	//共有的方法
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		init();
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return initView();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		initData();
		initListener();
		super.onActivityCreated(savedInstanceState);
	}



	/**
	 * @des 初始化view,而且是必须实现,但是不知道具体实现,定义成抽象方法
	 * @return
	 */
	public abstract View initView();

	
	public void init() {
		// TODO

	}
	
	public void initData() {
		// TODO

	}

	public void initListener() {
		// TODO

	}

}
