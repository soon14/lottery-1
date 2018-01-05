package com.oruit.app.ssq;/**
 * Created by wyt on 2017/9/5.
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author
 * @create 2017-09-05 11:43
 */
public class Issueno {

    /*
    双色球
    获取当前的期数
     */
    public static String achieveNum() throws Exception {
        SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
        String yyyy = new SimpleDateFormat("yyyy").format(new Date());
        int i = Integer.parseInt(yyyy) - 1 ;
        String date1230 = String.valueOf(i) + "1230";
        String s1 = CalendarUtil.lunarToSolar(date1230, false);//当前年份的上一年的12月30号的阳历日期
        Date parse = yyyyMMdd.parse(s1);
        String format = ymd.format(yyyyMMdd.parse(date1230));//开始日期
        String format1 = ymd.format(new Date());//结束日期
        List<String> list = AccountDate.getEveryday(format,format1);
        Calendar rightNow= Calendar.getInstance();
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(parse);
        calendar.add(Calendar.DATE,7);//当前年份的上一年的12月30号的阳历日期加上7天
        String str[]={"7","1","2","3","4","5","6"};//字符串数组
        int s = 0 ;
        for (String result : list) {
            if(ymd.parse(result).getTime() > calendar.getTime().getTime() || ymd.parse(result).getTime()<parse.getTime() ){
                rightNow.setTime(ymd.parse(result));
                int day=rightNow.get(Calendar.DAY_OF_WEEK);//获取时间
                if((str[day-1] == "2" || str[day-1] == "4" || str[day-1] == "7") ){
                    s++;
                }
            }
        }
        s = s + 1 ;
        String s3 = ymd.format(new Date()) + " 19:30:00";
        String statrt = ymd.format(new Date()) + " 19:30:00";
        String end = ymd.format(new Date()) + " 20:02:00";
        long time = ymdhms.parse(s3).getTime();
        rightNow.setTime(new Date());
        int day=rightNow.get(Calendar.DAY_OF_WEEK);//获取时间
       if((str[day-1] == "2" || str[day-1] == "4" || str[day-1] == "7") ){
            if(System.currentTimeMillis() < time){
                s = s - 1 ;
            }
        }
        String s2 = yyyy + s;
        return s2;
    }
    public static String achieveNumssq() throws Exception {
        SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
        String yyyy = new SimpleDateFormat("yyyy").format(new Date());
        int i = Integer.parseInt(yyyy) - 1 ;
        String date1230 = String.valueOf(i) + "1230";
        String s1 = CalendarUtil.lunarToSolar(date1230, false);//当前年份的上一年的12月30号的阳历日期
        Date parse = yyyyMMdd.parse(s1);
        String format = ymd.format(yyyyMMdd.parse(date1230));//开始日期
        String format1 = ymd.format(new Date());//结束日期
        List<String> list = AccountDate.getEveryday(format,format1);
        Calendar rightNow= Calendar.getInstance();
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(parse);
        calendar.add(Calendar.DATE,7);//当前年份的上一年的12月30号的阳历日期加上7天
        String str[]={"7","1","2","3","4","5","6"};//字符串数组
        int s = 0 ;
        for (String result : list) {
            if(ymd.parse(result).getTime() > calendar.getTime().getTime() || ymd.parse(result).getTime()<parse.getTime() ){
                rightNow.setTime(ymd.parse(result));
                int day=rightNow.get(Calendar.DAY_OF_WEEK);//获取时间
                if((str[day-1] == "2" || str[day-1] == "4" || str[day-1] == "7") ){
                    s++;
                }
            }
        }
        s = s + 1 ;
        String s3 = ymd.format(new Date()) + " 19:30:00";
        String statrt = ymd.format(new Date()) + " 19:30:00";
        String end = ymd.format(new Date()) + " 20:02:00";
        long time = ymdhms.parse(s3).getTime();
        long starttime = ymdhms.parse(statrt).getTime();
        long endtime = ymdhms.parse(end).getTime();
        rightNow.setTime(new Date());
        int day=rightNow.get(Calendar.DAY_OF_WEEK);//获取时间
        if((str[day-1] == "2" || str[day-1] == "4" || str[day-1] == "7") ){
            if(System.currentTimeMillis() < time){
                s = s - 1 ;
            }
            if(System.currentTimeMillis() > starttime && System.currentTimeMillis() < endtime){
                return "1000";
            }
        }
        String s2 = yyyy + s;
        return s2;
    }
    public static void main(String[] args) throws Exception {
        System.out.println("------------"+achieveNumssq());
        System.out.println(achieveNum());
        /*SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(ymdhms.format(new Date()));
        System.out.println(ymdhms.parse(ymdhms.format(new Date())).getTime());
        System.out.println(new Date().getTime());
        SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
        String s3 = ymd.format(new Date()) + " 21:30:00";
        System.out.println(s3);*/
    }
}