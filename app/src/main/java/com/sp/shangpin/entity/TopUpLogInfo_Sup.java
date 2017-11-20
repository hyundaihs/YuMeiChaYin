package com.sp.shangpin.entity;

import java.util.List;

/**
 * YuMeiChaYin
 * Created by 蔡雨峰 on 2017/11/20.
 */

public class TopUpLogInfo_Sup extends InterResult {
    private List<TopUpLogInfo> retRes;

    public TopUpLogInfo_Sup() {
        super();
    }

    public TopUpLogInfo_Sup(List<TopUpLogInfo> retRes) {
        this.retRes = retRes;
    }

    public List<TopUpLogInfo> getRetRes() {
        return retRes;
    }

    public void setRetRes(List<TopUpLogInfo> retRes) {
        this.retRes = retRes;
    }

    @Override
    public String toString() {
        return "TopUpLogInfo_Sup{" +
                super.toString() +
                "retRes=" + retRes +
                '}';
    }
}
