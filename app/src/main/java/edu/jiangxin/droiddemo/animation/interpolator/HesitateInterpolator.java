package edu.jiangxin.droiddemo.animation.interpolator;

import android.view.animation.Interpolator;

public class HesitateInterpolator implements Interpolator {

    public HesitateInterpolator() {}

    /**
     * 横坐标为传入的值，表示当前的时间，纵坐标为返回的值，表示动画的进度。
     * 我们取其中一点，当v=0.5时，返回值（我们假设为y）y=0.25，其含义表示，当时间到达总时间的一半时，动画的进度为总进度的四分之一。
     * 再取v=0.8，此时，y=0.64，则表示当时间为总时间的80%，动画的变化进度为总进度的64%。
     * 当v=1即表示动画时间结束，此时y=1，动画变化总进度100%，动画变化结束。
     */
    @Override
    public float getInterpolation(float input) {
        float x = 2.0f * input - 1.0f;
        return 0.5f * (x * x * x + 1.0f);
    }
}
