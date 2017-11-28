package com.sp.shangpin.entity;

/**
 * YuMeiChaYin
 * Created by 蔡雨峰 on 2017/11/28.
 */

public class AlipayOrder_Sup extends InterResult {
    private AlipayOrder retRes;

    public AlipayOrder_Sup() {
        super();
    }

    public AlipayOrder_Sup(AlipayOrder retRes) {
        this.retRes = retRes;
    }

    public AlipayOrder getRetRes() {
        return retRes;
    }

    public void setRetRes(AlipayOrder retRes) {
        this.retRes = retRes;
    }

    @Override
    public String toString() {
        return "AlipayOrder_Sup{" +
                super.toString() +
                "retRes=" + retRes +
                '}';
    }
}
