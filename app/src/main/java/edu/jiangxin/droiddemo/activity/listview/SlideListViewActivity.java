package edu.jiangxin.droiddemo.activity.listview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.jiangxin.droiddemo.R;
import edu.jiangxin.droiddemo.view.swipemenulistview.SwipeMenu;
import edu.jiangxin.droiddemo.view.swipemenulistview.SwipeMenuCreator;
import edu.jiangxin.droiddemo.view.swipemenulistview.SwipeMenuItem;
import edu.jiangxin.droiddemo.view.swipemenulistview.SwipeMenuListView;

public class SlideListViewActivity extends Activity {
    private List<SlideListViewEntity> mAppList;
    private SlideListViewAdapter mAdapter;
    private SwipeMenuListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        mAppList = new ArrayList<SlideListViewEntity>();
        mListView = findViewById(R.id.listView);
        for (int i = 0; i < 3; ++i) {
            SlideListViewEntity map = new SlideListViewEntity();
            map.setName("test" + i);
            map.setType(i);
            mAppList.add(map);
        }
        mAdapter = new SlideListViewAdapter(this, mAppList);
        mListView.setAdapter(mAdapter);

        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item

                SwipeMenuItem openItem1 = new SwipeMenuItem(getApplicationContext());
                openItem1.setBackground(new ColorDrawable(getResources().getColor(R.color.colorLightBlack)));
                openItem1.setWidth(dp2px(90));
                openItem1.setTitle("取消关注");
                openItem1.setTitleSize(18);
                openItem1.setTitleColor(Color.WHITE);
                menu.addMenuItem(openItem1);

                SwipeMenuItem openItem = new SwipeMenuItem(getApplicationContext());
                openItem.setBackground(new ColorDrawable(getResources().getColor(R.color.colorRed)));
                openItem.setWidth(dp2px(70));
                openItem.setTitle("Delete");
                openItem.setTitleSize(18);
                openItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(openItem);
            }
        };
        mListView.setMenuCreator(creator);

        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        break;
                    case 1:
                        mAppList.remove(position);
                        mAdapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(SlideListViewActivity.this, mAppList.get(position).getName() + " click", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

}

class SlideListViewAdapter extends BaseAdapter {

    private List<SlideListViewEntity> mAppList;
    private Context mContext;

    public SlideListViewAdapter(Context context, List<SlideListViewEntity> mAppList) {
        this.mContext = context;
        this.mAppList = mAppList;
    }

    @Override
    public int getCount() {
        return mAppList.size();
    }

    @Override
    public SlideListViewEntity getItem(int position) {
        return mAppList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        SlieListViewHolder holder = null;

        if (view == null) {
            holder = new SlieListViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_slide_list_view_item, null);
            holder.iv_icon = view.findViewById(R.id.iv_icon);
            holder.tv_name = view.findViewById(R.id.tv_name);

            view.setTag(holder);
        } else {
            holder = (SlieListViewHolder) view.getTag();
        }
        holder.tv_name.setText(mAppList.get(position).getName());

        holder.iv_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "iv_icon_click", Toast.LENGTH_SHORT).show();
            }
        });
        holder.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "iv_icon_click", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


    public boolean getSwipEnableByPosition(int position) {
        return position % 2 != 0;
    }
}

class SlideListViewEntity {

    private int id;
    private int type;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class SlieListViewHolder {
    ImageView iv_icon;
    TextView tv_name;
}