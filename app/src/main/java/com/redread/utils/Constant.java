package com.redread.utils;

import android.os.Environment;

/**
 * Created by zhangshexin on 2018/9/14.
 *
 * 所有常量数据
 */

public class Constant {
    //应用的sd卡根路径
    public static String sdCardPath= Environment.getExternalStorageDirectory().getPath()+"/yangchen";

    //下载的书的路径
    public static String bookPath=sdCardPath+"/bookstore";
    //封面等图片所在的路径
    public static String picture=sdCardPath+"/picture";
}
