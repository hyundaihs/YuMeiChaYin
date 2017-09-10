package com.sp.shangpin.entity;

/**
 * Created by Administrator on 2017/9/9/009.
 * 升级产品分类
 */

public class UpgradeGoodsType {
    private int id; //  Id
    private String title; //  标题
    private String file_url; //  图片路径（相对路径，需加上系统信息中的base_href）

    public UpgradeGoodsType() {
        super();
    }

    public UpgradeGoodsType(int id, String title, String file_url) {
        this.id = id;
        this.title = title;
        this.file_url = file_url;
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

    @Override
    public String toString() {
        return "UpgradeGoodsType{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", file_url='" + file_url + '\'' +
                '}';
    }
}
