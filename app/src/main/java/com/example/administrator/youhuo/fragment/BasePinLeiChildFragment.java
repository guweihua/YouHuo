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

import com.example.administrator.youhuo.R;
import com.example.administrator.youhuo.model.FLPinLeiFirstChildBean;
import com.example.administrator.youhuo.model.FLPinLeiSecondChildBean;
import com.example.administrator.youhuo.model.HttpModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        firstChildAdapter = new ArrayAdapter<String>(a,android.R.layout.simple_list_item_1,firstmenulist);
        secondSecondAdapter = new ArrayAdapter<String>(a,android.R.layout.simple_list_item_1,secondmenulist);

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
    }

    @Override
    public void onReload() {
        switchToLoad();
        loadData(path);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
