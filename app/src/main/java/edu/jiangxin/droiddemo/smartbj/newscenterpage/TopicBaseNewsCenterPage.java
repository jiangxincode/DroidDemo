package edu.jiangxin.droiddemo.smartbj.newscenterpage;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import edu.jiangxin.droiddemo.smartbj.activity.MainActivity;


public class TopicBaseNewsCenterPage extends BaseNewsCenterPage {

    public TopicBaseNewsCenterPage(MainActivity mainActivity) {
        super(mainActivity);
    }

    @Override
    public View initView() {
        TextView tv = new TextView(mainActivity);
        tv.setText("专题的内容");
        tv.setTextSize(25);
        tv.setGravity(Gravity.CENTER);
        return tv;
    }

}
