package com.oruit.app.util;/**
 * Created by wyt on 2017/10/12.
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author @wyt
 * @create 2017-10-12 10:40
 */
public class DateRandom {
    public static Map<String,Object> getDateRandom() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formats = new SimpleDateFormat("yyyyMMdd");
        Date randomDate = new Date();
        Map<String,Object> maps = new HashMap<>();
        String format1 = format.format(new Date());
        int max=84;
        int min=1;
        Random random = new Random();
        for (int i = 0 ; i<3;i++){
            int s = random.nextInt(max)%(max-min+1) + min;
            System.out.println(s);
            String formatss = String.format("%03d", s);
            randomDate = randomDate("2017-01-01", format1);
            String date = "date"+i;
            maps.put(date,formats.format(randomDate)+formatss);
        }
        return maps;
    }

    /**
     * 获取随机日期
     *
     * @param beginDate
     *            起始日期，格式为：yyyy-MM-dd
     * @param endDate
     *            结束日期，格式为：yyyy-MM-dd
     * @return
     */

    private static Date randomDate(String beginDate, String endDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date start = format.parse(beginDate);// 构造开始日期
            Date end = format.parse(endDate);// 构造结束日期
            // getTime()表示返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。
            if (start.getTime() >= end.getTime()) {
                return null;
            }
            long date = random(start.getTime(), end.getTime());

            return new Date(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static long random(long begin, long end) {
        long rtn = begin + (long) (Math.random() * (end - begin));
        // 如果返回的是开始时间和结束时间，则递归调用本函数查找随机值
        if (rtn == begin || rtn == end) {
            return random(begin, end);
        }
        return rtn;
    }
}
