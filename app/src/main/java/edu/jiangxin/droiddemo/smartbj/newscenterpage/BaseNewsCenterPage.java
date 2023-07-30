package edu.jiangxin.droiddemo.smartbj.newscenterpage;

import android.view.View;

import edu.jiangxin.droiddemo.smartbj.activity.MainActivity;

public abstract class BaseNewsCenterPage {
    protected MainActivity mainActivity;
    protected View root;//根布局

    public BaseNewsCenterPage(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        root = initView();
        initEvent();
    }

    /**
     * 子类覆盖此方法完成事件的处理
     */
    public void initEvent() {

    }

    /**
     * 子类覆盖此方法来显示自定义的View
     *
     * @return
     */
    public abstract View initView();

    public View getRoot() {
        return root;
    }


    /**
     * 子类覆盖此方法完成数据的显示
     */
    public void initData() {

    }


}
