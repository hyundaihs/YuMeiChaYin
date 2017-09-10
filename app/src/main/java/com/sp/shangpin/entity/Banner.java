package com.sp.shangpin.entity;

/**
 * Created by Administrator on 2017/9/9/009.
 * 顶部banner图列表
 */

public class Banner {
    private int id; //  Id
    private String title; //  标题
    private String file_url; //  图片路径（相对路径，需加上系统信息中的base_href）
    private String banner_file_url; // 抽奖页面的类型图片banner

    public Banner() {
        super();
    }

    public Banner(int id, String title, String file_url, String banner_file_url) {
        this.id = id;
        this.title = title;
        this.file_url = file_url;
        this.banner_file_url = banner_file_url;
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

    public String getBanner_file_url() {
        return banner_file_url;
    }

    public void setBanner_file_url(String banner_file_url) {
        this.banner_file_url = banner_file_url;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", file_url='" + file_url + '\'' +
                ", banner_file_url='" + banner_file_url + '\'' +
                '}';
    }
}
