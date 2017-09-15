package com.sp.shangpin.entity;

/**
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/15.
 */

public class OrderInfo_Sup extends InterResult {
    private OrderInfo retRes;

    public OrderInfo_Sup() {
        super();
    }

    public OrderInfo_Sup(OrderInfo retRes) {
        this.retRes = retRes;
    }

    public OrderInfo_Sup(int retInt, String retErr, OrderInfo retRes) {
        super(retInt, retErr);
        this.retRes = retRes;
    }

    public OrderInfo getRetRes() {
        return retRes;
    }

    public void setRetRes(OrderInfo retRes) {
        this.retRes = retRes;
    }

    @Override
    public String toString() {
        return "OrderInfo_Sup{" +
                super.toString() +
                "retRes=" + retRes +
                '}';
    }
}
