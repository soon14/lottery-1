package com.oruit.app.aliyun;/**
 * Created by wyt on 2017/8/22.
 */
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author
 * @create 2017-08-22 10:52
 */
public class aly {

    public static String test(String type,String caipiaoid){
        /**
         /**
         * 1.query 彩票开奖
         * 2.history 历史开奖
         * 3.class 彩票分类
         */
        String host = "http://jisucpkj.market.alicloudapi.com";
        String path = "/caipiao/"+type+"";
        String method = "GET";
        String appcode = "1cb03b6368664ab28eed3ef02b9e41dc";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("caipiaoid", caipiaoid);
        //querys.put("issueno", "20201710272750");
        querys.put("num", "20");//历史开奖查询条数
        String s="";
        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            s = EntityUtils.toString(response.getEntity());
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  s ;
    }
    public static void main(String[] args) {
        String history = test("class","11");

        System.out.println(history);
    }
}
