package edu.jiangxin.easymarry.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.jiangxin.easymarry.R;

/**
 * An kind TextView for long text display.
 *
 */
public class PullDownTextView extends LinearLayout implements View.OnClickListener{

    private TextView mTitleTextView;
    private TextView mContentTextView;
    private ImageButton mImageButton;

    private Drawable mPullDownDrawable;
    private Drawable mUpDownDrawable;

    /** height of content TextView on pull state */
    private int mContentTextViewPullHeight;

    /** height of content TextView not on pull state */
    private int mContentTextViewNotPullHeight;

    /** flag of pull or not */
    private boolean isPull;

    /** flag of needing relayout */
    private boolean isReLayout;

    /** flag of in animation state */
    private boolean isAnimator;

    /** flag of needing measure max height */
    private boolean isMaxHeightMeasure;

    /** flag of needing measure min height */
    private boolean isMinHeightMeasure;

    private int mTextVisibilityCount = 1;  //隐藏时 TextView可以显示的最大的行数
    private int mAnimatorDuration = 400;

    /** TextView展开回调 */
    private OnTextViewPullListener mOnTextViewPullListener ;

    public PullDownTextView(Context context) {
        this(context, null);
    }
    public PullDownTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }
    public PullDownTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /** 初始化PullDownTextView */
    private void initPullDownTextView() {
        mPullDownDrawable = getDrawable(R.drawable.ic_expand_small_holo_light);
        mUpDownDrawable = getDrawable(R.drawable.ic_collapse_small_holo_light);
        setOrientation(LinearLayout.VERTICAL);
        //默认隐藏
        setVisibility(View.GONE);
    }

    /** 当加载完XML布局时回调 */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initPullDownTextView();
        mTitleTextView = (TextView) this.getChildAt(0);
        mContentTextView = (TextView) this.getChildAt(1);
        mImageButton = (ImageButton) this.getChildAt(2);
        this.setOnClickListener(this);
        mImageButton.setImageDrawable(isPull ? mUpDownDrawable : mPullDownDrawable);
    }

    /** 测量方发，测量自己的宽高，测量孩子的宽高 */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //没有内容的时候，
        if(!isReLayout || getVisibility() == View.GONE){
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return ;
        }

        //有内容，但是内容比较短的时候，正常显示TextView，但是相应的隐藏ImageButton
        if(getLineCount(mContentTextView.getText()) <= mTextVisibilityCount){
            mContentTextView.setVisibility(View.VISIBLE);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return ;
        }

        //有内容，并且显示的内容比较长的时候，这里我们显示TextView、ImageButton。
        mImageButton.setVisibility(View.VISIBLE);
        if(!isMaxHeightMeasure && mContentTextViewPullHeight == 0){
            mContentTextView.setMaxLines(Integer.MAX_VALUE);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            mContentTextViewPullHeight = mContentTextView.getMeasuredHeight() ;
            isMaxHeightMeasure = true ;
        }

        if(!isMinHeightMeasure && mContentTextViewNotPullHeight == 0){
            mContentTextView.setMaxLines(mTextVisibilityCount);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            mContentTextViewNotPullHeight = mContentTextView.getMeasuredHeight();
            isMinHeightMeasure = true ;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * set content text
     * @param content content
     */
    public void setContentText(@Nullable CharSequence content) {
        isReLayout = true;
        mContentTextView.setText(content);
        setVisibility(TextUtils.isEmpty(content) ? View.GONE : View.VISIBLE);
    }

    /**
     * set title text
     * @param title title
     */
    public void setTitleText(@Nullable CharSequence title) {
        isReLayout = true;
        mTitleTextView.setText(title);
        setVisibility(TextUtils.isEmpty(title) ? View.GONE : View.VISIBLE);
    }

    /** 设置动画时常的方法 */
    public void setDuration(int duration){
        this.mAnimatorDuration = duration ;
    }

    /** 设置TextView */
    public void setTextVisibilityCount(int visibilityCount){
        this.mTextVisibilityCount = visibilityCount ;
    }

    public void setOnTextViewPullListener(OnTextViewPullListener listener){
        this.mOnTextViewPullListener = listener ;
    }

    @Override
    public void setOrientation(int orientation) {
        if(orientation == LinearLayout.HORIZONTAL){
            throw new IllegalArgumentException("参数错误：当前控件，不支持水平");
        }
        super.setOrientation(orientation);
    }

    /**
     * 通过图片的ID获取图片的Drawable、
     * @param id ： 图片的Id
     * @return ：获取到的图片
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private Drawable getDrawable(int id){
        Drawable drawable ;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            drawable = getContext().getResources().getDrawable(id, getContext().getTheme()) ;
        } else {
            drawable = getContext().getResources().getDrawable(id) ;
        }
        return drawable ;
    }

    /**
     * 获取TextView的高度。TextViewHeight = TextHeight + TextViewPadding ；
     * @return TextView的高度
     */
    private int getTextViewHeight(){
        int textHeight = mContentTextView.getLayout().getLineTop(mContentTextView.getLineCount());
        int padding = mContentTextView.getCompoundPaddingTop() + mContentTextView.getCompoundPaddingBottom() ;
        return textHeight + padding ;
    }

    /**
     * imageButton的点击事件
     * @param v 被点击的View
     */
    @Override
    public void onClick(View v) {
        if(isAnimator){
            return ;
        }
        if(isPull){
            startAnimator(mContentTextView, mContentTextViewPullHeight, mContentTextViewNotPullHeight);
        } else {
            startAnimator(mContentTextView, mContentTextViewNotPullHeight, mContentTextViewPullHeight);
        }
        //下拉，或者上拉的时候的回调
        if(this.mOnTextViewPullListener != null){
            this.mOnTextViewPullListener.textViewPull(mContentTextView, isPull);
        }
        isPull = !isPull ;
        mImageButton.setImageDrawable(isPull ? mUpDownDrawable : mPullDownDrawable);
        mContentTextView.getParent().requestLayout();
    }

    /**
     * 开始动画
     */
    private void startAnimator(final TextView view, int startHeight, int endHeight){
        ValueAnimator valueAnimator = ValueAnimator.ofInt(startHeight , endHeight ).setDuration(mAnimatorDuration);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}
            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimator = false ;
            }
            @Override
            public void onAnimationCancel(Animator animation) {}
            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.height = animatedValue ;
                //这句，让TextView文本的高度随TextView高度进行变化
                view.setMaxHeight(animatedValue);
                view.setLayoutParams(params);
            }
        });
        isAnimator = true ;
        valueAnimator.start();
    }

    /** TextView展开回调 */
    public interface OnTextViewPullListener{
        void textViewPull(TextView textView, boolean isPull) ;
    }

    private int getLineCount(CharSequence text) {
        int count = 0;
        for (int i=0;i<text.length();i++) {
            if (text.charAt(i) == '\n') {
                count++;
            }
        }
        return count++;
    }

}