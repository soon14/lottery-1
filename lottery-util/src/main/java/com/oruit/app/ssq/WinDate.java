package com.oruit.app.ssq;/**
 * Created by wyt on 2017/9/15.
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author @wyt
 * @create 2017-09-15 10:06
 */
public class WinDate {
    /**
     * 双色球开奖日期
     * @return
     */
    public static String date(String ordertime) throws ParseException {
        String lotterydate = "";
        String strs = "21:30:00";
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = simpleDateFormat1.parse(ordertime);
        String format = simpleDateFormat1.format(parse);
        String end = format + " 19:30:00";
        System.out.println(end);
        long endtime = simpleDateFormat.parse(end).getTime();
        String str[]={"7","1","2","3","4","5","6"};//字符串数组
        Calendar rightNow= Calendar.getInstance();
        rightNow.setTime(simpleDateFormat.parse(ordertime));
        int day=rightNow.get(Calendar.DAY_OF_WEEK);//获取时间
        if(str[day-1] == "2" || str[day-1] == "4" || str[day-1] == "7"){

            lotterydate = simpleDateFormat1.format(simpleDateFormat1.parse(ordertime)) +" " + strs;
            if(simpleDateFormat.parse(ordertime).getTime()>=endtime){
                if(str[day-1] == "2"|| str[day-1] == "7"){
                    rightNow.add(Calendar.DAY_OF_MONTH,2);
                    Date time = rightNow.getTime();
                    lotterydate = simpleDateFormat1.format(time) +" " + strs;
                }
                System.out.println("-------------:"+str[day-1]);
                if(str[day-1] == "4"){
                    rightNow.add(Calendar.DAY_OF_MONTH,3);
                    Date time = rightNow.getTime();
                    lotterydate = simpleDateFormat1.format(time) +" " + strs;

                }
            }
            return lotterydate ;
        }else if(str[day-1] == "5"){
            rightNow.add(Calendar.DAY_OF_MONTH,2);
            Date time = rightNow.getTime();
            lotterydate = simpleDateFormat1.format(time) +" " + strs;
            return lotterydate;
        }else{
            rightNow.add(Calendar.DAY_OF_MONTH,1);
            Date time = rightNow.getTime();
            lotterydate = simpleDateFormat1.format(time) +" " + strs;
            return lotterydate;
        }
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(date("2017-11-19 19:30:31"));
    }
}
