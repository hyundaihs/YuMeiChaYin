package com.sp.shangpin.entity;

/**
 * YuMeiChaYin
 * Created by 蔡雨峰 on 2017/11/20.
 */

public class TopUpLogInfo {
    private String id; //  Id
    private double price; //  金额
    private String pay_type; //  支付类型（weixin:微信，alipay：支付宝，bank：银联）
    private int status; //  状态（1待支付，2已支付，3已取消）
    private long create_time; //  时间（时间戳：1505292608）

    public TopUpLogInfo() {
        super();
    }

    public TopUpLogInfo(String id, double price, String pay_type, int status, long create_time) {
        this.id = id;
        this.price = price;
        this.pay_type = pay_type;
        this.status = status;
        this.create_time = create_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
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

    @Override
    public String toString() {
        return "TopUpLogInfo{" +
                "id='" + id + '\'' +
                ", price=" + price +
                ", pay_type='" + pay_type + '\'' +
                ", status=" + status +
                ", create_time='" + create_time + '\'' +
                '}';
    }
}
