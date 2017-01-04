package com.example.administrator.youhuo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.youhuo.R;
import com.example.administrator.youhuo.model.HomeDate;

import java.util.List;

/**
 * Created by Administrator on 2017/1/4.
 */

public class HomeBarndAdapter extends SimpleRecyclerAdapter {
    List<HomeDate> list;
    public HomeBarndAdapter(Context ctx, List list) {
        super(ctx, list);
        this.list = list;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        HomeDate homeDate = list.get(position);
        String src = homeDate.map.get("src");
        String title = homeDate.map.get("title");
        myViewHolder.tv.setText(title);
        Glide.with(ctx).load("http:"+src).error(R.mipmap.tt_default_message_error_image).into(myViewHolder.iv);
        super.onBindViewHolder(holder, position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(View.inflate(ctx, R.layout.item_home_barnd,null));
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView iv;
        TextView tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }
}
