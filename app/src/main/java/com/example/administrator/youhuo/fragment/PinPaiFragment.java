package com.example.administrator.youhuo.fragment;

import android.view.Gravity;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/1/11.
 */

public class PinPaiFragment extends BaseStausFragment {
    @Override
    protected void initChildView() {
        TextView tv=new TextView(a);
        tv.setTextSize(20);
        tv.setGravity(Gravity.CENTER);
        tv.setText(getClass().getSimpleName());
        setContentView(tv);
    }

    @Override
    public void onReload() {

    }
}
