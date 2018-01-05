package com.oruit.app.ssq;/**
 * Created by wyt on 2017/9/4.
 */

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author
 * @create 2017-09-04 15:00
 */
public class shuagnseqiu {
    /**
     * 双色球单式复式的注数算法
     * @param rn 红球个数
     * @param bn 蓝球个数
     * @return
     */
    public static BigInteger danshizhushu(int rn, int bn) {
        BigInteger tempNum = BigInteger.valueOf(0);
        if (rn < 6)
            return BigInteger.valueOf(0);
        if (bn == 0)
            return BigInteger.valueOf(0);
        if (rn == 6) {
            return BigInteger.valueOf(bn);
        }

        tempNum = BigInteger.valueOf(1);
        for (int i = 7; i <= rn; i++) {
            tempNum = tempNum.multiply(new BigInteger(String.valueOf(i)));
            System.out.println(tempNum);
        }

        for (int i = 2; i <= rn - 6; i++) {
            tempNum = tempNum.divide(new BigInteger(String.valueOf(i)));;
        }
        tempNum = tempNum.multiply(BigInteger.valueOf(bn));
        return tempNum;
    }

    /**
     * 双色球胆拖的注数算法
     * @param hqdm_count  红球胆码
     * @param hqtm_count  红球拖码
     * @param lqtm_count  蓝球
     * @return
     */
    public static Integer dantuozhushu(int hqdm_count,int hqtm_count,int lqtm_count){

            int r=6-hqdm_count;
            int n=hqtm_count;
            int fazs=(jiecheng(n)/(jiecheng(r)*jiecheng(n-r)))*lqtm_count;
           return fazs ;
    }
    public static  Integer jiecheng(int m){
        if(m==1 || m==0)return 1;
        else return m*(jiecheng(m-1)); //递归算:法n!=n*(n-1)!
    }


    /**
     * 快乐十分的投注注数
     * @param n 选着的球的数量
     * @param m 模式  任选一为1  任选二位2 。。。。
     * @return
     */
    public static Integer kuaileshifen(Integer n, Integer m){
        int n1=1, n2=1;
        for (int i=n,j=1; j<=m; n1*=i--,n2*=j++);
        Integer s = n1/n2;
        return n1/n2;
    }


