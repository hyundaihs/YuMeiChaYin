package com.sp.shangpin.entity;

/**
 * ChaYin
 * Created by ${蔡雨峰} on 2017/10/28/028.
 */

public class WxPayOrder_Sup extends InterResult {
    WxPayOrder retRes;

    public WxPayOrder_Sup() {
        super();
    }

    public WxPayOrder_Sup(int retInt, String retErr, WxPayOrder retRes) {
        super(retInt, retErr);
        this.retRes = retRes;
    }

    public WxPayOrder getRetRes() {
        return retRes;
    }

    public void setRetRes(WxPayOrder retRes) {
        this.retRes = retRes;
    }

    @Override
    public String toString() {
        return "WxPayOrder_Sup{" +
                super.toString() +
                "retRes=" + retRes +
                '}';
    }
}
