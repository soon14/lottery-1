package com.oruit.app.test;/**
 * Created by wyt on 2017/10/11.
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author @wyt
 * @create 2017-10-11 16:30
 */
public class sortDate {
    public static long DateCompare(String s1,String s2){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = sdf.parse(s1);
            Date d2 = sdf.parse(s2);
            return ((d1.getTime() - d2.getTime())/(24*3600*1000));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void sortlistTest(){
        List<String> list = new ArrayList<String>();
        List<String> newlist = new ArrayList<String>();
        list.add("2017-1-1");
        list.add("2017-1-8");
        list.add("2017-1-16");
        list.add("2017-1-9");
        list.add("2017-1-8");
        list.add("2017-1-12");
        list.add("2017-1-3");
        list.add("2017-1-11");
        list.add("2017-1-12");
        list.add("2017-1-3");
        list.add("2017-1-7");
        list.add("2017-1-7");
        System.out.println(list);
        for(int i=0; i<list.size(); i++){
            if(!newlist.contains(list.get(i))){
                newlist.add(list.get(i));
            }
        }
        System.out.println(newlist);
        String tmp;
        for(int i=1; i<newlist.size(); i++){
            tmp = newlist.get(i);
            int j=i-1;
            for(; j>=0&&(DateCompare(tmp, newlist.get(j))<0); j--){
                newlist.set(j+1, newlist.get(j));
            }
            newlist.set(j+1, tmp);
        }
        System.out.println(newlist);
    }
    public static void main(String[] args) {
        sortlistTest();
    }
}
