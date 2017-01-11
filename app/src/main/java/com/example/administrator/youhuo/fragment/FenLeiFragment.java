package com.example.administrator.youhuo.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.youhuo.R;
import com.example.administrator.youhuo.adapter.FLFragmentPageAdapter;
import com.example.administrator.youhuo.view.PinLeiViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/12/27.
 */

public class FenLeiFragment extends BaseFragment {
    private android.support.design.widget.TabLayout tab;
    private com.example.administrator.youhuo.view.PinLeiViewPager PLPager;
    private List<Fragment> list;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View inflate = inflater.inflate(R.layout.fragment_fenlei, container, false);
        this.PLPager = (PinLeiViewPager) inflate.findViewById(R.id.PLPager);
        this.tab = (TabLayout) inflate.findViewById(R.id.tab);
        tab.setupWithViewPager(PLPager);
        return inflate;
    }

    @Override
    protected void initData() {
        super.initData();
        list = new ArrayList<>();
        list.add(new PinLeiFragment());
        list.add(new PinPaiFragment());
        list.add(new GuanZhuFragment());
    }

    @Override
    protected void initAdapter() {
        super.initAdapter();
        PLPager.setAdapter(new FLFragmentPageAdapter(getFragmentManager(),list));
    }
}
