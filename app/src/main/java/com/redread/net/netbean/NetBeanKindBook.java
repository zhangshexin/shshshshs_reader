package com.redread.net.netbean;

/**
 * Created by zhangshexin on 2018/11/20.
 */

public class NetBeanKindBook {
    private int id;//": 0,
    private String name;//": "马克思主义、列宁主义、毛泽东思想、邓小平理论",
    private String scode;//": "A",
    private String ccode;//": "000000000000000000",
    private int type;//": 1,
    private int parentId;//": -1,
    private NetBeanKindBookPage books;

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

    public String getScode() {
        return scode;
    }

    public void setScode(String scode) {
        this.scode = scode;
    }

    public String getCcode() {
        return ccode;
    }

    public void setCcode(String ccode) {
        this.ccode = ccode;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public NetBeanKindBookPage getBooks() {
        return books;
    }

    public void setBooks(NetBeanKindBookPage books) {
        this.books = books;
    }
}
