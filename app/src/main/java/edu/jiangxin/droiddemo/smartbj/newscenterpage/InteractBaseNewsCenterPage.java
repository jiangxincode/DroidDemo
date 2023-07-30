

package edu.jiangxin.droiddemo.smartbj.newscenterpage;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import edu.jiangxin.droiddemo.smartbj.activity.MainActivity;

public class InteractBaseNewsCenterPage extends BaseNewsCenterPage {

    public InteractBaseNewsCenterPage(MainActivity mainActivity) {
        super(mainActivity);
    }

    @Override
    public View initView() {
        // 要展示的内容，
        TextView tv = new TextView(mainActivity);
        tv.setText("互动的内容");
        tv.setTextSize(25);
        tv.setGravity(Gravity.CENTER);
        return tv;
    }

}
