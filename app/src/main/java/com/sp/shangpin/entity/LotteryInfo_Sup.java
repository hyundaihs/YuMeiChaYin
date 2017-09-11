package com.sp.shangpin.entity;

import java.util.List;

/**
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/11.
 */

public class LotteryInfo_Sup extends InterResult {
    private List<LotteryInfo> retRes;

    public LotteryInfo_Sup() {
        super();
    }

    public LotteryInfo_Sup(int retInt, String retErr, List<LotteryInfo> retRes) {
        super(retInt, retErr);
        this.retRes = retRes;
    }

    public List<LotteryInfo> getRetRes() {
        return retRes;
    }

    public void setRetRes(List<LotteryInfo> retRes) {
        this.retRes = retRes;
    }

    @Override
    public String toString() {
        return "LotteryInfo_Sup{" +
                super.toString() +
                "retRes=" + retRes +
                '}';
    }
}
