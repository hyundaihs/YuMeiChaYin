package com.sp.shangpin.entity;

/**
 * ChaYin
 * Created by ${蔡雨峰} on 2017/11/5/005.
 */

public class NotificationInfo {
    private String title; //  标题
    private String file_url; // 头像地址（http://www.abc.com/1123.jpg）

    public NotificationInfo() {
        super();
    }

    public NotificationInfo(String title, String file_url) {
        this.title = title;
        this.file_url = file_url;
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
        return "NotificationInfo{" +
                "title='" + title + '\'' +
                ", file_url='" + file_url + '\'' +
                '}';
    }
}
