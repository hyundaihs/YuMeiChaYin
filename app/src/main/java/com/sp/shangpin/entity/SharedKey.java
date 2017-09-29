package com.sp.shangpin.entity;

/**
 * ChaYin
 * Created by ${蔡雨峰} on 2017/9/9/009.
 */

public class SharedKey {
    public static final String COOKIE = "Cookie";
    public static final String IS_REMEMBER = "is_remember";
    public static final String LOGIN_VERF = "login_verf";//自动登陆验证码
    public static final String ISWX_LOGIN = "isWx_login";//是否是微信登陆
    public static final String WEIXIN = "weixin"; // 微信
    public static final String ALIPAY = "alipay"; // 微信
    public static final String BANK = "bank"; // 微信

    /**
     * 微信通过code获取access_token返回的数据
     */
    public static final String ACCESS_TOKEN = "access_token";
    public static final String REFRESH_TOKEN = "REFRESH_TOKEN";
    public static final String OPENID = "openid";
    public static final String SCOPE = "scope";
    public static final String UNIONID = "unionid";
}
