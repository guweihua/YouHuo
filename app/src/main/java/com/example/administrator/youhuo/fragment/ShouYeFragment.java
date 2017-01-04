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
import com.example.administrator.youhuo.adapter.SimplePagerAdapter;
import com.example.administrator.youhuo.adapter.SimpleTestAdapter;
import com.example.administrator.youhuo.model.HomeDate;
import com.example.administrator.youhuo.utils.DimenUtils;
import com.example.administrator.youhuo.utils.HomeUtils;
import com.example.administrator.youhuo.view.GralleyPager;
import com.example.administrator.youhuo.view.PullToRelashLayout;
import com.example.administrator.youhuo.view.SuperViewPager;
import com.example.administrator.youhuo.view.SupperRecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by admin on 2016/12/27.
 */

public class ShouYeFragment extends BaseStausFragment implements SuperViewPager.OnItemClickListener {

    private PullToRelashLayout pullToRelashLayout;

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
            }
        }
    };

    private Thread thread;
    private SuperViewPager autoScrollViewPager;
    private List<View> bannerList;
    private List<HomeDate> bannerData;

    @Override
    protected void initChildView() {
        ViewGroup root = (ViewGroup) View.inflate(a, R.layout.fragment_shouye, null);
        pullToRelashLayout = (PullToRelashLayout) root.findViewById(R.id.relash);
        setContentView(root);
    }

    @Override
    protected void initData() {
        super.initData();
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
        HomeUtils.getAllHomeData(parse);
    }

    private void loadBanrdData(Document parse) {
        List<HomeDate> bannderData = HomeUtils.getBanrdData(parse);
    }

    private void LoadBannerData(Document parse) {
        bannerData = HomeUtils.getBannerData(parse);
        handler.sendEmptyMessage(1);
        /*for (int i = 0; i < bannerData.size(); i++) {
            String src = bannerData.get(i).map.get("src");
            String url = bannerData.get(i).map.get("url");
        }*/
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
        autoScrollViewPager.setAdapter(new SimplePagerAdapter<View>(bannerList));
        pullToRelashLayout.getSupperRecyclerView().addHeader(autoScrollViewPager);
        pullToRelashLayout.setAdapter(new SimpleTestAdapter(a));
    }

    private View getBannerIv(String src) {
        src = src.substring(0, src.lastIndexOf("?"));
        src = "http:" + src;
        ImageView iv = new ImageView(a);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(a).load(src).error(R.mipmap.tt_default_message_error_image).placeholder(R.drawable.load).into(iv);
        return iv;
    }

    @Override
    public void onReload() {
        switchToLoad();
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
}
