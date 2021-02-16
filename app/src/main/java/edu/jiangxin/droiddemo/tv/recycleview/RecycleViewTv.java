package edu.jiangxin.droiddemo.tv.recycleview;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import java.util.ArrayList;

import edu.jiangxin.droiddemo.R;

/**
 * Android TV中选中放大，添加发光状态的解决方案: https://blog.csdn.net/u014709812/article/details/53691649
 */
public class RecycleViewTv extends AppCompatActivity implements MyAppRecyclerView.BorderListener {
    private static final String TAG = "RecycleViewTv";
    private GridLayoutManager mLayoutManager;
    private MyAppRecyclerAdapter mAdapter;
    private MyAppRecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.TvTheme);
        setContentView(R.layout.activity_recycle_view_tv);
        mRecyclerView = (MyAppRecyclerView) this.findViewById(R.id.rv_my_app_content);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new MyAppGridLayoutManager(this, 6);
        mAdapter = new MyAppRecyclerAdapter(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setFocusable(false);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setRecyclerView(mRecyclerView);
        mRecyclerView.setBorderListener(this);
        mRecyclerView.addItemDecoration(new MyAPPGridItemDecoration(this, true));
        mAdapter.addDatas(getDatas());
    }

    private ArrayList<String> getDatas() {
        ArrayList<String> datas = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            datas.add(" 第" + i + "个数据");
        }
        return datas;
    }

    @Override
    public boolean onKeyBottomDown() {
        Log.e(TAG, "onKeyBottomDown");
        return false;
    }

    @Override
    public boolean onKeyTopUp() {
        Log.e(TAG, "onKeyTopUp");
        return false;
    }

    @Override
    public boolean onKeyLeftEnd() {
        Log.e(TAG, "onKeyLeftEnd");
        return false;
    }

    @Override
    public boolean onKeyRightEnd() {
        Log.e(TAG, "onKeyRightEnd");
        return false;
    }
}
