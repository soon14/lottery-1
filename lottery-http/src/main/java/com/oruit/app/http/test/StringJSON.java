package com.oruit.app.http.test;/**
 * Created by wyt on 2017/8/25.
 */

import com.alibaba.fastjson.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * @author
 * @create 2017-08-25 15:47g
 */
public class StringJSON {
    public final static String TIME_STAMP_PATTERN = "yyyyMMddHHmmssSSS";
    /**
     * 生成订单号
     * @return
     */
    public static String getTradeNo() {
        int random = (int) ((Math.random() * 9 + 1) * 100000);
        // 自增8位数 00000001
        return formatDate(new Date(), TIME_STAMP_PATTERN) + random;
    }
    /**
     * 格式化日期
     * @param date 日期
     * @param pattern 格式
     * @return
     */
    public static String formatDate(Date date, String pattern) {
        if (date == null)
            return null;
        DateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public static void main(String[] args) {
        String s = "20170203";
        System.out.println(s.substring(4));

    }
}
