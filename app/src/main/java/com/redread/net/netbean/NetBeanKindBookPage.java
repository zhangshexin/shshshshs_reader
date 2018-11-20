package com.redread.net.netbean;

import java.util.List;

/**
 * Created by zhangshexin on 2018/11/20.
 */

public class NetBeanKindBookPage extends BaseNetBeanPage{
    private List<NetBeanBook> pageData;

    public List<NetBeanBook> getPageData() {
        return pageData;
    }

    public void setPageData(List<NetBeanBook> pageData) {
        this.pageData = pageData;
    }
}
