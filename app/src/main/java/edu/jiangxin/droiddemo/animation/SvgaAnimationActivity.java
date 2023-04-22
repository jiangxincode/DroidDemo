package edu.jiangxin.droiddemo.animation;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGADynamicEntity;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;

import org.jetbrains.annotations.NotNull;

import edu.jiangxin.droiddemo.R;
import kotlin.jvm.functions.Function2;

public class SvgaAnimationActivity extends Activity {

    private SVGAImageView svgaImageView;
    private ProgressBar loadingProgressBar;

    /**
     * 演示了从网络加载动画，并播放动画
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svga_animation);
        svgaImageView = findViewById(R.id.svgaImageView);
        loadingProgressBar = findViewById(R.id.loadingProgressBar);

        loadAnimation();
    }

    /** 從Url下載svga檔, 再把它轉成SVGADrawable, 最後賦予svgaImageView 讓它顯示出來 */
    private void loadAnimation() {
        SVGAParser parser = new SVGAParser(this);
        try {
            parser.decodeFromAssets("svga_sample.svga", new SVGAParser.ParseCompletion() {
                @Override
                public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                    SVGADrawable drawable = new SVGADrawable(videoItem, requestDynamicItemWithSpannableText());
                    svgaImageView.setImageDrawable(drawable);
                    loadingProgressBar.setVisibility(View.INVISIBLE);
                    svgaImageView.startAnimation();
                }
                @Override
                public void onError() {
                    loadingProgressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(SvgaAnimationActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }, null);
        } catch (Exception e) {
            System.out.print(true);
        }
    }

    /**
     * 你可以设置富文本到 ImageKey 相关的元素上
     * 富文本是会自动换行的，不要设置过长的文本
     *
     * @return
     */
    private SVGADynamicEntity requestDynamicItemWithSpannableText() {
        SVGADynamicEntity dynamicEntity = new SVGADynamicEntity();
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("5566");
        spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.YELLOW), 0, 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        TextPaint textPaint = new TextPaint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(28);
        dynamicEntity.setDynamicText(new StaticLayout(
                spannableStringBuilder,
                0,
                spannableStringBuilder.length(),
                textPaint,
                0,
                Layout.Alignment.ALIGN_CENTER,
                1.0f,
                0.0f,
                false
        ), "banner");
        dynamicEntity.setDynamicDrawer(new Function2<Canvas, Integer, Boolean>() {
            @Override
            public Boolean invoke(Canvas canvas, Integer frameIndex) {
                Paint aPaint = new Paint();
                aPaint.setColor(Color.WHITE);
                canvas.drawCircle(50, 54, frameIndex % 5, aPaint);
                return false;
            }
        }, "banner");
        return dynamicEntity;
    }
}
