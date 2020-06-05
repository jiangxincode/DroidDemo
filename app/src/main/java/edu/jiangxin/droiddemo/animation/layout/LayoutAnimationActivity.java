package edu.jiangxin.droiddemo.animation.layout;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ListView;


import androidx.annotation.Nullable;

import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import edu.jiangxin.droiddemo.R;


public class LayoutAnimationActivity extends Activity {

  Button mAdd;
 ListView mList;
  private CommonAdapter<String> mCommonAdapter;

  List<String> mStrings = new ArrayList<>();
  private int mInitPos;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_layout_animation_list);
    mAdd = findViewById(R.id.add);
    mList = findViewById(android.R.id.list);
    mAdd.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        initData();
        mCommonAdapter.notifyDataSetChanged();
      }
    });

    initView();
  }

  private void initView() {

    initData();

    mList.setAdapter(mCommonAdapter =
        new CommonAdapter<String>(this, R.layout.item_main, mStrings) {
          @Override protected void convert(ViewHolder viewHolder, String item, int position) {
            viewHolder.setText(R.id.text_name, item);
          }
        });

    initAnimation();
    initTransition();
  }

  private void initTransition() {
    LayoutTransition mTransitioner = new LayoutTransition();
    mTransitioner.addTransitionListener(new LayoutTransition.TransitionListener() {
      @Override
      public void startTransition(LayoutTransition transition, ViewGroup container, View view,
          int transitionType) {

      }

      @Override
      public void endTransition(LayoutTransition transition, ViewGroup container, View view,
          int transitionType) {

      }
    });
    mList.setLayoutTransition(mTransitioner);
  }

  private void initAnimation() {
    Animation animation = AnimationUtils.loadAnimation(this,R.anim.slide_in_from_left);
    LayoutAnimationController animationController = new LayoutAnimationController(animation,
         1f);
    animationController.setOrder(LayoutAnimationController.ORDER_NORMAL);
    mList.setLayoutAnimation(animationController);
    animationController.setInterpolator(new AccelerateDecelerateInterpolator());
    mList.startLayoutAnimation();
  }

  private void initData() {

    for (int i = 0; i < 5; ++i) {
      mStrings.add(String.valueOf(++mInitPos)+"个条目被添加");
    }
  }
}
