package com.example.administrator.youhuo.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/4.
 * HomeContainerAdapter 适配  图片  画廊  Viewpager tuijian四个布局content
 */

public class HomeContainerAdapter extends SimpleRecyclerAdapter {

    List<HomeDate> list;
    public HomeContainerAdapter(Context ctx, List list) {
        super(ctx, list);
        this.list= list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HomeModel.ONEWEB){
            ImageView imageView = new ImageView(ctx);
            RecyclerView.LayoutParams params=new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, DimenUtils.dp2px(150));
            params.bottomMargin=DimenUtils.dp2px(5);
            imageView.setLayoutParams(params);
            return new RecyclerView.ViewHolder(imageView) {
            };
        }else if (viewType == HomeModel.HOTECATORY){
            SupperRecyclerView supperRecyclerView = new SupperRecyclerView(ctx);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ctx,LinearLayoutManager.HORIZONTAL,false);
            supperRecyclerView.setLayoutManager(linearLayoutManager);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, DimenUtils.dp2px(150));
            params.bottomMargin=DimenUtils.dp2px(5);
            supperRecyclerView.setLayoutParams(params);
            return new RecyclerView.ViewHolder(supperRecyclerView) {
            };
        }else if (viewType == HomeModel.HAODIAN){
            GralleyPager gralleyPager = new GralleyPager(ctx);
            RecyclerView.LayoutParams params=new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, DimenUtils.dp2px(200));
            params.bottomMargin=DimenUtils.dp2px(5);
            gralleyPager.setLayoutParams(params);
          return new RecyclerView.ViewHolder(gralleyPager) {
          };
        }else if (viewType == HomeModel.TUIJIAN){
            View inflate = View.inflate(ctx, R.layout.item_home_tuijian, null);
            RecyclerView.LayoutParams params=new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, DimenUtils.dp2px(350));
            params.bottomMargin=DimenUtils.dp2px(5);
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
    //    TuiJianHolder tuiJianHolder = (TuiJianHolder) holder;
        HomeDate homeDate = list.get(position);
        Map<String, String> map = homeDate.map;
        ((TuiJianHolder)holder).tv.setText(map.get("title")+"");
        Glide.with(ctx).load(map.get("src1")).error(R.mipmap.tt_default_message_error_image).into(((TuiJianHolder) holder).iv1);
        Glide.with(ctx).load(map.get("src2")).error(R.mipmap.tt_default_message_error_image).into(((TuiJianHolder) holder).iv2);
        Glide.with(ctx).load(map.get("src3")).error(R.mipmap.tt_default_message_error_image).into(((TuiJianHolder) holder).iv3);
        Glide.with(ctx).load(map.get("src4")).error(R.mipmap.tt_default_message_error_image).into(((TuiJianHolder) holder).iv4);
        Glide.with(ctx).load(map.get("src5")).error(R.mipmap.tt_default_message_error_image).into(((TuiJianHolder) holder).iv5);
        Glide.with(ctx).load(map.get("src6")).error(R.mipmap.tt_default_message_error_image).into(((TuiJianHolder) holder).iv6);
    }

    private void handleHaoDian(RecyclerView.ViewHolder holder, int position) {
        GralleyPager pager = (GralleyPager) holder.itemView;
        if (pager.getAdapter() == null){
            HomeDate homeDate = list.get(position);
            Map<String, String> map = homeDate.map;
            List<View> viewList = new ArrayList<>();
            for (int i = 0; i < map.size(); i++) {
                View iv = getIv(map.get("src" + (i + 1)));
                viewList.add(iv);
            }
            pager.setAdapter(new HomeHaoDianAdapter(viewList));
            pager.setCurrentItem(100 * map.size());
        }
    }

    private View getIv(String s) {
        ImageView iv = new ImageView(ctx);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(ctx).load(s).error(R.mipmap.tt_default_message_error_image).into(iv);
        return iv;
    }

    private void handleHot(RecyclerView.ViewHolder holder, int position) {
        SupperRecyclerView pager = (SupperRecyclerView) holder.itemView;
        if (pager.getAdapter() == null){
            HomeDate homeDate = list.get(position);
            pager.setAdapter(new HomeGarllyAdapter(homeDate.map,ctx));
        }
    }

    private void handleOneWeb(RecyclerView.ViewHolder holder, int position) {
        ImageView imageView = (ImageView) holder.itemView;
        HomeDate homeDate = list.get(position);
        String src = homeDate.map.get("src");
        Glide.with(ctx).load("http:"+src).error(R.mipmap.tt_default_message_error_image).into(imageView);
    }

    @Override
    public int getItemViewType(int position) {
        HomeDate homeDate = list.get(position);
        return homeDate.type;
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
