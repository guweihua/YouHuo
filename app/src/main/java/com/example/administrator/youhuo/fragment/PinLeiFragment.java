package com.example.administrator.youhuo.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.administrator.youhuo.R;
import com.example.administrator.youhuo.view.PinLeiViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/11.
 */

public class PinLeiFragment extends BaseStausFragment {

    private PinLeiViewPager pinViewPager;
    private List<Fragment> list;

    @Override
    protected void initChildView() {
        View rootView = View.inflate(a, R.layout.fragment_fl_pinlei, null);
        TabLayout tab = (TabLayout) rootView.findViewById(R.id.tab);
        pinViewPager = (PinLeiViewPager) rootView.findViewById(R.id.PinLeiPager);
        pinViewPager.setSlide(false);
        tab.setupWithViewPager(pinViewPager);
        setContentView(rootView);
    }

    @Override
    protected void initData() {
        super.initData();
        list = new ArrayList<>();
    //    list.get(new )
    }

    @Override
    protected void initAdapter() {
        super.initAdapter();

    }

    @Override
    public void onReload() {

    }
}
