package edu.jiangxin.droiddemo.smartbj.view;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.jiangxin.droiddemo.smartbj.activity.MainActivity;

public abstract class BaseFragment extends Fragment {

    protected MainActivity mainActivity;//上下文

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();//获取fragment所在Activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = initView(); //View
        return root;
    }

    /**
     * 必须覆盖此方法来完全界面的显示
     *
     * @return
     */
    public abstract View initView();


    /**
     * 子类覆盖此方法来完成数据的初始化
     */
    public void initData() {

    }

    /**
     * 子类覆盖此方法来完成事件的添加
     */
    public void initEvent() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        //初始化事件和数据
        super.onActivityCreated(savedInstanceState);
        initData();//初始化数据
        initEvent();//初始化事件
    }

}
