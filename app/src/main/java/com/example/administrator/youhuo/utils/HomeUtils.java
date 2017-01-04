package com.example.administrator.youhuo.utils;

import com.example.administrator.youhuo.model.HomeDate;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/3.
 */

public class HomeUtils {

    public static List<HomeDate> getAllHomeData(Document document){
        return null;
    }

    public static List<HomeDate> getBannerData(Document document){
        Element element = document.getElementsByClass("swiper-wrapper").get(0);
        Elements children = element.children();
        List<HomeDate> list = new ArrayList<>();
        for (int i = 0; i < children.size(); i++) {
            Map<String,String> map = new HashMap<>();
            String src = "src";
            if (i != 0){
                src = "data-src";
            }
            map.put("src",children.get(i).getElementsByTag("img").get(0).attr(src));
            map.put("url",children.get(i).getElementsByTag("a").get(0).attr("href"));
            HomeDate homeDate = new HomeDate(-1,map);
            list.add(homeDate);
        }
        return list;
    }

    public static HomeDate getBanrdData(Document document) {
        return null;
    }
}
