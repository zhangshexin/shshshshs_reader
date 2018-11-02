package com.redread.net.netbean;

/**
 * Created by zhangshexin on 2018/10/16.
 * 用户对象
 */

public class NetBeanUser {
    private String phoneNum;//电话号
    private String token;//用于每次网络请求
    private String photoUrl;//头像url
    private String userNickname;//用户昵称

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }
}
