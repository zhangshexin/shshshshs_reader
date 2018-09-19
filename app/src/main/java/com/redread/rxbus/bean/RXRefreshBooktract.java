package com.redread.rxbus.bean;

/**
 * Created by zhangshexin on 2018/9/19.
 * 刷新书架
 */

public class RXRefreshBooktract {
    public static final int STATUE_DELETE_CANCEL=1;
    public static final int STATUE_REFRESH=-1;
    private int statue=-1;
    public RXRefreshBooktract(int statue){
        this.statue=statue;
    }

    public int getStatue() {
        return statue;
    }

    public void setStatue(int statue) {
        this.statue = statue;
    }
}
