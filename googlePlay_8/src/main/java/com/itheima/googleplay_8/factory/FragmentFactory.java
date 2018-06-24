package com.itheima.googleplay_8.factory;

import android.support.v4.app.Fragment;
import android.support.v4.util.SparseArrayCompat;

import com.itheima.googleplay_8.base.BaseFragment;
import com.itheima.googleplay_8.fragment.AppFragment;
import com.itheima.googleplay_8.fragment.CategoryFragment;
import com.itheima.googleplay_8.fragment.GameFragment;
import com.itheima.googleplay_8.fragment.HomeFragment;
import com.itheima.googleplay_8.fragment.HotFragment;
import com.itheima.googleplay_8.fragment.RecommendFragment;
import com.itheima.googleplay_8.fragment.SubjectFragment;

/**
 * @author  Administrator
 * @time 	2015-7-15 下午3:07:05
 * @des	Fragment工厂类
 *
 * @version $Rev: 9 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2015-07-15 17:31:13 +0800 (星期三, 15 七月 2015) $
 * @updateDes TODO
 */
public class FragmentFactory {
	/**
	
	<string-array name="main_titles">
	<item>首页</item>
	<item>应用</item>
	<item>游戏</item>
	<item>专题</item>
	<item>推荐</item>
	<item>分类</item>
	<item>排行</item>
	</string-array>
	 */
	public static final int					FRAGMENT_HOME		= 0;
	public static final int					FRAGMENT_APP		= 1;
	public static final int					FRAGMENT_GAME		= 2;
	public static final int					FRAGMENT_SUBJECT	= 3;
	public static final int					FRAGMENT_RECOMMEND	= 4;
	public static final int					FRAGMENT_CATEGORY	= 5;
	public static final int					FRAGMENT_HOT		= 6;

	static SparseArrayCompat<BaseFragment>	cachesFragment		= new SparseArrayCompat<BaseFragment>();

	public static BaseFragment getFragment(int position) {

		BaseFragment fragment = null;
		// 如果缓存里面有对应的fragment,就直接取出返回

		BaseFragment tmpFragment = cachesFragment.get(position);
		if (tmpFragment != null) {
			fragment = tmpFragment;
			return fragment;
		}
		switch (position) {
		case FRAGMENT_HOME:// 主页
			fragment = new HomeFragment();
			break;
		case FRAGMENT_APP:// 应用
			fragment = new AppFragment();
			break;
		case FRAGMENT_GAME:// 游戏
			fragment = new GameFragment();
			break;
		case FRAGMENT_SUBJECT:// 专题
			fragment = new SubjectFragment();
			break;
		case FRAGMENT_RECOMMEND:// 推荐
			fragment = new RecommendFragment();
			break;
		case FRAGMENT_CATEGORY:// 分类
			fragment = new CategoryFragment();
			break;
		case FRAGMENT_HOT:// 排行
			fragment = new HotFragment();
			break;

		default:
			break;

		}
		// 保存对应的fragment
		cachesFragment.put(position, fragment);
		return fragment;
	}
}
