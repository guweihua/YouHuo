package com.example.administrator.youhuo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.administrator.youhuo.event.GoToChooseEvent;
import com.example.administrator.youhuo.view.MySlidePane;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends BaseActivity {

    private com.example.administrator.youhuo.view.MySlidePane sliding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        this.sliding = (MySlidePane) findViewById(R.id.sliding);
    }

    @Override
    public void onBackPressed() {
        //TODO
    //   super.onBackPressed();
        gotoChooseActivity(null);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    private void gotoChooseActivity(GoToChooseEvent event) {
        startActivity(new Intent(this,ChooseActivity.class));
        overridePendingTransition(R.anim.choose_in,R.anim.main_out);
        finish();
    }

    public void toggleMenu(View v) {
        boolean open = sliding.isOpen();
        if (open) {
            sliding.closePane();
        } else {
            sliding.openPane();
        }
    }
}
