package com.itheima.googleplay_8.fragment;

import java.util.List;
import java.util.Random;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.googleplay_8.base.BaseFragment;
import com.itheima.googleplay_8.base.LoadingPager.LoadedResult;
import com.itheima.googleplay_8.protocol.HotProtocol;
import com.itheima.googleplay_8.utils.UIUtils;
import com.itheima.googleplay_8.views.FlowLayout;

/**
 * @author  Administrator
 * @time 	2015-7-15 下午3:11:37
 * @des	TODO
 *
 * @version $Rev: 34 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2015-07-18 15:46:46 +0800 (星期六, 18 七月 2015) $
 * @updateDes TODO
 */
public class HotFragment extends BaseFragment {

	private List<String>	mDatas;

	@Override
	public LoadedResult initData() {// 真正加载数据
		HotProtocol protocol = new HotProtocol();
		try {
			mDatas = protocol.loadData(0);
			return checkState(mDatas);
		} catch (Exception e) {
			e.printStackTrace();
			return LoadedResult.ERROR;
		}
	}

	@Override
	public View initSuccessView() {
		// 返回成功的视图
		ScrollView scrollView = new ScrollView(UIUtils.getContext());
		// 创建流式布局
		FlowLayout fl = new FlowLayout(UIUtils.getContext());

		for (final String data : mDatas) {
			TextView tv = new TextView(UIUtils.getContext());
			tv.setText(data);
			tv.setTextColor(Color.WHITE);
			tv.setTextSize(16);
			int padding = UIUtils.dip2Px(5);
			tv.setPadding(padding, padding, padding, padding);
			tv.setGravity(Gravity.CENTER);

			// tv.setBackgroundResource(R.drawable.shape_hot_fl_tv);
			/*--------------- normalDrawable begin ---------------*/
			GradientDrawable normalDrawable = new GradientDrawable();
			// 得到随机颜色
			Random random = new Random();
			int alpha = 255;
			int green = random.nextInt(190) + 30; // 30-220
			int red = random.nextInt(190) + 30;// 30-220
			int blue = random.nextInt(190) + 30;// 30-220
			int argb = Color.argb(alpha, red, green, blue);
			// 设置填充颜色
			normalDrawable.setColor(argb);

			// 设置圆角半径
			normalDrawable.setCornerRadius(UIUtils.dip2Px(6));
			/*--------------- normalDrawable end ---------------*/

			/*--------------- pressedDrawable begin ---------------*/
			GradientDrawable pressedDrawable = new GradientDrawable();
			pressedDrawable.setColor(Color.DKGRAY);
			pressedDrawable.setCornerRadius(UIUtils.dip2Px(6));
			/*--------------- pressedDrawable end ---------------*/

			// 设置一个状态图片
			StateListDrawable stateListDrawable = new StateListDrawable();
			// @attr ref android.R.styleable#DrawableStates_state_pressed
			stateListDrawable.addState(new int[] { android.R.attr.state_pressed }, pressedDrawable);
			stateListDrawable.addState(new int[] {}, normalDrawable);

			tv.setBackgroundDrawable(stateListDrawable);

			tv.setClickable(true);

			tv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO
					Toast.makeText(UIUtils.getContext(), data, 0).show();
				}
			});

			// 往流式布局里面添加孩子
			fl.addView(tv);
		}

		scrollView.addView(fl);
		return scrollView;
	}
}
