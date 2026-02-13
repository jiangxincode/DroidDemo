package edu.jiangxin.droiddemo.animation.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import edu.jiangxin.droiddemo.R;

public class TransitionFirstActivity extends Activity {

    ImageView mOriginalImageView;
    TextView mOriginalTextView;
    ImageView mChromeImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityoptions_layout_main);
        mOriginalImageView = findViewById(R.id.original_imageView);
        mOriginalTextView = findViewById(R.id.original_textView);
        mChromeImageView = findViewById(R.id.chrome_imageView);
    }

    public void buttonListener(View views) {
        int id = views.getId();
        if (id == R.id.custom_button) {
            customAnim();
        } else if (id == R.id.scaleUp_button) {
            scaleUpAnim(mOriginalImageView);
        } else if (id == R.id.thumbnail_button) {
            thumbNailScaleAnim(mChromeImageView);
        } else if (id == R.id.scene_button) {
            sceneTransitionAnimation(mChromeImageView);
        } else if (id == R.id.scene_button1) {
            sceneTransitionAnimation();
        }
    }

    private void sceneTransitionAnimation() {
        Pair<View, String> imagePair = Pair.create(mOriginalImageView, getString(R.string.transitionImageName));
        Pair<View, String> chromePair = Pair.create(mChromeImageView, getString(R.string.transitionChromeName));
        Pair<View, String> textPair = Pair.create(mOriginalTextView, getString(R.string.transitionTextName));

        ActivityOptionsCompat compat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(this, imagePair, textPair, chromePair);
        ActivityCompat.startActivity(this, new Intent(this, TransitionSecondActivity.class),
                compat.toBundle());
    }

    private void sceneTransitionAnimation(ImageView _chromeImageView) {
        ActivityOptionsCompat compat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                        _chromeImageView, getString(R.string.transitionChromeName));
        ActivityCompat.startActivity(this, new Intent(this,
                TransitionSecondActivity.class), compat.toBundle());
    }

    private void thumbNailScaleAnim(ImageView _chromeImageView) {
        if (_chromeImageView.getWidth() <= 0 || _chromeImageView.getHeight() <= 0) {
            return;
        }
        Bitmap bitmap = Bitmap.createBitmap(_chromeImageView.getWidth(), _chromeImageView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        _chromeImageView.draw(canvas);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeThumbnailScaleUpAnimation(
                _chromeImageView, bitmap, 0, 0);
        ActivityCompat.startActivity(this, new Intent(this, TransitionSecondActivity.class), options.toBundle());
        bitmap.recycle();
    }

    private void scaleUpAnim(ImageView _originalImageView) {
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(_originalImageView,
                0, 0, //拉伸开始的坐标
                _originalImageView.getMeasuredWidth(), _originalImageView.getMeasuredHeight());
        ActivityCompat.startActivity(this, new Intent(this, TransitionSecondActivity.class),
                compat.toBundle());
    }

    private void customAnim() {
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeCustomAnimation(this,
                R.anim.slide_bottom_in, R.anim.slide_bottom_out);
        ActivityCompat.startActivity(this,
                new Intent(this, TransitionSecondActivity.class), compat.toBundle());
    }
}