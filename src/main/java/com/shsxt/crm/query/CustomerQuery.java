package com.shsxt.crm.query;

import com.shsxt.base.BaseQuery;

public class CustomerQuery extends BaseQuery {
    //客户名称
    private String cusName;
    //客户编号
    private String cusNo;
    //客户等级
    private String level;
    //客户满意度
    private String myd;

    //金额区间
    private String type;

    private String time;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCusNo() {
        return cusNo;
    }

    public void setCusNo(String cusNo) {
        this.cusNo = cusNo;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMyd() {
        return myd;
    }

    public void setMyd(String myd) {
        this.myd = myd;
    }
}
