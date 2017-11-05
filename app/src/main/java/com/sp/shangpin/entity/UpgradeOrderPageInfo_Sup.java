package com.sp.shangpin.entity;

/**
 * ChaYin
 * Created by ${蔡雨峰} on 2017/11/5/005.
 */

public class UpgradeOrderPageInfo_Sup extends InterResult {
    UpgradeOrderPageInfo retRes;

    public UpgradeOrderPageInfo_Sup() {
        super();
    }

    public UpgradeOrderPageInfo_Sup(UpgradeOrderPageInfo retRes) {
        this.retRes = retRes;
    }

    public UpgradeOrderPageInfo getRetRes() {
        return retRes;
    }

    public void setRetRes(UpgradeOrderPageInfo retRes) {
        this.retRes = retRes;
    }

    @Override
    public String toString() {
        return "UpgradeOrderPageInfo_Sup{" +
                super.toString() +
                "retRes=" + retRes +
                '}';
    }
}
