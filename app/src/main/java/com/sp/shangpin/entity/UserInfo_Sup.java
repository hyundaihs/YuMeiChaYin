package com.sp.shangpin.entity;

/**
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/12.
 */

public class UserInfo_Sup extends InterResult {
    private UserInfo retRes;

    public UserInfo_Sup() {
        super();
    }

    public UserInfo_Sup(int retInt, String retErr, UserInfo retRes) {
        super(retInt, retErr);
        this.retRes = retRes;
    }

    public UserInfo getRetRes() {
        return retRes;
    }

    public void setRetRes(UserInfo retRes) {
        this.retRes = retRes;
    }

    @Override
    public String toString() {
        return "UserInfo_Sup{" +
                super.toString() +
                "retRes=" + retRes +
                '}';
    }
}
