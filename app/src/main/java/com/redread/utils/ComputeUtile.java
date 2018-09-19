package com.redread.utils;

import java.text.DecimalFormat;

/**
 * Created by zhangshexin on 2018/9/17.
 * 计算类
 */

public class ComputeUtile {
    public static String getPercent(int x, int total) {
        if (total == 0)
            return "0%";
        int result = (int) (x / (float) total * 100);
        return result + "%";
    }
}
