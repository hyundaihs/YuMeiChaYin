package com.sp.shangpin.entity;

/**
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/18.
 */

public class NormalGoodsInfoList_Sup extends InterResult {
    private NormalGoodsInfoList retRes;

    public NormalGoodsInfoList_Sup() {
        super();
    }

    public NormalGoodsInfoList_Sup(int retInt, String retErr, NormalGoodsInfoList retRes) {
        super(retInt, retErr);
        this.retRes = retRes;
    }

    public NormalGoodsInfoList getRetRes() {
        return retRes;
    }

    public void setRetRes(NormalGoodsInfoList retRes) {
        this.retRes = retRes;
    }

    @Override
    public String toString() {
        return "NormalGoodsInfoList_Sup{" +
                super.toString() +
                "retRes=" + retRes +
                '}';
    }
}
