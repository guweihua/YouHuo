package com.example.administrator.youhuo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class ChooseActivity extends BaseActivity implements View.OnClickListener {

    private android.widget.RadioButton boyBtn;
    private android.widget.RadioButton girlBtn;
    private android.widget.RadioButton kidBtn;
    private android.widget.RadioButton lifeBtn;
    private android.widget.RadioGroup rg;
    private android.widget.RelativeLayout activitychoose;

    private List<View> btns;
    private long lastTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        for (View v : btns) {
            v.setOnClickListener(this);
        }
    }

    private void initData() {
        btns = new ArrayList<>();
        btns.add(boyBtn);
        btns.add(girlBtn);
        btns.add(kidBtn);
        btns.add(lifeBtn);
    }

    private void initView() {
        this.activitychoose = (RelativeLayout) findViewById(R.id.activity_choose);
        this.rg = (RadioGroup) findViewById(R.id.rg);
        this.lifeBtn = (RadioButton) findViewById(R.id.lifeBtn);
        this.kidBtn = (RadioButton) findViewById(R.id.kidBtn);
        this.girlBtn = (RadioButton) findViewById(R.id.girlBtn);
        this.boyBtn = (RadioButton) findViewById(R.id.boyBtn);
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.main_in, R.anim.choose_out);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastTime < 1500) {
            finish();
        } else {
            toast("双击退出");
        }
        lastTime = System.currentTimeMillis();
    }
}
