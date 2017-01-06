package com.example.administrator.youhuo.base;

import android.content.Context;
import android.view.View;

/**
 * Created by Administrator on 2017/1/4.
 */

public abstract class BaseView {
    Context ctx;

    public BaseView(Context ctx) {
        this.ctx = ctx;
        initView();
        initListener();
    }

    public View root;

    public BaseView bindData(Object o) {
        return this;
    }

    public BaseView bindAdapter() {
        return this;
    }


    protected abstract void initListener();

    protected abstract void initView();

}
