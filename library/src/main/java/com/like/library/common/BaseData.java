package com.like.library.common;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.animation.Interpolator;

public class BaseData {

    private int mBouncingType;
    private float mOffsetScale;
    private float mBouncingOffset;
    private Interpolator mTimeInterpolator = null;
    private ValueAnimator animator;
    private int mPreY;
    private int mDownY;
    private int mMoveX;
    private int mMoveY;
    private boolean mIsTop;
    private int mBouncingTopDuration = 500;
    private int mBouncingBottomDuration = 500;
    private Context mContext;

}
