package com.like.overscroll;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ScrollView;

import com.like.library.utils.ScreenUtils;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;

public class BounceRecyclerView extends RecyclerView {

    private int mBouncingType;
    private float mOffsetScale;
    private float mBouncingOffset;
    private Interpolator mTimeInterpolator = null;
    private ValueAnimator animator;
    private int mDownY;
    private int mDownY2;
    private int mMoveX;
    private int mMoveY;
    private boolean mIsTop;
    private Context mContext;

    public BounceRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public BounceRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BounceRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        mBouncingOffset = ScreenUtils.getScreenHeight(context) * 7;
        mBouncingType = BouncingType.TOP;
    }

    public void bouncingTo() {
        //设置X坐标点
        ViewHelper.setPivotX(this, getWidth() / 2);
        //设置Y坐标点
        ViewHelper.setPivotY(this, 0);
        //进行缩放
        ViewHelper.setScaleY(this, 1.0f + mOffsetScale);
    }

    /**
     * 从顶部开始滑动
     */
    public void bouncingBottom() {
        //设置X坐标点
        ViewHelper.setPivotX(this, getWidth() / 2);
        //设置Y坐标点
        ViewHelper.setPivotY(this, getHeight());
        ViewHelper.setScaleY(this, 1.0f + mOffsetScale);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int contentHeight = getChildAt(0).getHeight();
        if (contentHeight > 0 && contentHeight <= ScreenUtils.getScreenHeight(mContext)) {
            mBouncingType = BouncingType.TOP;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = (int) ev.getRawY();
                mDownY2 = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                mMoveX = (int) ev.getRawX();
                mMoveY = (int) ev.getRawY();
                int dy = mMoveY - mDownY;
                mDownY = mMoveY;
                if (getScrollY() == 0 && dy < 0 && mOffsetScale > 0) {//为顶部 并且dy为下拉 并且缩放值大于0
                    if (mBouncingType == BouncingType.TOP || mBouncingType == BouncingType.BOTH) {
                        //获取现在坐标与按下坐标的差值
                        int abs = mMoveY - mDownY2;
                        //计算缩放值
                        mOffsetScale = (Math.abs(abs) / mBouncingOffset);
                        if (mOffsetScale > 0.3f) {
                            mOffsetScale = 0.3f;
                        }
                        if (abs <= 0) {
                            mOffsetScale = 0;
                            mDownY2 = mMoveY;
                        }
                        mIsTop = true;
                        bouncingTo();
                        return true;
                    }
                }
                if (getScrollY() == 0 && dy > 0) {
                    //判断果冻的类型
                    if (mBouncingType == BouncingType.TOP || mBouncingType == BouncingType.BOTH) {
                        //获取现在坐标与按下坐标的差值
                        int abs = mMoveY - mDownY2;
                        //计算缩放值
                        mOffsetScale = (Math.abs(abs) / mBouncingOffset);
                        if (mOffsetScale > 0.3f) {
                            mOffsetScale = 0.3f;
                        }
                        mIsTop = true;
                        bouncingTo();
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                //初始化
                if (animator != null && animator.isRunning()) {
                    animator.cancel();
                    animator = null;
                    mOffsetScale = 0;
                    bouncingTo();
                }
                if (mTimeInterpolator == null) {
                    mTimeInterpolator = new OvershootInterpolator();
                }
                //散发值
                animator = ValueAnimator.ofFloat(mOffsetScale, 0).setDuration(400);
                animator.setInterpolator(mTimeInterpolator);//差值器
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        //获取动画阶段的值
                        mOffsetScale = (float) animation.getAnimatedValue();
                        if (mIsTop) {//回弹到顶部
                            bouncingTo();
                        } else {//回弹到底部
                            bouncingBottom();
                        }
                    }
                });
                animator.start();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    public class BouncingType {

        public static final int NONE = 0;
        public static final int TOP = 1;
        public static final int BOTTOM = 2;
        public static final int BOTH = 3;

    }
}
