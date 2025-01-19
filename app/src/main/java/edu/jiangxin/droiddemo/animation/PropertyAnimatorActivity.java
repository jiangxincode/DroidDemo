package edu.jiangxin.droiddemo.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import edu.jiangxin.droiddemo.R;

public class PropertyAnimatorActivity extends Activity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private static final String[] PROPERTY_ANIMATION = {"ValueAnimator", "ObjectAnimator", "AnimatorSet", "ViewPropertyAnimator"};
    private static final int RED = 0xffFF8080;
    private static final int BLUE = 0xff8080FF;
    private static final int BLACK = 0xff000000;

    private Spinner mSpinner = null;
    private ValueAnimator mValueAnimator = null;
    private ObjectAnimator mObjectAnimator = null;
    private AnimatorSet mAnimatorSet = null;

    private RelativeLayout mObjectAnimatorlayout;
    private TextView mTarget;
    private TextView mTv1, mTv2, mTv3, mTv4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animator);
        mSpinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, PROPERTY_ANIMATION);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(this);

        mObjectAnimatorlayout = findViewById(R.id.objectAnimator_layout);
        mTarget = findViewById(R.id.property_target);
        mTv1 = findViewById(R.id.textView);
        mTv2 = findViewById(R.id.textView2);
        mTv3 = findViewById(R.id.textView3);
        mTv4 = findViewById(R.id.textView4);

        mTv1.setOnClickListener(this);
        mTv2.setOnClickListener(this);
        mTv3.setOnClickListener(this);
        mTv4.setOnClickListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                mObjectAnimatorlayout.setVisibility(View.GONE);
                endAllAnimation();

                mValueAnimator = ValueAnimator.ofFloat(0, 200, 0);
                mValueAnimator.setDuration(2000);
                mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
                mValueAnimator.setRepeatMode(ValueAnimator.REVERSE);
                mValueAnimator.start();
                mValueAnimator.addUpdateListener(animation -> {
                    float value = (float) animation.getAnimatedValue();
                    mTarget.setTranslationX(value);
                });
                break;
            case 1:
                mObjectAnimatorlayout.setVisibility(View.VISIBLE);
                endAllAnimation();
                mObjectAnimator = ObjectAnimator.ofInt(mTarget, "textColor", BLACK, RED, BLUE);
                mObjectAnimator.setEvaluator(new ArgbEvaluator());
                // 对于颜色的变化，可以直接使用mObjectAnimator#ofArgb(mTarget, "textColor", BLACK, RED, BLUE)，则不用再设置setEvaluator()
                mObjectAnimator.setDuration(2000);
                mObjectAnimator.setRepeatCount(ValueAnimator.INFINITE);
                mObjectAnimator.setRepeatMode(ValueAnimator.REVERSE);
                mObjectAnimator.start();

                break;
            case 2:
                mObjectAnimatorlayout.setVisibility(View.GONE);
                endAllAnimation();
                ObjectAnimator translationX = ObjectAnimator.ofFloat(mTarget, "translationX", 400f, 0f);
                ObjectAnimator alpha = ObjectAnimator.ofFloat(mTarget, "alpha", 1f, 0f, 1f);
                ObjectAnimator rotation = ObjectAnimator.ofFloat(mTarget, "rotation", 0f, 360f);
                ObjectAnimator scaleY = ObjectAnimator.ofFloat(mTarget, "scaleY", 1f, 3f, 1f);

                mAnimatorSet = new AnimatorSet();
                mAnimatorSet.play(rotation).with(alpha).after(translationX).before(scaleY);
                mAnimatorSet.setDuration(3000);
                mAnimatorSet.start();

                // 使用xml实现属性动画集合
                // Animator animator = AnimatorInflater.loadAnimator(this,
                // R.animator.property_set);
                // animator.setTarget(mTarget);
                // animator.start();
                break;
            case 3:
                mObjectAnimatorlayout.setVisibility(View.GONE);
                endAllAnimation();

                // ViewPropertyAnimator 与 Animator对比，前者更为简洁
                // ObjectAnimator animX = ObjectAnimator.ofFloat(mTarget, "x", 50f);
                // ObjectAnimator animY = ObjectAnimator.ofFloat(mTarget, "y", 100f);
                // AnimatorSet animSetXY = new AnimatorSet();
                // animSetXY.playTogether(animX, animY);
                // animSetXY.start();
                // By是相对于当前位置，移动的偏移量
                mTarget.animate().yBy(200f).rotation(360).scaleY(2).setDuration(3000).setInterpolator(new LinearInterpolator()).start();
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void endAllAnimation() {
        if (mValueAnimator != null) {
            mTarget.setTranslationX(0);
            mValueAnimator.end();
        }
        if (mObjectAnimator != null) {
            mObjectAnimator.end();
        }
        if (mAnimatorSet != null) {
            mAnimatorSet.end();
        }
    }

    @Override
    public void onClick(View v) {
        endAllAnimation();

        ObjectAnimator objectAnimator = null;
        switch (v.getId()) {
            case R.id.textView:
                objectAnimator = ObjectAnimator.ofFloat(mTarget, "alpha", 1f, 0f, 1f);
                break;
            case R.id.textView2:
                PropertyValuesHolder propertyValuesHolder1 = PropertyValuesHolder.ofFloat("scaleX", 1f, 3f, 1f);
                PropertyValuesHolder propertyValuesHolder2 = PropertyValuesHolder.ofFloat("scaleY", 1f, 3f, 1f);
                objectAnimator = ObjectAnimator.ofPropertyValuesHolder(mTarget, propertyValuesHolder1, propertyValuesHolder2);
                break;
            case R.id.textView3:
                // 可以通过TypeEvaluator处理特殊类型：https://www.jianshu.com/p/6ea5e89f6f39
                objectAnimator = ObjectAnimator.ofObject(mTarget, "rotation", new FloatEvaluator(), 0f, 360f);
                break;
            case R.id.textView4:
                Path path = new Path();
                path.rLineTo(200, 200);
                path.rLineTo(-200, -200);
                objectAnimator = ObjectAnimator.ofFloat(mTarget, "translationX", "translationY", path);
                break;

        }
        objectAnimator.setDuration(1000);
        objectAnimator.start();
    }


    private void initAnimatorListener(Animator animator) {
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

        animator.addPauseListener(new Animator.AnimatorPauseListener() {
            @Override
            public void onAnimationPause(Animator animator) {
            }

            @Override
            public void onAnimationResume(Animator animator) {
            }
        });

        // AnimatorListenerAdapter实现了AnimatorListener, AnimatorPauseListener，减少开发工作量
        animator.addListener(new AnimatorListenerAdapter() {
        });

        if (animator instanceof ValueAnimator valueAnimator) {
            valueAnimator.addUpdateListener(animation -> {
            });
        }
    }
}
