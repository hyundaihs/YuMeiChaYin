package com.sp.shangpin.entity;

/**
 * Created by Administrator on 2017/9/9/009.
 */

public class SystemInfo_Sup extends InterResult {
    private SystemInfo retRes;

    public SystemInfo_Sup() {
        super();
    }

    public SystemInfo_Sup(int retInt, String retErr, SystemInfo retRes) {
        this.retInt = retInt;
        this.retErr = retErr;
        this.retRes = retRes;
    }

    public SystemInfo getRetRes() {
        return retRes;
    }

    public void setRetRes(SystemInfo retRes) {
        this.retRes = retRes;
    }

    @Override
    public String toString() {
        return "SystemInfo_Sup{" +
                super.toString() +
                ", retRes=" + retRes +
                '}';
    }
}
