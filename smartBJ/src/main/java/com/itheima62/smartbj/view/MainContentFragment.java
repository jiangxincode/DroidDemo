

package com.itheima62.smartbj.view;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.itheima62.smartbj.R;
import com.itheima62.smartbj.basepage.BaseTagPage;
import com.itheima62.smartbj.basepage.GovAffairsBaseTagPager;
import com.itheima62.smartbj.basepage.HomeBaseTagPager;
import com.itheima62.smartbj.basepage.NewCenterBaseTagPager;
import com.itheima62.smartbj.basepage.SettingCenterBaseTagPager;
import com.itheima62.smartbj.basepage.SmartServiceBaseTagPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @author Administrator
 * @创建时间 2015-7-4 下午4:17:20
 * @描述 主界面的fragment
 * 
 *     @ svn提交者：$Author: gd $ @ 提交时间: $Date: 2015-07-04 17:09:14 +0800 (Sat, 04
 *     Jul 2015) $ @ 当前版本: $Rev: 28 $
 */
public class MainContentFragment extends BaseFragment
{

	@ViewInject(R.id.vp_main_content_pages)
	private MyViewPager			viewPager;

	@ViewInject(R.id.rg_content_radios)
	private RadioGroup			rg_radios;

	private List<BaseTagPage>	pages	= new ArrayList<BaseTagPage>();

	private int					selectIndex;//设置当前选择的页面编号

	@Override
	public void initEvent() {
		// 添加自己的事件

		// 单选按钮的切换事件
		rg_radios.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// 五个单选按钮
				switch (checkedId) {// 是哪个单选按钮点击的
				case R.id.rb_main_content_home:// 主界面
					selectIndex = 0;
					break;
				case R.id.rb_main_content_newscenter:// 新闻中心界面的切换
					selectIndex = 1;
					break;
				case R.id.rb_main_content_smartservice:// 智慧服务面
					selectIndex = 2;
					break;
				case R.id.rb_main_content_govaffairs:// 政务界面
					selectIndex = 3;
					break;
				case R.id.rb_main_content_settingcenter:// 设置中心界面
					selectIndex = 4;
					break;

				default:
					break;
				}// end switch (checkedId) {
				
				switchPage();
			}
		});
		super.initEvent();
	}
	
	/**
	 * 左侧菜单点击，让主界面切换不同的页面
	 */
	public void leftMenuClickSwitchPage(int subSelectionIndex){
		BaseTagPage baseTagPage = pages.get(selectIndex);
		baseTagPage.switchPage(subSelectionIndex);
	}
	

	/**
	 *  设置选中的页面
	 */
	protected void switchPage() {
		//BaseTagPage currentPage = pages.get(selectIndex);
		viewPager.setCurrentItem(selectIndex);//设置viewpager显示页面
		
		//如果是第一个或者是最后一个 不让左侧菜单滑动出来
		if (selectIndex == 0 || selectIndex == pages.size() - 1) {
			//不让左侧菜单滑动出来
			mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);//滑不处理
		} else {
			//可以滑动出左侧菜单
			mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//屏幕任何位置都可以滑动出
		}
	}

	@Override
	public View initView() {
		View root = View.inflate(mainActivity, R.layout.fragment_content_view,
				null);

		// xutils 动态注入view

		ViewUtils.inject(this, root);
		return root;
	}

	@Override
	public void initData() {
		// 首页
		pages.add(new HomeBaseTagPager(mainActivity));
		// 首页
		pages.add(new NewCenterBaseTagPager(mainActivity));
		// 首页
		pages.add(new SmartServiceBaseTagPager(mainActivity));
		// 首页
		pages.add(new GovAffairsBaseTagPager(mainActivity));
		// 首页
		pages.add(new SettingCenterBaseTagPager(mainActivity));

		MyAdapter adapter = new MyAdapter();
		viewPager.setAdapter(adapter);
		
		//viewPager.setOffscreenPageLimit(2);//设置预加载为：前后各2个页面
		
		//设置默认选择首页
		switchPage();
		//设置第一个按钮被选中(首页)
		rg_radios.check(R.id.rb_main_content_home);
		
	}

	private class MyAdapter extends PagerAdapter
	{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return pages.size();
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
System.out.println("instantiateItem:" + position);			
			// TODO Auto-generated method stub
			BaseTagPage baseTagPage = pages.get(position);
			View root = baseTagPage.getRoot();
			container.addView(root);
			
			//加载数据库
			baseTagPage.initData();
			return root;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return view == object;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
System.out.println("destroyItem:" + position);	
			container.removeView((View) object);
		}

	}

}
