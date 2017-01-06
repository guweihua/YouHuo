package com.example.administrator.youhuo.view;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.administrator.youhuo.R;
import com.example.administrator.youhuo.utils.DimenUtils;
import com.example.administrator.youhuo.utils.IDUtils;
import com.example.administrator.youhuo.utils.OrientationUtils;

/**
 * Created by Administrator on 2016/12/27.
 */

public class PullToRelashLayout extends RelativeLayout {

    private float dimension;
    int[] images = new int[13];
    private ImageView headView;
    private LayoutParams headParams;
    private ImageView footerView;
    private LayoutParams footerParams;
    private SupperRecyclerView supperRecyclerView;
    private LayoutParams recycerParams;
    private GestureDetector detector;
    private boolean bottomChanged;
    private boolean topChanged;
    private float startX;
    private float startY;
    private int lastTop;
    private int lastBottom;
    private OnPullToRefreshListener onPullToRefreshListener;
    private boolean isPull;
    private boolean isLoad;

    public PullToRelashLayout(Context context) {
        super(context);
        init();
    }

    public PullToRelashLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PullToRelashLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        dimension = getResources().getDimension(R.dimen.headAndFooterSize);
        detector = new GestureDetector(getContext(),new MyGestureListener());
        detector.setIsLongpressEnabled(false);
        initImages();
        initHead();
        initFooter();
        initContent();
    }

    public void setOnItemClickListener(SupperRecyclerView.OnItemClickListener listener){
        supperRecyclerView.setOnItemClickListener(listener);
    }



    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float v = (e2.getY() - e1.getY()) / 2;
            if ((v > 0 || headParams.topMargin != -headView.getHeight())
                && supperRecyclerView.getFirstVisibleChildPosition() == 0
                    && headView.getParent() != null){
                   /* && footerParams.bottomMargin == -footerView.getHeight()
                    && !bottomChanged){*/
                //下拉刷新

                headParams.topMargin = Math.max(lastTop + (int)v ,-headView.getHeight());
                headView.setLayoutParams(headParams);
                int i = (headParams.topMargin + headView.getHeight()) / 5;
                headView.setImageResource(images[i % images.length]);

            }else if ((v < 0 || footerParams.bottomMargin != -footerView.getHeight())
                    && supperRecyclerView.getLastVisibleChildPosition()
                            == supperRecyclerView.getAdapter().getItemCount() - 1
                    &&footerView.getParent() != null) {
                   /* && headParams.topMargin == -headView.getHeight()
                    && !topChanged){*/

                //上拉加载
                footerParams.bottomMargin = Math.max(-footerView.getHeight() , lastBottom + (int)(-v));
                footerView.setLayoutParams(footerParams);
                recycerParams.topMargin = -(footerParams.bottomMargin + footerView.getHeight());
                supperRecyclerView.setLayoutParams(recycerParams);
                int i = (footerParams.bottomMargin + footerView.getHeight()) / 5;
                footerView.setImageResource(images[i % images.length]);
            }
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = ev.getRawX();
                startY = ev.getRawY();
            case MotionEvent.ACTION_MOVE:
                float rawX = ev.getRawX();
                float rawY = ev.getRawY();
                Point startPoint = new Point((int)startX,(int)startY);
                Point endPoint = new Point((int)rawX,(int)rawY);
                if(supperRecyclerView.getAdapter()!=null)
                if (((isLoad && footerParams.bottomMargin != -footerView.getHeight())
                    || (isPull && headParams.topMargin != -headView.getHeight()))
                        ||(OrientationUtils.isVerticalScroll(startPoint,endPoint)
                        && ((supperRecyclerView.getFirstVisibleChildPosition() == 0 && rawY - startY > 0 )
                        || (supperRecyclerView.getLastVisibleChildPosition()
                                    == supperRecyclerView.getAdapter().getItemCount() - 1
                        && rawY - startY < 0 )))){

                    startX = ev.getRawX();
                    startY = ev.getRawY();
                    lastTop = headParams.topMargin;
                    lastBottom = footerParams.bottomMargin;
                    ev.setAction(MotionEvent.ACTION_DOWN);
                    detector.onTouchEvent(ev);
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = event.getRawX();
                startY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                detector.onTouchEvent(event);
                break;
            case MotionEvent.ACTION_UP:
             /*   bottomChanged = false;
                topChanged = false;*/
                if (isHead()){
                    handlerHead();
                }
                if (isFooter()){
                    handlerFooter();
                }
                break;
        }
        return true;
    }

    private void handlerFooter() {
        boolean b = footerParams.bottomMargin >= 0;
        if (b){
            //
            footerParams.bottomMargin = 0;
            recycerParams.topMargin = -headView.getHeight();
            startLoadAnimation(footerView);
            if (!isLoad){
                isLoad = true;
                if (onPullToRefreshListener != null){
                    onPullToRefreshListener.loadMore();
                }
            }else {
                footerParams.bottomMargin = -footerView.getHeight();
                recycerParams.topMargin = 0;
            }
            footerView.setLayoutParams(footerParams);
            supperRecyclerView.setLayoutParams(recycerParams);
        }
    }

    private boolean isFooter() {
        return !(footerParams.bottomMargin == -footerView.getHeight()
                && footerView.getParent() != null);
    }

    private void handlerHead() {
        boolean b = headParams.topMargin >= 0;
        if (b){
            //pullMore
            headParams.topMargin = 0;
            //open animation
            startLoadAnimation(headView);
            if (!isPull){
                isPull = true;
                if (onPullToRefreshListener != null){
                    onPullToRefreshListener.pullMore();
                }
            }
        }else {
            headParams.topMargin = - headView.getHeight();
        }
        headView.setLayoutParams(headParams);
    }

    private void startLoadAnimation(ImageView iv) {
        iv.setImageResource(R.drawable.animation_load);
        AnimationDrawable animationDrawable = (AnimationDrawable) iv.getDrawable();
        animationDrawable.start();
    }

    private boolean isHead() {
        return !(headParams.topMargin == -headView.getHeight())
                && headView.getParent() != null;
    }

    private boolean isPullOrLoad() {
        return !(headParams.topMargin == -headView.getHeight()
                && footerParams.bottomMargin == -footerView.getHeight());
    }

    private void initContent() {
        supperRecyclerView = new SupperRecyclerView(getContext());
        recycerParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        recycerParams.addRule(ABOVE, R.id.footer);
        recycerParams.addRule(BELOW, R.id.header);
    //    recycerParams.topMargin = DimenUtils.dp2px(25);
    //    recycerParams.bottomMargin = DimenUtils.dp2px(25);
        supperRecyclerView.setLayoutParams(recycerParams);
        addView(supperRecyclerView);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        supperRecyclerView.setAdapter(adapter);
    }

    private void initFooter() {
        footerView = new ImageView(getContext());
        footerView.setOverScrollMode(OVER_SCROLL_NEVER);
        footerParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) dimension);
        footerView.setId(R.id.footer);
        footerParams.addRule(CENTER_HORIZONTAL);
        footerParams.addRule(ALIGN_PARENT_BOTTOM);
        footerView.setLayoutParams(footerParams);
        footerParams.bottomMargin = (int) -dimension;
        footerView.setImageResource(images[0]);
        addView(footerView);
    }

    private void initHead() {
        headView = new ImageView(getContext());
        headView.setOverScrollMode(OVER_SCROLL_NEVER);
        headParams = new LayoutParams(LayoutParams.WRAP_CONTENT, (int) dimension);
        headView.setId(R.id.header);
        headParams.addRule(CENTER_HORIZONTAL);
        headView.setLayoutParams(headParams);
        headParams.topMargin = (int) -dimension;
        headView.setImageResource(images[0]);
        addView(headView);
    }

    private void initImages() {
        for (int i= 1; i<= images.length;i++){
            images[i - 1] = IDUtils.getDrawableId("js_classify_images_loading_icon_loading_frame_" + i);
        }
    }

    public SupperRecyclerView getSupperRecyclerView() {
        return supperRecyclerView;
    }

    public interface OnPullToRefreshListener{
        void pullMore();

        void loadMore();
    }

    public void setOnPullToRefreshListener(OnPullToRefreshListener listener){
        this.onPullToRefreshListener = listener;
    }

    public void removeFooter() {
        removeView(footerView);
    }

    public void removeHeader() {
        removeView(headView);
    }

    public void setPullSuccess(){
        headParams.topMargin = -headView.getHeight();
        isPull = false;
        headView.setLayoutParams(headParams);
    }

    public void setLoadSuccess(){
        footerParams.bottomMargin = -footerView.getHeight();
        recycerParams.topMargin = 0;
        isLoad = false;
        footerView.setLayoutParams(footerParams);
        supperRecyclerView.setLayoutParams(recycerParams);
    }
}
