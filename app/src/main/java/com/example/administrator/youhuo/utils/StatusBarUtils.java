package com.example.administrator.youhuo.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;

/**
 * Created by admin on 2016/12/26.
 */

public class StatusBarUtils {
    public static void setFullScreentStatus(Activity a) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorView = a.getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            a.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
