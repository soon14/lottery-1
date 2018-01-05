package com.oruit.app.sendRequest; /**
 * Created by wyt on 2017-10-26.
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * @author @wyt
 * @create 2017-10-26 11:07
 */
public class OpenULr {
    private static String url = "http://f.apiplus.net/ssq-1.json";
    //private static String url = "http://www.superzhifu.com:8002/pay_server/pay_query?out_trade_no=test_1505703314";

    /*
        * 利用HttpClient进行post请求的工具类
    */
    public static String doPost(String url, Map<String, String> map, String charset) {
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {
            httpClient = new SSLClient();
            httpPost = new HttpPost(url);
            //设置参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> elem = (Map.Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }
            if (list.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
            }
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
    public static String test() {
        String httpOrgCreateTest = url ;
        Map<String, String> createMap = new HashMap<String, String>();
        String httpOrgCreateTestRtn = doPost(httpOrgCreateTest, createMap, "utf-8");
        System.out.println("result:" + httpOrgCreateTestRtn);
        return httpOrgCreateTestRtn;
    }
    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        //test();
        //发送 GET 请求
       /* String s=sendGet("http://www.superzhifu.com:8002/pay_server/pay_query", "out_trade_no=test_150570331");
        System.out.println(s);*/
       /* String test = test();
        JSONObject jsonObject = JSONObject.parseObject(test);
        JSONArray data = jsonObject.getJSONArray("data");
        for (int i = 0; i <data.size() ; i++) {
            String o = data.get(i).toString();
            JSONObject jsonObject1 = JSONObject.parseObject(o);
            System.out.println(jsonObject1.get("opencode"));
        }*/
       // System.out.println(opencode);
        Map<String,String> map = new HashMap<>();
        String str="615680759@qq|中华人民共和国|中华人民共和国";
        BASE64Encoder base = new BASE64Encoder();
        str = base.encode(str.getBytes("utf-8"));
        System.out.println(str);
        map.put("content",str);
        String s = doPost("http://www.owodream.net/sendmail/", map, "utf-8");
        System.out.println(s);
    }
}
