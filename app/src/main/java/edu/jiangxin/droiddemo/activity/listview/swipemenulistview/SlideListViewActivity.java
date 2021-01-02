package edu.jiangxin.droiddemo.activity.listview.swipemenulistview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.jiangxin.droiddemo.R;

public class SlideListViewActivity extends Activity {
    private List<SlideListViewEntity> mSlideListViewEntities;
    private SlideListViewAdapter mAdapter;
    private SwipeMenuListView mSwipeMenuListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        mSlideListViewEntities = new ArrayList<>();
        mSwipeMenuListView = findViewById(R.id.listView);
        for (int i = 0; i < 3; ++i) {
            SlideListViewEntity slideListViewEntity = new SlideListViewEntity();
            slideListViewEntity.setName("test" + i);
            slideListViewEntity.setType(i);
            mSlideListViewEntities.add(slideListViewEntity);
        }
        mAdapter = new SlideListViewAdapter(this, mSlideListViewEntities);
        mSwipeMenuListView.setAdapter(mAdapter);

        mSwipeMenuListView.setMenuCreator(new SwipeMenuCreatorImpl(getApplicationContext()));

        mSwipeMenuListView.setOnMenuItemClickListener((position, menu, index) -> {
            switch (index) {
                case 0:
                    break;
                case 1:
                    mSlideListViewEntities.remove(position);
                    mAdapter.notifyDataSetChanged();
                    break;
            }
            return false;
        });

        mSwipeMenuListView.setOnItemClickListener((parent, view, position, id) -> Toast.makeText(SlideListViewActivity.this, mSlideListViewEntities.get(position).getName() + " click", Toast.LENGTH_SHORT).show());
    }
}

class SlideListViewAdapter extends BaseAdapter {

    private final List<SlideListViewEntity> mSlideListViewEnties;
    private final Context mContext;

    public SlideListViewAdapter(Context context, List<SlideListViewEntity> mSlideListViewEnties) {
        this.mContext = context;
        this.mSlideListViewEnties = mSlideListViewEnties;
    }

    @Override
    public int getCount() {
        return mSlideListViewEnties.size();
    }

    @Override
    public SlideListViewEntity getItem(int position) {
        return mSlideListViewEnties.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        SlideListViewHolder holder = null;

        if (view == null) {
            holder = new SlideListViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_slide_list_view_item, null);
            holder.iv_icon = view.findViewById(R.id.iv_icon);
            holder.tv_name = view.findViewById(R.id.tv_name);

            view.setTag(holder);
        } else {
            holder = (SlideListViewHolder) view.getTag();
        }
        holder.tv_name.setText(mSlideListViewEnties.get(position).getName());

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

class SlideListViewHolder {
    ImageView iv_icon;
    TextView tv_name;
}