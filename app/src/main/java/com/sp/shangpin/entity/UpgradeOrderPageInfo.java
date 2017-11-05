package com.sp.shangpin.entity;

import java.util.List;

/**
 * ChaYin
 * Created by ${蔡雨峰} on 2017/11/5/005.
 */

public class UpgradeOrderPageInfo {
    private UpgradeOrderInfo info;
    private List<LotteryInfo> lists;
    private long next_time;

    public UpgradeOrderPageInfo() {
        super();
    }

    public UpgradeOrderPageInfo(int retInt, String retErr, UpgradeOrderInfo info, List<LotteryInfo> lists, long next_time) {
        this.info = info;
        this.lists = lists;
        this.next_time = next_time;
    }

    public UpgradeOrderInfo getInfo() {
        return info;
    }

    public void setInfo(UpgradeOrderInfo info) {
        this.info = info;
    }

    public List<LotteryInfo> getLists() {
        return lists;
    }

    public void setLists(List<LotteryInfo> lists) {
        this.lists = lists;
    }

    public long getNext_time() {
        return next_time;
    }

    public void setNext_time(long next_time) {
        this.next_time = next_time;
    }

    @Override
    public String toString() {
        return "UpgradeOrderPageInfo{" +
                "info=" + info +
                ", lists=" + lists +
                ", next_time=" + next_time +
                '}';
    }
}
