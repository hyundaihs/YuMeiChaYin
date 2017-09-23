package com.sp.shangpin.entity;

/**
 * ChaYin
 * Created by ${蔡雨峰} on 2017/9/23/023.
 */

public class RealNameInfo_Sup extends InterResult {
    private RealNameInfo retRes;

    public RealNameInfo_Sup() {
        super();
    }

    public RealNameInfo_Sup(RealNameInfo retRes) {
        this.retRes = retRes;
    }

    public RealNameInfo getRetRes() {
        return retRes;
    }

    public void setRetRes(RealNameInfo retRes) {
        this.retRes = retRes;
    }

    @Override
    public String toString() {
        return "RealNameInfo_Sup{" +
                super.toString() +
                "retRes=" + retRes +
                '}';
    }
}
