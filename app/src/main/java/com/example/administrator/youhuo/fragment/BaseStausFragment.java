package com.example.administrator.youhuo.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.youhuo.R;
import com.example.administrator.youhuo.view.StatusLayout;

/**
 * Created by Administrator on 2017/1/3.
 */

public abstract class BaseStausFragment extends BaseFragment implements StatusLayout.OnErrorClickListener {

    private StatusLayout statusLayout;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        statusLayout = (StatusLayout) inflater.inflate(R.layout.fragment_base_status, container,false);
        statusLayout.setOnErrorClickListener(this);
        initChildView();
        return statusLayout;
    }

    protected abstract void initChildView();

    public void setContentView(View view){
        statusLayout.addView(view);
    }

    public void switchToNormal(){
        statusLayout.switchToNormal();
    }

    public void switchToError(){
        statusLayout.switchToError();
    }

    public void switchToLoad(){
        statusLayout.switchToLoad();
    }

    @Override
    public void onClick() {
        onReload();
    }

    public abstract void onReload();
}
