package com.sp.shangpin.entity;

import java.util.List;

/**
 * ChaYin
 * Created by ${蔡雨峰} on 2017/11/5/005.
 */

public class NotificationInfo_Sup extends InterResult {
    List<NotificationInfo> retRes;

    public NotificationInfo_Sup() {
        super();
    }

    public NotificationInfo_Sup(int retInt, String retErr, List<NotificationInfo> retRes) {
        super(retInt, retErr);
        this.retRes = retRes;
    }

    public List<NotificationInfo> getRetRes() {
        return retRes;
    }

    public void setRetRes(List<NotificationInfo> retRes) {
        this.retRes = retRes;
    }

    @Override
    public String toString() {
        return "NotificationInfo_Sup{" +
                super.toString() +
                "retRes=" + retRes +
                '}';
    }
}
