package com.sp.shangpin.entity;

/**
 * Created by Administrator on 2017/9/9/009.
 * 升级产品列表
 */

public class UpgradeGoods {
    protected int id; //  Id
    protected String title; //  标题
    protected String file_url; //  图片路径（相对路径，需加上系统信息中的base_href）
    private String info_file_url; //  详情图片
    protected double price; //  价格
    protected double yuanjia; //  原价
    protected int kucun; //  库存
    private String title_2; //  标题（升级后）
    private String file_url_2; //  图片（升级后）
    private double price_2; //  价格（升级后）
    private String app_contents; //  详情(html代码)
    private double yf; //  运费
    private double yf_one; //  每增加一件增加的运费

    public UpgradeGoods() {
        super();
    }

    public UpgradeGoods(int id, String title, String file_url, String info_file_url, double price, double yuanjia, int kucun, String title_2, String file_url_2, double price_2, String app_contents, double yf, double yf_one) {
        this.id = id;
        this.title = title;
        this.file_url = file_url;
        this.info_file_url = info_file_url;
        this.price = price;
        this.yuanjia = yuanjia;
        this.kucun = kucun;
        this.title_2 = title_2;
        this.file_url_2 = file_url_2;
        this.price_2 = price_2;
        this.app_contents = app_contents;
        this.yf = yf;
        this.yf_one = yf_one;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public String getTitle_2() {
        return title_2;
    }

    public void setTitle_2(String title_2) {
        this.title_2 = title_2;
    }

    public String getFile_url_2() {
        return file_url_2;
    }

    public void setFile_url_2(String file_url_2) {
        this.file_url_2 = file_url_2;
    }

    public double getPrice_2() {
        return price_2;
    }

    public void setPrice_2(double price_2) {
        this.price_2 = price_2;
    }

    public String getInfo_file_url() {
        return info_file_url;
    }

    public void setInfo_file_url(String info_file_url) {
        this.info_file_url = info_file_url;
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

    @Override
    public String toString() {
        return "UpgradeGoods{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", file_url='" + file_url + '\'' +
                ", info_file_url='" + info_file_url + '\'' +
                ", price=" + price +
                ", yuanjia=" + yuanjia +
                ", kucun=" + kucun +
                ", title_2='" + title_2 + '\'' +
                ", file_url_2='" + file_url_2 + '\'' +
                ", price_2='" + price_2 + '\'' +
                ", app_contents='" + app_contents + '\'' +
                ", yf='" + yf + '\'' +
                ", yf_one='" + yf_one + '\'' +
                '}';
    }
}
