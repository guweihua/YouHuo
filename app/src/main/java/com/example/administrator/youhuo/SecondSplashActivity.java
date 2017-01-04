package com.example.administrator.youhuo;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.administrator.youhuo.listener.SimpleAnimatorListener;

public class SecondSplashActivity extends BaseActivity implements ValueAnimator.AnimatorUpdateListener {

    private android.widget.ImageView iv;
    private android.view.View mark;
    private android.widget.RelativeLayout activitysecondsplash;

    private ValueAnimator animator;
    private boolean isCancel;
    private ObjectAnimator scaleAnimator;
    private View decorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_second_splash);
        initView();
        initMark();
        initDecorView();
        initScaleAnimation();
    }

    private void initDecorView() {
        decorView.post(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator animator = ObjectAnimator.ofFloat(decorView, "translationY", -decorView.getHeight(), 0);
                animator.setDuration(1000);
                animator.setInterpolator(new BounceInterpolator());
                animator.setEvaluator(new FloatEvaluator());
                animator.start();
            }
        });
    }

    private void initScaleAnimation() {
        iv.setScaleX(1.1f);
        iv.setScaleY(1.1f);
        PropertyValuesHolder xHolder = PropertyValuesHolder.ofFloat("scaleX", 1.1f, 1.0f);
        PropertyValuesHolder yHolder = PropertyValuesHolder.ofFloat("scaleY", 1.1f, 1.0f);
        scaleAnimator = ObjectAnimator.ofPropertyValuesHolder(iv, xHolder, yHolder);
        scaleAnimator.setDuration(3000);
        scaleAnimator.setInterpolator(new LinearInterpolator());
        scaleAnimator.setEvaluator(new FloatEvaluator());
        scaleAnimator.addListener(new ScaleAimationListener());
    }

    private void initMark() {
        animator = ValueAnimator.ofInt(Color.parseColor("#333333"), Color.parseColor("#00333333"));
        animator.setDuration(3000);
        animator.setEvaluator(new ArgbEvaluator());
        animator.addUpdateListener(this);
        animator.setInterpolator(new LinearInterpolator());
        animator.addListener(new MarkAnimationListener());
        animator.start();
    }

    private void initView() {
        this.activitysecondsplash = (RelativeLayout) findViewById(R.id.activity_second_splash);
        this.mark = (View) findViewById(R.id.mark);
        this.iv = (ImageView) findViewById(R.id.iv);
    }

    public void click(View v) {
        clearAnimation();
        gotoChooseActivity();
    }

    /**
     * 清空动画 防止内存泄露
     */
    private void clearAnimation() {
        isCancel = true;
        animator.cancel();
        scaleAnimator.cancel();
    }

    @Override
    public void onBackPressed() {
        clearAnimation();
        super.onBackPressed();
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        mark.setBackgroundColor((Integer) animation.getAnimatedValue());
    }

    class MarkAnimationListener extends SimpleAnimatorListener {
        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            scaleAnimator.start();
        }
    }

    class ScaleAimationListener extends SimpleAnimatorListener {
        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            if (!isCancel) {
                gotoChooseActivity();
            }
        }
    }

    private void gotoChooseActivity() {
        startActivity(new Intent(SecondSplashActivity.this, ChooseActivity.class));
        finish();
    }


}
