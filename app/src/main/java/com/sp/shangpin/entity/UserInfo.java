package com.sp.shangpin.entity;

/**
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/12.
 */

public class UserInfo {
    private int id; //  用户id
    private String title; //  昵称
    private String file_url; //  头像地址（http://www.abc.com/1.jpg）
    private double jf_price; //  金币/积分余额(0.00)
    private double ye_price; //  账户余额（0.00）
    private String wl_title; //  收货人
    private String wl_phone; //  收货人电话
    private String wl_pca; //  收货地区
    private String wl_address; //  收货地址
    private String wl_content; //收货备注

    public UserInfo() {
        super();
    }

    public UserInfo(int id, String title, String file_url, double jf_price, double ye_price, String wl_title, String wl_phone, String wl_pca, String wl_address, String wl_content) {
        this.id = id;
        this.title = title;
        this.file_url = file_url;
        this.jf_price = jf_price;
        this.ye_price = ye_price;
        this.wl_title = wl_title;
        this.wl_phone = wl_phone;
        this.wl_pca = wl_pca;
        this.wl_address = wl_address;
        this.wl_content = wl_content;
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

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public double getJf_price() {
        return jf_price;
    }

    public void setJf_price(double jf_price) {
        this.jf_price = jf_price;
    }

    public double getYe_price() {
        return ye_price;
    }

    public void setYe_price(double ye_price) {
        this.ye_price = ye_price;
    }

    public String getWl_title() {
        return wl_title;
    }

    public void setWl_title(String wl_title) {
        this.wl_title = wl_title;
    }

    public String getWl_phone() {
        return wl_phone;
    }

    public void setWl_phone(String wl_phone) {
        this.wl_phone = wl_phone;
    }

    public String getWl_pca() {
        return wl_pca;
    }

    public void setWl_pca(String wl_pca) {
        this.wl_pca = wl_pca;
    }

    public String getWl_address() {
        return wl_address;
    }

    public void setWl_address(String wl_address) {
        this.wl_address = wl_address;
    }

    public String getWl_content() {
        return wl_content;
    }

    public void setWl_content(String wl_content) {
        this.wl_content = wl_content;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", file_url='" + file_url + '\'' +
                ", jf_price=" + jf_price +
                ", ye_price=" + ye_price +
                ", wl_title='" + wl_title + '\'' +
                ", wl_phone='" + wl_phone + '\'' +
                ", wl_pca='" + wl_pca + '\'' +
                ", wl_address='" + wl_address + '\'' +
                ", wl_content='" + wl_content + '\'' +
                '}';
    }
}
