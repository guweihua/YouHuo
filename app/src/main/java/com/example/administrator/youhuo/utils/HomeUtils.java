package com.example.administrator.youhuo.utils;

import com.example.administrator.youhuo.MyApplication;
import com.example.administrator.youhuo.model.HomeDate;
import com.example.administrator.youhuo.model.HomeModel;
import com.example.administrator.youhuo.model.HttpModel;
import com.example.administrator.youhuo.server.HaoDianService;
import com.example.administrator.youhuo.server.HomeTuiJianService;
import com.example.administrator.youhuo.server.HotService;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/1/3.
 */

public class HomeUtils {

    public static List<HomeDate> getAllHomeData(Document document) {
        List<HomeDate> list = new ArrayList<>();
        Element one1 = document.getElementsByAttributeValue("data-id", "37473").get(0);
        Element one2 = document.getElementsByAttributeValue("data-id", "36611").get(0);
        list.add(getOneWebData(one1));
        list.add(getOneWebData(one2));
        // 获得热门品咧
        list.add(getHotData());
        list.add(getHaoDian());
        list.addAll(getHomeTuiJian());
        return list;
    }

    private static HomeDate getOneWebData(Element elementById) {
        Element a = elementById.getElementsByTag("a").get(0);
        String url = a.attr("href");
        String src = elementById.getElementsByTag("img").attr("src");
        Map<String, String> map = new HashMap<>();
        map.put("src", src);
        map.put("url", url);
//        LogUtils.log("one",src);
//        LogUtils.log("one",url);
        return new HomeDate(HomeModel.ONEWEB, map);
    }

    public static List<HomeDate> getBannerData(Document document) {
        Element element = document.getElementsByClass("swiper-wrapper").get(0);
        Elements children = element.children();
        List<HomeDate> list = new ArrayList<>();
        for (int i = 0; i < children.size(); i++) {
            Map<String, String> map = new HashMap<>();
            String src = "src";
            if (i != 0) {
                src = "data-src";
            }
            map.put("src", children.get(i).getElementsByTag("img").get(0).attr(src));
            map.put("url", children.get(i).getElementsByTag("a").get(0).attr("href"));
            HomeDate homeDate = new HomeDate(-1, map);
            list.add(homeDate);
        }
        return list;
    }

    public static List<HomeDate> getBanrdData(Document document) {
        Element element = document.getElementsByClass("icons-wrapper").get(0).children().get(0);

        Elements children = element.children();
        List<HomeDate> list = new ArrayList<>();
        for (Element e : children) {
            //        LogUtils.log("tag1", e.html());
            String src = e.children().get(0).children().get(0).attr("src");
            String linkbar = e.children().get(1).text();
            Map<String, String> map = new HashMap<>();
            map.put("src", src);
            map.put("title", linkbar);
            HomeDate homeDate = new HomeDate(-1, map);
            list.add(homeDate);
        }
        return list;
    }

    public static HomeDate getHotData() {
        HotService hotService = MyApplication.app.retrofit.create(HotService.class);
        Map<String, String> map = new HashMap<>();
        Call<ResponseBody> hot = hotService.getHot();
        try {
            Response<ResponseBody> execute = hot.execute();
            boolean successful = execute.isSuccessful();
            if (successful) {
                String string = execute.body().string();
//                LogUtils.log("asdasd",string);
                JSONObject job = new JSONObject(string);
                JSONArray brands = job.getJSONArray("brand");
                for (int i = 0; i < brands.length(); i++) {
                    JSONObject jsonObject = brands.getJSONObject(i);
                    String imgpath = HttpModel.IMG + jsonObject.getString("imgpath");
                    map.put("src" + (i + 1), imgpath);
//                    Logger.e(imgpath);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        HomeDate homeDate = new HomeDate(HomeModel.HOTECATORY, map);
        return homeDate;
    }

    public static HomeDate getHaoDian() {
        Map<String, String> map = new HashMap<>();
        HaoDianService haoDianService = MyApplication.app.retrofit.create(HaoDianService.class);
        Call<ResponseBody> haoDian = haoDianService.getHaoDian();
        try {
            Response<ResponseBody> execute = haoDian.execute();
            if (execute.isSuccessful()) {
                String string = execute.body().string();
//                LogUtils.log("sadasda",string);
                JSONArray jsonArray = new JSONArray(string);
                for (int i = 0; i < jsonArray.length() * 2; i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i % jsonArray.length());
                    String imgpath = jsonObject.getString("imgpath");
                    map.put("src" + (i + 1), HttpModel.IMG + imgpath);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HomeDate(HomeModel.HAODIAN, map);
    }

    public static Collection<? extends HomeDate> getHomeTuiJian() {
        List<HomeDate> list = new ArrayList<>();
        HomeTuiJianService homeTuiJianService = MyApplication.app.otherretrofit.create(HomeTuiJianService.class);
        Call<ResponseBody> homeTuiJian = homeTuiJianService.getHomeTuiJian();
        try {
            Response<ResponseBody> execute = homeTuiJian.execute();
            if (execute.isSuccessful()) {
                String string = execute.body().string();
                JSONArray datas = new JSONObject(string).getJSONArray("datas");
                for (int i = 0; i < datas.length(); i++) {
                    JSONObject jsonObject = datas.getJSONObject(i);
                    Map<String, String> map = new HashMap<>();
                    String title = jsonObject.getString("type");
                    map.put("title", title);
                    JSONArray categorydatas = jsonObject.getJSONArray("categorydatas");
                    for (int j = 0; j < categorydatas.length(); j++) {
                        JSONObject jsonObject1 = categorydatas.getJSONObject(j);
                        map.put("src" + (j+1), HttpModel.IMG +jsonObject1.getString("imgpath"));
                        map.put("shopid" + j, jsonObject1.getString("shopid"));
                    }
                    HomeDate homeDate = new HomeDate(HomeModel.TUIJIAN, map);
                    list.add(homeDate);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
