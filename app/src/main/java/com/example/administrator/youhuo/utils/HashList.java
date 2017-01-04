package com.example.administrator.youhuo.utils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/1/4.
 */

public class HashList<T> {

    List<T> list = new ArrayList<>();

    public void add(T t){
        boolean contains = list.contains(t);
        if (!contains){
            list.add(t);
        }
    }

    public T get(int position){
        return list.get(position);
    }

    public int size(){
        return list.size();
    }

    public void clear(){
        list.clear();
    }
}
