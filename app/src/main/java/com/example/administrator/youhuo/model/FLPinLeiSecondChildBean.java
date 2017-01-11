package com.example.administrator.youhuo.model;

/**
 * Created by Administrator on 2017/1/11.
 */

public class FLPinLeiSecondChildBean {
    public FLPinLeiSecondChildBean(String _id, String name, String categoryid) {
        this._id = _id;
        this.name = name;
        this.categoryid = categoryid;
    }

    /**
     * _id : 1
     * name : TShirt
     * categoryid : 1
     */

    private String _id;
    private String name;
    private String categoryid;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }
}
