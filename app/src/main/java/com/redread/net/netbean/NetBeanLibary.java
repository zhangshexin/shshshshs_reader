package com.redread.net.netbean;

import java.util.List;

/**
 * Created by zhangshexin on 2018/9/20.
 * 馆藏页面信息
 */

public class NetBeanLibary {
    private List<NetBeanBanner> bannerList;//banner图
    private List<NetBeanBook> notifyList;//滚动通知
    private List<NetBeanModel> modelList;//版块列表


    public List<NetBeanBanner> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<NetBeanBanner> bannerList) {
        this.bannerList = bannerList;
    }

    public List<NetBeanBook> getNotifyList() {
        return notifyList;
    }

    public void setNotifyList(List<NetBeanBook> notifyList) {
        this.notifyList = notifyList;
    }

    public List<NetBeanModel> getModelList() {
        return modelList;
    }

    public void setModelList(List<NetBeanModel> modelList) {
        this.modelList = modelList;
    }
}
