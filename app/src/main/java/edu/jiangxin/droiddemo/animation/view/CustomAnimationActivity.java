package edu.jiangxin.droiddemo.animation.view;

public class CustomAnimationActivity extends BaseViewAnimationActivity {

    @Override
    protected void starXmlAnimation() {
        return;
    }

    @Override
    protected void starCodeAnimation() {
        mImage.clearAnimation();
        Rotate3dAnimation rotate3dAnimation = new Rotate3dAnimation(0, 360, mImage.getWidth() / 2.0f, 0f, 0f, true);
        rotate3dAnimation.setDuration(2000);
        mImage.startAnimation(rotate3dAnimation);
    }
}