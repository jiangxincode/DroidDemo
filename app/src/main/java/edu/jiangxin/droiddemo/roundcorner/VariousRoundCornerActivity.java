package edu.jiangxin.droiddemo.roundcorner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import edu.jiangxin.droiddemo.R;
import edu.jiangxin.droiddemo.roundcorner.cardview.CardViewActivity;
import edu.jiangxin.droiddemo.roundcorner.clippath.ClipPathActivity;
import edu.jiangxin.droiddemo.roundcorner.draweeview.DraweeViewActivity;
import edu.jiangxin.droiddemo.roundcorner.gradientdrawable.GradientDrawableActivity;
import edu.jiangxin.droiddemo.roundcorner.viewoutlineprovider.ViewOutlineProviderActivity;
import edu.jiangxin.droiddemo.roundcorner.xmlshape.XmlShapeActivity;

public class VariousRoundCornerActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * 优点：
     * 1.最简单
     * 2.四个圆角可以单独定制，也可以统一处理
     * 3.还可以一并设置背景颜色等属性
     * 4.圆角无锯齿
     * 缺点：
     * 1.圆角属性是提前确定的并卸载客户端本地的drawable中，如果是动态下发再去配置的话这种方式无法支持。
     * 2.如果子View长宽都match_parent并且设置一个有色background属性的话，圆角就消失了。
     */
    private Button mBtnShapeXml;

    /**
     * 优点：
     * 1.支持动态配置啊
     * 2.四个圆角可以单独定制，也可以统一处理
     * 3.还可以一并设置背景颜色等属性
     * 4.圆角无锯齿
     * 缺点
     * 1.如果子View长宽都match_parent并且设置一个有色background属性的话，圆角就消失了
     */
    private Button mBtnGradientDrawable;

    /**
     * 优点：
     * 1.整个外层轮廓都切成圆角矩形，子View的background不会影响到圆角
     * 2.四个角可以单独配置也可以统一配置
     * 缺点：
     * 1.无法抗锯齿，在一些老的手机上面效果可能差强人意
     * 2.需要实现自己的自定义布局
     */
    private Button mBtnClipPath;

    /**
     * 优点：
     * 1.Google的控件，效果和稳定性都是杠杠的，还支持阴影等其他的配置
     * 2.抗锯齿
     * 缺点：
     * 1.四个角要一起配置，不支持其中若干个角单独配置
     * 2.使用时外层要嵌套CardView
     */
    private Button mBtnCardView;

    /**
     * 优点：
     * 1.支持动态下发与配置
     * 缺点：
     * 1.只能四个角同时配置不支持每个角单独配置
     */
    private Button mBtnViewOutlineProvider;

    private Button mBtnDraweeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_various_round_corner);

        initWidgets();
    }

    private void initWidgets() {
        mBtnShapeXml = findViewById(R.id.btn_xml_shape);
        mBtnShapeXml.setOnClickListener(this);

        mBtnGradientDrawable = findViewById(R.id.btn_gradient_dawable);
        mBtnGradientDrawable.setOnClickListener(this);

        mBtnClipPath = findViewById(R.id.btn_clip_path);
        mBtnClipPath.setOnClickListener(this);

        mBtnCardView = findViewById(R.id.btn_cardview);
        mBtnCardView.setOnClickListener(this);

        mBtnViewOutlineProvider = findViewById(R.id.btn_view_outline_provider);
        mBtnViewOutlineProvider.setOnClickListener(this);

        mBtnDraweeView = findViewById(R.id.btn_drawee_view);
        mBtnDraweeView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent = null;
        if (id == R.id.btn_xml_shape) {
            intent = new Intent(this, XmlShapeActivity.class);
        } else if (id == R.id.btn_gradient_dawable) {
            intent = new Intent(this, GradientDrawableActivity.class);
        } else if (id == R.id.btn_clip_path) {
            intent = new Intent(this, ClipPathActivity.class);
        } else if (id == R.id.btn_cardview) {
            intent = new Intent(this, CardViewActivity.class);
        } else if (id == R.id.btn_view_outline_provider) {
            intent = new Intent(this, ViewOutlineProviderActivity.class);
        } else if (id == R.id.btn_drawee_view) {
            intent = new Intent(this, DraweeViewActivity.class);
        }

        startActivity(intent);
    }
}