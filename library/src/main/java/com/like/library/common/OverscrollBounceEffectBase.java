package com.like.library.common;

import android.view.MotionEvent;
import android.view.View;

import com.like.library.adapters.IOverScrollDecoratorAdapter;


public class OverscrollBounceEffectBase implements View.OnTouchListener {

    private IOverScrollDecoratorAdapter mViewAdapter;

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
}
