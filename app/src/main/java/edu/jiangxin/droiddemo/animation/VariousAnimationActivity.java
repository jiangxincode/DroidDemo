package edu.jiangxin.droiddemo.animation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import edu.jiangxin.droiddemo.R;
import edu.jiangxin.droiddemo.animation.activity.TransitionFirstActivity;
import edu.jiangxin.droiddemo.animation.layout.GridLayoutAnimationActivity;
import edu.jiangxin.droiddemo.animation.layout.LayoutAnimationAndTransitionActivity;
import edu.jiangxin.droiddemo.animation.layout.LayoutTransitionByXmlActivity;
import edu.jiangxin.droiddemo.animation.view.AlphaAnimationActivity;
import edu.jiangxin.droiddemo.animation.view.AnimationSetActivity;
import edu.jiangxin.droiddemo.animation.view.CustomAnimationActivity;
import edu.jiangxin.droiddemo.animation.view.RotateAnimationActivity;
import edu.jiangxin.droiddemo.animation.view.ScaleAnimationActivity;
import edu.jiangxin.droiddemo.animation.view.TranslateAnimationActivity;

public class VariousAnimationActivity extends Activity implements View.OnClickListener {

    private TextView mTvAlphaAnimation, mTvScaleAnimation, mTvTranslateAnimation, mTvRotateAnimation, mTvCustomAnimation, mTvAnimationSet;
    private TextView mTvInterpolator;
    private TextView mTvPropertyAnimator;
    private TextView mTvPhysicsAnimation;
    private TextView mTvVectorDrawableAnimator;
    private TextView mTvFrameAnimation;
    private TextView mTvActivityTransition;
    private TextView mTvLayoutChanges, mTvLayoutAnimation, mTvGridLayoutAnimation;
    private TextView mTvOverlayAnimation;
    private TextView mTvSVGAAnimation;
    private TextView mTvLottieAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_various_animation);
        mTvAlphaAnimation = findViewById(R.id.tv_alpha_animation);
        mTvScaleAnimation = findViewById(R.id.tv_scale_animation);
        mTvTranslateAnimation = findViewById(R.id.tv_translate_animation);
        mTvRotateAnimation = findViewById(R.id.tv_rotate_animation);
        mTvCustomAnimation = findViewById(R.id.tv_custom_animation);
        mTvAnimationSet = findViewById(R.id.tv_animation_set);
        mTvInterpolator = findViewById(R.id.tv_interpolator);
        mTvPropertyAnimator = findViewById(R.id.tv_property_animator);
        mTvPhysicsAnimation = findViewById(R.id.tv_physics_animation);
        mTvVectorDrawableAnimator = findViewById(R.id.tv_vector_drawable_animator);
        mTvFrameAnimation = findViewById(R.id.tv_frame_animation);
        mTvActivityTransition = findViewById(R.id.tv_activity_transition);
        mTvLayoutChanges = findViewById(R.id.tv_layout_transition_xml);
        mTvLayoutAnimation = findViewById(R.id.tv_layout_animation_transition);
        mTvGridLayoutAnimation = findViewById(R.id.tv_grid_layout_animation);
        mTvOverlayAnimation = findViewById(R.id.tv_overlay_animation);
        mTvSVGAAnimation = findViewById(R.id.tv_svga_animation);
        mTvLottieAnimation = findViewById(R.id.tv_lottie_animation);

        mTvAlphaAnimation.setOnClickListener(this);
        mTvScaleAnimation.setOnClickListener(this);
        mTvTranslateAnimation.setOnClickListener(this);
        mTvRotateAnimation.setOnClickListener(this);
        mTvCustomAnimation.setOnClickListener(this);
        mTvAnimationSet.setOnClickListener(this);
        mTvInterpolator.setOnClickListener(this);
        mTvPropertyAnimator.setOnClickListener(this);
        mTvPhysicsAnimation.setOnClickListener(this);
        mTvVectorDrawableAnimator.setOnClickListener(this);
        mTvFrameAnimation.setOnClickListener(this);
        mTvActivityTransition.setOnClickListener(this);
        mTvLayoutChanges.setOnClickListener(this);
        mTvLayoutAnimation.setOnClickListener(this);
        mTvGridLayoutAnimation.setOnClickListener(this);
        mTvOverlayAnimation.setOnClickListener(this);
        mTvSVGAAnimation.setOnClickListener(this);
        mTvLottieAnimation.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_alpha_animation) {
            enterTestActivity(AlphaAnimationActivity.class);
        } else if (id == R.id.tv_scale_animation) {
            enterTestActivity(ScaleAnimationActivity.class);
        } else if (id == R.id.tv_translate_animation) {
            enterTestActivity(TranslateAnimationActivity.class);
        } else if (id == R.id.tv_rotate_animation) {
            enterTestActivity(RotateAnimationActivity.class);
        } else if (id == R.id.tv_custom_animation) {
            enterTestActivity(CustomAnimationActivity.class);
        } else if (id == R.id.tv_animation_set) {
            enterTestActivity(AnimationSetActivity.class);
        } else if (id == R.id.tv_interpolator) {
            enterTestActivity(InterpolatorTestActivity.class);
        } else if (id == R.id.tv_property_animator) {
            enterTestActivity(PropertyAnimatorActivity.class);
        } else if (id == R.id.tv_physics_animation) {
            enterTestActivity(PhysicsAnimationActivity.class);
        } else if (id == R.id.tv_vector_drawable_animator) {
            enterTestActivity(VectorDrawableAnimatorActivity.class);
        } else if (id == R.id.tv_frame_animation) {
            enterTestActivity(FrameAnimationActivity.class);
        } else if (id == R.id.tv_activity_transition) {
            enterTestActivity(TransitionFirstActivity.class);
        } else if (id == R.id.tv_layout_transition_xml) {
            enterTestActivity(LayoutTransitionByXmlActivity.class);
        } else if (id == R.id.tv_layout_animation_transition) {
            enterTestActivity(LayoutAnimationAndTransitionActivity.class);
        } else if (id == R.id.tv_grid_layout_animation) {
            enterTestActivity(GridLayoutAnimationActivity.class);
        } else if (id == R.id.tv_overlay_animation) {
            enterTestActivity(OverlayAnimationActivity.class);
        } else if (id == R.id.tv_svga_animation) {
            enterTestActivity(SvgaAnimationActivity.class);
        } else if (id == R.id.tv_lottie_animation) {
            enterTestActivity(LottieAnimationActivity.class);
        }
    }

    private void enterTestActivity(Class clazz) {
        startActivity(new Intent(this, clazz));
//        overridePendingTransition();
    }
}
