package edu.jiangxin.droiddemo.animation.layout;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.GridLayoutAnimationController;
import android.widget.BaseAdapter;
import android.widget.GridView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import edu.jiangxin.droiddemo.R;


public class GridLayoutAnimationActivity extends Activity {

    GridView mGridView;
    List<String> mStrings = new ArrayList<>();
    private int mInitPos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_animation_grid);
        mGridView = findViewById(R.id.grid_view);
        initView();
    }

    private void initView() {
        for (int i = 0; i < 10; ++i) {
            mStrings.add(++mInitPos + "个条目被添加");
        }
        mGridView.setAdapter(new GridViewAdapter(this, mStrings));
        initAnimation();
    }

    private void initAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_in_from_left);
        GridLayoutAnimationController gridLayoutAnimationController =
                new GridLayoutAnimationController(animation);
        gridLayoutAnimationController.setColumnDelay(0.75f);
        gridLayoutAnimationController.setRowDelay(0.5f);
        gridLayoutAnimationController.setDirection(GridLayoutAnimationController.DIRECTION_BOTTOM_TO_TOP
                | GridLayoutAnimationController.DIRECTION_RIGHT_TO_LEFT);
        gridLayoutAnimationController.setDirectionPriority(GridLayoutAnimationController.PRIORITY_COLUMN);
        gridLayoutAnimationController.setInterpolator(new DecelerateInterpolator());

        mGridView.setLayoutAnimation(gridLayoutAnimationController);
        mGridView.startLayoutAnimation();
    }
}

class GridViewAdapter extends BaseAdapter {

    private final List<String> mList;
    private final Context mContext;

    public GridViewAdapter(Context context, List<String> mList) {
        this.mContext = context;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public String getItem(int position) {
        return mList.get(position);
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
            view = LayoutInflater.from(mContext).inflate(R.layout.layout_animation_item, parent, false);
            holder.title = view.findViewById(R.id.title);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.title.setText(mList.get(position));
        return view;
    }
}
