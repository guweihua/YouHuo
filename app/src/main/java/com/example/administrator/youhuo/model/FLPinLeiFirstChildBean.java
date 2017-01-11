package com.example.administrator.youhuo.model;

/**
 * Created by Administrator on 2017/1/11.
 */

public class FLPinLeiFirstChildBean {
    public FLPinLeiFirstChildBean(String _id, String name, String sexId) {
        this._id = _id;
        this.name = name;
        SexId = sexId;
    }

    /**
     * _id : 21
     * name : 数码3c
     * SexId : 2
     */


    private String _id;
    private String name;
    private String SexId;

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

    public String getSexId() {
        return SexId;
    }

    public void setSexId(String SexId) {
        this.SexId = SexId;
    }
}
