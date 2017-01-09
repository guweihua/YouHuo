package com.example.administrator.youhuo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.youhuo.R;
import com.example.administrator.youhuo.utils.DimenUtils;

import java.util.Map;

/**
 * Created by Administrator on 2017/1/4.
 * 适配 图片content
 */

public class HomeGarllyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Map<String,String> map;
    Context ctx;

    public HomeGarllyAdapter(Map<String, String> map,Context ctx) {
        this.map = map;
        this.ctx=ctx;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerView.ViewHolder(new ImageView(ctx)) {
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ImageView iv = (ImageView) holder.itemView;
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setLayoutParams(new RecyclerView.LayoutParams(DimenUtils.dp2px(300),RecyclerView.LayoutParams.MATCH_PARENT));

        String s = map.get("src" + (position + 1));
        Glide.with(ctx).load(s).error(R.mipmap.tt_default_message_error_image).into(iv);
    }

    @Override
    public int getItemCount() {
        return map.size();
    }
}
