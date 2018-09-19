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

    /**
     * 书的类型-txt
     */
    public static String BOOK_TYPE_TXT="TXT";
    /**
     * 书的类型-pdf
     */
    public static String BOOK_TYPE_PDF="PDF";

    ///////////////////////////preference///////////////////////////////
    /**
     * 第一次进入应用，作一些必要的处理
     */
    public static String KEY_BOOLEAN_FIRST_USE="KEY_BOOLEAN_FIRST_USE";
}
