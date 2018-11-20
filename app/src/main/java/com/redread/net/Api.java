package com.redread.net;

import android.content.Context;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by zhangshexin on 2018/9/25.
 */

public class Api {
    public static final String baseUrl = "http://47.95.111.63:9999";

    /**
     * 普通登录
     * <p>
     * params:username,password
     */
    public static Request loginPost(Context mContext, HashMap params) {
        String paramsStr = paramToString(params);
        MediaType MEDIA_TYPE_NORAML_FORM = MediaType.parse("application/x-www-form-urlencoded;charset=utf-8");
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_NORAML_FORM, paramsStr);
        Request requestPost = new Request.Builder().url(baseUrl + "/reader/api/v1/login").post(requestBody).build();
        return requestPost;
    }

    /**
     * 生成验证码
     *
     * @param mContext
     * @return
     */
    public static Request codeGenPost(Context mContext, String phone) {
        MediaType MEDIA_TYPE_NORAML_FORM = MediaType.parse("application/x-www-form-urlencoded;charset=utf-8");
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_NORAML_FORM, "phone=" + phone);
        Request requestPost = new Request.Builder().url(baseUrl + "/reader/api/v1/kaptcha").post(requestBody).build();
        return requestPost;
    }

    /**
     * 机构登录
     */
    public static Request deptLoginPost(Context mContext, HashMap params) {
        String paramsStr = paramToString(params);
        MediaType MEDIA_TYPE_NORAML_FORM = MediaType.parse("application/x-www-form-urlencoded;charset=utf-8");
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_NORAML_FORM, paramsStr);
        Request requestPost = new Request.Builder().url(baseUrl + "/reader/api/v1/deptLogin").post(requestBody).build();
        return requestPost;
    }

    /**
     * 验证码登录
     * name = "phone", value = "登录手机号",
     * name = "kaptcha", value = "登录验证码
     *
     * @param mContext
     * @param params
     * @return
     */
    public static Request kaptchaLoginPost(Context mContext,HashMap params, String phone, String kaptcha) {
//        RequestBody requestBody = new FormBody.Builder()
//                .add("phone", phone)
//                .add("kaptcha", kaptcha)
//                .build();
        String paramsStr = paramToString(params);
        MediaType MEDIA_TYPE_NORAML_FORM = MediaType.parse("application/x-www-form-urlencoded;charset=utf-8");
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_NORAML_FORM, paramsStr);
        Request requestPost = new Request.Builder().url(baseUrl + "/reader/api/v1/kaptchaLogin").post(requestBody).build();
        return requestPost;
    }

    /**
     * 设置密码
     * name = "userId", value = "用户id",
     * name = "username", value = "登录用户名",
     * name = "password", value = "登录密码",
     * name = "deptId", value = "机构Id(普通用户的机构id固定为1)"
     *
     * @param mContext
     * @param params
     * @return
     */
    public static Request setPasswordPost(Context mContext, HashMap params) {
        String paramsStr = paramToString(params);
        MediaType MEDIA_TYPE_NORAML_FORM = MediaType.parse("application/x-www-form-urlencoded;charset=utf-8");
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_NORAML_FORM, paramsStr);
        Request requestPost = new Request.Builder().url(baseUrl + "/reader/api/v1/setPassword").post(requestBody).build();
        return requestPost;
    }

    /**
     * 下载地址前缀
     */
    public static final String downUrl = baseUrl + "/reader/api/v1/download/";


    /**
     * 图书详情
     */
    public static Request bookDetailGet(Context mContext, String bookId) {
        Request request = new Request.Builder().url(baseUrl + "/reader/api/v1/book/" + bookId).build();
        return request;
    }

    /**
     * 模块详情
     */
    public static Request modelListGet(int modelId,int offset,int pageCount) {
        Request request = new Request.Builder().url(baseUrl + "/reader/api/v1/module/" + modelId+"?limit="+pageCount+"&offset="+offset+"&order=asc").build();
        return request;
    }

    /**
     * 推荐馆藏
     * bookId
     */
    public static Request recommendPost(Context mContext, String bookId) {
        MediaType MEDIA_TYPE_NORAML_FORM = MediaType.parse("application/x-www-form-urlencoded;charset=utf-8");
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_NORAML_FORM, "");
        Request requestPost = new Request.Builder().url(baseUrl + "/recommendation/" + bookId).post(requestBody).build();
        return requestPost;
    }

    /**
     * 馆藏信息
     */
    public static Request libaryInfoGet() {
        Request request = new Request.Builder().url(baseUrl + "/reader/api/v1/favorite/1").build();
        return request;
    }

    /**
     * 大类
     * 有分页
     * @return
     */
    public static Request typeBigGet() {
        Request request = new Request.Builder().url(baseUrl + "/reader/api/v1/kind?limit=100&offset=0&order=asc&sort=scode").build();
        return request;
    }

    /**
     * 小类
     * 有分页
     * @param id
     * @return
     */
    public static Request typeLittleGet(String id) {
        Request request = new Request.Builder().url(baseUrl + "/reader/api/v1/kind/" + id+"?limit=100&offset=0&order=asc&sort=scode").build();
        return request;
    }


    /**
     * 获取分类号下书籍信息
     */
    public static Request kindBookGet(int id,int offset,int pageCount){
        Request request = new Request.Builder().url(baseUrl + "/reader/api/v1/kindBook/" + id+"?limit="+pageCount+"&offset="+offset+"&order=asc").build();
        return request;
    }

    /**
     * 搜索
     */
    public static Request searchBookGet(String bookName,int offset,int pageCount){
        Request request = new Request.Builder().url(baseUrl + "/reader/api/v1/searchBook/keyword" + bookName+"?limit="+pageCount+"&offset="+offset+"&order=asc").build();
        return request;
    }
    /**
     * 查询机构列表
     *
     * @return
     */
    public static Request deptListGet() {
        Request request = new Request.Builder().url(baseUrl + "/reader/api/v1/dept").build();
        return request;
    }

    /**
     * 拼接参数
     *
     * @param data
     * @return
     */
    public static String paramToString(HashMap<String, String> data) {
        String url = "";
        Iterator<Map.Entry<String, String>> iterator = data.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry ent = (Map.Entry) iterator.next();
            url = url + ent.getKey().toString() + "=" + ent.getValue().toString() + "&";
        }
        return url.substring(0, url.length() - 1);
    }
//    /**
//     * 手机号查重接口
//     *
//     * 参数：mobile
//     */
//    public static String registerMobileRepeatGet(Context mContext, HashMap params){
//        params.put("method","mx.user.mobile.repeat");
//        return ConfigParam.getInstance(mContext).commonGetApi(params);
//    }
//
//    /**
//     * 普通用户注册
//     * params:account,password
//     */
//    public static String registerAcountGet(Context mContext, HashMap params){
//        params.put("method","mx.user.account.register");
//        return ConfigParam.getInstance(mContext).commonGetApi(params);
//    }
//
//    /**
//     *
//     * 普通帐号是否存在
//     * params:account
//     */
//    public static String accountExistGet(Context mContext, HashMap params){
//        params.put("method","mx.user.account.exist");
//        return ConfigParam.getInstance(mContext).commonGetApi(params);
//    }
}
