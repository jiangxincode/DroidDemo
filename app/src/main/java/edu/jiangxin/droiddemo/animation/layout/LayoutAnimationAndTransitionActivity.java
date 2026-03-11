package edu.jiangxin.droiddemo.animation.layout;

import android.animation.Animator;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import edu.jiangxin.droiddemo.R;

/**
 * LayoutAnimation 是API Level 1 就已经有的，LayoutAnimation是对于ViewGroup控件所有的child view的操作，
 * 也就是说它是用来控制ViewGroup中所有的child view 显示的动画。
 * LayoutTransition 是API Level 11 才出现的。LayoutTransition的动画效果，只有当ViewGroup中有View添加、删除、隐藏、显示的时候才会体现出来。
 * 是一个布局改变动画，只需要在XML中设置animateLayoutChanges="true"或者在Java代码中添加一个LayoutTransition对象即可实现任何ViewGroup布局改变时的动画。
 * 目前系统中支持以下5种状态变化，应用程序可以为下面任意一种状态设置自定义动画：
 * 1、APPEARING：容器中出现一个视图。
 * 2、DISAPPEARING：容器中消失一个视图。
 * 3、CHANGING：布局改变导致某个视图随之改变，例如调整大小，但不包括添加或者移除视图。
 * 4、CHANGE_APPEARING：其他视图的出现导致某个视图改变。
 * 5、CHANGE_DISAPPEARING：其他视图的消失导致某个视图改变。
 */
public class LayoutAnimationAndTransitionActivity extends Activity {

    private Button mAdd;
    private ListView mList;
    private BaseAdapter mBaseAdapter;

    private final List<String> mStrings = new ArrayList<>();
    private int mInitPos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_animation_list);
        mAdd = findViewById(R.id.add);
        mList = findViewById(android.R.id.list);
        mAdd.setOnClickListener(v -> {
            addItems();
            mBaseAdapter.notifyDataSetChanged();
        });
        initView();
    }

    private void initView() {
        addItems();
        mList.setAdapter(mBaseAdapter = new ListViewAdapter(this, mStrings));
        startLayoutAnimation();
        initLayoutTransition();
    }

    private void initLayoutTransition() {
        LayoutTransition layoutTransition = new LayoutTransition();
        layoutTransition.addTransitionListener(new LayoutTransition.TransitionListener() {
            @Override
            public void startTransition(LayoutTransition transition, ViewGroup container, View view,
                                        int transitionType) {
            }

            @Override
            public void endTransition(LayoutTransition transition, ViewGroup container, View view,
                                      int transitionType) {
            }
        });
        replaceDefaultTransitionAnimator(layoutTransition);

        mList.setLayoutTransition(layoutTransition);
    }

    private void replaceDefaultTransitionAnimator(LayoutTransition layoutTransition) {
        //使用翻转进入的动画代替默认动画
        Animator appearAnim = ObjectAnimator
                .ofFloat(null, "rotationY", 90f, 0)
                .setDuration(layoutTransition.getDuration(LayoutTransition.APPEARING));
        layoutTransition.setAnimator(LayoutTransition.APPEARING, appearAnim);

        //使用翻转消失的动画代替默认动画
        Animator disappearAnim = ObjectAnimator.ofFloat(null, "rotationX", 0,
                90f).setDuration(
                layoutTransition.getDuration(LayoutTransition.DISAPPEARING));
        layoutTransition.setAnimator(LayoutTransition.DISAPPEARING, disappearAnim);

        //使用滑动动画代替默认布局改变的动画
        //这个动画会让视图滑动进入并短暂地缩小一半，具有平滑和缩放的效果
        PropertyValuesHolder pvhSlide = PropertyValuesHolder.ofFloat("y", 0, 1);
        PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofFloat("scaleY",
                1f, 0.5f, 1f);
        PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofFloat("scaleX",
                1f, 0.5f, 1f);

        //这里将上面三个动画综合
        Animator changingDisappearAnim = ObjectAnimator.ofPropertyValuesHolder(
                this, pvhSlide, pvhScaleY, pvhScaleX);
        changingDisappearAnim.setDuration(layoutTransition.getDuration(LayoutTransition.CHANGE_DISAPPEARING));
        layoutTransition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING,
                changingDisappearAnim);
    }

    private void startLayoutAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_in_from_left);
        LayoutAnimationController animationController = new LayoutAnimationController(animation,
                1f);
        animationController.setOrder(LayoutAnimationController.ORDER_NORMAL);
        mList.setLayoutAnimation(animationController);
        animationController.setInterpolator(new AccelerateDecelerateInterpolator());
        mList.startLayoutAnimation();
    }

    private void addItems() {
        for (int i = 0; i < 5; ++i) {
            mStrings.add(++mInitPos + "个条目被添加");
        }
    }
}

class ListViewAdapter extends BaseAdapter {

    private final List<String> mList;
    private final Context mContext;

    public ListViewAdapter(Context context, List<String> mList) {
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
        edu.jiangxin.droiddemo.animation.layout.ViewHolder holder;

        if (view == null) {
            holder = new edu.jiangxin.droiddemo.animation.layout.ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.layout_animation_item, parent, false);
            holder.title = view.findViewById(R.id.title);
            view.setTag(holder);
        } else {
            holder = (edu.jiangxin.droiddemo.animation.layout.ViewHolder) view.getTag();
        }
        holder.title.setText(mList.get(position));
        return view;
    }
}