    //快乐十分的期数
    public static String KLSFissueno() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String s1 = format + " 09:00:00";
        String s2 = format + " 08:59:00";
        Date parse2 = simpleDateFormat.parse(s1);
        long time = parse2.getTime();
        long a = System.currentTimeMillis();
        long l = a - time;
        Date parse = simpleDateFormat.parse(s2);
        long time1 = parse.getTime();//9点的时间
        long l2 = time1 - a;
        boolean b = l2 > 0;
        if(b){
            return "1000";
        }
        int s = 0 ;
        for (int i = 0; i < l;) {
            i = i + 60*1000*10;
            s++;
        }
        String format1 = String.format("%02d", s);
        return format1 ;
    }
    //快乐十分的期数
    public static String KLSFissuenotuikuan() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String s1 = format + " 09:00:00";
        Date parse2 = simpleDateFormat.parse(s1);
        long time = parse2.getTime();
        long a = System.currentTimeMillis();
        long l = a - time;
        int s = 0 ;
        for (int i = 0; i < l;) {
            i = i + 60*1000*10;
            s++;
        }
        String format1 = String.format("%02d", s);
        return format1 ;
    }
    //快乐十分的期数
    public static String KLSFissuenoquery() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String s1 = format + " 09:00:00";
        String s2 = format + " 08:59:00";
        Date parse2 = simpleDateFormat.parse(s1);
        long time = parse2.getTime();
        long a = System.currentTimeMillis();
        long l = a - time;
        Date parse = simpleDateFormat.parse(s2);
        long time1 = parse.getTime();//9点的时间
        long l2 = time1 - a;
        boolean b = l2 > 0;
        if(b){
            return "1000";
        }
        int s = 0 ;
        for (int i = 0; i < l;) {
            i = i + 60*1000*10;
            s++;
        }
        s++;
        String format1 = String.format("%02d", s);
        return format1 ;
    }

    //判断快乐十分的期数是否在最后一分钟
    public static String isKLSFissueno() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String s1 = format + " 09:00:00";
        String s2 = format + " 08:59:00";
        Date parse2 = simpleDateFormat.parse(s1);
        long time = parse2.getTime();
        long a = System.currentTimeMillis();//当前的时间戳
        long l = a - time;
        Date parse = simpleDateFormat.parse(s2);
        long time1 = parse.getTime();//9点的时间
        long l2 = time1 - a;
        boolean b = l2 > 0;
        if(b){
            return "1000";
        }
        int s = 0 ;
        for (int i = 0; i < l;) {
            i = i + 60*1000*10;
            s++;
        }
        System.out.println(s);
        int i1 = (s-1) * 60 * 1000 * 10;
        long l1 = (l - i1) / (60 * 1000);
        if(l1 == 9){
            return "false";
        }
        String format1 = String.format("%02d", s);
        return format1 ;
    }
    //快乐十分的期数
    public static String KLSFissuenodate(String ordertime) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String s1 = format + " 09:00:00";
        Date parse2 = simpleDateFormat.parse(s1);
        long time = parse2.getTime();
        long a = simpleDateFormat.parse(ordertime).getTime();
        long l = a - time;
        System.out.println(l);
        int s = 0 ;
        for (int i = 0; i < l;) {
            i = i + 60*1000*10;
            s++;
        }
        String format1 = String.format("%02d", s);
        return format1 ;
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(isKLSFissueno());
        System.out.println(KLSFissuenotuikuan());
      /*  Integer kuaileshifen = kuaileshifen(4, 3);
        System.out.println(kuaileshifen);*/
        /*Calendar nowTime = Calendar.getInstance();
        nowTime.setTime(new Date());
        nowTime.add(Calendar.MINUTE, 5);
        Date time = nowTime.getTime();
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);*/
       /* String s = KLSFissuenoquery();
        String s1 = isKLSFissueno();
        System.out.println(s);
        System.out.println(s1);*/

        //BigInteger danshizhushu = danshizhushu(33, 16);
        //System.out.println(danshizhushu);
      /*  int issueno = 1;
        String date = "20170930081";
        String substring = date.substring(0,8);
        String substring1 = date.substring(9);
        System.out.println(substring);
        System.out.println(substring1);
        getwindate(Integer.parseInt(substring1), substring);*/

        //String klsFissueno = KLSFissueno();
      /*  if("false".equals(klsFissueno)){
            klsFissueno = KLSFissueno();
        }*/
       // System.out.println(klsFissueno);
        /*String s = KLSFissuenodate("2017-10-10 11:13:50");
        System.out.println(s);*/
        /*String issueno = s;
        Map<String, Object> date = getDate(1,issueno);
        System.out.println(date);*/

    }

    /**
     * 获取第三方接口失败时 计算出快乐十分顶部的开奖信息
     * @param issueno
     * @return
     */
    public static Map<String,Object> getDate(Integer type ,String issueno) throws ParseException {
        Map<String,Object> maps = new HashMap<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar nowTime = Calendar.getInstance();
        String format = "";
        format = format1.format(new Date());
        if(type==0){
            nowTime.setTime(format1.parse(format));
            nowTime.add(Calendar.DATE,1);
            Date time = nowTime.getTime();
            format = format1.format(time);
            String s1 = format1.format(new Date()) + " 23:00:00";
            String s2 = format + " 09:00:00";
            maps.put("startdate",s1);
            maps.put("enddate",s2);
            return maps;
        }

        String s1 = format + " 09:00:00";
        Date parse2 = simpleDateFormat.parse(s1);
        nowTime.setTime(parse2);
        nowTime.add(Calendar.MINUTE,(Integer.parseInt(issueno)-1)*10-1);
        Date time = nowTime.getTime();
        String startdate = simpleDateFormat.format(time);
        System.out.println(startdate);
        nowTime.add(Calendar.MINUTE, 10);
        Date time1 = nowTime.getTime();
        String enddate = simpleDateFormat.format(time1);
        System.out.println(enddate);
        maps.put("startdate",startdate);
        maps.put("enddate",enddate);
        return maps;
    }

    /**
     * 快乐十分的开奖日期
     * @param issueno
     * @param date
     * @throws ParseException
     */
    public static String getwindate(int issueno, String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
        Date parse1 = sdf2.parse(date);
        String format = sdf1.format(parse1);
        Date parse = sdf.parse(format + " 09:00:00");
        Date afterDate = new Date(parse.getTime() + issueno * (10*60*1000));
        String format1 = sdf.format(afterDate);
        System.out.println(format1);
        return format1;
    }
}
