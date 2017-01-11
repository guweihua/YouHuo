package com.example.administrator.youhuo.fragment;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.youhuo.MyApplication;
import com.example.administrator.youhuo.R;
import com.example.administrator.youhuo.adapter.HomeContainerAdapter;
import com.example.administrator.youhuo.adapter.SimplePagerAdapter;
import com.example.administrator.youhuo.base.BaseView;
import com.example.administrator.youhuo.base.HomeCateView;
import com.example.administrator.youhuo.event.HaoDianEvent;
import com.example.administrator.youhuo.event.HomeBrandEvent;
import com.example.administrator.youhuo.event.HotEcatoryEvent;
import com.example.administrator.youhuo.event.NormalEvent;
import com.example.administrator.youhuo.event.OneWebEvent;
import com.example.administrator.youhuo.event.TuiJianEvent;
import com.example.administrator.youhuo.model.HomeDate;
import com.example.administrator.youhuo.model.HomeModel;
import com.example.administrator.youhuo.model.HttpModel;
import com.example.administrator.youhuo.server.HomeBrandService;
import com.example.administrator.youhuo.utils.DimenUtils;
import com.example.administrator.youhuo.utils.HomeUtils;
import com.example.administrator.youhuo.utils.LogUtils;
import com.example.administrator.youhuo.view.GralleyPager;
import com.example.administrator.youhuo.view.PullToRelashLayout;
import com.example.administrator.youhuo.view.SuperViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 2016/12/27.
 */

public class ShouYeFragment extends BaseStausFragment implements SuperViewPager.OnItemClickListener, PullToRelashLayout.OnPullToRefreshListener, View.OnClickListener {



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
    private GridLayoutManager gridLayoutManager;
    private ImageView upView;


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
        upView = (ImageView) root.findViewById(R.id.upIv);
        upView.setOnClickListener(this);
        pullToRelashLayout.getSupperRecyclerView().addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int topPosition = gridLayoutManager.findFirstVisibleItemPosition();
    //            LogUtils.log("tag",topPosition+"");
                if (topPosition > 5){
                    upView.setVisibility(View.VISIBLE);
                }else {
                    upView.setVisibility(View.GONE);
                }
            }
        });
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
    //
        gridLayoutManager = new GridLayoutManager(a,2);
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
        pullToRelashLayout.getSupperRecyclerView().setAdapter(homeContainerAdapter);
        toNormal();
    }

    //全部分类
    private void handleBard() {
        BaseView baseView = new HomeCateView(a).bindData(banrdData).bindAdapter();
        View root = baseView.root;
        root.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DimenUtils.dp2px(180)));
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
        //
        pullToRelashLayout.setOnPullToRefreshListener(this);
        pullToRelashLayout.setOnPullToRefreshListener(this);
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

    @Override
    public void pullMore() {

    }

    @Override
    public void loadMore() {
    //    LogUtils.log("loadMore","loadMore");
        loadData();
    }

    private void loadData() {
        HomeBrandService homeBrandService = MyApplication.app.otherretrofit.create(HomeBrandService.class);
        Call<ResponseBody> allBrand = homeBrandService.getAllBrand();
       allBrand.enqueue(new Callback<ResponseBody>() {

           private List<HomeDate> tempList;

           @Override
           public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
               try {
                   tempList = new ArrayList<HomeDate>();
                   String string = response.body().string();
                   JSONObject jsonObject = new JSONObject(string);
                   JSONArray datas = jsonObject.getJSONArray("datas");
                   for (int i = 0; i < datas.length(); i++) {
                       JSONObject js = datas.getJSONObject(i);
                       Map<String ,String > map = new HashMap<String, String>();
                       map.put("title",js.getString("title"));
                       map.put("price", js.getString("price"));
                       map.put("dis", js.getString("discount"));
           //            LogUtils.log("imgpath",js.getString("imgpath")+"");
                       map.put("src", HttpModel.IMG + js.getString("imgpath"));
                       HomeDate homeDate = new HomeDate(HomeModel.NORMAL, map);
                       tempList.add(homeDate);
                   }

               } catch (IOException e) {
                   e.printStackTrace();
               } catch (JSONException e) {
                   e.printStackTrace();
               }catch (Exception e){
                   e.printStackTrace();
               }finally {
                   allHomeData.addAll(tempList);
                   pullToRelashLayout.removeFooter();
                   pullToRelashLayout.getSupperRecyclerView().getAdapter().notifyDataSetChanged();
               }
             pullToRelashLayout.setLoadSuccess();
           }

           @Override
           public void onFailure(Call<ResponseBody> call, Throwable t) {

           }
       });
    }

    @Subscribe
    public void OnOneWebClick(OneWebEvent event){
        toast(event.url+"");
    }

    @Subscribe
    public void OnHotItemClick(HotEcatoryEvent event){
        toast(event.src+"");
    }

    @Subscribe
    public void OnHaoDianItemClick(HaoDianEvent event){
        toast(event.str+"");
    }
    @Subscribe
    public void OnTuiJianItemClick(TuiJianEvent event){
        toast(event.con+"");
    }

    @Subscribe
    public void OnNormalItemClick(NormalEvent event){
        toast(event.normal+"");
    }

    @Override
    public void onClick(View v) {
        //回到第一个位置
        ScrollToFirstPosition();
    }

    private void ScrollToFirstPosition() {
        pullToRelashLayout.getSupperRecyclerView().smoothScrollToPosition(0);
    }
}
