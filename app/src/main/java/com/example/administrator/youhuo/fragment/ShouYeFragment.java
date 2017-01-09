package com.example.administrator.youhuo.fragment;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.youhuo.R;
import com.example.administrator.youhuo.adapter.HomeContainerAdapter;
import com.example.administrator.youhuo.adapter.SimplePagerAdapter;
import com.example.administrator.youhuo.adapter.SimpleTestAdapter;
import com.example.administrator.youhuo.base.BaseView;
import com.example.administrator.youhuo.base.HomeCateView;
import com.example.administrator.youhuo.event.HomeBrandEvent;
import com.example.administrator.youhuo.model.HomeDate;
import com.example.administrator.youhuo.model.HomeModel;
import com.example.administrator.youhuo.utils.DimenUtils;
import com.example.administrator.youhuo.utils.HomeUtils;
import com.example.administrator.youhuo.view.GralleyPager;
import com.example.administrator.youhuo.view.PullToRelashLayout;
import com.example.administrator.youhuo.view.SuperViewPager;
import com.example.administrator.youhuo.view.SupperRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/12/27.
 */

public class ShouYeFragment extends BaseStausFragment implements SuperViewPager.OnItemClickListener {



    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case -200:
                    switchToError();
                    break;
                case 1:
                    handleBanner();
                    break;
                case 2:
                    handleBard();
                    break;
                case 3:
                    handleAllHotData();
                    break;
            }
        }
    };
    private HomeContainerAdapter homeContainerAdapter;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

    private PullToRelashLayout pullToRelashLayout;
    private Thread thread;
    private SuperViewPager autoScrollViewPager;
    private List<View> bannerList;
    private List<HomeDate> bannerData;
    private List<HomeDate> banrdData;
    private List<HomeDate> allHomeData;

    @Override
    protected void initChildView() {
        ViewGroup root = (ViewGroup) View.inflate(a, R.layout.fragment_shouye, null);
        pullToRelashLayout = (PullToRelashLayout) root.findViewById(R.id.relash);
        setContentView(root);
    }

    @Override
    protected void initData() {
        super.initData();
        switchToLoad();
        thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Document parse = Jsoup.parse(new URL("https://m.yohobuy.com/boys"), 1000);
                   //TODO  干什么的
                    LoadBannerData(parse);
                    loadBanrdData(parse);
                    loadContainerData(parse);
                } catch (IOException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(-200);
                }
            }
        };
        thread.start();
    }

    private void loadContainerData(Document parse) {
        allHomeData = HomeUtils.getAllHomeData(parse);
        handler.sendEmptyMessage(3);
    }

    private void loadBanrdData(Document parse) {
        banrdData = HomeUtils.getBanrdData(parse);
        handler.sendEmptyMessage(2);
    }

    private void LoadBannerData(Document parse) {
        HomeDate haoDian = HomeUtils.getHaoDian();
        List<HomeDate> list = new ArrayList<>();
        Map<String, String> map = haoDian.map;
        for (int i = 0; i < map.size(); i++) {
            Map map1 = new HashMap();
            map1.put("src", map.get("src" + (i + 1)));
            list.add(new HomeDate(0,map1));
        }
        bannerData = list;
        handler.sendEmptyMessage(1);
    }


    private void handleAllHotData() {
        homeContainerAdapter = new HomeContainerAdapter(a,allHomeData);
        pullToRelashLayout.getSupperRecyclerView().setAdapter(homeContainerAdapter);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(a,2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int itemViewType = homeContainerAdapter.getItemViewType(position);
                if (itemViewType == HomeModel.NORMAL)
                    return 1;
                return gridLayoutManager.getSpanCount();
            }
        });
        pullToRelashLayout.getSupperRecyclerView().setLayoutManager(gridLayoutManager);
        toNormal();
    }

    private void handleBard() {
        BaseView baseView = new HomeCateView(a).bindData(banrdData).bindAdapter();
        View root = baseView.root;
        root.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        pullToRelashLayout.getSupperRecyclerView().addHeader(root);
        toNormal();
    }

    private void handleBanner() {
       if (autoScrollViewPager == null){
           bannerList = new ArrayList<>();
           autoScrollViewPager = new SuperViewPager(a);
           autoScrollViewPager.setOnItemClickListener(this);
           RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, DimenUtils.dp2px(250));
           autoScrollViewPager.setLayoutParams(params);
       }else {
           autoScrollViewPager.setAdapter(null);
           bannerList.clear();
       }
        for (int i = 0; i < bannerData.size(); i++) {
            HomeDate homeDate = bannerData.get(i);
            String src = homeDate.map.get("src");
            View bannerIv = getBannerIv(src);
            bannerList.add(bannerIv);
        }
    //    autoScrollViewPager.setAdapter(new SimplePagerAdapter<View>(bannerList));
        autoScrollViewPager.setAdapter(new SimplePagerAdapter<View>(bannerList));
        pullToRelashLayout.getSupperRecyclerView().addHeader(autoScrollViewPager);

        toNormal();
    }

    private void toNormal() {
        if (bannerData != null && bannerData.size() > 0
                && banrdData != null && banrdData.size() > 0
                && allHomeData != null && allHomeData.size() > 0) {

            switchToNormal();
        }
    }

    private View getBannerIv(String src) {
       /* src = src.substring(0, src.lastIndexOf("?"));
        src = "http:" + src;*/
        ImageView iv = new ImageView(a);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(a).load(src).error(R.mipmap.tt_default_message_error_image).placeholder(R.drawable.load).into(iv);
        return iv;
    }

    @Override
    public void onReload() {
        switchToLoad();
        initData();
    }

    @Override
    public void onDestroy() {
       try {
           thread.interrupt();
           handler.removeCallbacksAndMessages(null);
       }catch (SecurityException e){
           e.printStackTrace();
       }
        super.onDestroy();
    }

    @Override
    public void onItenClick(ViewPager pager, View view, int position) {
        toast(position + "");
    }

    @Subscribe
    public void onBrandClick(HomeBrandEvent event) {
        toast(event.title);
    }
}
