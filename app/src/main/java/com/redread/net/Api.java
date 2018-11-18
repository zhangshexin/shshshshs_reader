package com.redread.net;

import android.content.Context;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
        Request requestPost = new Request.Builder().url(baseUrl + "/login").post(requestBody).build();
        return requestPost;
    }

    /**
     * 生成验证码
     * @param mContext
     * @param params
     * @return
     */
    public static Request codeGenPost(Context mContext, HashMap params){
        String paramsStr = paramToString(params);
        MediaType MEDIA_TYPE_NORAML_FORM = MediaType.parse("application/x-www-form-urlencoded;charset=utf-8");
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_NORAML_FORM, paramsStr);
        Request requestPost = new Request.Builder().url(baseUrl + "/kaptcha").post(requestBody).build();
        return requestPost;
    }

    /**
     * 机构登录
     */
    public static Request deptLoginPost(Context mContext, HashMap params) {
        String paramsStr = paramToString(params);
        MediaType MEDIA_TYPE_NORAML_FORM = MediaType.parse("application/x-www-form-urlencoded;charset=utf-8");
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_NORAML_FORM, paramsStr);
        Request requestPost = new Request.Builder().url(baseUrl + "/deptLogin").post(requestBody).build();
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
    public static Request modelListGet(Context mContext, String modelId) {
        Request request = new Request.Builder().url(baseUrl + "/reader/api/v1/module/" + modelId).build();
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
        Request request = new Request.Builder().url(baseUrl + "/reader/api/v1/favorite/9").build();
        return request;
    }

    /**
     * 大类
     *
     * @return
     */
    public static Request typeBigGet() {
        Request request = new Request.Builder().url(baseUrl + "/reader/api/v1/kind").build();
        return request;
    }

    /**
     * 小类
     *
     * @param id
     * @return
     */
    public static Request typeLittleGet(String id) {
        Request request = new Request.Builder().url(baseUrl + "/reader/api/v1/kind/" + id).build();
        return request;
    }


    /**
     * 查询机构列表
     * @return
     */
    public static Request deptListGet(){
        Request request = new Request.Builder().url(baseUrl + "/reader/api/v1/dept" ).build();
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
        return url;
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
