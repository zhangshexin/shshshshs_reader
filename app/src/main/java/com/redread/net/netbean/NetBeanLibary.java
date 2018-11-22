package com.redread.net.netbean;

/**
 * Created by zhangshexin on 2018/9/20.
 * 馆藏页面信息
 */

public class NetBeanLibary {
    private NetBeanBannerPage bannerList;//banner图
    private NetBeanBookPage notifyList;//滚动通知
    private NetBeanLibaryModelPage modelList;//版块列表


    public NetBeanBannerPage getBannerList() {
        return bannerList;
    }

    public void setBannerList(NetBeanBannerPage bannerList) {
        this.bannerList = bannerList;
    }

    public NetBeanBookPage getNotifyList() {
        return notifyList;
    }

    public void setNotifyList(NetBeanBookPage notifyList) {
        this.notifyList = notifyList;
    }

    public NetBeanLibaryModelPage getModelList() {
        return modelList;
    }

    public void setModelList(NetBeanLibaryModelPage modelList) {
        this.modelList = modelList;
    }
}
