package com.example.administrator.youhuo.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.youhuo.R;
import com.example.administrator.youhuo.model.HomeDate;
import com.example.administrator.youhuo.model.HomeModel;
import com.example.administrator.youhuo.model.HttpModel;
import com.example.administrator.youhuo.utils.DimenUtils;
import com.example.administrator.youhuo.view.GralleyPager;
import com.example.administrator.youhuo.view.SupperRecyclerView;

import java.util.List;

/**
 * Created by Administrator on 2017/1/4.
 * HomeContainerAdapter 适配  图片  画廊  Viewpager tuijian四个布局content
 */

public class HomeContainerAdapter extends SimpleRecyclerAdapter {

    List<HomeDate> list;
    public HomeContainerAdapter(Context ctx, List list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HomeModel.ONEWEB){
            return new RecyclerView.ViewHolder(new ImageView(ctx)) {
            };
        }else if (viewType == HomeModel.HOTECATORY){
            SupperRecyclerView supperRecyclerView = new SupperRecyclerView(ctx);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ctx,LinearLayoutManager.HORIZONTAL,false);
            supperRecyclerView.setLayoutManager(linearLayoutManager);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DimenUtils.dp2px(150));
            supperRecyclerView.setLayoutParams(params);
            return new RecyclerView.ViewHolder(supperRecyclerView) {
            };
        }else if (viewType == HomeModel.HAODIAN){
            GralleyPager gralleyPager = new GralleyPager(ctx);
            RecyclerView.LayoutParams params=new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, DimenUtils.dp2px(200));
            gralleyPager.setLayoutParams(params);
          return new RecyclerView.ViewHolder(gralleyPager) {
          };
        }else if (viewType == HomeModel.TUIJIAN){
            View inflate = View.inflate(ctx, R.layout.item_home_tuijian, null);
            RecyclerView.LayoutParams params=new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, DimenUtils.dp2px(300));
            inflate.setLayoutParams(params);
            return new TuiJianHolder(inflate);
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case HomeModel.ONEWEB:
                handleOneWeb(holder,position);
                break;
            case HomeModel.HOTECATORY:
                handleHot(holder,position);
                break;
            case HomeModel.HAODIAN:
                handleHaoDian(holder,position);
                break;
            case HomeModel.TUIJIAN:
                handleTuiJian(holder,position);
                break;
        }
    }

    private void handleTuiJian(RecyclerView.ViewHolder holder, int position) {

    }

    private void handleHaoDian(RecyclerView.ViewHolder holder, int position) {

    }

    private void handleHot(RecyclerView.ViewHolder holder, int position) {

    }

    private void handleOneWeb(RecyclerView.ViewHolder holder, int position) {
        ImageView imageView = (ImageView) holder.itemView;
        HomeDate homeDate = list.get(position);
        String src = homeDate.map.get("src");
        Glide.with(ctx).load("http:"+src).error(R.mipmap.tt_default_message_error_image).into(imageView);
    }

    class TuiJianHolder extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView iv1;
        ImageView iv2;
        ImageView iv3;
        ImageView iv4;
        ImageView iv5;
        ImageView iv6;

        public TuiJianHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.TuiJianTv);
            iv1 = (ImageView) itemView.findViewById(R.id.TuijianIv1);
            iv2 = (ImageView) itemView.findViewById(R.id.TuiJianIv2);
            iv3 = (ImageView) itemView.findViewById(R.id.TuiJianIv3);
            iv4 = (ImageView) itemView.findViewById(R.id.TuiJianIv4);
            iv5 = (ImageView) itemView.findViewById(R.id.TuiJianIv5);
            iv6 = (ImageView) itemView.findViewById(R.id.TuiJianIv6);
        }
    }
}