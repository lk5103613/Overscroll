package com.like.library.common;

import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

public class BouncingInterpolatorType {

    public static final int OVERSHOOT_INTERPOLATOR = 1;
    public static final int BOUNCE_INTERPOLATOR = 2;
    public static final int LINEAR_INTERPOLATOR = 3;
    public static final int ACCELERATE_DECELERATE_INTERPOLATOR = 4;

    /**
     * 获取弹跳类型
     *
     * @return
     */

    public static Interpolator getTimeInterpolator(int type) {
        Interpolator mTimeInterpolator = null;
        switch (type) {
            case OVERSHOOT_INTERPOLATOR:
                mTimeInterpolator = new OvershootInterpolator();
                break;
            case BOUNCE_INTERPOLATOR:
                mTimeInterpolator = new BounceInterpolator();
                break;
            case LINEAR_INTERPOLATOR:
                mTimeInterpolator = new LinearInterpolator();
                break;
            case ACCELERATE_DECELERATE_INTERPOLATOR:
                mTimeInterpolator = new AccelerateDecelerateInterpolator();
                break;
        }
        return mTimeInterpolator;
    }
}
