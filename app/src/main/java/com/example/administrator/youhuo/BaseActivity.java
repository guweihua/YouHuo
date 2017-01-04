package com.example.administrator.youhuo;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.administrator.youhuo.utils.StatusBarUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2016/12/26.
 */

public class BaseActivity extends AppCompatActivity {

    protected Handler delayHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setFullScreentStatus(this);
       try {
           EventBus.getDefault().register(this);
       }catch (Exception e){

       }
    }

    @Override
    protected void onDestroy() {
        try {
            EventBus.getDefault().unregister(this);
        }catch (Exception e){

        }
        super.onDestroy();
        delayHandler.removeCallbacksAndMessages(null);
    }

    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
