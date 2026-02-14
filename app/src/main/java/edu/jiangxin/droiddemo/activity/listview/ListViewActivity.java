package edu.jiangxin.droiddemo.activity.listview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.jiangxin.droiddemo.R;
import edu.jiangxin.droiddemo.activity.listview.swipemenulistview.SlideListViewActivity;

public class ListViewActivity extends Activity {

    private ListView lv_view;
    private ListViewMenuAdapter adapter;
    private List<String> menuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        lv_view = findViewById(R.id.lv_menu_view);
        menuList = new ArrayList<String>();
        String[] menu = new String[]{"ExpandableList", "GridView", "ListView侧滑",
                "ViewFlipper"};

        Collections.addAll(menuList, menu);

        adapter = new ListViewMenuAdapter(this, menuList);
        lv_view.setAdapter(adapter);
        lv_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    startActivity(new Intent(ListViewActivity.this, ExpandableListActivity.class));
                } else if (position == 1) {
                    startActivity(new Intent(ListViewActivity.this, GridViewActivity.class));
                } else if (position == 2) {
                    startActivity(new Intent(ListViewActivity.this, SlideListViewActivity.class));
                } else if (position == 3) {
                    startActivity(new Intent(ListViewActivity.this, ViewFlipperActivity.class));
                }
            }
        });
    }

}

class ListViewMenuAdapter extends BaseAdapter {

    private final List<String> mMenuList;
    private final Context mContext;

    public ListViewMenuAdapter(Context context, List<String> mMenuList) {
        this.mContext = context;
        this.mMenuList = mMenuList;
    }

    @Override
    public int getCount() {
        return mMenuList.size();
    }

    @Override
    public String getItem(int position) {
        return mMenuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    @SuppressWarnings("unchecked")
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;

        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_listview_item, parent, false);
            holder.tvMenu = view.findViewById(R.id.tv_list_view_menu_item);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tvMenu.setText(mMenuList.get(position));
        return view;
    }


}

class ViewHolder {
    TextView tvMenu;
}
