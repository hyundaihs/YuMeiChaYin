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
}
