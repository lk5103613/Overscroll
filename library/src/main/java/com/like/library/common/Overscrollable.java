package com.like.library.common;

public interface Overscrollable {

    /**
     * 用于判断是否在控件最顶端
     *
     * @return
     */
    boolean isTop();

    /**
     * 用于判断是否在控件最底端
     *
     * @return
     */
    boolean isBottom();

    int getWidth();

    int getHeight();

}
