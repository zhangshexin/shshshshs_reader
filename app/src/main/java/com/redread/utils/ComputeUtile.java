package com.redread.utils;

import java.text.DecimalFormat;
import java.util.Random;

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

    /**
     * 获取十六进制的颜色代码.例如 "#6E36B4" , For HTML ,
     *
     * @return String
     */
    public static String getRandColorCode() {
        String r, g, b;
        Random random = new Random();
        r = Integer.toHexString(random.nextInt(256)).toUpperCase();
        g = Integer.toHexString(random.nextInt(256)).toUpperCase();
        b = Integer.toHexString(random.nextInt(256)).toUpperCase();

        r = r.length() == 1 ? "0" + r : r;
        g = g.length() == 1 ? "0" + g : g;
        b = b.length() == 1 ? "0" + b : b;

        return r + g + b;
    }

    public static int getRandColorInt() {
        // 随机颜色  
        Random random = new Random();
        int ranColor = 0xff000000 | random.nextInt(0x00ffffff);
        return ranColor;
    }


}
