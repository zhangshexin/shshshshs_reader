package com.redread.net.netbean;

/**
 * Created by zhangshexin on 2018/10/16.
 * banner对象
 */

public class NetBeanBanner {
    private String bannerUrl;//banner图的url
    private String bannerDes;//banner的描述
    private String bannerWebUrl;//banner跳转web页url

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getBannerDes() {
        return bannerDes;
    }

    public void setBannerDes(String bannerDes) {
        this.bannerDes = bannerDes;
    }

    public String getBannerWebUrl() {
        return bannerWebUrl;
    }

    public void setBannerWebUrl(String bannerWebUrl) {
        this.bannerWebUrl = bannerWebUrl;
    }
}
