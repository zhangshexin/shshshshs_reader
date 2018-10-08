package com.redread.net.netbean;

import java.util.List;

/**
 * Created by zhangshexin on 2018/10/8.
 *
 * 中图分类-类型
 */

public class NetBeanType {
    private String typeCode;//类型码
    private String typeName;//类型名
    private List<NetBeanType> typeContents;//类型内容

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<NetBeanType> getTypeContents() {
        return typeContents;
    }

    public void setTypeContents(List<NetBeanType> typeContents) {
        this.typeContents = typeContents;
    }
}
