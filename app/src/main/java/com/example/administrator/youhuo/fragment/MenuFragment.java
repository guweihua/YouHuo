package com.example.administrator.youhuo.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.youhuo.R;
import com.example.administrator.youhuo.view.NoSlideViewPager;

/**
 * Created by Administrator on 2016/12/27.
 */

public class MenuFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    protected NoSlideViewPager rootView;
    private ListView left;
    private ListView right;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        rootView = (NoSlideViewPager) inflater.inflate(R.layout.fragment_menu, container, false);
        return rootView;
    }

    @Override
    protected void initData() {
        super.initData();
        left = (ListView) rootView.getChildAt(0);
        right = (ListView) rootView.getChildAt(1);
        left.setOnItemClickListener(this);
        right.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (left == parent) {
            handleLeftMenuClick(position);
        } else {
            handleRightMenuClick(position);
        }
    }

    private void handleRightMenuClick(int position) {
        if (position == 0) {
            rootView.setCurrentItem(0);
        }
    }

    private void handleLeftMenuClick(int position) {
        if (position == left.getCount() - 1) {
            rootView.setCurrentItem(1);
        }
    }
}
