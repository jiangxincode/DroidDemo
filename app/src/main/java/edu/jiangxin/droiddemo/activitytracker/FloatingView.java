package edu.jiangxin.droiddemo.activitytracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import edu.jiangxin.droiddemo.R;

public class FloatingView extends LinearLayout {
    public static final String TAG = "FloatingView";

    private final Context mContext;
    private final WindowManager mWindowManager;
    private TextView mTvPackageName;
    private TextView mTvClassName;
    private ImageView mIvClose;
    private Point preP;
    private Point curP;

    public FloatingView(Context context) {
        super(context);
        mContext = context;
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        initView();
    }

    private void initView() {
        inflate(mContext, R.layout.layout_floating, this);
        mTvPackageName = findViewById(R.id.tv_package_name);
        mTvClassName = findViewById(R.id.tv_class_name);
        mIvClose = findViewById(R.id.iv_close);

        mIvClose.setOnClickListener(v -> {
            Toast.makeText(mContext, "关闭悬浮框", Toast.LENGTH_SHORT).show();
            mContext.startService(
                    new Intent(mContext, TrackerService.class)
                            .putExtra(TrackerService.KEY_ACTION, TrackerService.ACTION_CLOSE)
            );
        });
    }

    public void updateContent(String packageName, String className) {
        mTvPackageName.setText(packageName);
        String simplifiedClassName = className;
        if (simplifiedClassName.startsWith(packageName)) {
            simplifiedClassName = simplifiedClassName.substring(packageName.length());
        }
        mTvClassName.setText(simplifiedClassName);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                preP = new Point((int) event.getRawX(), (int) event.getRawY());
                break;

            case MotionEvent.ACTION_MOVE:
                curP = new Point((int) event.getRawX(), (int) event.getRawY());
                int dx = curP.x - preP.x,
                        dy = curP.y - preP.y;

                WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) this.getLayoutParams();
                layoutParams.x += dx;
                layoutParams.y += dy;
                mWindowManager.updateViewLayout(this, layoutParams);

                preP = curP;
                break;
        }

        return false;
    }
}
