package com.example.administrator.youhuo.fragment;

import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.administrator.youhuo.MyApplication;
import com.example.administrator.youhuo.R;
import com.example.administrator.youhuo.model.FLPinLeiFirstChildBean;
import com.example.administrator.youhuo.model.FLPinLeiSecondChildBean;
import com.example.administrator.youhuo.model.HttpModel;
import com.example.administrator.youhuo.server.BasePinLeiFirstMenuServer;
import com.example.administrator.youhuo.server.BasePinLeiSecondServer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/1/11.
 */

public class BasePinLeiChildFragment extends BaseStausFragment implements AdapterView.OnItemClickListener {

    Map<String, String> map = new HashMap<>();
    List<String> firstmenulist = new ArrayList<>();
    List<String> secondmenulist = new ArrayList<>();
    private DrawerLayout drawer;
    private ListView firstMenuLv;
    private ListView secondMenuLv;
    private ArrayAdapter<String> firstChildAdapter;
    private ArrayAdapter<String> secondSecondAdapter;
    private String type;
    private String path;
    private List<FLPinLeiFirstChildBean> firstChildBeanList;
    private List<FLPinLeiSecondChildBean> secondChildBeanList;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        map.put("boy", HttpModel.CATEGORY_BOYS_URL);
        map.put("girl", HttpModel.CATEGORY_GIRLS_URL);
        map.put("life", HttpModel.CATEGORY_LIFE_URL);
        return super.initView(inflater, container);
    }

    @Override
    protected void initChildView() {
        drawer = (DrawerLayout) View.inflate(a, R.layout.fragment_base_fenlei_pinlei_child, null);
        firstMenuLv = (ListView) drawer.findViewById(R.id.firstMenu);
        secondMenuLv = (ListView) drawer.findViewById(R.id.secondMenu);

        firstChildAdapter = new ArrayAdapter<String>(a, android.R.layout.simple_list_item_1, firstmenulist);
        secondSecondAdapter = new ArrayAdapter<String>(a, android.R.layout.simple_list_item_1, secondmenulist);

        firstMenuLv.setAdapter(firstChildAdapter);
        secondMenuLv.setAdapter(secondSecondAdapter);

        firstMenuLv.setOnItemClickListener(this);
        drawer.setDrawerShadow(R.drawable.translpant_shadow, Gravity.LEFT);
        drawer.setScrimColor(Color.TRANSPARENT);
        setContentView(drawer);
        switchToLoad();
    }

    @Override
    protected void initData() {
        super.initData();
        type = getArguments().getString("type");
        path = map.get(type);
        loadData(path);
    }

    private void loadData(String path) {
        //TODO
        BasePinLeiFirstMenuServer basePinLeiFirstMenuServer = MyApplication.app.retrofit.create(BasePinLeiFirstMenuServer.class);
        Call<ResponseBody> childMenu = basePinLeiFirstMenuServer.getChildMenu(path);
        childMenu.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                switchToNormal();
                try {
                    parseData(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                switchToError();
            }
        });
    }

    private void parseData(String string) {
        firstChildBeanList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(string);
            JSONArray jsonArray = jsonObject.getJSONArray(type);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                String id = jsonObject1.getString("_id");
                String name = jsonObject1.getString("name");
                String sexId = jsonObject1.getString("SexId");
                firstChildBeanList.add(new FLPinLeiFirstChildBean(id,name,sexId));
                firstmenulist.add(name);
            }
            firstChildAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onReload() {
        switchToLoad();
        loadData(path);
    }

    String second = "";

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        boolean drawerOpen = drawer.isDrawerOpen(secondMenuLv);
        if (drawerOpen){
            if (second.equals(firstmenulist.get(position))){
                close();
            }else {
                switchData(firstChildBeanList.get(position).get_id());
            }
        }else {
            open();
            switchData(firstChildBeanList.get(position).get_id());
        }
        second = firstmenulist.get(position);
    }

    private void open() {
        drawer.openDrawer(secondMenuLv);
    }

    private void switchData(String menuType) {
        BasePinLeiSecondServer basePinLeiSecondServer = MyApplication.app.otherretrofit.create(BasePinLeiSecondServer.class);
        Call<ResponseBody> second = basePinLeiSecondServer.getSecond(menuType);
        second.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String string = response.body().string();
                    parseSecondData(string);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                toast("请求失败");
            }
        });
    }

    private void parseSecondData(String string) {
        secondChildBeanList = new ArrayList<>();
        secondmenulist.clear();
        try {
            JSONObject jsonObject = new JSONObject(string);
            JSONArray datas = jsonObject.getJSONArray("datas");
            for (int i = 0; i < datas.length(); i++) {
                JSONObject jsonObject1 = datas.getJSONObject(i);
                String id = jsonObject1.getString("_id");
                String  name = jsonObject1.getString("name");
                String categoryid = jsonObject1.getString("categoryid");
                secondChildBeanList.add(new FLPinLeiSecondChildBean(id,name,categoryid));
                secondmenulist.add(name);
            }
            secondSecondAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void close() {
        drawer.closeDrawer(secondMenuLv);
    }
}
