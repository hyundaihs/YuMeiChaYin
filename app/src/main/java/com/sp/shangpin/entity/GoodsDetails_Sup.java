package com.sp.shangpin.entity;

/**
 * Created by Administrator on 2017/9/10/010.
 */

public class GoodsDetails_Sup extends InterResult {
    private UpgradeGoods retRes;

    public GoodsDetails_Sup() {
        super();
    }

    public GoodsDetails_Sup(int retInt, String retErr, UpgradeGoods upgradeGoods) {
        super(retInt, retErr);
        this.retRes = upgradeGoods;
    }

    public UpgradeGoods getRetRes() {
        return retRes;
    }

    public void setRetRes(UpgradeGoods retRes) {
        this.retRes = retRes;
    }

    @Override
    public String toString() {
        return "GoodsDetails_Sup{" +
                "retRes=" + retRes +
                '}';
    }
}
