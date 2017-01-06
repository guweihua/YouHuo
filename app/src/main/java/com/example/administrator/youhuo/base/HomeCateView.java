package com.example.administrator.youhuo.base;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.youhuo.adapter.HomeBarndAdapter;
import com.example.administrator.youhuo.event.HomeBrandEvent;
import com.example.administrator.youhuo.model.HomeDate;
import com.example.administrator.youhuo.view.SupperRecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Administrator on 2017/1/4.
 */

public class HomeCateView extends BaseView implements SupperRecyclerView.OnItemClickListener {

    private List<HomeDate> list;
    private SupperRecyclerView supperRecyclerView;

    public HomeCateView(Context ctx) {
        super(ctx);
    }

    @Override
    protected void initListener() {
        supperRecyclerView.setOnItemClickListener(this);
    }

    @Override
    protected void initView() {
        supperRecyclerView = new SupperRecyclerView(ctx);
        supperRecyclerView.setLayoutManager(new GridLayoutManager(ctx,5));
        root = supperRecyclerView;
    }

    @Override
    public BaseView bindData(Object o) {
        list = (List<HomeDate>) o;
        return super.bindData(o);
    }

    @Override
    public BaseView bindAdapter() {
        supperRecyclerView.setAdapter(new HomeBarndAdapter(ctx,list));
        return super.bindAdapter();
    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View view, int position) {
        String title = list.get(position).map.get("title");
        EventBus.getDefault().post(new HomeBrandEvent(title));
    }
}
