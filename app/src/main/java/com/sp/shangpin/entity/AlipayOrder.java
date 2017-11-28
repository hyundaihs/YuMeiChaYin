package com.sp.shangpin.entity;

/**
 * YuMeiChaYin
 * Created by 蔡雨峰 on 2017/11/28.
 */

public class AlipayOrder {
    private String str;

    public AlipayOrder() {
        super();
    }

    public AlipayOrder(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return "AlipayOrder{" +
                "str='" + str + '\'' +
                '}';
    }
}
