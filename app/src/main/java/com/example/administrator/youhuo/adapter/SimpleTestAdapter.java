package com.example.administrator.youhuo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.administrator.youhuo.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by admin on 2016/12/29.
 */

public class SimpleTestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public Context ctx;
    List<String> list = new ArrayList<>();

    public SimpleTestAdapter(Context ctx) {
        this.ctx = ctx;
        list.addAll(Arrays.asList(ctx.getResources().getStringArray(R.array.testData)));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerView.ViewHolder(View.inflate(ctx, android.R.layout.simple_list_item_1, null)) {
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextView tv = (TextView) holder.itemView;
        tv.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
