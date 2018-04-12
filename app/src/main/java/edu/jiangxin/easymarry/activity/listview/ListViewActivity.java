package edu.jiangxin.easymarry.activity.listview;

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
import java.util.List;

import edu.jiangxin.easymarry.R;

public class ListViewActivity extends Activity {

    private ListView lv_view;
    private ListViewMenuAdapter adapter;
    private List<String> menuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        findViewById();
        ininData();
    }

    public void findViewById() {
        lv_view = (ListView) findViewById(R.id.lv_menu_view);
    }

    public void ininData() {
        menuList = new ArrayList<String>();
        String[] menu = new String[]{"ExpandableList", "GridView", "ListView侧滑",
                "ViewFlipperActivity"};

        for (int i = 0; i < menu.length; ++i) {
            menuList.add(menu[i]);
        }

        adapter = new ListViewMenuAdapter(this, menuList);
        lv_view.setAdapter(adapter);
        lv_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent(position);
            }
        });
    }

    public void intent(int position) {
        if (position == 0) {
            startActivity(new Intent(this, ExpandableListActivity.class));
        } else if (position == 1) {
            startActivity(new Intent(this, GridViewActivity.class));
        } else if (position == 2) {
            startActivity(new Intent(this, SlideListViewActivity.class));
        } else if (position == 3) {
            startActivity(new Intent(this, ViewFlipperActivity.class));
        }
    }

}

class ListViewMenuAdapter extends BaseAdapter {

    private List<String> mMenuList;
    private Context mContext;

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
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder = null;

        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_listview_item, null);
            holder.tvMenu = (TextView) view.findViewById(R.id.tv_list_view_menu_item);

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
