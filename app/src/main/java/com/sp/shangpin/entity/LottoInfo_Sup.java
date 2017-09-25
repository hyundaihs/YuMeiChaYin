package com.sp.shangpin.entity;

/**
 * YuMeiChaYin
 * Created by 蔡雨峰 on 2017/9/10/010.
 */

public class LottoInfo_Sup extends InterResult {
    private LottoInfo retRes;

    public LottoInfo_Sup() {
        super();
    }

    public LottoInfo_Sup(int retInt, String retErr, LottoInfo retRes) {
        super(retInt, retErr);
        this.retRes = retRes;
    }

    public LottoInfo getRetRes() {
        return retRes;
    }

    public void setRetRes(LottoInfo retRes) {
        this.retRes = retRes;
    }

    @Override
    public String toString() {
        return "LottoInfo_Sup{" +
                super.toString() +
                "retRes=" + retRes +
                '}';
    }
}
