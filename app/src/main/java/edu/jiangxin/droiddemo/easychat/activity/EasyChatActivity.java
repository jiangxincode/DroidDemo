package edu.jiangxin.droiddemo.easychat.activity;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import edu.jiangxin.droiddemo.easychat.fragment.ContactsFragment;
import edu.jiangxin.droiddemo.easychat.fragment.SessionFragment;
import edu.jiangxin.droiddemo.easychat.utils.ToolBarUtil;

import java.util.ArrayList;
import java.util.List;

import edu.jiangxin.droiddemo.R;

public class EasyChatActivity extends AppCompatActivity {

	ViewPager2 mMainViewpager;
	TabLayout mTabLayout;

	LinearLayout			mMainBottom;

	private final List<Fragment>	mFragments	= new ArrayList<Fragment>();
	private ToolBarUtil		mToolBarUtil;
	private String[]		mToolBarTitleArr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_easychat);
		mMainViewpager = findViewById(R.id.easychat_viewpager);
		mTabLayout = findViewById(R.id.easychat_tabLayout);
		mMainBottom = findViewById(R.id.main_bottom);
		initData();
		initListener();
	}

	private void initListener() {
		mMainViewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
			@Override
			public void onPageSelected(int position) {
				// 修改颜色
				mToolBarUtil.changeColor(position);
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
		// 添加fragment到集合中
		mFragments.add(new SessionFragment());
		mFragments.add(new ContactsFragment());

		mMainViewpager.setAdapter(new MyPagerAdapter(this));

		// 底部按钮
		mToolBarUtil = new ToolBarUtil();
		// 文字内容
		mToolBarTitleArr = new String[] { "会话", "联系人" };
		// 图标内容
		int[] iconArr = { R.drawable.selector_meassage, R.drawable.selector_selfinfo };

		mToolBarUtil.createToolBar(mMainBottom, mToolBarTitleArr, iconArr);

		// 设置默认选中会话
		mToolBarUtil.changeColor(0);

		// TabLayout with ViewPager2
		new TabLayoutMediator(mTabLayout, mMainViewpager,
				(tab, position) -> tab.setText(mToolBarTitleArr[position])
		).attach();
	}

	class MyPagerAdapter extends FragmentStateAdapter {

		public MyPagerAdapter(@NonNull FragmentActivity fa) {
			super(fa);
		}

		@NonNull
		@Override
		public Fragment createFragment(int position) {
			return mFragments.get(position);
		}

		@Override
		public int getItemCount() {
			return mFragments.size();
		}
	}
}
