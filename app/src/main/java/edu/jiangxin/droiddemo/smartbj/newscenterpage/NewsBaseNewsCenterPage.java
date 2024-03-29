package edu.jiangxin.droiddemo.smartbj.newscenterpage;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import edu.jiangxin.droiddemo.R;
import edu.jiangxin.droiddemo.smartbj.activity.MainActivity;
import edu.jiangxin.droiddemo.smartbj.domain.NewsCenterData;
import edu.jiangxin.droiddemo.smartbj.newstpipage.TPINewsNewsCenterPager;

public class NewsBaseNewsCenterPage extends BaseNewsCenterPage {

    @ViewInject(R.id.newcenter_vp)
    private ViewPager vp_newscenter;

    @ViewInject(R.id.newcenter_tpi)
    private TabPageIndicator tpi_newscenter;

    @OnClick(R.id.newcenter_ib_nextpage)
    public void next(View v) {
        //切换到下一个页面
        vp_newscenter.setCurrentItem(vp_newscenter.getCurrentItem() + 1);
    }

    private List<NewsCenterData.NewsData.ViewTagData> viewTagDatas = new ArrayList<NewsCenterData.NewsData.ViewTagData>(); // 页签的数据

    public NewsBaseNewsCenterPage(MainActivity mainActivity,
                                  List<NewsCenterData.NewsData.ViewTagData> children) {
        super(mainActivity);
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

            }

            @Override
            public void onPageScrollStateChanged(int state) {
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
     * @ svn提交者：$Author: gd $ @ 提交时间: $Date: 2015-07-07 10:35:57 +0800 (Tue, 07 Jul 2015) $ @ 当前版本: $Rev: 39 $
     */
    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
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
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // 要展示的内容，
            TPINewsNewsCenterPager tpiPager = new TPINewsNewsCenterPager(mainActivity, viewTagDatas.get(position));
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
            container.removeView((View) object);
        }

    }

}
