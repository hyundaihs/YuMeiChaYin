package com.sp.shangpin.entity;

/**
 * Created by Administrator on 2017/9/17/017.
 * 升级状态（0：未升级，1：升级成功，2：升级失败）
 */

public class UpgradeStatus {
    public static final String[] STRINGS = {"未升级", "升级成功", "升级失败"};
    public static final int UPGRADE_NOT = 0;
    public static final int UPGRADE_SUCCESS = 1;
    public static final int UPGRADE_FAILED = 2;
}
