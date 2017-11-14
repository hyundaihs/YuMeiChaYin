package com.sp.shangpin.entity;

/**
 * YuMeiChaYin
 * Created by 蔡雨峰 on 2017/11/14.
 */

public class GetCashInfo {
    private int type_id; //  提现类型（1：银行卡，2：微信）
    private double price; //  金额
    private String contents; //  备注
    private String title; //  姓名
    private String phone; //  电话
    private String bank_name; //  开户行
    private String card_numbers; //  银行卡号
    private int status; //  状态（1：未处理，2：已打款，3：拒绝打款）
    private String update_time; //  更新时间（时间戳）
    private String create_time; //  申请时间（时间戳）

    public GetCashInfo() {
        super();
    }

    public GetCashInfo(int type_id, double price, String contents, String title, String phone, String bank_name, String card_numbers, int status, String update_time, String create_time) {
        this.type_id = type_id;
        this.price = price;
        this.contents = contents;
        this.title = title;
        this.phone = phone;
        this.bank_name = bank_name;
        this.card_numbers = card_numbers;
        this.status = status;
        this.update_time = update_time;
        this.create_time = create_time;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
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

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getCard_numbers() {
        return card_numbers;
    }

    public void setCard_numbers(String card_numbers) {
        this.card_numbers = card_numbers;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "GetCashInfo{" +
                "type_id=" + type_id +
                ", price=" + price +
                ", contents='" + contents + '\'' +
                ", title='" + title + '\'' +
                ", phone='" + phone + '\'' +
                ", bank_name='" + bank_name + '\'' +
                ", card_numbers='" + card_numbers + '\'' +
                ", status=" + status +
                ", update_time='" + update_time + '\'' +
                ", create_time='" + create_time + '\'' +
                '}';
    }
}
