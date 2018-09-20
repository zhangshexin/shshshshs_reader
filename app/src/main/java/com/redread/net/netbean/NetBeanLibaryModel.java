package com.redread.net.netbean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangshexin on 2018/9/20.
 */

public class NetBeanLibaryModel {
    private int modelId;
    private String modelName;
    private String modelDescript;
    private String bookName;
    private String author;
    private String cover;

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

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public List<NetBeanLibaryModel> getModelList() {
        return modelList;
    }

    public void setModelList(List<NetBeanLibaryModel> modelList) {
        this.modelList.addAll(modelList);
    }

    private List<NetBeanLibaryModel> modelList=new ArrayList<>();
}
