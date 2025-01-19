package edu.jiangxin.droiddemo.activity.listview.swipemenulistview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.annotation.NonNull;

import edu.jiangxin.droiddemo.R;
import edu.jiangxin.droiddemo.Utils;

public class SwipeMenuCreatorImpl implements SwipeMenuCreator {
    @NonNull
    private final Context mContext;

    public SwipeMenuCreatorImpl(@NonNull Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void create(SwipeMenu menu) {
        // create "open" item
        SwipeMenuItem openItem1 = new SwipeMenuItem(mContext);
        openItem1.setBackground(new ColorDrawable(mContext.getColor(R.color.colorLightBlack)));
        openItem1.setWidth(Utils.dp2px(mContext, 90));
        openItem1.setTitle("取消关注");
        openItem1.setTitleSize(18);
        openItem1.setTitleColor(Color.WHITE);
        menu.addMenuItem(openItem1);

        SwipeMenuItem openItem = new SwipeMenuItem(mContext);
        openItem.setBackground(new ColorDrawable(mContext.getColor(R.color.colorRed)));
        openItem.setWidth(Utils.dp2px(mContext, 70));
        openItem.setTitle("Delete");
        openItem.setTitleSize(18);
        openItem.setTitleColor(Color.WHITE);
        menu.addMenuItem(openItem);
    }
}
