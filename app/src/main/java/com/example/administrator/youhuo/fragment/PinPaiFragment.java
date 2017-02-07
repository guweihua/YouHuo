package com.example.administrator.youhuo.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.youhuo.R;
import com.example.administrator.youhuo.view.PinLeiViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/11.
 */

public class PinPaiFragment extends BaseStausFragment {

    private View root;
    private TabLayout tab;
    private PinLeiViewPager pinPaiPager;
    private List<Fragment> list;

    @Override
    protected void initChildView() {
        root = View.inflate(a, R.layout.fragment_fl_pinpai, null);
        tab = (TabLayout) root.findViewById(R.id.tab);
        pinPaiPager = (PinLeiViewPager) root.findViewById(R.id.pinPaiPager);
        pinPaiPager.setSlide(false);
        tab.setupWithViewPager(pinPaiPager);
        setContentView(root);
    }

    @Override
    protected void initData() {
        super.initData();
        list = new ArrayList();
        list.add()
    }

    @Override
    protected void initAdapter() {
        super.initAdapter();
    }

    @Override
    public void onReload() {

    }
}
