/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.util.web;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.oruit.app.logic.util.ConstUtil;
import com.oruit.app.oruitkey.OruitKey;
import com.oruit.weixin.util.JsonUtil;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 *
 * 接口返回的实体类
 *
 * @author Liuk
 */
public class ResultBean {

    private static final SerializerFeature[] features = {SerializerFeature.WriteNullNumberAsZero,
            SerializerFeature.WriteDateUseDateFormat, SerializerFeature.WriteNullStringAsEmpty,
            SerializerFeature.WriteMapNullValue};

    private String code;
    private String msg;
    private String total;
    private String ruit;

    public String getRuit() {
        return ruit;
    }

    public void setRuit(String ruit) {
        this.ruit = ruit;
    }

    /**
     *  data 类型必须为 Map  或 List《Map》
     */
    private Object data;

    public ResultBean() {
        
    }

    public ResultBean(String code, String msg, Object data, String total) {

        this.code = code;
        this.msg = msg;
        this.total = total;
        this.data = data;
        this.ruit = "0";
        Object obj = this.getData();
        if (obj instanceof Map) {
            this.setData(ResponseUtils.toStringMap((Map<String, Object>) obj));
        }
        if (obj instanceof List) {
            this.setData(ResponseUtils.toStringListMap((List<Map<String, Object>>) obj));
        }
        String outString = JSONObject.toJSONString(this,  features);
        //System.out.println("-------------outString------:" + outString);
        Properties pprVue = ConfigUtil.getPprVue("config.properties");
        String isdeletedb = pprVue.getProperty("RETURN_SECRETRESULT");
        String scrert = "02"+OruitKey.encryptKeyReturn(isdeletedb, outString);
        ruit = scrert;
    }
    
     public ResultBean(String code, String msg) {
        this.code = code;
        this.msg = msg;
        this.total = "0";
        this.data =  "";
         this.ruit = "0";
         Object obj = this.getData();
         if (obj instanceof Map) {
             this.setData(ResponseUtils.toStringMap((Map<String, Object>) obj));
         }
         if (obj instanceof List) {
             this.setData(ResponseUtils.toStringListMap((List<Map<String, Object>>) obj));
         }
         String outString = JSONObject.toJSONString(this,  features);
         Properties pprVue = ConfigUtil.getPprVue("config.properties");
         String isdeletedb = pprVue.getProperty("RETURN_SECRETRESULT");
         String scrert = "02"+OruitKey.encryptKeyReturn(isdeletedb, outString);
         ruit = scrert;
    }

   public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
