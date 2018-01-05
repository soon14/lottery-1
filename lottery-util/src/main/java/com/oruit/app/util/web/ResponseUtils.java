/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.util.web;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 接口返回输出流
 *
 * @author Liuk
 */
public class ResponseUtils {

    private static final SerializerFeature[] features = {SerializerFeature.WriteNullNumberAsZero,
        SerializerFeature.WriteDateUseDateFormat, SerializerFeature.WriteNullStringAsEmpty,
        SerializerFeature.WriteMapNullValue};

    /**
     * 把接口返回的数据，写入到输出流中
     *
     * @param request
     * @param response
     * @param resultBean
     * @throws IOException
     */
    public static void writeResult(HttpServletRequest request, HttpServletResponse response, ResultBean resultBean) throws IOException {
        response.setContentType("text/plain");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        if (resultBean == null) {
            resultBean = new ResultBean("1200", "服务器异常");
        }
        if (resultBean.getData() != null && resultBean.getData() != "") {
            Object obj = resultBean.getData();
            if (obj instanceof Map) {
                resultBean.setData(toStringMap((Map<String, Object>) obj));
            }
            if (obj instanceof List) {
                resultBean.setData(toStringListMap((List<Map<String, Object>>) obj));
            }
        }
        String outString = JSONObject.toJSONString(resultBean,  features);

        out.println(outString);// 返回jsonp格式数据
        out.flush();
        out.close();
    }
    
    
     /**
     * 把接口返回的数据，写入到输出流中
     *
     * @param request
     * @param response
     * @param str
     * @throws IOException
     */
    public static void writeResultStr(HttpServletRequest request, HttpServletResponse response, String str) throws IOException {
        response.setContentType("text/plain");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println(str);// 返回jsonp格式数据
        out.flush();
        out.close();
    }

    /**
     * map 转换 json 全部为字符串
     *
     * @param map
     * @return
     */
    public static Map<String, Object> toStringMap(Map<String, Object> map) {
        if(map == null){
            return new HashMap<>();
        }
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value == null) {
                value = "";
            }
            if (value instanceof Map) {
                map.put(key, toStringMap((Map<String, Object>) value));
            } else if (value instanceof List) {
                List<Map<String, Object>> list = (List<Map<String, Object>>) value;
                if(list.isEmpty()){
                    map.put(key, new ArrayList<>());
                }else{
                    map.put(key, toStringListMap(list));
                }
                
            } else {
                map.put(key, String.valueOf(value));
            }
        }
        return map;
    }

    /**
     * List 转换 json 全部为字符串
     *
     * @param list
     * @return
     */
    public static List<Map<String, Object>> toStringListMap(List<Map<String, Object>> list) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            resultList.add(toStringMap(map));
        }
        return resultList;
    }
}
