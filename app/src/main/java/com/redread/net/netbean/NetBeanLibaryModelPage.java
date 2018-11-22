package com.redread.net.netbean;

import java.util.List;

/**
 * Created by zhangshexin on 2018/11/22.
 */

public class NetBeanLibaryModelPage extends BaseNetBeanPage{
    private List<NetBeanModel> pageData;

    public List<NetBeanModel> getPageData() {
        return pageData;
    }

    public void setPageData(List<NetBeanModel> pageData) {
        this.pageData = pageData;
    }
}
