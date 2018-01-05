/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 时间通用类
 *
 * @author Liuk
 */
public class DateUtil {

    /**
     * 时间规则：
     *
     * <2分钟：刚刚
     *
     * =今天：今天 HH:mm
     *
     * =昨天：昨天 HH:mm
     *
     * =今年 : MM月dd日 HH:mm
     *
     * <今年：yyyy/MM/dd HH:mm
     *
     * FFF @param createTime
     *
     * @param createTime
     * @return
     * @
     * return
     * @throws ParseException
     */
    public static String getTimes(String createTime) {
        try
        {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date now = new Date();
            Date date = df.parse(createTime);
            Calendar calendarNow = Calendar.getInstance();  
            calendarNow.setTime(now);
            Calendar calendarParam = Calendar.getInstance();  
            calendarParam.setTime(date);

            long l = now.getTime() - date.getTime();
            long day = l / (24 * 60 * 60 * 1000);
            long hour = (l / (60 * 60 * 1000) - day * 24);
            long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
            System.out.println("------------" + day + "==============" + createTime);
            if (calendarNow.get(Calendar.YEAR) == calendarParam.get(Calendar.YEAR)
                    && calendarNow.get(Calendar.MONTH) == calendarParam.get(Calendar.MONTH)
                    && calendarNow.get(Calendar.DAY_OF_MONTH) == calendarParam.get(Calendar.DAY_OF_MONTH)
                    && hour == 0 && min <= 2) {
                return "刚刚";
            } else if (calendarNow.get(Calendar.YEAR) == calendarParam.get(Calendar.YEAR)
                    && calendarNow.get(Calendar.MONTH) == calendarParam.get(Calendar.MONTH)
                    && calendarNow.get(Calendar.DAY_OF_MONTH) == calendarParam.get(Calendar.DAY_OF_MONTH)) {
                return createTime.substring(11, 16);
            } else if (calendarNow.get(Calendar.YEAR) == calendarParam.get(Calendar.YEAR)
                    && calendarNow.get(Calendar.MONTH) == calendarParam.get(Calendar.MONTH)
                    && calendarNow.get(Calendar.DAY_OF_MONTH) == calendarParam.get(Calendar.DAY_OF_MONTH)+1) {
                return "昨天 " + createTime.substring(11, 16);
            } else if (calendarNow.get(Calendar.YEAR) == calendarParam.get(Calendar.YEAR)) {
                return createTime.substring(5, 16);
            } else {
                return createTime.substring(0, 16);
            }
        }catch(ParseException ex)
        {
            return createTime;
        }
    }
    
    /**
     * 时间规则：
     * 一天内的显示：HH:mm      
     * 一年内显示：MM-dd     
     * 超过一年：yyyy
     * 
     * @param createTime
     * @return
     * @
     * return
     */
    public static String getShortTimesForShow(String createTime) {
        try
        {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date now = new Date();
            Date date = df.parse(createTime);
            Calendar calendarNow = Calendar.getInstance();  
            calendarNow.setTime(now);
            Calendar calendarParam = Calendar.getInstance();  
            calendarParam.setTime(date);

            long l = now.getTime() - date.getTime();
            long day = l / (24 * 60 * 60 * 1000);
            long hour = (l / (60 * 60 * 1000) - day * 24);
            long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
            System.out.println("------------" + day + "==============" + createTime);
            if (calendarNow.get(Calendar.YEAR) == calendarParam.get(Calendar.YEAR)
                    && calendarNow.get(Calendar.MONTH) == calendarParam.get(Calendar.MONTH)
                    && calendarNow.get(Calendar.DAY_OF_MONTH) == calendarParam.get(Calendar.DAY_OF_MONTH)){
                return createTime.substring(11, 16);
            } else if (calendarNow.get(Calendar.YEAR) == calendarParam.get(Calendar.YEAR)) {
                return createTime.substring(5, 10);
            } else if (calendarNow.get(Calendar.YEAR) > calendarParam.get(Calendar.YEAR)) {
                return createTime.substring(0, 4);
            } else {
                return createTime;
            }
        }catch(ParseException ex)
        {
            return createTime;
        }
    }
    
