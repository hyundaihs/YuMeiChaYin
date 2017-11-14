package com.sp.shangpin.entity;

import java.util.List;

/**
 * YuMeiChaYin
 * Created by 蔡雨峰 on 2017/11/14.
 */

public class GetCashInfo_Sup extends InterResult {
    private List<GetCashInfo> retRes;

    public GetCashInfo_Sup() {
        super();
    }

    public GetCashInfo_Sup(List<GetCashInfo> retRes) {
        this.retRes = retRes;
    }

    public List<GetCashInfo> getRetRes() {
        return retRes;
    }

    public void setRetRes(List<GetCashInfo> retRes) {
        this.retRes = retRes;
    }

    @Override
    public String toString() {
        return "GetCashInfo_Sup{" +
                "retRes=" + retRes +
                '}';
    }
}
