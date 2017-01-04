package com.example.administrator.youhuo.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.administrator.youhuo.R;

/**
 * Created by Administrator on 2016/12/30.
 */

public class StatusLayout extends FrameLayout implements View.OnClickListener {

    private View errorView;
    private OnErrorClickListener onErrorClickListener;
    private View loadView;
    private ImageView loadIv;
    private AnimationDrawable loadDrawable;
    private View normalView;

    public StatusLayout(Context context) {
        super(context);
        init();
    }

    public StatusLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StatusLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        initError();
        initLoad();
    }

    private void initLoad() {
        loadView = View.inflate(getContext(), R.layout.layout_load, null);
        loadIv = (ImageView) loadView.findViewById(R.id.loadIv);
        loadView.setBackgroundColor(Color.WHITE);
        loadIv.setImageResource(R.drawable.load);
        loadDrawable = (AnimationDrawable) loadIv.getDrawable();
        addView(loadView);
    }

    private void initError() {
        errorView = View.inflate(getContext(), R.layout.layout_error, null);
        errorView.setBackgroundColor(Color.WHITE);
        errorView.setOnClickListener(this);
        addView(errorView);
    }

    @Override
    public void addView(View child) {
        int childCount = getChildCount();
        if (childCount > 2) {
            throw new RuntimeException("只能添加一个孩子");
        }
        normalView = child;
        super.addView(child);
    }

    @Override
    public void onClick(View v) {
        if (onErrorClickListener != null){
            onErrorClickListener.onClick();
        }
    }

    public void switchToNormal(){
        loadDrawable.stop();
        removeView(normalView);
        addView(normalView);
    }
    public void switchToError(){
        loadDrawable.stop();
        removeView(errorView);
        addView(errorView);
    }

    public void switchToLoad(){
        loadDrawable.stop();
        removeView(loadView);
        addView(loadIv);
        loadDrawable.start();
    }

    public interface OnErrorClickListener {
        void onClick();
    }

    public void setOnErrorClickListener(OnErrorClickListener listener) {
        this.onErrorClickListener = listener;
    }
}
