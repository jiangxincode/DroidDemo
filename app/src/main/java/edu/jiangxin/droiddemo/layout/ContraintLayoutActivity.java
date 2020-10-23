package edu.jiangxin.droiddemo.layout;

import android.app.Activity;
import android.os.Bundle;

import edu.jiangxin.droiddemo.R;

/**
 * 安卓约束控件(ConstraintLayout)扁平化布局入门: https://www.jianshu.com/p/792d2682c538
 * (已废弃，请使用ConstraintLayout)Android百分比布局: https://www.jianshu.com/p/7a6475757743?from=jiantop.com
 * 其他布局可以参考：Android Wireless Application Development Volume I Android Essentials 3rd[Android移动应用开发(第3版)卷Ⅰ基础篇] : Using Built-in Layout Classes
 */
public class ContraintLayoutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contraint_layout);
    }
}
