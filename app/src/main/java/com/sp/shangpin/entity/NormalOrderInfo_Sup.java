package com.sp.shangpin.entity;

import java.util.List;

/**
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/18.
 */

public class NormalOrderInfo_Sup extends InterResult {
    private List<NormalOrderInfo> retRes;

    public NormalOrderInfo_Sup() {
        super();
    }

    public NormalOrderInfo_Sup(int retInt, String retErr, List<NormalOrderInfo> retRes) {
        super(retInt, retErr);
        this.retRes = retRes;
    }

    public List<NormalOrderInfo> getRetRes() {
        return retRes;
    }

    public void setRetRes(List<NormalOrderInfo> retRes) {
        this.retRes = retRes;
    }

    @Override
    public String toString() {
        return "NormalOrderInfo_Sup{" +
                super.toString() +
                "retRes=" + retRes +
                '}';
    }
}
