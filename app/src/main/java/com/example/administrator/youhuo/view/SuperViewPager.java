package com.example.administrator.youhuo.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.youhuo.utils.HashList;

/**
 * Created by Administrator on 2017/1/4.
 */

public class SuperViewPager extends ViewPager {
    private OnItemClickListener onItemClickListener;

    public SuperViewPager(Context context) {
        super(context);
    }

    public SuperViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    HashList<View> viewHashList = new HashList<>();

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
        initChild();
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                initChild();
            }
        });
    }

    private void initChild() {
        viewHashList.clear();
        int i = 0;
        while (true){
           try {
               View v = (View) getAdapter().instantiateItem(null, i);
               if (v != null){
                   viewHashList.add(v);
               }
           }catch (Exception e){
               e.printStackTrace();
           }
            i++;
            if (i != viewHashList.size()){
                break;
            }
        }
        for (int j = 0;j < viewHashList.size();j++ ){
            final View view = viewHashList.get(j);
            final int finalJ = j;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null){
                        onItemClickListener.onItenClick(SuperViewPager.this,view,finalJ);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItenClick(ViewPager pager,View view,int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }
}
