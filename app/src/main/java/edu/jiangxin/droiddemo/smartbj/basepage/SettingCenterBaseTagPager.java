

package edu.jiangxin.droiddemo.smartbj.basepage;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import edu.jiangxin.droiddemo.smartbj.activity.MainActivity;

public class SettingCenterBaseTagPager extends BaseTagPage {

    public SettingCenterBaseTagPager(MainActivity context) {
        super(context);
    }

    @Override
    public void initData() {
        // 屏蔽菜单按钮
        ib_menu.setVisibility(View.GONE);
        // 设置本page的标题
        tv_title.setText("设置中心");

        // 要展示的内容，
        TextView tv = new TextView(mainActivity);
        tv.setText("设置中心的内容");
        tv.setTextSize(25);
        tv.setGravity(Gravity.CENTER);

        // 替换掉白纸
        fl_content.addView(tv);// 添加自己的内容到白纸上
        super.initData();
    }

}
