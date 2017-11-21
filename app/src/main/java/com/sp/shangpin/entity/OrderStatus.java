package com.sp.shangpin.entity;

/**
 * Created by Administrator on 2017/9/17/017.
 * 1：待支付，2：已支付，3：升级中，4：升级完毕，5：退货中，6：已退货，7：提货中，8：已发货，9：提货成功，10：取消
 */

public class OrderStatus {
    public static final String[] STRINGS = {"", "待支付", "已支付", "升级中", "升级完毕", "退货中", "已兑金币", "提货中", "已发货", "提货成功", "取消"};
    public static final int NOT_PAID = 1;
    public static final int PAID = 2;
    public static final int UPGRADING = 3;
    public static final int UPGRADED = 4;
    public static final int RETURNING = 5;
    public static final int RETURNED = 6;
    public static final int ON_PICKING = 7;
    public static final int SENT = 8;
    public static final int PICK_SUCCESS = 9;
    public static final int CANCELED = 10;
}
