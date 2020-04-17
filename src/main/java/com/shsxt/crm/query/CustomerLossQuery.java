package com.shsxt.crm.query;

import com.shsxt.base.BaseQuery;

public class CustomerLossQuery extends BaseQuery {
    //客户编号
    private String cusNo;
    //暂缓状态
    private Integer state;
    //客户名称
    private String cusName;

    public String getCusNo() {
        return cusNo;
    }

    public void setCusNo(String cusNo) {
        this.cusNo = cusNo;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }
}
