package com.itheima.googleplay_8.base;

import java.util.List;
import java.util.Map;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itheima.googleplay_8.base.LoadingPager.LoadedResult;
import com.itheima.googleplay_8.utils.UIUtils;

/**
 * @author  Administrator
 * @time 	2015-7-15 下午4:00:42
 * @des	TODO
 *
 * @version $Rev: 20 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2015-07-16 14:38:48 +0800 (星期四, 16 七月 2015) $
 * @updateDes TODO
 */
public abstract class BaseFragment extends Fragment {

	private LoadingPager	mLoadingPager;

	public LoadingPager getLoadingPager() {
		return mLoadingPager;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (mLoadingPager == null) {// 第一次执行
			mLoadingPager = new LoadingPager(UIUtils.getContext()) {
				@Override
				public LoadedResult initData() {
					return BaseFragment.this.initData();
				}

				@Override
				public View initSuccessView() {
					return BaseFragment.this.initSuccessView();
				}
			};
		} else {// 第2次执行
			((ViewGroup) mLoadingPager.getParent()).removeView(mLoadingPager);
		}
		return mLoadingPager;// mLoadingPager frament
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	/**
	 //页面显示分析
	//Fragment共性-->页面共性-->视图的展示
	/**
	 任何应用其实就只有4种页面类型
	 ① 加载页面
	 ② 错误页面
	 ③ 空页面 		
	 ④ 成功页面 	
	 
		①②③三种页面一个应用基本是固定的
		每一个fragment/activity对应的页面④就不一样
		进入应用的时候显示①,②③④需要加载数据之后才知道显示哪个		
	*/

	// 数据加载的流程
	/**
	① 触发加载  	进入页面开始加载/点击某一个按钮的时候加载
	② 异步加载数据  -->显示加载视图
	③ 处理加载结果
		① 成功-->显示成功视图
		② 失败
			① 数据为空-->显示空视图
			② 数据加载失败-->显示加载失败的视图
	*/
	/**
	 * @des 真正加载数据,但是BaseFragment不知道具体实现,必须实现,定义成为抽象方法,让子类具体实现
	 * @des 它是LoadingPager同名方法
	 * @call loadData()方法被调用的时候
	 */
	public abstract LoadedResult initData();

	/**
	 * @des 返回成功视图,但是不知道具体实现,所以定义成抽象方法
	 * @des 它是LoadingPager同名方法
	 * @call 正在加载数据完成之后,并且数据加载成功,我们必须告知具体的成功视图
	 */
	public abstract View initSuccessView();

	/**
	 * @param obj 网络数据json化之后的对象
	 * @return
	 */
	public LoadedResult checkState(Object obj) {
		if (obj == null) {
			return LoadedResult.EMPTY;
		}
		// list
		if (obj instanceof List) {
			if (((List) obj).size() == 0) {
				return LoadedResult.EMPTY;
			}
		}
		// map
		if (obj instanceof Map) {
			if (((Map) obj).size() == 0) {
				return LoadedResult.EMPTY;
			}
		}
		return LoadedResult.SUCCESS;
	}
}
