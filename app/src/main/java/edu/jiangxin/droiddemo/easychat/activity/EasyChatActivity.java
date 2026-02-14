package edu.jiangxin.droiddemo.easychat.activity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import edu.jiangxin.droiddemo.easychat.fragment.ContactsFragment;
import edu.jiangxin.droiddemo.easychat.fragment.SessionFragment;
import edu.jiangxin.droiddemo.easychat.utils.ToolBarUtil;

import java.util.ArrayList;
import java.util.List;

import edu.jiangxin.droiddemo.R;

public class EasyChatActivity extends AppCompatActivity {
	TextView				mMainTvTitle;

	ViewPager mMainViewpager;

	LinearLayout			mMainBottom;

	private final List<Fragment>	mFragments	= new ArrayList<Fragment>();
	private ToolBarUtil		mToolBarUtil;
	private String[]		mToolBarTitleArr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_easychat);
		mMainTvTitle = findViewById(R.id.main_tv_title);
		mMainViewpager = findViewById(R.id.easychat_viewpager);
		mMainBottom = findViewById(R.id.main_bottom);
		initData();
		initListener();
	}

	private void initListener() {
		mMainViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				// 修改颜色
				mToolBarUtil.changeColor(position);
				// 修改title
				mMainTvTitle.setText(mToolBarTitleArr[position]);
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

		mToolBarUtil.setOnToolBarClickListener(new ToolBarUtil.OnToolBarClickListener() {
			@Override
			public void onToolBarClick(int position) {
				mMainViewpager.setCurrentItem(position);
			}
		});
	}

	private void initData() {
		// viewPager-->view-->pagerAdapter
		// viewPager-->fragment-->fragmentPagerAdapter-->fragment数量比较少
		// viewPager-->fragment-->fragmentStatePagerAdapter

		// 添加fragment到集合中
		mFragments.add(new SessionFragment());
		mFragments.add(new ContactsFragment());

		mMainViewpager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

		// 底部按钮
		mToolBarUtil = new ToolBarUtil();
		// 文字内容
		mToolBarTitleArr = new String[] { "会话", "联系人" };
		// 图标内容
		int[] iconArr = { R.drawable.selector_meassage, R.drawable.selector_selfinfo };

		mToolBarUtil.createToolBar(mMainBottom, mToolBarTitleArr, iconArr);

		// 设置默认选中会话
		mToolBarUtil.changeColor(0);
	}

	class MyPagerAdapter extends FragmentPagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
		}

		@Override
		public Fragment getItem(int position) {
			return mFragments.get(position);
		}

		@Override
		public int getCount() {
			return 2;
		}
	}
}
