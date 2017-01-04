package com.example.administrator.youhuo.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.youhuo.R;

/**
 * Created by Administrator on 2016/12/27.
 */

public abstract class BaseFragment extends Fragment {
    Activity a;
    private View rootView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        a = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = initView(inflater, container);
            initData();
            initAdapter();
        }
        return rootView;
    }

    protected  void initAdapter(){};

    protected  void initData(){};

    protected abstract View initView(LayoutInflater inflater, ViewGroup container);

    public void toast(String msg){
        Toast.makeText(a,msg,Toast.LENGTH_SHORT).show();
    }
}
