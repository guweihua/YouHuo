package com.example.administrator.youhuo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2016/12/26.
 */

public class FirstSplashActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_splash);
        delayHandler.postDelayed(new GoToSecondRunnable(), 1500);
    }

    private class GoToSecondRunnable implements Runnable {
        @Override
        public void run() {
            startActivity(new Intent(FirstSplashActivity.this, SecondSplashActivity.class));
            finish();
        }
    }
}
