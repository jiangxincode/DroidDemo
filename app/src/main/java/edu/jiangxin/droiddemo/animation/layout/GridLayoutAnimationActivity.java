package edu.jiangxin.droiddemo.animation.layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.GridLayoutAnimationController;
import android.widget.Button;
import android.widget.GridView;

import androidx.annotation.Nullable;

import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import edu.jiangxin.droiddemo.R;


public class GridLayoutAnimationActivity extends Activity {

  Button mAdd;
  GridView mGridView;
  private CommonAdapter<String> mCommonAdapter;

  List<String> mStrings = new ArrayList<>();
  private int mInitPos;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_layout_animation_grid);
    mAdd = findViewById(R.id.add);
    mAdd.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        initData();

        mCommonAdapter.notifyDataSetChanged();
      }
    });
    mGridView = findViewById(R.id.grid_view);
    initView();
  }

  private void initView() {

    initData();

    mGridView.setAdapter(mCommonAdapter =
        new CommonAdapter<String>(this, R.layout.item_main, mStrings) {
          @Override protected void convert(ViewHolder viewHolder, String item, int position) {
            viewHolder.setText(R.id.text_name, item);
          }
        });

    initAnimation();
  }

  private void initData() {

    for (int i = 0; i < 10; ++i) {
      mStrings.add(String.valueOf(++mInitPos) + "个条目被添加");
    }
  }

  private void initAnimation() {
    Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_in_from_left);
    GridLayoutAnimationController gridLayoutAnimationController =
        new GridLayoutAnimationController(animation);
    gridLayoutAnimationController.setColumnDelay(0.75f);
    gridLayoutAnimationController.setRowDelay(0.5f);
    gridLayoutAnimationController.setDirection(GridLayoutAnimationController.DIRECTION_BOTTOM_TO_TOP
        | GridLayoutAnimationController.DIRECTION_RIGHT_TO_LEFT);
    gridLayoutAnimationController.setDirectionPriority(
        GridLayoutAnimationController.DIRECTION_BOTTOM_TO_TOP);
    gridLayoutAnimationController.setInterpolator(new DecelerateInterpolator());

    mGridView.setLayoutAnimation(gridLayoutAnimationController);
    mGridView.startLayoutAnimation();
  }
}
