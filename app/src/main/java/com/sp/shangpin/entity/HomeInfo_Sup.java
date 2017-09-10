package com.sp.shangpin.entity;

/**
 * Created by Administrator on 2017/9/9/009.
 */

public class HomeInfo_Sup extends InterResult {
    private HomeInfo retRes;

    public HomeInfo_Sup() {
        super();
    }

    public HomeInfo_Sup(int retInt, String retErr, HomeInfo retRes) {
        super(retInt, retErr);
        this.retRes = retRes;
    }

    public HomeInfo getRetRes() {
        return retRes;
    }

    public void setRetRes(HomeInfo retRes) {
        this.retRes = retRes;
    }

    @Override
    public String toString() {
        return "HomeInfo_Sup{" +
                super.toString() +
                "retRes=" + retRes +
                '}';
    }
}
