package com.sp.shangpin.entity;

/**
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/15.
 */

public class OrderInfo {
    private int orders_id; // 订单ID

    public OrderInfo() {
        super();
    }

    public OrderInfo(int orders_id) {
        this.orders_id = orders_id;
    }

    public int getOrders_id() {
        return orders_id;
    }

    public void setOrders_id(int orders_id) {
        this.orders_id = orders_id;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "orders_id=" + orders_id +
                '}';
    }
}
