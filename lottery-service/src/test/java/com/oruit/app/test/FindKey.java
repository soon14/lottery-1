package com.oruit.app.test;

import com.alibaba.fastjson.JSONObject;

import javax.ws.rs.Encoded;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
*1.先定义一个字符串，以及需要查找的字符串
	String st1="nafjfalsnafjlajnafjlkajnafjlakjfnafjlajfnafnafnafnanfanfanfnafnafnfanfanfnafn";
	String key="naf";
*2.定义一个计数器用于记录次数
*3.判断是否查找到,如果找到就记录
*4.接着查找第二个位置
*5.循环



*/
public class FindKey//查找数组中一个字符串出现的次数

{
    public static void main(String[] args) {


        /*List<String> list = new ArrayList<>();
        list.add("01 05 15 25");
        list.add("01 06 08 12 15 18");
        list.add("01 25");
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0 ; i< list.size();i++){
            stringBuffer.append(list.get(i)+ " ");
        }
        String st1 = stringBuffer.toString();
        String key1="01";
        String key2="15";
        int count1 = getKey1(st1 , key1);
        System.out.print("count1="+count1);
        int count2 = getKey1(st1 , key2);
        System.out.print("count2=" + count2);*/
        /*int characterPosition = getCharacterPosition("sasas/fsdfsdf/sd/as/dfas/df//asdf");
        System.out.println(characterPosition);*/

        String str = "{\"status\":0,\"code_url\":\"http:\\/\\/wxt.ideanin.com\\/?mch_id=903170680668&r=http%3A%2F" +
                "                %2Fpddvideo.com%2Ftext2.php%3Fout_trade_no%3D1499676778%26mer_id%3D10001%2" +
                "            6goods_name%3Dvip%26total_fee%3D100%26callback_url%3Dhttp%3A%2F%2Fwww.baidu" +
                "                .com%26noify_url%3Dhttp%3A%2F%2Fwww.baidu.com%26attach%3D709%26nonce_str%3" +
                "            D1499684308%26sign%3D4d85472d8ca2762714a4a9536b402eb2\"}";
        JSONObject jsonparams = JSONObject.parseObject(str.trim());
        String code_url = jsonparams.get("code_url").toString();
        String encode = URLEncoder.encode(code_url);
        System.out.println(encode);


    }
    public static int getCharacterPosition(String string){
        //这里是获取"/"符号的位置
        Matcher slashMatcher = Pattern.compile("/").matcher(string);
        int mIdx = 0;
        while(slashMatcher.find()) {
            mIdx++;
            //当"/"符号第三次出现的位置
            if(mIdx == 10){
                break;
            }
        }
        return slashMatcher.start();
    }
    public static int getKey1(String st1 , String key1)
    {
        int count = 0;
        int index = 0;
        while((index = st1.indexOf(key1,index))!=-1)
        {
            index = index+key1.length();
            count++;
        }
        return count;
    }
    public static int getKey2(String st1 , String key2)
    {
        int index = 0;
        int count2 = 0;
        while((index=st1.indexOf(key2))!=-1)
        {
            st1 = st1.substring(index+key2.length());
            count2++;
        }
        return count2;
    }

}