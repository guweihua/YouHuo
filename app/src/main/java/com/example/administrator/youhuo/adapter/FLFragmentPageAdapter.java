package com.example.administrator.youhuo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

/**
 * Created by Administrator on 2017/1/11.
 */

public class FLFragmentPageAdapter extends FLSimpleFragmentPageAdapter {

    String[] titles = {"品类", "品牌", "关注"};

    public FLFragmentPageAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm, list);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
