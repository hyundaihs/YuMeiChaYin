package com.sp.shangpin.entity;

import java.util.List;

/**
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/18.
 */

public class NormalGoodsInfoList {
    private List<Banner> info;
    private List<NormalGoodsInfo> lists;

    public NormalGoodsInfoList() {
        super();
    }

    public NormalGoodsInfoList(List<Banner> info, List<NormalGoodsInfo> lists) {
        this.info = info;
        this.lists = lists;
    }

    public List<Banner> getInfo() {
        return info;
    }

    public void setInfo(List<Banner> info) {
        this.info = info;
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
                "info=" + info +
                ", lists=" + lists +
                '}';
    }
}
