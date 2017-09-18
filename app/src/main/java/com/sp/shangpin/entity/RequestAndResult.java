package com.sp.shangpin.entity;

/**
 * ChaYin
 * Created by ${蔡雨峰} on 2017/9/17/017.
 */

public class RequestAndResult {
    public static final int RESULT_OK = 100;
    public static final int RESULT_NOT_OK = 101;

    /**
     * 从已付订单Fragment访问收货地址填写
     */
    public static final int REQUEST_FROM_PAID = 10;
    /**
     * 从升级订单详情进入充值
     */
    public static final int REQUEST_FROM_GOODS_DETAILS = 11;

    /**
     * 从fragment我的进入充值
     */
    public static final int REQUEST_FROM_FRAGMENT_MINE = 12;
    /**
     * 从升级订单详情进入地址填写
     */
    public static final int REQUEST_FROM_GOODS_DETAILS_INPUT = 13;
    /**
     * 从普通订单详情进入充值
     */
    public static final int REQUEST_FROM_NORMAL_GOODS_DETAILS = 14;
    /**
     * 从普通订单详情进入地址填写
     */
    public static final int REQUEST_FROM_NORMAL_GOODS_DETAILS_INPUT = 15;
}
