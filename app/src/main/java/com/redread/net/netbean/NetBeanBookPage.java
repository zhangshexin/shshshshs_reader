package com.redread.net.netbean;

import java.util.List;

/**
 * Created by zhangshexin on 2018/11/21.
 */

public class NetBeanBookPage extends BaseNetBeanPage {
    private List<NetBeanBook> pageData;

    public List<NetBeanBook> getPageData() {
        return pageData;
    }

    public void setPageData(List<NetBeanBook> pageData) {
        this.pageData = pageData;
    }
}
