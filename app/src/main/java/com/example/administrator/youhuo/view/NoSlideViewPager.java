package com.example.administrator.youhuo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * Created by Administrator on 2016/12/27.
 */

public class NoSlideViewPager extends FrameLayout {

    private Scroller scroller;

    public NoSlideViewPager(Context context) {
        super(context);
    }

    public NoSlideViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initScroller();
    }

    private void initScroller() {
        scroller = new Scroller(getContext());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            childAt.layout(i * getMeasuredWidth(),0 , (i + 1)*getMeasuredWidth(),getMeasuredHeight());
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()){
            int currX = scroller.getCurrX();
            scrollTo(currX,0);
            invalidate();
        }
    }

    public void setCurrentItem(int currentItem){
        if (currentItem != getCurrentItem()){
            int i = currentItem - getCurrentItem();
            scroller.startScroll(getScrollX(),0,i * getWidth(),0,1000);
            postInvalidate();
        }
    }

    private int getCurrentItem() {
        return getScrollX() / getMeasuredWidth();
    }
}
