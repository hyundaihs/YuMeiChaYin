package com.sp.shangpin.entity;

/**
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/18.
 */

public class NormalGoodsInfo {
    private int id; //  ID
    private String title; //  标题
    private String info_file_url; //  详情图片
    private double price; //  价格
    private String file_url; //  图片
    private double yuanjia; //  原价
    private int kucun; //  库存
    private String app_contents; //  详情(html代码)
    private double yf; //  运费
    private double yf_one; //  每增加一件增加的运费
    private int yhq_num; //  允许使用优惠券数量
    private int cx; //  是否促销（0：否，1：是）

    public NormalGoodsInfo() {
        super();
    }

    public NormalGoodsInfo(int id, String title, String info_file_url, double price, String file_url, double yuanjia, int kucun, String app_contents, double yf, double yf_one, int yhq_num, int cx) {
        this.id = id;
        this.title = title;
        this.info_file_url = info_file_url;
        this.price = price;
        this.file_url = file_url;
        this.yuanjia = yuanjia;
        this.kucun = kucun;
        this.app_contents = app_contents;
        this.yf = yf;
        this.yf_one = yf_one;
        this.yhq_num = yhq_num;
        this.cx = cx;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo_file_url() {
        return info_file_url;
    }

    public void setInfo_file_url(String info_file_url) {
        this.info_file_url = info_file_url;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public double getYuanjia() {
        return yuanjia;
    }

    public void setYuanjia(double yuanjia) {
        this.yuanjia = yuanjia;
    }

    public int getKucun() {
        return kucun;
    }

    public void setKucun(int kucun) {
        this.kucun = kucun;
    }

    public String getApp_contents() {
        return app_contents;
    }

    public void setApp_contents(String app_contents) {
        this.app_contents = app_contents;
    }

    public double getYf() {
        return yf;
    }

    public void setYf(double yf) {
        this.yf = yf;
    }

    public double getYf_one() {
        return yf_one;
    }

    public void setYf_one(double yf_one) {
        this.yf_one = yf_one;
    }

    public int getYhq_num() {
        return yhq_num;
    }

    public void setYhq_num(int yhq_num) {
        this.yhq_num = yhq_num;
    }

    public int getCx() {
        return cx;
    }

    public void setCx(int cx) {
        this.cx = cx;
    }

    @Override
    public String toString() {
        return "NormalGoodsInfo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", info_file_url='" + info_file_url + '\'' +
                ", price=" + price +
                ", file_url='" + file_url + '\'' +
                ", yuanjia=" + yuanjia +
                ", kucun=" + kucun +
                ", app_contents='" + app_contents + '\'' +
                ", yf=" + yf +
                ", yf_one=" + yf_one +
                ", yhq_num=" + yhq_num +
                ", cx=" + cx +
                '}';
    }
}
