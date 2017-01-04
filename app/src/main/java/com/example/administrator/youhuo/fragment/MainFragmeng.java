package com.example.administrator.youhuo.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.administrator.youhuo.R;
import com.example.administrator.youhuo.event.GoToChooseEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/27.
 */

public class MainFragmeng extends BaseFragment implements View.OnClickListener {

    private View rootView;
    private FrameLayout fragmentGroup;
    private RadioButton shouyeBtn;
    private RadioButton fenleiBtn;
    private RadioButton guangBtn;
    private RadioButton gouwucheBtn;
    private RadioButton woodeBtn;

    private ShouYeFragment shouYeFragment;
    private FenLeiFragment fenLeiFragment;
    private GuangFragment guangFragment;
    private GouWuCheFragment gouWuCheFragment;
    private WoDeFragment woDeFragment;

    private int curentPosition;
    private RadioGroup rg;
    private List<RadioButton> list;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        fragmentGroup = (FrameLayout) rootView.findViewById(R.id.fragmentGroup);
        shouyeBtn = (RadioButton) rootView.findViewById(R.id.homeBtn);
        fenleiBtn = (RadioButton) rootView.findViewById(R.id.fenleiBtn);
        guangBtn = (RadioButton) rootView.findViewById(R.id.guangBtn);
        gouwucheBtn = (RadioButton) rootView.findViewById(R.id.gouwucheBtn);
        woodeBtn = (RadioButton) rootView.findViewById(R.id.wodeBtn);
        rg = (RadioGroup) rootView.findViewById(R.id.rg);
        initRadioButton();
        initListener();
        return rootView;
    }

    private void initRadioButton() {
        list = new ArrayList<RadioButton>();
        list.add(shouyeBtn);
        list.add(fenleiBtn);
        list.add(guangBtn);
        list.add(gouwucheBtn);
        list.add(woodeBtn);
    }

    private void initListener() {
        for (View v:list){
            v.setOnClickListener(this);
        }
    }

    @Override
    protected void initData() {
        super.initData();
        initFragment();
    }

    private void initFragment() {
        shouYeFragment = new ShouYeFragment();
        fenLeiFragment = new FenLeiFragment();
        guangFragment = new GuangFragment();
        gouWuCheFragment = new GouWuCheFragment();
        woDeFragment = new WoDeFragment();
        getFragmentManager().beginTransaction().replace(R.id.fragmentGroup, shouYeFragment).commit();
        curentPosition = 0;
    }

    @Override
    public void onClick(View v) {
        getCurrentId();
        if (v.getId() == getCurrentId()){
            if (curentPosition == 0){
                EventBus.getDefault().post(new GoToChooseEvent());
            }
            return;
        }
        switch (v.getId()){
            case R.id.homeBtn:
                getFragmentManager().beginTransaction().replace(R.id.fragmentGroup, shouYeFragment).commit();
                curentPosition = 0;
                break;
            case R.id.fenleiBtn:
                getFragmentManager().beginTransaction().replace(R.id.fragmentGroup, fenLeiFragment).commit();
                curentPosition = 1;
                break;
            case R.id.guangBtn:
                getFragmentManager().beginTransaction().replace(R.id.fragmentGroup, guangFragment).commit();
                curentPosition = 2;
                break;
            case R.id.gouwucheBtn:
                getFragmentManager().beginTransaction().replace(R.id.fragmentGroup, gouWuCheFragment).commit();
                curentPosition = 3;
                break;
            case R.id.wodeBtn:
                getFragmentManager().beginTransaction().replace(R.id.fragmentGroup, woDeFragment).commit();
                curentPosition = 4;
                break;
        }
    }

    public int getCurrentId() {
        return list.get(curentPosition).getId();
    }
}
