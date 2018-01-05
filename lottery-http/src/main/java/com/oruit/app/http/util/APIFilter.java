/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.http.util;

import com.alibaba.fastjson.JSONObject;
import com.oruit.app.logic.util.ConstUtil;
import com.oruit.app.oruitkey.OruitKey;
import com.oruit.app.oruitkey.OruitMD5;
import com.oruit.app.util.web.ConfigUtil;
import com.oruit.app.util.web.ResponseUtils;
import com.oruit.app.util.web.ResultBean;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author Liuk
 */
public class APIFilter extends OncePerRequestFilter {

    private static final Logger log = Logger.getLogger(APIFilter.class);

    /**
     * 过滤接口请求，根据不同的请求method ，调整到不同的处理controller中
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 跨域请求
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600"); //设置过期时间
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, client_id, uuid, Authorization");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // 支持HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // 支持HTTP 1.0. response.setHeader("Expires", "0");

        ResultBean result = null;
        String[] uris = request.getRequestURI().split("/");
        String uri = request.getRequestURI();
        // 是否过滤
        boolean doFilter = false;
        if (uri.contains(".")) {
            // css不进行过滤
            doFilter = false;
        }
        String appInterface = uris[uris.length - 1];


        // 请求的URL中有接口方法时候，才进行过滤
        if ("api".equals(appInterface)) {
            doFilter = true;
        }

        if (doFilter) {// 执行过滤
            String params = getRequestParams(request);
            JSONObject jsonparams = JSONObject.parseObject(params);
            jsonparams.put("remoteAddr",request.getRemoteAddr());
            System.out.println(jsonparams+"+++++++++++++++++++++++++");
            //log.info("-------------调用接口，请求参数----------" + params);
            String method = jsonparams.getString("Method");
            String Infversion = jsonparams.getString("infversion");
            String UID = jsonparams.getString("UID");
            String Key = jsonparams.getString("Key");
            if (StringUtils.isBlank(method) || StringUtils.isBlank(Infversion) || StringUtils.isBlank(UID)) {
                result = new ResultBean();
                result.setCode("1100");
                result.setMsg("请求参数异常");
                ResponseUtils.writeResult(request, response, result);
                return;
            }
            Properties pprVue = ConfigUtil.getPprVue("config.properties");
            String isdeletedb = pprVue.getProperty("RETURN_SECRET");
            String md5par = OruitMD5.getMD5UpperCaseStr(dealString(params));
            /*System.out.println("-------:" + OruitKey.encrypt(isdeletedb, md5par));
            System.out.println("3-*-----:" + md5par);
            System.out.println("4-------:" + OruitKey.encrypt(UID, md5par));
            System.out.println("key-------:"+Key);
            System.out.println("key---------:"+OruitKey.encrypt(UID, method));
            System.out.println("key----------:"+OruitKey.encrypt(UID, md5par));*/
            request.setAttribute("inputJsonObject", jsonparams);
            request.getRequestDispatcher(method).forward(request, response);
            String str = Key.substring(0,2);//key截取出来的字符串
            Boolean results = false;
            Key = Key.substring(2);//截取后的key
            if("01".equals(str)){
                results = Key.equals(OruitKey.encrypt(UID, method));
            }
            if("02".equals(str)){
                results = Key.equals(OruitKey.encrypt(isdeletedb, md5par));
            }
            if (results) {
                request.setAttribute("inputJsonObject", jsonparams);
                request.getRequestDispatcher(method).forward(request, response);
            } else {
                result = new ResultBean();
                result.setCode("1101");
                result.setMsg("加密结果异常,无权限");
                ResponseUtils.writeResult(request, response, result);
                return;
            }
        } else {
            // 如果不执行过滤，则继续
            filterChain.doFilter(request, response);
        }
    }

    /**
     * 读接口请求流 ， 并转换成JSONObject
     *
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    public String getRequestParams(HttpServletRequest request) throws UnsupportedEncodingException, IOException {
        StringBuilder builder;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"))) {
            builder = new StringBuilder();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
        }
//        return JSONObject.parseObject(builder.toString());
        return builder.toString();
    }

    public String dealString(String params) {

        String[] pr = params.split(",");
        String ps = "";
        for (int i = 0; i <= pr.length - 1; i++) {
            if (pr[i].contains("Key")) {
                if (i == 0) {
                    pr[i] = "{\"Key\":\"0\"";
                } else if (i == pr.length - 1) {
                    pr[i] = "\"Key\":\"0\"}";
                } else {
                    pr[i] = "\"Key\":\"0\"";
                }
            }
            if (pr.length - i == 1) {
                ps = ps + pr[i].trim();
            } else {
                ps = ps + pr[i] + ",".trim();
            }
        }
        return ps;
    }
}
