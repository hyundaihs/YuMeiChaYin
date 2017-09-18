package com.sp.shangpin.entity;

import java.util.List;

/**
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/18.
 */

public class NormalOrderInfo {
    private int id; //  订单ID
    private int cx; //  是否促销
    private String numbers; //  订单编号
    private double price; //  订单总价
    private double yf; //  运费
    private double goods_price; //  产品总价
    private String title; //  姓名
    private String phone; //  电话
    private String pca; //  地区
    private String address; //  详细地址
    private String wl_title; //  物流名称
    private String contents; // 备注
    private String wl_numbers; //  物流编号
    private int status; //  订单状态（1:待支付 2:待发货 3:待收货 4:已取消 5:已完成 6:退款中 7:已退款）
    private long create_time; //  创建时间（时间戳）
    private List<NormalOrderGoodsInfo> goods_lists; //  Array（产品列表）

    public NormalOrderInfo() {
        super();
    }

    public NormalOrderInfo(int id, int cx, String numbers, double price, double yf, double goods_price, String title, String phone, String pca, String address, String wl_title, String contents, String wl_numbers, int status, long create_time, List<NormalOrderGoodsInfo> goods_lists) {
        this.id = id;
        this.cx = cx;
        this.numbers = numbers;
        this.price = price;
        this.yf = yf;
        this.goods_price = goods_price;
        this.title = title;
        this.phone = phone;
        this.pca = pca;
        this.address = address;
        this.wl_title = wl_title;
        this.contents = contents;
        this.wl_numbers = wl_numbers;
        this.status = status;
        this.create_time = create_time;
        this.goods_lists = goods_lists;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCx() {
        return cx;
    }

    public void setCx(int cx) {
        this.cx = cx;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getYf() {
        return yf;
    }

    public void setYf(double yf) {
        this.yf = yf;
    }

    public double getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(double goods_price) {
        this.goods_price = goods_price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPca() {
        return pca;
    }

    public void setPca(String pca) {
        this.pca = pca;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWl_title() {
        return wl_title;
    }

    public void setWl_title(String wl_title) {
        this.wl_title = wl_title;
    }

    public String getWl_numbers() {
        return wl_numbers;
    }

    public void setWl_numbers(String wl_numbers) {
        this.wl_numbers = wl_numbers;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public List<NormalOrderGoodsInfo> getGoods_lists() {
        return goods_lists;
    }

    public void setGoods_lists(List<NormalOrderGoodsInfo> goods_lists) {
        this.goods_lists = goods_lists;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "NormalOrderInfo{" +
                "id=" + id +
                ", cx=" + cx +
                ", numbers='" + numbers + '\'' +
                ", price=" + price +
                ", yf=" + yf +
                ", goods_price=" + goods_price +
                ", title='" + title + '\'' +
                ", phone='" + phone + '\'' +
                ", pca='" + pca + '\'' +
                ", address='" + address + '\'' +
                ", wl_title='" + wl_title + '\'' +
                ", contents='" + contents + '\'' +
                ", wl_numbers='" + wl_numbers + '\'' +
                ", status=" + status +
                ", create_time=" + create_time +
                ", goods_lists=" + goods_lists +
                '}';
    }
}
