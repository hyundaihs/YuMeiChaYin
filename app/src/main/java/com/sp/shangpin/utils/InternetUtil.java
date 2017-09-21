package com.sp.shangpin.utils;

/**
 * ShangPin
 * Created by 蔡雨峰 on 2017/9/8.
 */

public class InternetUtil {
    static {
        System.loadLibrary("native-lib");
    }

    /**
     * URL
     *
     * @return
     */
    public static native String urlHome();

    /**
     * APi url
     *
     * @return
     */
    public static native String urlApi();

    /**
     * 加密信息
     * @return
     */
    public static native String securityStr();

    /**
     * 登录
     *
     * @return
     */
    public static native String login();

    /**
     * 获取短信验证码
     *
     * @return
     */
    public static native String sendmsg();

    /**
     * 获取系统介绍信息
     *
     * @return
     */
    public static native String sysinfo();

    /**
     * 注册
     *
     * @return
     */
    public static native String reg();

    /**
     * 登录验证码登录
     *
     * @return
     */
    public static native String verflogin();

    /**
     * 退出登录
     *
     * @return
     */
    public static native String logout();

    /**
     * 修改密码
     *
     * @return
     */
    public static native String repass();

    /**
     * 找回密码
     *
     * @return
     */
    public static native String fpass();

    /**
     * 登录验证
     *
     * @return
     */
    public static native String checklogin();

    /**
     * 获取用户信息
     *
     * @return
     */
    public static native String userinfo();

    /**
     * 获取我的优惠券
     * @return
     */

    public static native String yhq();

    /**
     * 通知信息
     *
     * @return
     */
    public static native String tz();

    /**
     * 首页数据
     *
     * @return
     */
    public static native String indexdata();

    /**
     * 历史开奖列表
     *
     * @return
     */
    public static native String cpkj();

    /**
     * 升级产品列表
     *
     * @return
     */
    public static native String goodssj();

    /**
     * 升级产品详情
     *
     * @return
     */
    public static native String goodssjinfo();

    /**
     * 普通产品分类
     * @return
     */
    public static native String goodstype();

    /**
     * 普通产品列表
     *  @return
     */
    public static native String goods();

    /**
     * 普通产品详情
     * @return
     */
    public static native String goodsinfo();

    /**
     * 充值
     * @return
     */
    public static native String cz();

    /**
     * 充值记录
     * @return
     */
    public static native String czlists();

    /**
     * 取消充值
     * @return
     */
    public static native String czunset();

    /**
     * 创建升级订单
     * @return
     */
    public static native String orderssj();

    /**
     * 升级记录
     * @return
     */
    public static native String sjlists();

    /**
     * 升级订单(猜奇偶)
     * @return
     */
    public static native String sjsj();

    /**
     * 升级订单退货
     * @return
     */
    public static native String sjth();

    /**
     * 升级提货
     * @return
     */
    public static native String sjtihuo();

    /**
     * 积分操作记录
     * @return
     */
    public static native String jflists();
    /**
     * 创建普通订单
     * @return
     */
    public static native String orders();
    /**
     * 订单列表
     * @return
     */
    public static native String orderslists();
    /**
     * 取消普通订单(退款)
     * @return
     */
    public static native String unsetorders();
    /**
     * 普通订单确认收货
     * @return
     */
    public static native String shorders();

    /**
     * 金币产品分类
     * @return
     */
    public static native String goodsjftype();

    /**
     * 金币产品列表
     * @return
     */
    public static native String goodsjf();

    /**
     * 金币产品详情
     * @return
     */
    public static native String goodsjfinfo();

    /**
     * 创建金币产品订单
     * @return
     */
    public static native String ordersjf();

    /**
     * 金币产品订单列表
     * @return
     */
    public static native String ordersjflists();

    /**
     * 金币订单确认收货
     * @return
     */
    public static native String shordersjf();
}
