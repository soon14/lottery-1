/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.util;

import com.oruit.app.client.LocalHttpClient;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;

/**
 * 流量充值通用类
 *
 * @author Liuk
 */
public class FlowUtil {

    private static final String UserId = "2665";
    private static final String UserName = "guaguazhuan";
    private static final String Password = "D65EOjlslNyuygnVF8TX";
     /**
     * 流量包接口url
     */
    public static final String FLOW_URL = "http://sdk.le-mian.com/JsonApi.ashx";
   
    /**
     * 流量充值接口
     * @param mobile 手机号
     * @param flow  流量包（20,50,100）
     * @return 
     */
    public static FlowResult flow(String mobile,Integer flow){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
        String stamp = sdf.format(date);
        stamp = stamp.substring(0, 10);
        Map<String, String> authMap  = new LinkedHashMap<>();
        authMap.put("UserId", UserId);
        authMap.put("UserName", UserName);
        authMap.put("Password", SignatureUtil.md5(Password + stamp));
        authMap.put("mobile", mobile);
        authMap.put("flow", flow.toString());
        authMap.put("stamp", stamp);
        String secret = SignatureUtil.generateFlowSign(authMap);
        HttpUriRequest httpUriRequest = RequestBuilder.get()
                .setUri(FLOW_URL)
                .addParameter("UserId", UserId)
                .addParameter("UserName", UserName)
                .addParameter("Password", SignatureUtil.md5(Password + stamp))
                .addParameter("mobile", mobile)
                .addParameter("flow", flow.toString())
                .addParameter("stamp", stamp).addParameter("secret", secret)
                .build();
        System.out.println("-------"+httpUriRequest.getURI());
        FlowResult executeJsonResult = LocalHttpClient.executeJsonResult(httpUriRequest, FlowResult.class);
        System.out.println(executeJsonResult.toString());
        return executeJsonResult;
    }
    public static void main(String[] args) {
//       FlowResult result = FlowUtil.flow("18518510004", 20);
//        System.out.println("------------"+ result);
            Thread tread = new Thread() {
                public void run() {
                    try {
                        for(int i=0; i<1 ;i++){
                            sleep(30000);
                            System.out.println("thread alive.");
                        }
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            };
             tread.start();
        System.out.println("main end");
        
                                     
    }
}
