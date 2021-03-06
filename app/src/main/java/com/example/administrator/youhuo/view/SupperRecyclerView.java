package com.example.administrator.youhuo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/27.
 */

public class SupperRecyclerView extends RecyclerView {

    private GestureDetector detector;
    private OnItemClickListener onItemClickListener;

    List<View> heads = new ArrayList<>();
    List<View> footers = new ArrayList<>();
    public static final int HEADTYPE = -1;
    public static final int FOOTERTYPE = 2000;
    private SupperRecyclerView supperRecyclerView;

    public SupperRecyclerView(Context context) {
        super(context);
        init();
    }

    public SupperRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SupperRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        detector = new GestureDetector(getContext(),new MyOnGenstorListener());
        setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        detector.onTouchEvent(e);
        return super.onTouchEvent(e);
    }

    class MyOnGenstorListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            int position = pointToPosition(e.getX(), e.getY());
            View view = pointToChild(e.getX(), e.getY());
            if (onItemClickListener != null && view != null){
                onItemClickListener.onItemClick(SupperRecyclerView.this,view,position);
            }
            return true;
        }
    }

    private int pointToPosition(float x, float y) {
        View view = pointToChild(x,y);
        if (view == null)
            return -1;
        ViewHolder containingViewHolder = findContainingViewHolder(view);
        return containingViewHolder.getAdapterPosition();
    }

    private View pointToChild(float x, float y) {
        return findChildViewUnder(x,y);
    }

    public interface OnItemClickListener{
        void onItemClick(RecyclerView recyclerView, View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener = listener;
    }

    public SupperRecyclerView getSupperRecyclerView() {
        return supperRecyclerView;
    }

    public void addHeader(View view) {
        check();
        heads.add(view);
    }

    public void addFooter(View view) {
        check();
        footers.add(view);
    }

    private void check() {
        Adapter adapter = getAdapter();
        if (!(adapter == null || adapter.getClass() == HeadFooterAdapter.class)) {
            throw new RuntimeException("addHead必须在setAdapter之前");
        }
    }

    public void removeHeader(View view) {
        heads.remove(view);
        getAdapter().notifyDataSetChanged();
    }

    public void removeFooter(View view) {
        footers.remove(view);
        getAdapter().notifyDataSetChanged();
    }

    public void clearHeaders(View view) {
        heads.clear();
        getAdapter().notifyDataSetChanged();
    }

    public void clearFooters(View view) {
        footers.clear();
        getAdapter().notifyDataSetChanged();
    }

    @Override
    public void setAdapter(Adapter adapter) {
        RecyclerView.Adapter adapter1 = adapter;
        if (adapter != null){
            if (heads.size() > 0 || footers.size() > 0){
                adapter1 = new HeadFooterAdapter(adapter);
            }
        }
        super.setAdapter(adapter1);
    }

    class HeadFooterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

         public RecyclerView.Adapter rawAdapter;

         public HeadFooterAdapter(Adapter rawAdapter) {
             this.rawAdapter = rawAdapter;
         }

         public RecyclerView.Adapter getRawAdapter() {
             return rawAdapter;
         }

         //TODO
         //理解
         @Override
         public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
             if (viewType <= HEADTYPE){
                 return new ViewHolder(heads.get(HEADTYPE - viewType)) {
                 };
             }else if (viewType >= FOOTERTYPE){
                 return new ViewHolder(footers.get(viewType - FOOTERTYPE)) {
                 };
             }
             return rawAdapter.onCreateViewHolder(parent,viewType);
         }

         @Override
         public void onBindViewHolder(ViewHolder holder, int position) {
            if (position >= heads.size()
                    && position < heads.size() + rawAdapter.getItemCount()
                    && rawAdapter != null){

                rawAdapter.onBindViewHolder(holder,position - heads.size());
            }
         }

         @Override
         public int getItemViewType(int position) {
             if (position < heads.size()){
                 //TODO 注意条目的点击监听会出现问题
                 // 头部
                 return HEADTYPE - position;
             }else if (position < heads.size() + rawAdapter.getItemCount()){
                 return rawAdapter.getItemViewType(position - heads.size());
             }
             return FOOTERTYPE + (position - heads.size() - rawAdapter.getItemCount());
         }

         @Override
         public int getItemCount() {
             return rawAdapter.getItemCount() + heads.size() + footers.size();
         }
     }

    public int getHeadsCount() {
        return heads.size();
    }

    public int getFooterCount() {
        return footers.size();
    }

    @Override
    public void setLayoutManager(final LayoutManager layout) {
        super.setLayoutManager(layout);
       post(new Runnable() {
           @Override
           public void run() {
               if (layout instanceof GridLayoutManager
                       && (getHeadsCount() > 0  || getFooterCount() > 0 )){

                   final HeadFooterAdapter adapter = (HeadFooterAdapter) getAdapter();
                   final GridLayoutManager.SpanSizeLookup spanSizeLookup = ((GridLayoutManager) layout).getSpanSizeLookup();
                   ((GridLayoutManager) layout).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                       @Override
                       public int getSpanSize(int position) {
                           int itemViewType = adapter.getItemViewType(position);

                           if (itemViewType <= HEADTYPE || itemViewType >= FOOTERTYPE){
                               return ((GridLayoutManager) layout).getSpanCount();
                           }
                           if (spanSizeLookup == null)
                            return 1;
                           else
                               return spanSizeLookup.getSpanSize(position - heads.size());
                       }
                   });
               }
           }
       });
    }

    public int getLastVisibleChildPosition(){
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager){
            return ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
        }else if (layoutManager instanceof GridLayoutManager){
            return ((GridLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
        }else {
            new RuntimeException("superRecycler 不支持其他布局管理器");
        }
        return -1;
    }

    public int getFirstVisibleChildPosition(){
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof  LinearLayoutManager){
            return ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
        }else if (layoutManager instanceof GridLayoutManager){
            return ((GridLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
        }else {
            new RuntimeException("superRecycler 不支持其他布局管理器");
        }
        return -1;
    }
}
