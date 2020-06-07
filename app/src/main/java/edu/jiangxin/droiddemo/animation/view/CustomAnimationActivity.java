package edu.jiangxin.droiddemo.animation.view;

;

public class CustomAnimationActivity extends BaseActivity {

    @Override
    protected void starXmlAnimation() {
        return;
    }

    @Override
    protected void starCodeAnimation() {
        mImage.clearAnimation();
        CustomAnimation animation = new CustomAnimation();
        animation.setRotateY(30);
        mImage.startAnimation(animation);
    }
}