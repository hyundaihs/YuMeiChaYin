package com.sp.shangpin.entity;

import java.util.Arrays;

/**
 * Created by Administrator on 2017/9/9/009.
 */

public class HomeInfo {
    private Banner[] banner;
    private UpgradeGoodsType[] sjfl;
    private UpgradeGoods[] sjcp;

    public HomeInfo() {
        super();
    }

    public HomeInfo(Banner[] banner, UpgradeGoodsType[] sjfl, UpgradeGoods[] sjcp) {
        this.banner = banner;
        this.sjfl = sjfl;
        this.sjcp = sjcp;
    }

    public Banner[] getBanner() {
        return banner;
    }

    public void setBanner(Banner[] banner) {
        this.banner = banner;
    }

    public UpgradeGoodsType[] getSjfl() {
        return sjfl;
    }

    public void setSjfl(UpgradeGoodsType[] sjfl) {
        this.sjfl = sjfl;
    }

    public UpgradeGoods[] getSjcp() {
        return sjcp;
    }

    public void setSjcp(UpgradeGoods[] sjcp) {
        this.sjcp = sjcp;
    }

    @Override
    public String toString() {
        return "HomeInfo{" +
                "banner=" + Arrays.toString(banner) +
                ", sjfl=" + Arrays.toString(sjfl) +
                ", sjcp=" + Arrays.toString(sjcp) +
                '}';
    }
}
