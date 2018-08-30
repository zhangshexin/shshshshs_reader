package com.redread;

import android.app.Application;
import android.content.Context;

/**
 * Created by zhangshexin on 2018/8/30.
 */

public class App extends Application {
    private static Context sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static Context getContext(){
        return sInstance;
    }
}
