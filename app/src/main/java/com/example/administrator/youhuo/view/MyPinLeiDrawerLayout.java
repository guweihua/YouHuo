package com.example.administrator.youhuo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2017/1/11.
 */

public class MyPinLeiDrawerLayout extends PinLeiDrawerLayout {
    public MyPinLeiDrawerLayout(Context context) {
        super(context);
    }

    public MyPinLeiDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyPinLeiDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }
}
