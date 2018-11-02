package com.redread.net.netbean;

import java.util.List;

/**
 * Created by zhangshexin on 2018/10/16.
 *
 * 版块
 */

public class NetBeanModel {
    private int modelId;//模块id，用于获取对应模块的图书列表
    private String modelName;//模块名
    private String modelDescript;//模块描述
    private List<NetBeanBook> books;//图书列表

    public List<NetBeanBook> getBooks() {
        return books;
    }

    public void setBooks(List<NetBeanBook> books) {
        this.books = books;
    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelDescript() {
        return modelDescript;
    }

    public void setModelDescript(String modelDescript) {
        this.modelDescript = modelDescript;
    }
}
