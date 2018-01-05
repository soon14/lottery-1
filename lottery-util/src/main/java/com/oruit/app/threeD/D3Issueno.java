package com.oruit.app.threeD;

import com.oruit.app.ssq.CalendarUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wyt on 2017-11-03.
 */
public class D3Issueno {
    public static void main(String[] args) throws Exception {
        String issueno = Issueno();
    }

    /**
     * 获取当前的期号
     * @return
     * @throws Exception
     */
    public static String getIssueno() throws Exception {
        SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String yyyy = new SimpleDateFormat("yyyy").format(new Date());
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat yMd = new SimpleDateFormat("yyyy-MM-dd");
        String format2 = yMd.format(new Date());
        String s = format2 + " 19:30:30";//每天的截止时间
        String ss = format2 + " 20:30:30";//每天的开始时间
        Date parse2 = ymdhms.parse(s);
        Date parse3 = ymdhms.parse(ss);
        long time4 = parse2.getTime();//每天的截止时间时间戳
        long time5 = parse3.getTime();//每天的开始时间时间戳
        int i = Integer.parseInt(yyyy) - 1 ;
        String date1230 = String.valueOf(i) + "1230";
        String s1 = CalendarUtil.lunarToSolar(date1230, false);//当前年份的上一年的12月30号的阳历日期
        Date parse1 = yyyyMMdd.parse(s1);
        String format1 = yMd.format(parse1);
        s1 = format1 + " 00:00:00";
        Date parse = ymdhms.parse(s1);//12月30号的日期
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(parse);
        calendar.add(calendar.DATE,7);//当前年份的上一年的12月30号的阳历日期加上7天
        Date time = calendar.getTime();
        String format = ymdhms.format(time);
        //获取系统时间
        Date date =new Date();
        long time1 = date.getTime();//当前日期的时间戳
        long time2 = parse.getTime();//12月30号的日期的时间戳
        long time3 = time.getTime();//当前年份的上一年的12月30号的阳历日期加上7天的时间戳
        //%tj表示一年中的第几天
        boolean a = time1 < time2;//当前日期的时间 < 12月30号的日期的时间戳
        boolean b = time1 >= time2;//当前日期的时间 》 12月30号的日期的时间戳
        boolean c = time1 <= time3;//当前日期的时间戳 《 当前年份的上一年的12月30号的阳历日期加上7天的时间戳
        boolean b1 = time1 > time4;// 当前日期的时间 > //每天的截止时间时间戳 日期加1
        String strDate =String.format("%tj",date);
        int i1 = Integer.parseInt(strDate);
        if(b&&c){
            strDate =String.format("%tj",parse);
        }else if(a){
            strDate =String.format("%tj",time1);
        } else {
            int i2 = i1 - 7;
            if(b1){
                i2 = i2 +1 ;
            }
            strDate = String.valueOf(i2);
        }
        //输出时间
        System.out.println(strDate);
        return strDate;
    }

    /**
     * 真实的期号
     * @return
     * @throws Exception
     */
    public static String Issueno() throws Exception {
        SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String yyyy = new SimpleDateFormat("yyyy").format(new Date());
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat yMd = new SimpleDateFormat("yyyy-MM-dd");
        String format2 = yMd.format(new Date());
        String s = format2 + " 19:30:30";//每天的截止时间
        String ss = format2 + " 20:30:30";//每天的开始时间
        Date parse2 = ymdhms.parse(s);
        Date parse3 = ymdhms.parse(ss);
        long time4 = parse2.getTime();//每天的截止时间时间戳
        long time5 = parse3.getTime();//每天的开始时间时间戳
        int i = Integer.parseInt(yyyy) - 1 ;
        String date1230 = String.valueOf(i) + "1230";
        String s1 = CalendarUtil.lunarToSolar(date1230, false);//当前年份的上一年的12月30号的阳历日期
        Date parse1 = yyyyMMdd.parse(s1);
        String format1 = yMd.format(parse1);
        s1 = format1 + " 00:00:00";
        Date parse = ymdhms.parse(s1);//12月30号的日期
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(parse);
        calendar.add(calendar.DATE,7);//当前年份的上一年的12月30号的阳历日期加上7天
        Date time = calendar.getTime();
        String format = ymdhms.format(time);
        //获取系统时间
        Date date =new Date();
        long time1 = date.getTime();//当前日期的时间戳
        long time2 = parse.getTime();//12月30号的日期的时间戳
        long time3 = time.getTime();//当前年份的上一年的12月30号的阳历日期加上7天的时间戳
        //%tj表示一年中的第几天
        boolean a = time1 < time2;//当前日期的时间 < 12月30号的日期的时间戳
        boolean b = time1 >= time2;//当前日期的时间 》 12月30号的日期的时间戳
        boolean c = time1 <= time3;//当前日期的时间戳 《 当前年份的上一年的12月30号的阳历日期加上7天的时间戳
        boolean b1 = time1 > time4;// 当前日期的时间 > //每天的截止时间时间戳 日期加1
        String strDate =String.format("%tj",date);
        int i1 = Integer.parseInt(strDate);
        if(b&&c){
            strDate =String.format("%tj",parse);
        }else if(a){
            strDate =String.format("%tj",time1);
        } else {
            strDate = String.valueOf("1000");
        }
        //输出时间
        System.out.println(strDate);
        return strDate;
    }
}