    /**
     * 时间规则：
     * 一天内的显示：HH:mm      
     * 一年内显示：yyyy-MM-dd     
     * 
     * @param createTime
     * @return
     * @
     * return
     */
    public static String getTimeForArticleList(String createTime) {
        try
        {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date now = new Date();
            Date date = df.parse(createTime);
            Calendar calendarNow = Calendar.getInstance();  
            calendarNow.setTime(now);
            Calendar calendarParam = Calendar.getInstance();  
            calendarParam.setTime(date);

            long l = now.getTime() - date.getTime();
            long day = l / (24 * 60 * 60 * 1000);
            long hour = (l / (60 * 60 * 1000) - day * 24);
            if (calendarNow.get(Calendar.YEAR) == calendarParam.get(Calendar.YEAR)
                    && calendarNow.get(Calendar.MONTH) == calendarParam.get(Calendar.MONTH)
                    && calendarNow.get(Calendar.DAY_OF_MONTH) == calendarParam.get(Calendar.DAY_OF_MONTH)){
                return createTime.substring(11, 16);
            } 
            else
            {
                return createTime.substring(0,10);
            }
        }catch(ParseException ex)
        {
            return createTime;
        }
    }

    @SuppressWarnings("empty-statement")
    public static String getZHDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");;
        return sdf.format(new Date());
    }
    
    /**
     * 格式化日期字符串
     * @param format
     * @return 
     */
    public static String formatDate(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }

    public static String getZHDate(String date) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = df.parse(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return sdf.format(date1);
    }

    /**
     * 获取几天后的时间
     *
     * @param d
     * @param day 几天后
     * @return
     */
    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    /**
     * 获取几小时后时间
     *
     * @param d
     * @param hour 几小时后
     * @return
     */
    public static Date getHourAfter(Date d, int hour) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.HOUR, now.get(Calendar.HOUR) + hour);
        return now.getTime();
    }

    /**
     * 多少秒以后
     *
     * @param d
     * @param minute
     * @return
     */
    public static Date getMinuteAfter(Date d, int minute) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) + minute);
        return now.getTime();
    }

    /**
     * 比较时间大小--比较当前时间和传入时间（HH：mm）大小
     *
     * @param time
     * @return
     */
    public static long compareTimeToNow(String time) {
        try {
            long nowM = System.currentTimeMillis();
            Date date = new Date(nowM);
            SimpleDateFormat sf = new SimpleDateFormat("HH:mm");
            String nowTime = sf.format(date);
            date = sf.parse(nowTime);
            nowM = date.getTime();

            Date comTime = sf.parse(time);
            return nowM - comTime.getTime();
        } catch (Exception ex) {
            Logger.getLogger(DateUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * 比较时间大小--比较是否为同一天
     *
     * @param comDate
     * @return 小于0： 小于当前时间 等于0： 和当前时间相同 大于0：小于当前时间
     */
    public static long compareDayToNow(Date comDate) {
        return compareDay(new Date(),comDate);
    }
    
    /**
     * 比较时间大小--比较是否为同一天
     *
     * @param currentDate
     * @param comDate
     * @return 小于0： 小于当前时间 等于0： 和当前时间相同 大于0：小于当前时间
     */
    public static long compareDay(Date currentDate,Date comDate) {
        try {
            Date date = new Date();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            String nowTime = sf.format(currentDate);
            String comTime = sf.format(comDate);
            if (nowTime.equals(comTime)) {
                return 0;
            } else {
                return date.compareTo(comDate);
            }
        } catch (Exception ex) {
            Logger.getLogger(DateUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * 获取指定时间的longMillis
     *
     * @param time
     * @param fromat
     * @return
     */
    public static long getTimeMillis(String time, String fromat) {
        try {
            SimpleDateFormat sf = new SimpleDateFormat(fromat);
            Date date = sf.parse(time);
            return date.getTime();
        } catch (Exception ex) {
            Logger.getLogger(DateUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    /**
     * 获取今天的开始时间
     * @return 
     */
    public static Date getTodayStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
}
    
    /**
     * 将时间戳改成String中的时间戳
     * @param time
     * @return 
     */
    public static String changeTimestampToDateString(Long time)
    {
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        return format.format(time);
    }
              

    public static void main(String[] args) throws ParseException {
      
        System.out.println("---------------" + getTimes("2017-06-21 17:59:41"));
//        System.out.println("---------------" + formatDatetimeToNormal(1498040371));
//        System.out.println("---------------" + getTimes("2017-06-20 10:20:20"));
//        System.out.println("---------------" + getTimes("2017-04-07 20:20:20"));
//        System.out.println("---------------" + getTimes("2017-03-08 20:20:20"));
//        System.out.println("---------------" + getTimes("2016-03-08 20:20:20"));
    }
}
