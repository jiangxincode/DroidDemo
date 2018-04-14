package edu.jiangxin.easymarry.activity.listview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.jiangxin.easymarry.R;

public class ExpandableListActivity extends Activity {

    private ExpandableListView expandableListView;

    //设置组视图的显示文字
    private List<String> group;           //组列表
    private List<List<String>> childText;     //子列表
    private List<List<Integer>> childLogo;     //子列表
    private ExpandableListAdapter adapter;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_expandable_list);
        expandableListView = findViewById(R.id.ev_view);
        group = new ArrayList<String>();
        childText = new ArrayList<List<String>>();
        childLogo = new ArrayList<List<Integer>>();
        addInfo("魏",
                new String[]{"夏侯惇", "甄姬", "许褚", "郭嘉", "司马懿", "杨修"},
                new Integer[]{R.mipmap.el_xia_hou_dun, R.mipmap.el_zhen_ji,
                        R.mipmap.el_xu_zhu, R.mipmap.el_guo_jia,
                        R.mipmap.el_si_ma_yi, R.mipmap.el_yang_xiu}
        );
        addInfo("蜀",
                new String[]{"马超", "张飞", "刘备", "诸葛亮", "黄月英", "赵云"},
                new Integer[]{R.mipmap.el_ma_chao, R.mipmap.el_zhang_fei,
                        R.mipmap.el_liu_bei, R.mipmap.el_zhu_ge_liang,
                        R.mipmap.el_huang_yue_ying, R.mipmap.el_zhao_yun}
        );
        addInfo("吴",
                new String[]{"吕蒙", "陆逊", "孙权", "周瑜", "孙尚香"},
                new Integer[]{R.mipmap.el_lv_meng, R.mipmap.el_lu_xun, R.mipmap.el_sun_quan,
                        R.mipmap.el_zhou_yu, R.mipmap.el_sun_shang_xiang}
        );

        adapter = new ExpandableListAdapter(this, group, childText, childLogo);
        expandableListView.setAdapter(adapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (mToast != null) {
                    mToast.cancel();
                }
                mToast = Toast.makeText(
                        ExpandableListActivity.this,
                        "你点击了" + adapter.getChild(groupPosition, childPosition),
                        Toast.LENGTH_SHORT);
                mToast.show();
                return false;
            }
        });
    }

    /**
     * 模拟给组、子列表添加数据
     */
    private void addInfo(String g, String[] c, Integer[] l) {
        group.add(g);
        List<String> childTextItem = new ArrayList<String>();
        List<Integer> childLogoItem = new ArrayList<Integer>();
        for (int i = 0; i < c.length; i++) {
            childTextItem.add(c[i]);
            childLogoItem.add(l[i]);
        }
        childLogo.add(childLogoItem);
        childText.add(childTextItem);
    }
}

class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<String> mGroup;           //组列表
    private List<List<String>> mChildText;     //子列表
    private List<List<Integer>> mChildLogo;     //子列表

    public ExpandableListAdapter(Context context, List<String> group, List<List<String>> childText,
                                 List<List<Integer>> childLogo) {
        this.mContext = context;
        this.mGroup = group;
        this.mChildText = childText;
        this.mChildLogo = childLogo;
    }

    //  获得某个父项
    @Override
    public Object getGroup(int groupPosition) {
        return mGroup.get(groupPosition);
    }

    //  获得某个父项的某个子项
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChildText.get(groupPosition).get(childPosition);
    }

    //  获得父项的数量
    @Override
    public int getGroupCount() {
        return mGroup.size();
    }

    //  获得某个父项的子项数目
    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildText.get(groupPosition).size();
    }

    //  获得某个父项的id
    @Override
    public long getGroupId(int parentPos) {
        return parentPos;
    }

    //  获得某个父项的某个子项的id
    @Override
    public long getChildId(int parentPos, int childPos) {
        return childPos;
    }

    //  按函数的名字来理解应该是是否具有稳定的id，这个方法目前一直都是返回false，没有去改动过
    @Override
    public boolean hasStableIds() {
        return true;
    }

    //  获得父项显示的view
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = convertView;
        GroupHolder holder = null;
        if (view == null) {
            holder = new GroupHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_expandlist_group_item, null);
            holder.groupName = view.findViewById(R.id.tv_expandlist_group_name);
            holder.groupArrow = view.findViewById(R.id.iv_expandlist_group);
            view.setTag(holder);
        } else {
            holder = (GroupHolder) view.getTag();
        }

        //判断是否已经打开列表
        if (isExpanded) {
            holder.groupArrow.setBackgroundResource(R.mipmap.el_arrow_down);
        } else {
            holder.groupArrow.setBackgroundResource(R.mipmap.el_arrow_right);
        }
        holder.groupName.setText(mGroup.get(groupPosition));

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        View view = convertView;
        ChildHolder holder = null;
        if (view == null) {
            holder = new ChildHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_expandlist_child_item, null);
            holder.childLogo = view.findViewById(R.id.iv_expandlist_child_avatar);
            holder.childTitle = view.findViewById(R.id.tv_expandlist_child_name);
            holder.childContent = view.findViewById(R.id.tv_expandlist_child_content);
            holder.childDivider = view.findViewById(R.id.iv_expandlist_child_divider);
            view.setTag(holder);
        } else {
            holder = (ChildHolder) view.getTag();
        }

        if (childPosition == getChildrenCount(groupPosition) - 1) {
            holder.childDivider.setVisibility(View.GONE);
        } else {
            holder.childDivider.setVisibility(View.VISIBLE);
        }

        holder.childLogo.setImageResource(mChildLogo.get(groupPosition).get(childPosition));
        holder.childTitle.setText(mChildText.get(groupPosition).get(childPosition));
        holder.childContent.setText(mChildText.get(groupPosition).get(childPosition));

        return view;
    }

    //  子项是否可选中，如果需要设置子项的点击事件，需要返回true
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}

class GroupHolder {
    public TextView groupName;
    public ImageView groupArrow;
}

class ChildHolder {
    public TextView childTitle, childContent;
    public ImageView childLogo;
    public ImageView childDivider;
}

