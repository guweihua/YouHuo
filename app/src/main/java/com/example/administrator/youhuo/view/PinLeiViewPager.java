package com.example.administrator.youhuo.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2017/1/11.
 */

public class PinLeiViewPager extends ViewPager {

    private boolean isSlide;

    public PinLeiViewPager(Context context) {
        super(context);
    }

    public PinLeiViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public void setCurrentItem(int item) {
        setCurrentItem(item,isSlide);
    }

    public void setSlide(boolean isSlide){
        this.isSlide = isSlide;
    }
}
