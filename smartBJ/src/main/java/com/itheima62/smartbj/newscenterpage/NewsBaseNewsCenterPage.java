

package com.itheima62.smartbj.newscenterpage;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itheima62.smartbj.R;
import com.itheima62.smartbj.activity.MainActivity;
import com.itheima62.smartbj.domain.NewsCenterData;
import com.itheima62.smartbj.domain.NewsCenterData.NewsData.ViewTagData;
import com.itheima62.smartbj.newstpipage.TPINewsNewsCenterPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.viewpagerindicator.TabPageIndicator;

/**
 * @author Administrator
 * @创建时间 2015-7-6 下午2:20:16
 * @描述 TODO
 * 
 *     @ svn提交者：$Author: gd $ @ 提交时间: $Date: 2015-07-06 14:57:36 +0800 (Mon, 06
 *     Jul 2015) $ @ 当前版本: $Rev: 39 $
 */
public class NewsBaseNewsCenterPage extends BaseNewsCenterPage
{

	@ViewInject(R.id.newcenter_vp)
	private ViewPager			vp_newscenter;

	@ViewInject(R.id.newcenter_tpi)
	private TabPageIndicator	tpi_newscenter;
	
	@OnClick(R.id.newcenter_ib_nextpage)
	public void next(View v) {
		//切换到下一个页面
		vp_newscenter.setCurrentItem(vp_newscenter.getCurrentItem() + 1);
	}

	private List<ViewTagData>	viewTagDatas	= new ArrayList<NewsCenterData.NewsData.ViewTagData>(); // 页签的数据

	public NewsBaseNewsCenterPage(MainActivity mainActivity,
			List<ViewTagData> children) {
		super(mainActivity);
		// TODO Auto-generated constructor stub
		this.viewTagDatas = children;
	}
	
	@Override
	public void initEvent() {
		//添加自己的事件
		
		//给Viewpager添加页面切换的监听事件，当页面位于第一个可以滑动出左侧菜单，否则不滑动
		
		tpi_newscenter.setOnPageChangeListener(new OnPageChangeListener() {
			
			
			/* (non-Javadoc)
			 * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageSelected(int)
			 * 监听页面停留的位置
			 */
			@Override
			public void onPageSelected(int position) {
				//当页面位于第一个可以滑动出左侧菜单
				if (position == 0) {
					//第一个
					//可以滑动出左侧菜单
					mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
				} else {
					//不可以滑动出左侧菜单
					mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
				}
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub
				
			}
		});
		
		super.initEvent();
	}

	@Override
	public View initView() {
		View newsCenterRoot = View.inflate(mainActivity,
				R.layout.newscenterpage_content, null);
		// xutils工具注入组件
		ViewUtils.inject(this, newsCenterRoot);

		return newsCenterRoot;
	}

	@Override
	public void initData() {
		// 设置数据
		MyAdapter adapter = new MyAdapter();

		// 设置ViewPager的适配器
		vp_newscenter.setAdapter(adapter);

		// 把ViewPager和Tabpagerindicator关联
		tpi_newscenter.setViewPager(vp_newscenter);
		super.initData();
	}

	/**
	 * @author Administrator
	 * @创建时间 2015-7-6 下午4:29:02
	 * @描述 页签对应ViewPage的适配器
	 * 
	 *     @ svn提交者：$Author: gd $ @ 提交时间: $Date: 2015-07-07 10:35:57 +0800 (Tue, 07 Jul 2015) $ @ 当前版本: $Rev: 39 $
	 */
	private class MyAdapter extends PagerAdapter
	{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return viewTagDatas.size();// 获取数据的个数
		}

		/**
		 * 页签显示数据调用该方法
		 */
		@Override
		public CharSequence getPageTitle(int position) {
			//获取页签的数据
			return viewTagDatas.get(position).title;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// 要展示的内容，
			TPINewsNewsCenterPager tpiPager = new TPINewsNewsCenterPager(mainActivity,viewTagDatas.get(position));
			View rootView = tpiPager.getRootView();
			container.addView(rootView);
			return rootView;
		/*	TextView tv = new TextView(mainActivity);
			tv.setText(viewTagDatas.get(position).title);
			tv.setTextSize(25);
			tv.setGravity(Gravity.CENTER);

			container.addView(tv);

			return tv;*/
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView((View) object);
		}

	}

}
