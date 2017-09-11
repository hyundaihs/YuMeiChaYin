package com.sp.shangpin.entity;

import java.util.List;

/**
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/9/009.
 */

public class HomeInfo {
    private List<Banner> banner;
    private List<UpgradeGoodsType> sjfl;
    private List<UpgradeGoods> sjcp;

    public HomeInfo() {
        super();
    }

    public HomeInfo(List<Banner> banner, List<UpgradeGoodsType> sjfl, List<UpgradeGoods> sjcp) {
        this.banner = banner;
        this.sjfl = sjfl;
        this.sjcp = sjcp;
    }

    public List<Banner> getBanner() {
        return banner;
    }

    public void setBanner(List<Banner> banner) {
        this.banner = banner;
    }

    public List<UpgradeGoodsType> getSjfl() {
        return sjfl;
    }

    public void setSjfl(List<UpgradeGoodsType> sjfl) {
        this.sjfl = sjfl;
    }

    public List<UpgradeGoods> getSjcp() {
        return sjcp;
    }

    public void setSjcp(List<UpgradeGoods> sjcp) {
        this.sjcp = sjcp;
    }

    @Override
    public String toString() {
        return "HomeInfo{" +
                "banner=" + banner +
                ", sjfl=" + sjfl +
                ", sjcp=" + sjcp +
                '}';
    }
}
