package com.sp.shangpin.entity;

/**
 * YuMeiChaYin
 * Created by 蔡雨峰 on 2017/9/21.
 */

public class YHQ {
    private int id; //  ID
    private String title; //  名称
    private double price; //  价格
    private int status; //  状态（1：未使用，2：已使用，3：已过期）
    private long end_time; //  截止时间（时间戳）

    public YHQ() {
        super();
    }

    public YHQ(int id, String title, double price, int status, long end_time) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.status = status;
        this.end_time = end_time;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    @Override
    public String toString() {
        return "YHQ{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", status=" + status +
                ", end_time=" + end_time +
                '}';
    }
}
