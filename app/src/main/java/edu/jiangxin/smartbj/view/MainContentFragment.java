

package edu.jiangxin.smartbj.view;

import androidx.viewpager.widget.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

import edu.jiangxin.smartbj.R;
import edu.jiangxin.smartbj.basepage.BaseTagPage;
import edu.jiangxin.smartbj.basepage.GovAffairsBaseTagPager;
import edu.jiangxin.smartbj.basepage.HomeBaseTagPager;
import edu.jiangxin.smartbj.basepage.NewCenterBaseTagPager;
import edu.jiangxin.smartbj.basepage.SettingCenterBaseTagPager;
import edu.jiangxin.smartbj.basepage.SmartServiceBaseTagPager;

/**
 * 主界面的fragment
 */
public class MainContentFragment extends BaseFragment {

    private MyViewPager viewPager;

    private RadioGroup rg_radios;

    private List<BaseTagPage> pages = new ArrayList<BaseTagPage>();

    private int selectIndex;//设置当前选择的页面编号

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
    public void leftMenuClickSwitchPage(int subSelectionIndex) {
        BaseTagPage baseTagPage = pages.get(selectIndex);
        baseTagPage.switchPage(subSelectionIndex);
    }


    /**
     * 设置选中的页面
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

        viewPager = root.findViewById(R.id.vp_main_content_pages);
        rg_radios = root.findViewById(R.id.rg_content_radios);

        return root;
    }

    @Override
    public void initData() {
        pages.add(new HomeBaseTagPager(mainActivity));
        pages.add(new NewCenterBaseTagPager(mainActivity));
        pages.add(new SmartServiceBaseTagPager(mainActivity));
        pages.add(new GovAffairsBaseTagPager(mainActivity));
        pages.add(new SettingCenterBaseTagPager(mainActivity));

        MyAdapter adapter = new MyAdapter();
        viewPager.setAdapter(adapter);

        //viewPager.setOffscreenPageLimit(2);//设置预加载为：前后各2个页面

        //设置默认选择首页
        switchPage();
        //设置第一个按钮被选中(首页)
        rg_radios.check(R.id.rb_main_content_home);

    }

    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return pages.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            System.out.println("instantiateItem:" + position);
            BaseTagPage baseTagPage = pages.get(position);
            View root = baseTagPage.getRoot();
            container.addView(root);

            //加载数据库
            baseTagPage.initData();
            return root;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            System.out.println("destroyItem:" + position);
            container.removeView((View) object);
        }

    }

}
