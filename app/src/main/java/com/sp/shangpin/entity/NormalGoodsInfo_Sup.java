package com.sp.shangpin.entity;

/**
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/18.
 */

public class NormalGoodsInfo_Sup extends InterResult {
    private NormalGoodsInfo retRes;

    public NormalGoodsInfo_Sup() {
        super();
    }

    public NormalGoodsInfo_Sup(int retInt, String retErr, NormalGoodsInfo retRes) {
        super(retInt, retErr);
        this.retRes = retRes;
    }

    public NormalGoodsInfo getRetRes() {
        return retRes;
    }

    public void setRetRes(NormalGoodsInfo retRes) {
        this.retRes = retRes;
    }

    @Override
    public String toString() {
        return "NormalGoodsInfo_Sup{" +
                super.toString() +
                "retRes=" + retRes +
                '}';
    }
}
