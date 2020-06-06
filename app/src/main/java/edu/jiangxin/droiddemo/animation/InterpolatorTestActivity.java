package edu.jiangxin.droiddemo.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import edu.jiangxin.droiddemo.R;
import edu.jiangxin.droiddemo.animation.interpolator.EaseCubicInterpolator;
import edu.jiangxin.droiddemo.animation.interpolator.HesitateInterpolator;


public class InterpolatorTestActivity extends Activity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "InterpolatorTestActivity";

    private static final String[] INTERPOLATORS = {
            "Accelerate", "Decelerate", "Accelerate/Decelerate",
            "Anticipate", "Overshoot", "Anticipate/Overshoot",
            "Linear", "Bounce", "CycleInterpolator", "HesitateInterpolator//自定义",
            "FastOutLinearInInterpolator", "LinearOutSlowInInterpolator", "FastOutSlowInInterpolator",
            "阻尼曲线", "急缓曲线", "锐利曲线", "节奏曲线", "平滑曲线"};
    private static final int mTotalTime = 3000;
    private Interpolator mInterpolator = new AccelerateInterpolator();
    private ObjectAnimator objectAnimator;

    private View target;
    private View targetParent;

    private TextView distance_y;
    private TextView total_y;
    private TextView distance_y_percent;
    private TextView interpolator_y;

    private TextView distance_time;
    private TextView total_time;
    private TextView distance_time_percent;
    private TextView interpolator_t;
    private Button pause;
    private TextView formula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interpolator_test);

        target = findViewById(R.id.target);
        targetParent = (View) target.getParent();

        distance_y = findViewById(R.id.distance_y);
        total_y = findViewById(R.id.total_y);
        distance_y_percent = findViewById(R.id.distance_y_percent);
        interpolator_y = findViewById(R.id.interpolator_y);

        distance_time = findViewById(R.id.distance_time);
        total_time = findViewById(R.id.total_time);
        distance_time_percent = findViewById(R.id.distance_time_percent);
        interpolator_t = findViewById(R.id.interpolator_t);
        formula = findViewById(R.id.formula);

        pause = findViewById(R.id.pause);
        pause.setOnClickListener(v -> {
            if (objectAnimator != null) {
                if (objectAnimator.isPaused()) {
                    objectAnimator.resume();
                    pause.setText("Pause");
                } else if (objectAnimator.isRunning()) {
                    objectAnimator.pause();
                    pause.setText("Start");
                } else {
                    objectAnimator.start();
                    pause.setText("Pause");
                }
            }

        });

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, INTERPOLATORS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        resetState();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        resetState();

        final float startY = target.getY();
        final float totalY = targetParent.getHeight() - target.getHeight() - target.getY();

        objectAnimator = ObjectAnimator.ofFloat(target, "translationY", totalY);
        objectAnimator.setDuration(mTotalTime);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);

        if (position == 0) {
            formula.setVisibility(View.VISIBLE);
        } else {
            formula.setVisibility(View.GONE);
            target.clearAnimation();
        }

        switch (position) {
            case 0:
                mInterpolator = new AccelerateInterpolator();
                break;
            case 1:
                mInterpolator = AnimationUtils.loadInterpolator(this, android.R.anim.decelerate_interpolator);
                break;
            case 2:
                mInterpolator = AnimationUtils.loadInterpolator(this, android.R.anim.accelerate_decelerate_interpolator);
                break;
            case 3:
                mInterpolator = AnimationUtils.loadInterpolator(this, android.R.anim.anticipate_interpolator);
                break;
            case 4:
                mInterpolator = AnimationUtils.loadInterpolator(this, android.R.anim.overshoot_interpolator);
                break;
            case 5:
                mInterpolator = AnimationUtils.loadInterpolator(this, android.R.anim.anticipate_overshoot_interpolator);
                break;
            case 6:
                mInterpolator = AnimationUtils.loadInterpolator(this, android.R.anim.linear_interpolator);
                break;
            case 7:
                mInterpolator = AnimationUtils.loadInterpolator(this, android.R.anim.bounce_interpolator);
                break;
            case 8:
                mInterpolator = AnimationUtils.loadInterpolator(this, android.R.anim.cycle_interpolator);
                break;
            case 9:
                mInterpolator = new HesitateInterpolator();
                break;
            case 10:
                // FastOutLinearInInterpolator 先加速然后匀速，和Accelerate  Interpolator类似
                mInterpolator = AnimationUtils.loadInterpolator(this, android.R.interpolator.fast_out_linear_in);
                break;
            case 11:
                // LinearOutSlowInInterpolator 先匀速再减速，和和Decelerate Interpolator类似
                mInterpolator = AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in); //减速
                break;
            case 12:
                // FastOutSlowInInterpolator 加速然后减速，和Accelerate Decelerate Interpolator类似，又叫做标准曲线
                mInterpolator = AnimationUtils.loadInterpolator(this, android.R.interpolator.fast_out_slow_in);
                break;
            case 13:
                mInterpolator = new EaseCubicInterpolator(0.2f, 0f, 0.2f, 1f);
                break;
            case 14:
                mInterpolator = new EaseCubicInterpolator(0f, 0f, 0f, 1f);
                break;
            case 15:
                mInterpolator = new EaseCubicInterpolator(0.33f, 0f, 0.67f, 1f);
                break;
            case 16:
                mInterpolator = new EaseCubicInterpolator(0.7f, 0f, 0.2f, 1f);
                break;
            case 17:
                mInterpolator = new EaseCubicInterpolator(0.4f, 0f, 0.4f, 1f);
                break;
        }


        objectAnimator.setInterpolator(mInterpolator);

        total_y.setText("总距离：" + totalY);
        total_time.setText("总时间：" + mTotalTime);

        objectAnimator.addUpdateListener(animation -> {
            distance_y.setText("运动距离：" + (target.getY() - startY));
            distance_time.setText("动画时间：" + animation.getCurrentPlayTime());
            distance_y_percent.setText("距离百分比：" + (target.getY() - startY) / totalY);
            float timeRatio = (float) animation.getCurrentPlayTime() / (float) animation.getDuration();
            distance_time_percent.setText("时间百分比：" + timeRatio);
            interpolator_y.setText("公式y=" + mInterpolator.getInterpolation(timeRatio));
            interpolator_t.setText("公式t=" + timeRatio);
            Log.i(TAG, "startY: " + startY + "totalY: " + totalY + ", parentY: " + targetParent.getY() + ", targetParent: " + targetParent.getHeight() + ", Y: " + target.getY() + ", height: " + target.getHeight() + ", calc: " + totalY * mInterpolator.getInterpolation(timeRatio));
        });

        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                resetState();
            }
        });

    }

    private void resetState() {
        distance_y.setText("运动距离：0");
        distance_y_percent.setText("距离百分比：0.0");
        interpolator_y.setText("公式y=0");
        distance_time.setText("动画时间：0");
        distance_time_percent.setText("时间百分比：0.0");
        interpolator_t.setText("公式t=0");
        pause.setText("Start");
        ObjectAnimator.ofFloat(target, "translationY", 0).setDuration(0).start();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
