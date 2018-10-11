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
    public static final String baseUrl = "http://47.95.111.63:9090";
    /**
     * 登录
     *
     * params:username,password
     */
    public static Request loginPost(Context mContext, HashMap params){
        String paramsStr=paramToString(params);
        MediaType MEDIA_TYPE_NORAML_FORM = MediaType.parse("application/x-www-form-urlencoded;charset=utf-8");
        RequestBody requestBody=RequestBody.create(MEDIA_TYPE_NORAML_FORM,paramsStr);
        Request requestPost=new Request.Builder().url(baseUrl+"/login").post(requestBody).build();
        return requestPost;
    }

    /**
     * 拼接参数
     * @param data
     * @return
     */
    public static String paramToString(HashMap<String, String> data){
        String url="";
        Iterator<Map.Entry<String, String>> iterator = data.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry ent = (Map.Entry) iterator.next();
            url=url+ent.getKey().toString()+"="+ ent.getValue().toString()+"&";
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
