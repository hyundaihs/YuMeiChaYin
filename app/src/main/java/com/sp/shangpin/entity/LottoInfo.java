package com.sp.shangpin.entity;

import java.util.List;

/**
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/10/010.
 */

public class LottoInfo {
    private List<Banner> banner; // 分类信息
    private List<UpgradeGoods> lists; // 产品列表

    public LottoInfo() {
        super();
    }

    public LottoInfo(List<Banner> banner, List<UpgradeGoods> lists) {
        this.banner = banner;
        this.lists = lists;
    }

    public List<Banner> getBanner() {
        return banner;
    }

    public void setBanner(List<Banner> banner) {
        this.banner = banner;
    }

    public List<UpgradeGoods> getLists() {
        return lists;
    }

    public void setLists(List<UpgradeGoods> lists) {
        this.lists = lists;
    }

    @Override
    public String toString() {
        return "LottoInfo{" +
                "banner=" + banner +
                ", lists=" + lists +
                '}';
    }
}
