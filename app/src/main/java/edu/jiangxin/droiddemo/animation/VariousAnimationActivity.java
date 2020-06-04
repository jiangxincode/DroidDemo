package edu.jiangxin.droiddemo.animation;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import edu.jiangxin.droiddemo.R;
import edu.jiangxin.droiddemo.animation.view.AlphaTestActivity;
import edu.jiangxin.droiddemo.animation.view.RotateTestActivity;
import edu.jiangxin.droiddemo.animation.view.ScaleTestActivity;
import edu.jiangxin.droiddemo.animation.view.SetTestActivity;
import edu.jiangxin.droiddemo.animation.view.TranslateTestActivity;

public class VariousAnimationActivity extends Activity implements View.OnClickListener {

    private TextView mTvAlpha, mTvScale, mTvTranslate, mTvRotate, mTvSet;
    private TextView mTvInterpolator;
    private TextView mTvPropertyAnimator;
    private TextView mTvPhysicsAnimation;
    private TextView mTvVectorDrawableAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_various_animation);
        mTvAlpha = findViewById(R.id.tv_alpha);
        mTvScale = findViewById(R.id.tv_scale);
        mTvTranslate = findViewById(R.id.tv_translate);
        mTvRotate = findViewById(R.id.tv_rotate);
        mTvSet = findViewById(R.id.tv_set);
        mTvInterpolator = findViewById(R.id.tv_interpolator);
        mTvPropertyAnimator = findViewById(R.id.tv_property);
        mTvPhysicsAnimation = findViewById(R.id.tv_physics_animation);
        mTvVectorDrawableAnimator = findViewById(R.id.tv_vector_drawable_animator);

        mTvAlpha.setOnClickListener(this);
        mTvScale.setOnClickListener(this);
        mTvTranslate.setOnClickListener(this);
        mTvRotate.setOnClickListener(this);
        mTvSet.setOnClickListener(this);
        mTvInterpolator.setOnClickListener(this);
        mTvPropertyAnimator.setOnClickListener(this);
        mTvPhysicsAnimation.setOnClickListener(this);
        mTvVectorDrawableAnimator.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_alpha:
                enterTestActivity(AlphaTestActivity.class);
                break;
            case R.id.tv_scale:
                enterTestActivity(ScaleTestActivity.class);
                break;
            case R.id.tv_translate:
                enterTestActivity(TranslateTestActivity.class);
                break;
            case R.id.tv_rotate:
                enterTestActivity(RotateTestActivity.class);
                break;
            case R.id.tv_set:
                enterTestActivity(SetTestActivity.class);
                break;
            case R.id.tv_interpolator:
                enterTestActivity(InterpolatorTestActivity.class);
                break;
            case R.id.tv_property:
                enterTestActivity(PropertyAnimatorActivity.class);
                break;
            case R.id.tv_physics_animation:
                enterTestActivity(PhysicsAnimationActivity.class);
                break;
            case R.id.tv_vector_drawable_animator:
                enterTestActivity(VectorDrawableAnimatorActivity.class);
                break;
            default:
                break;
        }
    }

    private void enterTestActivity(Class clazz) {
        startActivity(new Intent(this, clazz));
//        overridePendingTransition();
    }
}
