package com.example.administrator.youhuo.fragment;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
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

import com.example.administrator.youhuo.R;
import com.example.administrator.youhuo.adapter.SimplePagerAdapter;
import com.example.administrator.youhuo.model.HomeDate;
import com.example.administrator.youhuo.utils.DimenUtils;
import com.example.administrator.youhuo.utils.HomeUtils;
import com.example.administrator.youhuo.view.GralleyPager;
import com.example.administrator.youhuo.view.PullToRelashLayout;
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

public class ShouYeFragment extends BaseStausFragment {

    private PullToRelashLayout pullToRelashLayout;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            switch (what) {
                case -200:
                    switchToError();
                    break;
            }
        }
    };
    private Thread thread;

    @Override
    protected void initChildView() {
        pullToRelashLayout = (PullToRelashLayout) View.inflate(a, R.layout.fragment_shouye, null);
        setContentView(pullToRelashLayout);
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
                    LoadBannerData(parse);
                } catch (IOException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(-200);
                }
            }
        };
        thread.start();
    }

    private void LoadBannerData(Document parse) {
        List<HomeDate> bannerData = HomeUtils.getBannerData(parse);
        for (int i = 0; i < bannerData.size(); i++) {
            String src = bannerData.get(i).map.get("src");
            String url = bannerData.get(i).map.get("url");
        }
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
}
