package com.oruit.app.test;/**
 * Created by wyt on 2017-10-30.
 */

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author @wyt
 * @create 2017-10-30 10:43
 */
public class redpackage {
   public static void hb(double total, int num, double min){
        for(int i=1;i<num;i++){
            double safe_total=(total-(num-i)*min)/(num-i);
            double money=Math.random()*(safe_total-min)+min;
            BigDecimal money_bd=new BigDecimal(money);
            money=money_bd.setScale(2,BigDecimal.ROUND_HALF_UP).intValue();
            total=total-money;
            BigDecimal total_bd=new BigDecimal(total);
            total=total_bd.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
            System.out.println("第"+i+"个红包："+money+",余额为:"+total+"元");
        }
        System.out.println("第"+num+"个红包："+total+",余额为:0元");
    }
    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static void main(String[] args) {
        String s = stampToDate("2017102502000");
        System.out.println(s);
    }
}
