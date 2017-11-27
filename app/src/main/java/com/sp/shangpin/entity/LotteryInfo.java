package com.sp.shangpin.entity;

/**
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/11.
 */

public class LotteryInfo {
    private String dates; //  开奖时间
    private String numbers; //  编号
    private String codes; //  开奖号码
    private int jo; //  鸡藕（1鸡，2藕）

    public LotteryInfo() {
        super();
    }

    public LotteryInfo(String dates, String numbers, String codes, int jo) {
        this.dates = dates;
        this.numbers = numbers;
        this.codes = codes;
        this.jo = jo;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    public String getCodes() {
        return codes;
    }

    public void setCodes(String codes) {
        this.codes = codes;
    }

    public int getJo() {
        return jo;
    }

    public void setJo(int jo) {
        this.jo = jo;
    }

    @Override
    public String toString() {
        return "LotteryInfo{" +
                "dates='" + dates + '\'' +
                ", numbers='" + numbers + '\'' +
                ", codes='" + codes + '\'' +
                ", jo=" + jo +
                '}';
    }
}
