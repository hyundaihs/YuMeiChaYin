package com.sp.shangpin.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/9/17/017.
 */

public class OrdersInfo_Sup extends InterResult {
    private List<OrderInfo> retRes;

    public OrdersInfo_Sup() {
        super();
    }

    public OrdersInfo_Sup(List<OrderInfo> retRes) {
        this.retRes = retRes;
    }

    public List<OrderInfo> getRetRes() {
        return retRes;
    }

    public void setRetRes(List<OrderInfo> retRes) {
        this.retRes = retRes;
    }

    @Override
    public String toString() {
        return "OrdersInfo_Sup{" +
                super.toString() +
                "retRes=" + retRes +
                '}';
    }
}
