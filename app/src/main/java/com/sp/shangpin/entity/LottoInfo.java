package com.sp.shangpin.entity;

import java.util.Arrays;

/**
 * Created by Administrator on 2017/9/10/010.
 */

public class LottoInfo {
    private Banner info; // 分类信息
    private UpgradeGoods[] lists; // 产品列表

    public LottoInfo() {
        super();
    }

    public LottoInfo(Banner info, UpgradeGoods[] lists) {
        this.info = info;
        this.lists = lists;
    }

    public Banner getInfo() {
        return info;
    }

    public void setInfo(Banner info) {
        this.info = info;
    }

    public UpgradeGoods[] getLists() {
        return lists;
    }

    public void setLists(UpgradeGoods[] lists) {
        this.lists = lists;
    }

    @Override
    public String toString() {
        return "LottoInfo{" +
                "info=" + info.toString() +
                ", lists=" + Arrays.toString(lists) +
                '}';
    }
}
