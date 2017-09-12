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

    public UserInfo() {
        super();
    }

    public UserInfo(int id, String title, String file_url, double jf_price, double ye_price) {
        this.id = id;
        this.title = title;
        this.file_url = file_url;
        this.jf_price = jf_price;
        this.ye_price = ye_price;
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

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", file_url='" + file_url + '\'' +
                ", jf_price=" + jf_price +
                ", ye_price=" + ye_price +
                '}';
    }
}
