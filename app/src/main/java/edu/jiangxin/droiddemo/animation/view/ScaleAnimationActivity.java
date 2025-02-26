package edu.jiangxin.droiddemo.animation.view;


import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;

import edu.jiangxin.droiddemo.R;

public class ScaleAnimationActivity extends BaseViewAnimationActivity {

    /**
     * 使用AnimationUtils类的静态方法loadAnimation()来加载XML中的动画XML文件
     */
    @Override
    protected void starXmlAnimation() {
        mImage.clearAnimation();
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.scale);
        mImage.startAnimation(animation);

    }

    /**
     * 使用Animation的子类AlphaAnimation
     */
    @Override
    protected void starCodeAnimation() {
        mImage.clearAnimation();
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT, 0.5f);
        scaleAnimation.setDuration(3000);
        mImage.startAnimation(scaleAnimation);

    }

}
