package com.sp.shangpin.entity;

/**
 * ChaYin
 * Created by ${蔡雨峰} on 2017/11/5/005.
 */

public class UpgradeOrderInfo {
    private int id; //  订单ID
    private int goodssj_id; //  升级产品id
    private String goodssj_title; //  标题
    private String goodssj_file_url; //  图片
    private String goodssj_title_2; //  标题（升级后）
    private String goodssj_file_url_2; //  图片（升级后）
    private double danjia; //  单价
    private double goodssj_price_2; //  单价（升级后）
    private int num; //  数量
    private int sj_numbers; //  升级彩票期数
    private int sj_status; //  升级状态（0：未升级，1：升级成功，2：升级失败）
    private int sj_jo; //  用户猜的奇偶（1奇，2偶）
    private long sj_time; //  升级时间（时间戳）

    public UpgradeOrderInfo() {
        super();
    }

    public UpgradeOrderInfo(int id, int goodssj_id, String goodssj_title, String goodssj_file_url, String goodssj_title_2, String goodssj_file_url_2, double danjia, double goodssj_price_2, int num, int sj_numbers, int sj_status, int sj_jo, long sj_time) {
        this.id = id;
        this.goodssj_id = goodssj_id;
        this.goodssj_title = goodssj_title;
        this.goodssj_file_url = goodssj_file_url;
        this.goodssj_title_2 = goodssj_title_2;
        this.goodssj_file_url_2 = goodssj_file_url_2;
        this.danjia = danjia;
        this.goodssj_price_2 = goodssj_price_2;
        this.num = num;
        this.sj_numbers = sj_numbers;
        this.sj_status = sj_status;
        this.sj_jo = sj_jo;
        this.sj_time = sj_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGoodssj_id() {
        return goodssj_id;
    }

    public void setGoodssj_id(int goodssj_id) {
        this.goodssj_id = goodssj_id;
    }

    public String getGoodssj_title() {
        return goodssj_title;
    }

    public void setGoodssj_title(String goodssj_title) {
        this.goodssj_title = goodssj_title;
    }

    public String getGoodssj_file_url() {
        return goodssj_file_url;
    }

    public void setGoodssj_file_url(String goodssj_file_url) {
        this.goodssj_file_url = goodssj_file_url;
    }

    public String getGoodssj_title_2() {
        return goodssj_title_2;
    }

    public void setGoodssj_title_2(String goodssj_title_2) {
        this.goodssj_title_2 = goodssj_title_2;
    }

    public String getGoodssj_file_url_2() {
        return goodssj_file_url_2;
    }

    public void setGoodssj_file_url_2(String goodssj_file_url_2) {
        this.goodssj_file_url_2 = goodssj_file_url_2;
    }

    public double getDanjia() {
        return danjia;
    }

    public void setDanjia(double danjia) {
        this.danjia = danjia;
    }

    public double getGoodssj_price_2() {
        return goodssj_price_2;
    }

    public void setGoodssj_price_2(double goodssj_price_2) {
        this.goodssj_price_2 = goodssj_price_2;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getSj_numbers() {
        return sj_numbers;
    }

    public void setSj_numbers(int sj_numbers) {
        this.sj_numbers = sj_numbers;
    }

    public int getSj_status() {
        return sj_status;
    }

    public void setSj_status(int sj_status) {
        this.sj_status = sj_status;
    }

    public int getSj_jo() {
        return sj_jo;
    }

    public void setSj_jo(int sj_jo) {
        this.sj_jo = sj_jo;
    }

    public long getSj_time() {
        return sj_time;
    }

    public void setSj_time(long sj_time) {
        this.sj_time = sj_time;
    }

    @Override
    public String toString() {
        return "UpgradeOrderInfo{" +
                "id=" + id +
                ", goodssj_id=" + goodssj_id +
                ", goodssj_title='" + goodssj_title + '\'' +
                ", goodssj_file_url='" + goodssj_file_url + '\'' +
                ", goodssj_title_2='" + goodssj_title_2 + '\'' +
                ", goodssj_file_url_2='" + goodssj_file_url_2 + '\'' +
                ", danjia=" + danjia +
                ", goodssj_price_2=" + goodssj_price_2 +
                ", num=" + num +
                ", sj_numbers=" + sj_numbers +
                ", sj_status=" + sj_status +
                ", sj_jo=" + sj_jo +
                ", sj_time=" + sj_time +
                '}';
    }
}
