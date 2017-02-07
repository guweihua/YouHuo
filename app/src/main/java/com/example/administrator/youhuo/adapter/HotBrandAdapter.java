package com.example.administrator.youhuo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.youhuo.utils.DimenUtils;

import java.util.List;

/**
 * Created by guweihua on 2017/2/6.
 */

public class HotBrandAdapter extends SimpleRecyclerAdapter {
    List<String> list;
    public HotBrandAdapter(Context ctx, List list) {
        super(ctx, list);
        this.list = list;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ImageView iv = (ImageView) holder.itemView;
        Glide.with(ctx).load(list.get(position)).into(iv);
        super.onBindViewHolder(holder, position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView iv = new ImageView(ctx);
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(DimenUtils.dp2px(200), ViewGroup.LayoutParams.MATCH_PARENT);
        params.rightMargin = DimenUtils.dp2px(10);
        iv.setLayoutParams(params);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        return new RecyclerView.ViewHolder(iv) {
        };
    }
}
