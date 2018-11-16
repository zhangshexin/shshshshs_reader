package com.redread.net.netbean;

import java.util.List;

/**
 * Created by zhangshexin on 2018/10/8.
 *
 * 中图分类-类型
 */

public class NetBeanType {

    private Long id;//": 10537,
    private String name;//": "综合性图书",
    private String scode;//": "Z",
    private String ccode;//": "021000000000000000",
    private String type;//": "1",
    private List<NetBeanType> subKindList;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<NetBeanType> getSubKindList() {
        return subKindList;
    }

    public void setSubKindList(List<NetBeanType> subKindList) {
        this.subKindList = subKindList;
    }
}
