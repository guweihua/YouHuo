package com.example.administrator.youhuo.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2016/12/29.
 */

public class GralleyPager extends ViewPager {

    public GralleyPager(Context context) {
        super(context);
        init();
        initTranslation();
    }

    public GralleyPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    Set<View> set = new HashSet<>();

    private void initTranslation() {
        setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                View currentChild = (View) getAdapter().instantiateItem(null, position);
                View lastChild = (View) getAdapter().instantiateItem(null, position + 1);
                currentChild.setScaleX(1.2f - Math.min(1,positionOffset * 5f) * 0.2f);
                currentChild.setScaleY(1.2f - Math.min(1,positionOffset * 5f) * 0.2f);
                currentChild.setAlpha(1 - Math.min(1,positionOffset * 5f) * 0.5f);
                if (positionOffset > 0.8f){
                    lastChild.setScaleY(1.0f + (5 * positionOffset - 4) * 0.2f);
                    lastChild.setScaleX(1.0f + (5 * positionOffset - 4) * 0.2f);
                    lastChild.setAlpha(0.5f + (5 * positionOffset - 4) * 0.5f);
                }
            }

            @Override
            public void onPageSelected(int position) {
                View curent = (View) getAdapter().instantiateItem(null, position);
                updateViews(curent);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void updateViews(View curent) {
        for (View view:set){
          if (curent == view){
              view.setAlpha(1.0f);
              view.setScaleX(1.2f);
              view.setScaleY(1.2f);
          }else{
              view.setAlpha(0.5f);
              view.setScaleX(1.0f);
              view.setScaleY(1.0f);
          }
        }
    }


    private void init() {
        post(new Runnable() {
            @Override
            public void run() {
                setPadding(getWidth()/4,getHeight()/12,getWidth()/4,getHeight()/12);
                setClipToPadding(false);
                setPageMargin(getWidth()/12);
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        set.clear();
        int pre = 0;
        int i = 0;
        PagerAdapter adapter = getAdapter();
        if (adapter != null){
            while (true){
                View ChildAt = (View) adapter.instantiateItem(null, i);
                set.add(ChildAt);
                ChildAt.setAlpha(0.5f);

                if (pre == set.size()){
                    break;
                }
                i++;
                pre++;
            }
        }
    }
}
