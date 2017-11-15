package com.sp.shangpin.entity;

/**
 * ChaYin
 * Created by 蔡雨峰 on 2017/9/15.
 */

public class OrderInfo {
    private int orders_id; // 订单ID
    private int id; //  ID
    private String numbers; //  订单编号
    private double price; //  总价
    private int num; //  数量
    private String goodssj_title; //  名称
    private double danjia; //  单价
    private String goodssj_file_url; //  产品图片
    private int status; //  状态（1：待支付，2：已支付，3：升级中，4：升级完毕，5：退货中，6：已退货，7：提货中，8：已发货，9：提货成功，10：取消）
    private long create_time; //  时间（时间戳：1505292608）
    private long sj_numbers; //  升级彩票期数
    private int sj_status; //  升级状态（0：未升级，1：升级成功，2：升级失败）
    private int sj_jo; //  用户猜的奇偶（1奇，2偶）
    private long sj_time; //  升级时间（时间戳）
    private String kj_dates; //  开奖时间 2017-09-15 13:10:00
    private String title; //  收货人
    private String phone; //  电话
    private String pca; //  地区
    private String address; //  地址
    private String contents; //  备注
    private String wl_numbers; //  物流编号
    private String wl_title; //  物流名称
    private String goodssj_title_2; //  产品名称（升级后）
    private String goodssj_file_url_2; //  产品图片（升级后）
    private double goodssj_price_2; //  单价（升级后）
    private double price_2; //  总价（升级后）
    private double yf;

    public OrderInfo() {
        super();
    }

    public OrderInfo(int orders_id) {
        this.orders_id = orders_id;
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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getGoodssj_title() {
        return goodssj_title;
    }

    public void setGoodssj_title(String goodssj_title) {
        this.goodssj_title = goodssj_title;
    }

    public double getDanjia() {
        return danjia;
    }

    public void setDanjia(double danjia) {
        this.danjia = danjia;
    }

    public String getGoodssj_file_url() {
        return goodssj_file_url;
    }

    public void setGoodssj_file_url(String goodssj_file_url) {
        this.goodssj_file_url = goodssj_file_url;
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

    public long getSj_numbers() {
        return sj_numbers;
    }

    public void setSj_numbers(long sj_numbers) {
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

    public String getKj_dates() {
        return kj_dates;
    }

    public void setKj_dates(String kj_dates) {
        this.kj_dates = kj_dates;
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

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getWl_numbers() {
        return wl_numbers;
    }

    public void setWl_numbers(String wl_numbers) {
        this.wl_numbers = wl_numbers;
    }

    public String getWl_title() {
        return wl_title;
    }

    public void setWl_title(String wl_title) {
        this.wl_title = wl_title;
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

    public double getGoodssj_price_2() {
        return goodssj_price_2;
    }

    public void setGoodssj_price_2(double goodssj_price_2) {
        this.goodssj_price_2 = goodssj_price_2;
    }

    public double getPrice_2() {
        return price_2;
    }

    public void setPrice_2(double price_2) {
        this.price_2 = price_2;
    }

    public double getYf() {
        return yf;
    }

    public void setYf(double yf) {
        this.yf = yf;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "orders_id=" + orders_id +
                ", id=" + id +
                ", numbers='" + numbers + '\'' +
                ", price=" + price +
                ", num=" + num +
                ", goodssj_title='" + goodssj_title + '\'' +
                ", danjia=" + danjia +
                ", goodssj_file_url='" + goodssj_file_url + '\'' +
                ", status=" + status +
                ", create_time=" + create_time +
                ", sj_numbers=" + sj_numbers +
                ", sj_status=" + sj_status +
                ", sj_jo=" + sj_jo +
                ", sj_time=" + sj_time +
                ", kj_dates='" + kj_dates + '\'' +
                ", title='" + title + '\'' +
                ", phone='" + phone + '\'' +
                ", pca='" + pca + '\'' +
                ", address='" + address + '\'' +
                ", contents='" + contents + '\'' +
                ", wl_numbers='" + wl_numbers + '\'' +
                ", wl_title='" + wl_title + '\'' +
                ", goodssj_title_2='" + goodssj_title_2 + '\'' +
                ", goodssj_file_url_2='" + goodssj_file_url_2 + '\'' +
                ", goodssj_price_2=" + goodssj_price_2 +
                ", price_2=" + price_2 +
                ", yf=" + yf +
                '}';
    }
}
