package com.sp.shangpin.entity;

/**
 * Created by Administrator on 2017/9/9/009.
 */

public class LoginInfo_Sup extends InterResult {
    private LoginInfo retRes;

    public LoginInfo_Sup() {
        super();
    }

    public LoginInfo_Sup(int retInt, String retErr, LoginInfo retRes) {
        super(retInt, retErr);
        this.retRes = retRes;
    }

    public LoginInfo getRetRes() {
        return retRes;
    }

    public void setRetRes(LoginInfo retRes) {
        this.retRes = retRes;
    }

    @Override
    public String toString() {
        return "LoginInfo_Sup{" +
                super.toString() +
                "retRes=" + retRes +
                '}';
    }
}
