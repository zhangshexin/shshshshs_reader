package com.redread.net.netbean;

/**
 * Created by zhangshexin on 2018/10/16.
 *
 * 版块
 */

public class NetBeanModel {
    private int id;//模块id，用于获取对应模块的图书列表
    private String name;//模块名
    private String description;//模块描述
    private NetBeanModelPage books;//图书列表

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public NetBeanModelPage getBooks() {
        return books;
    }

    public void setBooks(NetBeanModelPage books) {
        this.books = books;
    }
}
