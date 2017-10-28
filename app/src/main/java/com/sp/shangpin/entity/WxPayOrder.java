package com.sp.shangpin.entity;

/**
 * ChaYin
 * Created by ${蔡雨峰} on 2017/10/28/028.
 */

public class WxPayOrder {
    private int orders_id;
    private int id; //  订单id
    private String partnerid; //  1490913452
    private String prepayid; //  wx201710271139387cfeef18e40955965740
    private String noncestr; //  PnQ5qxWMqKNgznrg
    private String timestamp; //  1509075578
    private String packages; //  Sign=WXPay
    private String sign; //  C7F544B5700E7F43C8E036DF7B0E8F6E

    public WxPayOrder() {
        super();
    }

    public WxPayOrder(int orders_id, int id, String partnerid, String prepayid, String noncestr, String timestamp, String packages, String sign) {
        this.orders_id = orders_id;
        this.id = id;
        this.partnerid = partnerid;
        this.prepayid = prepayid;
        this.noncestr = noncestr;
        this.timestamp = timestamp;
        this.packages = packages;
        this.sign = sign;
    }

    public int getOrders_id() {
        return orders_id;
    }

    public void setOrders_id(int orders_id) {
        this.orders_id = orders_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "WxPayOrder{" +
                "orders_id=" + orders_id +
                ", id=" + id +
                ", partnerid='" + partnerid + '\'' +
                ", prepayid='" + prepayid + '\'' +
                ", noncestr='" + noncestr + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", packages='" + packages + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
