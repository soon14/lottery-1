/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.sina.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oruit.app.util.web.RequestUtils;
import java.io.IOException;
import java.util.Calendar;

/**
 *
 * @author hanfeng
 */
public class ShortUrlUtil {

    public final static String SHORTURL = "https://api.weibo.com/2/short_url/shorten.json?source=4187365176&url_long=";
    
    public static void main(String[] args) throws Exception, IOException {
//        System.out.print(getShortUrl("http://www.baidu.com"));
//Calendar calendar = Calendar.getInstance();
//                int weekDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;
//                System.out.print("=====" + weekDay);
System.out.println(Float.valueOf("67073") / Float.valueOf("1000"));
    }
    
    public static String getShortUrl(String url_long)
    {
        String url = SHORTURL + url_long;
        try
        {
            JSONObject jsonObject = RequestUtils.readJsonObjectFromUrl(url);
            JSONArray jsonArr = JSONArray.parseArray(jsonObject.getString("urls"));
            JSONObject dealJson = jsonArr.getJSONObject(0);
            if(dealJson.getBooleanValue("result"))
            {
                return dealJson.getString("url_short");
            }
            else
            {
                return "";
            }
        }
        catch(Exception ex)
        {
            return ""; // 抛异常时返回空
        }
    }
}
