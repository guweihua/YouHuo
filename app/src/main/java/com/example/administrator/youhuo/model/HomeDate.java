package com.example.administrator.youhuo.model;

import java.util.Map;

/**
 * Created by Administrator on 2016/12/30.
 */

public class HomeDate {
    public int type;
    public Map<String,String> map;

    public HomeDate(int type, Map<String, String> map) {
        this.type = type;
        this.map = map;
    }
}
