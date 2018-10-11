package com.redread.net;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by zhangshexin on 2018/9/25.
 */

public class OkHttpManager {
    private static OkHttpManager mInstance;

    private OkHttpClient mOkHttpClient;

    /**
     * 初始化OkHttpManager
     */

    public OkHttpManager(Context context) {
        //初始化OkHttpClient
        mOkHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(10, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(10, TimeUnit.SECONDS)//设置写入超时时间
                .build();
        //初始化Handler
//        okHttpHandler = new Handler(context.getMainLooper());
    }

    public OkHttpClient getmOkHttpClient(){
        return mOkHttpClient;
    }


    /**
     * 获取单例引用
     *
     * @return
     */

    public static OkHttpManager getInstance(Context context) {
        OkHttpManager inst = mInstance;
        if (inst == null) {
            synchronized (OkHttpManager.class) {
                inst = mInstance;
                if (inst == null) {
                    inst = new OkHttpManager(context.getApplicationContext());
                    mInstance = inst;
                }
            }
        }
        return inst;
    }

}
