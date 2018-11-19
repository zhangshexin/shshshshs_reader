package com.redread.utils;

import android.os.Environment;

import com.redread.model.entity.DownLoad;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangshexin on 2018/9/14.
 *
 * 所有常量数据
 */

public class Constant {
    public static String USER_ID_INT="user_id";
    public static String USER_NAME_STR="user_name";
    public static String USER_TOKEN_STR="user_token";
    public static String USER_DEPTID_INT="user_dept_id";//机构id





    /**
     * 失败
     */
    public static final int DOWN_STATUS_FAILE=0;
    /**
     * 完成
     */
    public static final int DOWN_STATUS_SUCCESS=1;

    /**
     * 下载中
     */
    public static final int DOWN_STATUS_ING=2;
    /**
     * 暂停
     */
    public static final int DOWN_STATUS_PAUS=3;
    /**
     * 等待下载
     */
    public static final int DOWN_STATUS_WAIT=-1;
    /**
     * 删除
     */
    public static final int DOWN_STATUS_CLEAR=-2;

    //记录所有下载中和等待下载的任务
    private static List<DownLoad> tasks=new ArrayList<>();

    /**
     * 添加任务
     * @param task
     */
    public static synchronized void addTask(DownLoad task){
        tasks.add(task);
    }

    /**
     * 移除任务
     * @param url
     */
    public  static synchronized void removeTask(String url){
        int position=-1;
        for (int i=0;i<tasks.size();i++){
            if(tasks.get(i).getUrl().equals(url)){
                position=i;
                i=Integer.MAX_VALUE;
            }
        }
        tasks.remove(position);
    }

    /**
     * 据下载url找任务
     * @param url
     * @return
     */
    public static DownLoad findTask(String url){
        for (int i=0;i<tasks.size();i++){
            if(tasks.get(i).getUrl().equals(url)){
                return tasks.get(i);
            }
        }
        return null;
    }


    //应用的sd卡根路径
    public static String sdCardPath= Environment.getExternalStorageDirectory().getPath()+"/hanchuang";

    //下载的书的路径
    public static String bookPath=sdCardPath+"/bookstore/";
    //封面等图片所在的路径
    public static String picture=sdCardPath+"/picture/";

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
