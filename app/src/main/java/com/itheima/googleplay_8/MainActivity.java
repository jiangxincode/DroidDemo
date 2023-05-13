package com.itheima.googleplay_8;

import androidx.legacy.app.ActionBarDrawerToggle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.itheima.googleplay_8.base.BaseActivity;
import com.itheima.googleplay_8.base.BaseFragment;
import com.itheima.googleplay_8.factory.FragmentFactory;
import com.itheima.googleplay_8.holder.MenuHolder;
import com.itheima.googleplay_8.utils.LogUtils;
import com.itheima.googleplay_8.utils.UIUtils;

public class MainActivity extends BaseActivity {

	private PagerSlidingTabStrip mTabs;
	private ViewPager					mViewPager;
	private String[]					mMainTitles;
	private DrawerLayout				mDrawerLayout;
	private ActionBarDrawerToggle		mToggle;
	private FrameLayout	mMain_menu;

	/**初始化view*/
	@Override
	public void initView() {
		setContentView(R.layout.activity_main);
		mTabs = (PagerSlidingTabStrip) findViewById(R.id.main_tabs);
		mViewPager = (ViewPager) findViewById(R.id.main_viewpager);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawlayout);
		mMain_menu = (FrameLayout) findViewById(R.id.main_menu);
	}

	@Override
	public void initActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setLogo(R.drawable.ic_launcher);// 设置logo
		actionBar.setTitle("GooglePlay");

		// 显示返回按钮
		actionBar.setDisplayHomeAsUpEnabled(true);
		initActionBarToggle();
	}

	private void initActionBarToggle() {
		mToggle = new ActionBarDrawerToggle(//
				this, //
				mDrawerLayout, //
				R.drawable.ic_drawer_am, //
				R.string.open, //
				R.string.close//
		);

		// 同步状态的方法
		mToggle.syncState();
		// 设置mDrawerLayout拖动的监听
		mDrawerLayout.setDrawerListener(mToggle);
	}

	/**初始化Data*/
	@Override
	public void initData() {
		mMainTitles = UIUtils.getStringArr(R.array.main_titles);
		// MainPagerAdapter adapter = new MainPagerAdapter();

		// MainFragmentPagerAdapter adapter = new MainFragmentPagerAdapter(getSupportFragmentManager());

		MainFragmentStatePagerAdapter adapter = new MainFragmentStatePagerAdapter(getSupportFragmentManager());

		mViewPager.setAdapter(adapter);

		// Bind the tabs to the ViewPager
		// viewpager和tabs的一个绑定
		mTabs.setViewPager(mViewPager);
		
		MenuHolder menuHolder = new MenuHolder();
		mMain_menu.addView(menuHolder.getHolderView());
		menuHolder.setDataAndRefreshHolderView(null);
	}

	@Override
	public void initListener() {
		mTabs.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// 完成触发加载
				BaseFragment fragment = FragmentFactory.getFragment(position);
				if (fragment != null) {
					fragment.getLoadingPager().loadData();
				}
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				// TODO

			}

			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO

			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// mToggle控制打开关闭drawlayout
			mToggle.onOptionsItemSelected(item);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	class MainPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			if (mMainTitles != null) {
				return mMainTitles.length;
			}
			return 0;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			TextView tv = new TextView(UIUtils.getContext());
			tv.setText(mMainTitles[position]);
			container.addView(tv);
			return tv;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO
			container.removeView((View) object);
		}

		/**
		 * 必须要重写此方法
		 */
		@Override
		public CharSequence getPageTitle(int position) {
			// TODO
			return mMainTitles[position];
		}

	}

	class MainFragmentPagerAdapter extends FragmentPagerAdapter {

		public MainFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// 创建fragment
			Fragment fragment = FragmentFactory.getFragment(position);
			LogUtils.sf("初始化 " + mMainTitles[position]);
			return fragment;
		}

		@Override
		public int getCount() {
			if (mMainTitles != null) {
				return mMainTitles.length;
			}
			return 0;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			// TODO
			return mMainTitles[position];
		}

	}

	class MainFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

		public MainFragmentStatePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = FragmentFactory.getFragment(position);
			LogUtils.sf("初始化 " + mMainTitles[position]);
			return fragment;
		}

		@Override
		public int getCount() {
			if (mMainTitles != null) {
				return mMainTitles.length;
			}
			return 0;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			// TODO
			return mMainTitles[position];
		}

	}
}
