package com.example.administrator.youhuo.view;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2017/1/11.
 */

public class PinLeiDrawerLayout extends DrawerLayout {
    public PinLeiDrawerLayout(Context context) {
        super(context);
    }

    public PinLeiDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PinLeiDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }
}
