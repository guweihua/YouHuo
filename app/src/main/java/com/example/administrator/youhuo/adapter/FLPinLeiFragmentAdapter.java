package com.example.administrator.youhuo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

/**
 * Created by Administrator on 2017/1/11.
 */

public class FLPinLeiFragmentAdapter extends FLSimpleFragmentPageAdapter {
    public FLPinLeiFragmentAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm, list);
    }

    String[] titles = {"Boy", "Girl", "Life"};

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
