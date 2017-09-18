package com.sp.shangpin.entity;

/**
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/18.
 */

public class NormalOrderGoodsInfo {
    private double price; // 单价
    private int num; // 数量
    private double xiaoji; // 小计
    private int goods_id; // 产品id
    private String goods_title; // 产品名称
    private String goods_file_url; // 产品图片

    public NormalOrderGoodsInfo() {
        super();
    }

    public NormalOrderGoodsInfo(double price, int num, double xiaoji, int goods_id, String goods_title, String goods_file_url) {
        this.price = price;
        this.num = num;
        this.xiaoji = xiaoji;
        this.goods_id = goods_id;
        this.goods_title = goods_title;
        this.goods_file_url = goods_file_url;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getXiaoji() {
        return xiaoji;
    }

    public void setXiaoji(double xiaoji) {
        this.xiaoji = xiaoji;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_title() {
        return goods_title;
    }

    public void setGoods_title(String goods_title) {
        this.goods_title = goods_title;
    }

    public String getGoods_file_url() {
        return goods_file_url;
    }

    public void setGoods_file_url(String goods_file_url) {
        this.goods_file_url = goods_file_url;
    }

    @Override
    public String toString() {
        return "NormalOrderGoodsInfo{" +
                "price='" + price + '\'' +
                ", num='" + num + '\'' +
                ", xiaoji='" + xiaoji + '\'' +
                ", goods_id='" + goods_id + '\'' +
                ", goods_title='" + goods_title + '\'' +
                ", goods_file_url='" + goods_file_url + '\'' +
                '}';
    }
}
