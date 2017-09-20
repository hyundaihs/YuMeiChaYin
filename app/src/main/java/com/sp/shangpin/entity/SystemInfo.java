package com.sp.shangpin.entity;

/**
 * Created by Administrator on 2017/9/9/009.
 */

public class SystemInfo {
    private String title;// 系统名称
    private String gz_contents;// 规则介绍（htmal代码）
    private String base_href;// 文件域名（图片地址的域名，http://chayin.hyk001.com/）
    private int yhq_max; // 优惠券最大张数

    public SystemInfo() {
        super();
    }

    public SystemInfo(String title, String gz_contents, String base_href, int yhq_max) {
        this.title = title;
        this.gz_contents = gz_contents;
        this.base_href = base_href;
        this.yhq_max = yhq_max;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGz_contents() {
        return gz_contents;
    }

    public void setGz_contents(String gz_contents) {
        this.gz_contents = gz_contents;
    }

    public String getBase_href() {
        return base_href;
    }

    public void setBase_href(String base_href) {
        this.base_href = base_href;
    }

    public int getYhq_max() {
        return yhq_max;
    }

    public void setYhq_max(int yhq_max) {
        this.yhq_max = yhq_max;
    }

    @Override
    public String toString() {
        return "SystemInfo{" +
                "title='" + title + '\'' +
                ", gz_contents='" + gz_contents + '\'' +
                ", base_href='" + base_href + '\'' +
                ", yhq_max='" + yhq_max + '\'' +
                '}';
    }
}
