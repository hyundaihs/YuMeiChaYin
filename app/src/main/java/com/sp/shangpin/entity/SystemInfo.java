package com.sp.shangpin.entity;

/**
 * Created by Administrator on 2017/9/9/009.
 */

public class SystemInfo {
    private String title;// 系统名称
    private String gz_contents;// 规则介绍（htmal代码）
    private String base_href;// 文件域名（图片地址的域名，http://chayin.hyk001.com/）
    private int yhq_max; // 优惠券最大张数
    private String kj_banner_file_url; //  开奖历史banner图
    private String zc_file_url; //  注册页面背景图
    private String dl_file_url; //  登录页面背景图
    private String wjmm_file_url; //  忘记密码背景图
    private String sjsc_file_url; //  升级商城背景图

    public SystemInfo() {
        super();
    }

    public SystemInfo(String title, String gz_contents, String base_href, int yhq_max, String kj_banner_file_url, String zc_file_url, String dl_file_url, String wjmm_file_url, String sjsc_file_url) {
        this.title = title;
        this.gz_contents = gz_contents;
        this.base_href = base_href;
        this.yhq_max = yhq_max;
        this.kj_banner_file_url = kj_banner_file_url;
        this.zc_file_url = zc_file_url;
        this.dl_file_url = dl_file_url;
        this.wjmm_file_url = wjmm_file_url;
        this.sjsc_file_url = sjsc_file_url;
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

    public String getKj_banner_file_url() {
        return kj_banner_file_url;
    }

    public void setKj_banner_file_url(String kj_banner_file_url) {
        this.kj_banner_file_url = kj_banner_file_url;
    }

    public String getZc_file_url() {
        return zc_file_url;
    }

    public void setZc_file_url(String zc_file_url) {
        this.zc_file_url = zc_file_url;
    }

    public String getDl_file_url() {
        return dl_file_url;
    }

    public void setDl_file_url(String dl_file_url) {
        this.dl_file_url = dl_file_url;
    }

    public String getWjmm_file_url() {
        return wjmm_file_url;
    }

    public void setWjmm_file_url(String wjmm_file_url) {
        this.wjmm_file_url = wjmm_file_url;
    }

    public String getSjsc_file_url() {
        return sjsc_file_url;
    }

    public void setSjsc_file_url(String sjsc_file_url) {
        this.sjsc_file_url = sjsc_file_url;
    }


}
