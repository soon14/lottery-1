package com.oruit.weixin.api;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;

/**
 * 获取微信access_token工具类
 * <一句话功能简述>
 *
 * @author  xlp
 * @version  [V1.00, 2016年10月22日]
 * @see  [相关类/方法]
 * @since V1.00
 */
public class WeiXinAccessTokenUtil
{
    private static String appid = "wxdd7bce23cb41e8f4";
    private static String secret = "1be75681b2f8bdf25442c3ce861ef764";

    /**
     *
     * 获取微信access_token
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static GetAccessTokenRsp getWeiXinAccessToken(String code)
    {
        GetAccessTokenRsp getAccessTokenRsp = new GetAccessTokenRsp();
        try
        {
            String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
            HttpClient httpClient = new HttpClient();
            GetMethod getMethod = new GetMethod(url);
            int execute = httpClient.executeMethod(getMethod);
            System.out.println("execute:"+execute);
            String getResponse = getMethod.getResponseBodyAsString();
            JSONObject jsonObject = JSONObject.parseObject(getResponse);
            System.out.println(jsonObject);
            String access_token = jsonObject.get("access_token").toString();
            String openid = jsonObject.get("openid").toString();
            getAccessTokenRsp.setAccessToken(access_token);
            getAccessTokenRsp.setOpenid(openid);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return getAccessTokenRsp;
    }
    public static JSONObject info(String access_token,String openid)
    {
        GetAccessTokenRsp getAccessTokenRsp = new GetAccessTokenRsp();
        String getResponse = "";
        JSONObject jsonObject = null;
        try
        {
            String url = "https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid="+openid;
            HttpClient httpClient = new HttpClient();
            GetMethod getMethod = new GetMethod(url);
            int execute = httpClient.executeMethod(getMethod);
            System.out.println("execute:"+execute);
            getResponse = getMethod.getResponseBodyAsString();
             jsonObject = JSONObject.parseObject(getResponse);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        //System.out.println(getResponse);
        return jsonObject;
    }

    public static void main(String[] args) {
       // GetAccessTokenRsp weiXinAccessToken = getWeiXinAccessToken("wxa5179dcdd1b4f901", "14b9a6971a000176cd0040f5699ec776");
        //info("0Hh0jKWnXQu7jyRcwcL41dcApLK9BBTR2nBBm-rcY91vmwzaECDM34kmafvIL_M969yAnHIX7wkGlDA1Q53CRQ","oa_yPvz0N-pZTVSE4CC_XUKRV6Ko");
        info("AobfuzNqnLKgLnrKQFkp1t1Xd6PxxAkAolIukIHmnxOqn-pBUwVWjQzjNnkkmSmMbeyzMMyOZrQvqdwTQ0xkoA","oa_yPvwCwaYUTEBT9KKKXEbZiPqM");

    }
}
