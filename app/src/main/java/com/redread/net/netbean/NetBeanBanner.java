package com.redread.net.netbean;

/**
 * Created by zhangshexin on 2018/10/16.
 * banner对象
 */

public class NetBeanBanner {
    private String id;
    private String name;
    private String imageUrl;//banner图的url
    private String description;//banner的描述
    private String webUrl;//banner跳转web页url

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }
}
