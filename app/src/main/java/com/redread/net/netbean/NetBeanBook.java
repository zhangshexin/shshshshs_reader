package com.redread.net.netbean;

import java.io.Serializable;

/**
 * Created by zhangshexin on 2018/9/20.
 *
 * 图书对象
 */
public class NetBeanBook implements Serializable{
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrowsPath() {
        return browsPath;
    }

    public void setBrowsPath(String browsPath) {
        this.browsPath = browsPath;
    }

    public String getBorrowAddress() {
        return borrowAddress;
    }

    public void setBorrowAddress(String borrowAddress) {
        this.borrowAddress = borrowAddress;
    }

    private String title;//通知标题，用于通知中的滚动显示
    private String name;//书名
    private String author;//作者
    private String coverPath;//封面地址
    private String introduction;//图书简介
    private String id;//图书id，用于获取图书详情
    private String type;//类型，是txt还是pdf
    private String browsPath;//图书下载地址
    private String borrowAddress="http://www.baidu.com";//借阅纸书，图书馆地址 TODO 正式的这里要删除

}
