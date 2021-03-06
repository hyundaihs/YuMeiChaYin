package com.sp.shangpin.utils;

/**
 * ChaYin
 * Created by ${蔡雨峰} on 2017/9/17/017.
 */

public class IntentUtil {
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
    /**
     * 从普通订单详情进入优惠券选择
     */
    public static final int REQUEST_FROM_NORMAL_GOODS_DETAILS_YHQ = 16;
    /**
     * 从我的进入密码修改
     */
    public static final int REQUEST_FROM_MINE_TO_ALERT_PASS = 17;
}
