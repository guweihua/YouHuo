package com.example.administrator.youhuo.adapter;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/1/4.
 * 适配画廊
 */

public class HomeHaoDianAdapter extends SimplePagerAdapter {

    public HomeHaoDianAdapter(List<View> list) {
        super(list);
    }

    @Override
    public int getCount() {
        if (list.size() == 0)
            return 0;
        return Integer.MAX_VALUE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (list.size() != 0)
            position = position % list.size();
        return super.instantiateItem(container, position);
    }
}
