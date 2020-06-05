package edu.jiangxin.droiddemo.animation;

import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import edu.jiangxin.droiddemo.R;

public class VectorDrawableAnimatorActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector_drawable_animator);
    }

    public void doClick(View view) {
        Drawable[] drawables = ((TextView) view).getCompoundDrawables();
        ((Animatable) drawables[1]).start();
    }
}