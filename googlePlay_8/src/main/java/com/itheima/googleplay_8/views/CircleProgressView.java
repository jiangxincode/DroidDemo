package com.itheima.googleplay_8.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itheima.googleplay_8.R;
import com.itheima.googleplay_8.utils.UIUtils;

/**
 * @author  Administrator
 * @time 	2015-7-20 下午3:00:42
 * @des	TODO
 *
 * @version $Rev: 51 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2015-07-20 16:22:06 +0800 (星期一, 20 七月 2015) $
 * @updateDes TODO
 */
public class CircleProgressView extends LinearLayout {

	private ImageView	mIcon;
	private TextView	mNote;

	private boolean		mProgressEnable;
	private long		mMax	= 100;
	private long		mProgress;

	/**是否允许进度*/
	public void setProgressEnable(boolean progressEnable) {
		mProgressEnable = progressEnable;
	}

	/**进度最大值*/
	public void setMax(long max) {
		mMax = max;
	}

	/**进度当前进度*/
	public void setProgress(long progress) {
		mProgress = progress;
		invalidate();
	}

	/**设置图标*/
	public void setIcon(int resId) {
		mIcon.setImageResource(resId);
	}

	/**设置文字*/
	public void setNote(String note) {
		mNote.setText(note);
	}

	public CircleProgressView(Context context) {
		this(context, null);
	}

	public CircleProgressView(Context context, AttributeSet attrs) {
		super(context, attrs);
		View view = View.inflate(context, R.layout.inflate_circleprogressview, this);

		mIcon = (ImageView) view.findViewById(R.id.circleProgressView_iv_icon);
		mNote = (TextView) view.findViewById(R.id.circleProgressView_tv_note);
	}

	// ondraw onmeasure onlayout
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO
		super.onDraw(canvas);// 绘制背景,透明图片

	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		// TODO
		super.dispatchDraw(canvas);// 绘制具体的内容(图片和文字)

		if (mProgressEnable) {
			RectF oval = new RectF(mIcon.getLeft(), mIcon.getTop(), mIcon.getRight(), mIcon.getBottom());
			float startAngle = -90;

			float sweepAngle = mProgress * 360.f / mMax;

			boolean useCenter = false;// 是否保留两条边
			Paint paint = new Paint();
			paint.setColor(Color.BLUE);
			paint.setStyle(Style.STROKE);
			paint.setStrokeWidth(UIUtils.dip2Px(3));
			paint.setAntiAlias(true);// 消除锯齿
			canvas.drawArc(oval, startAngle, sweepAngle, useCenter, paint);
		}
	}
}
