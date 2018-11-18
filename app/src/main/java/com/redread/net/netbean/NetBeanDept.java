package com.redread.net.netbean;

/**
 * Created by zhangshexin on 2018/11/18.
 */

public class NetBeanDept {
    private int id;//": 17,
    private String name;//": "实验二组",

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

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    private int parentId;//": 11,
}
