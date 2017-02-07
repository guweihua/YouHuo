package com.example.administrator.youhuo.fragment;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;

import com.bumptech.glide.Glide;
import com.example.administrator.youhuo.MyApplication;
import com.example.administrator.youhuo.R;
import com.example.administrator.youhuo.adapter.HotBrandAdapter;
import com.example.administrator.youhuo.adapter.SimplePagerAdapter;
import com.example.administrator.youhuo.model.HttpModel;
import com.example.administrator.youhuo.server.HomeBrandService;
import com.example.administrator.youhuo.server.HotService;
import com.example.administrator.youhuo.server.HotbrandService;
import com.example.administrator.youhuo.view.PinLeiViewPager;
import com.example.administrator.youhuo.view.SuperViewPager;
import com.example.administrator.youhuo.view.SupperRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by guweihua on 2017/2/6.
 */

public class PinPaiBaseChildFragment extends BaseStausFragment implements SuperViewPager.OnItemClickListener {

    private ListView menuLv;
    private SuperViewPager pinPaiBannderPager;
    private SupperRecyclerView supperRecycler;
    private TabLayout tabPinPai;
    private PinLeiViewPager containerPager;

    @Override
    public void onAttach(Activity activity) {
        EventBus.getDefault().register(this);
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initChildView() {
        View root = View.inflate(a, R.layout.fragment_base_pinpai, null);
        pinPaiBannderPager = (SuperViewPager) root.findViewById(R.id.pinPaiBannderPager);
        menuLv = (ListView) root.findViewById(R.id.menuLv);
        menuLv.setDividerHeight(0);
        supperRecycler = (SupperRecyclerView) root.findViewById(R.id.supperRecycler);
        supperRecycler.setLayoutManager(new LinearLayoutManager(a,LinearLayoutManager.HORIZONTAL,false));
        tabPinPai = (TabLayout) root.findViewById(R.id.tabPinPai);
        containerPager = (PinLeiViewPager) root.findViewById(R.id.basePager);
        containerPager.setSlide(false);
        tabPinPai.setupWithViewPager(containerPager);
        pinPaiBannderPager.setOnItemClickListener(this);
        setContentView(root);
    }

    @Override
    protected void initData() {
        super.initData();
        loadBanner();
        loadHot();
        loadContainer();
    }

    private void loadContainer() {

    }

    private void loadHot() {
        HotbrandService hotbrandService = MyApplication.app.retrofit.create(HotbrandService.class);
        Call<ResponseBody> hotBrand = hotbrandService.getHotBrand();
      hotBrand.enqueue(new Callback<ResponseBody>() {
          @Override
          public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
              List<String > list = new ArrayList<String>();
              try {
                  String string = response.body().string();
                  JSONObject jsonObject = new JSONObject(string);
                  JSONArray brand = jsonObject.getJSONArray("brand");
                  for (int i = 0; i < brand.length(); i++) {
                      JSONObject jsonObject1 = brand.getJSONObject(i);
                      String imgpath = jsonObject1.getString("imgpath");
                      list.add(HttpModel.IMG + imgpath);
                  }
                    supperRecycler.setAdapter(new HotBrandAdapter(a,list));
              } catch (IOException e) {
                  e.printStackTrace();
              } catch (JSONException e) {
                  e.printStackTrace();
              }
          }

          @Override
          public void onFailure(Call<ResponseBody> call, Throwable t) {

          }
      });
    }

    private void loadBanner() {
        // 可以通过getAragmuents获得具体的不同路径
        HomeBrandService homeBrandService = MyApplication.app.retrofit.create(HomeBrandService.class);
        Call<ResponseBody> allBrand = homeBrandService.getAllBrand();
        allBrand.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String string = response.body().string();
                    JSONArray jsonArray = new JSONArray(string);
                    List<String> bannerImagePaths = new ArrayList<String>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String imgpath = jsonObject.getString("imgpath");
                        bannerImagePaths.add(HttpModel.IMG + imgpath);
                    }
                    handleBanner(bannerImagePaths);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void handleBanner(List<String> bannerImagePaths) {
        List<View> list = new ArrayList<>();
        for (int i = 0; i < bannerImagePaths.size(); i++) {
            String s = bannerImagePaths.get(i);
            //设置图片样式
            View bannerItemView = getBannerItemView(s);
            list.add(bannerItemView);
        }
        pinPaiBannderPager.setAdapter(new SimplePagerAdapter<View>(list));
    }

    private View getBannerItemView(String s) {
        ImageView iv = new ImageView(a);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(a).load(s).into(iv);
        return iv;
    }

    @Override
    public void onReload() {

    }

    @Override
    public void onItenClick(ViewPager pager, View view, int position) {
        toast(position + "");
    }
}
