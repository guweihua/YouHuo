package com.example.administrator.youhuo.utils;

import android.graphics.Point;
import android.view.ViewConfiguration;

import com.example.administrator.youhuo.MyApplication;

/**
 * Created by Administrator on 2016/12/28.
 * 判断是水平滑动还是垂直滑动
 */

public class OrientationUtils {

    static ViewConfiguration configuration = ViewConfiguration.get(MyApplication.app);

    public static boolean isHorizontalScroll(Point startPoint , Point endPoint){
        int scaledTouchSlop = configuration.getScaledTouchSlop();
        return Math.abs(endPoint.x - startPoint.x) > Math.abs(endPoint.y - startPoint.y)
                && Math.abs(endPoint.x - startPoint.x) >= scaledTouchSlop;
    }

    public static boolean isVerticalScroll(Point startPoint , Point endPoint){
        int scaledTouchSlop = configuration.getScaledTouchSlop();
        return Math.abs(endPoint.y - startPoint.y) > Math.abs(endPoint.x - startPoint.x)
                && Math.abs(endPoint.y - startPoint.y) >= scaledTouchSlop;
    }
}
