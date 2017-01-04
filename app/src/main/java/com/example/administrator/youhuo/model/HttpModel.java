package com.example.administrator.youhuo.model;

/**
 * Created by admin on 2016/12/29.
 */

public class HttpModel {
    /**服务器的基础路径**/
    public static final String BASE = "http://www.iwens.org/School_Sky/";
    /**服务器图片的基础路径**/
    public static final String IMG = "http://www.iwens.org/School_Sky/Img/";
    /**首页的广告访问地址**/
    public static final String HOME_ADVERT_URL= "yohoadvert.php";
    /**分类中的boys访问地址**/
    public static final String CATEGORY_BOYS_URL= BASE+"categoryboy.php";
    /**分类中的girls访问地址**/
    public static final String CATEGORY_GIRLS_URL= BASE+"categorygirl.php";
    /**分类中的life访问地址**/
    public static final String CATEGORY_LIFE_URL= BASE+"categorylife.php";
    /**分类中的二级菜单的访问地址**/
    public static final String CATEGORY_VALUE_URL= "http://123.206.47.205:8080/yoho/categoryvalue";
    /**品牌中热门的访问地址**/
    public static final String CATEGORY_BRAND_HOT= "hotbrand.php";
    /**品牌中全部品牌访问地址**/
    public static  final String CATEGORY_BRAND_ALL= BASE+"allbrand.php";
    /**关注访问地址**/
    public static  final String CATEGORY_FOCUS= BASE+"follow.php";
    /**商品列表的访问地址**/
    public static  final String BRAND_VALUE= "http://123.206.47.205:8080/yoho/brandvalue";
    /**商品详情访问的路径*/
    public static  final String GOODS_DETAILURL = "http://123.206.47.205:8080/yoho/goodsdetail";
    /**首页中推荐商品访问的路径*/
    public static  final String HOME_CATEGORY = "homecategory";
    /**逛页面数据访问的路径*/
    public static  final String SEE_NEWS = "http://123.206.47.205:8080/yoho/news";
    /**购物车页面数据访问的路径*/
    public static  final String CAR_GOODS = "http://123.206.47.205:8080/yoho/cartlist";
    //登录接口--拼接
    public static final String LOGINURL =  "http://123.206.47.205:8080/yoho/login";
    //注册接口--拼接
    public static final String REGISTERURL =  "http://123.206.47.205:8080/yoho/register";
    //版本检查更新的接口
    public static final String VERSIONCHECKURL = "http://123.206.47.205:8080/yoho/version";
}
