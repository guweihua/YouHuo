package com.example.administrator.youhuo.view;

import android.content.Context;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2016/12/27.
 */

public class MySlidePane extends SlidingPaneLayout {
    public MySlidePane(Context context) {
        super(context);
    }

    public MySlidePane(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MySlidePane(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isOpen()) {
            return super.onInterceptTouchEvent(ev);
        } else {
            return false;
        }
    }
}
