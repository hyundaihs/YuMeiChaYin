package com.sp.shangpin.entity;

/**
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/18.
 */

public class NormalOrderStatus {
    /**
     * （1:待支付 2:待发货 3:待收货 4:已取消 5:已完成 6:退款中 7:已退款）
     */
    public static final String[] STRINGS = {"","待支付","待发货","待收货","已取消","已完成","退款中","已退款"};
    public static final int NOT_PAID = 1;
    public static final int NOT_SEND = 2;
    public static final int WAIT_RECEIVE = 3;
    public static final int CANCELED = 4;
    public static final int DONE = 5;
    public static final int RETURNING = 6;
    public static final int RETURNED = 7;
}
