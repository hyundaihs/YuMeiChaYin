package com.sp.shangpin.entity;

import java.util.List;

/**
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/18.
 */

public class NormalGoodsInfoList {
    private List<Banner> banner;
    private List<NormalGoodsInfo> lists;

    public NormalGoodsInfoList() {
        super();
    }

    public NormalGoodsInfoList(List<Banner> banner, List<NormalGoodsInfo> lists) {
        this.banner = banner;
        this.lists = lists;
    }

    public List<Banner> getBanner() {
        return banner;
    }

    public void setInfo(List<Banner> banner) {
        this.banner = banner;
    }

    public List<NormalGoodsInfo> getLists() {
        return lists;
    }

    public void setLists(List<NormalGoodsInfo> lists) {
        this.lists = lists;
    }

    @Override
    public String toString() {
        return "NormalGoodsInfoList{" +
                "banner=" + banner +
                ", lists=" + lists +
                '}';
    }
}
