/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.http.util;

import com.alibaba.fastjson.JSONObject;

/**
 *
 * @author Administrator
 */
public class JsonUtil {

    /**
     * 调用接口成功，返回的数据格式
     *
     * @param msg
     * @param totle
     * @param data
     * @return
     */
    public static JSONObject getSuccJSONObject(String msg,String totle, Object data) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("totle", totle);
        jSONObject.put("data", data);
        jSONObject.put("msg", msg);
        jSONObject.put("code", "1000");
        return jSONObject;
    }
    
    
     public static JSONObject getSuccQiandao() {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("msg", "0|已经签到");
        jSONObject.put("code", "2000");
        
        return jSONObject;
    }
     
    public static JSONObject getSuccVcode() {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("msg", "0|手机号重复");
        jSONObject.put("code", "2001");
        return jSONObject;
    }
    
    /**
     * 调用接口失败
     *
     * @param msg
     * @return
     */
    public static JSONObject getErrJSONObject(String msg) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("msg", msg);
        jSONObject.put("data", "");
        jSONObject.put("code", "2000");
        jSONObject.put("totle", "1");
        return jSONObject;
    }
    
    /**
     * 没有数据
     * @return 
     */
     public static JSONObject getNullJSONObject() {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("msg", "1|无数据");
        jSONObject.put("code", "2100");
        jSONObject.put("data", "");
        jSONObject.put("total", "1");
        return jSONObject;
    }
}
