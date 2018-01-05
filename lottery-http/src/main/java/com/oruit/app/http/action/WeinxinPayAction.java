/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.http.action;

import com.oruit.app.util.XMLConverUtil;
import com.oruit.weixin.api.PayMchAPI;
import com.oruit.weixin.paymch.bean.*;
import com.oruit.weixin.util.PayUtil;
import com.oruit.weixin.util.SignatureUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

/**
 * App微信支付回调和微信下单
 *
 * @author Liuk
 */
@Controller
@RequestMapping("pay")
public class WeinxinPayAction {

    private static final Logger log = Logger.getLogger(WeinxinPayAction.class);

    private final String appid = "wxe99c393389decb57";//appid
    private final String mch_id = "1279859301";      	//微信支付商户号
    private final String key = "7ce9b9a25197308edf37e3248368046d";//API密钥

    /**
     * 微信支付回调
     * @param request
     * @param response
     * @throws IOException 
     */
    @RequestMapping("/pay_result")
    public void defaultMethod(HttpServletRequest request, HttpServletResponse response) throws IOException {
       //获取请求数据
        MchPayNotify payNotify = XMLConverUtil.convertToObject(MchPayNotify.class, request.getInputStream());
        //已处理 去重
        //签名验证
        log.error("-------------------微信回调--------------------------------" + payNotify.toString());
        if (SignatureUtil.validateAppSignature(payNotify, key)) {
            log.error("-------------支付回调---微信支付成功----------");
            MchBaseResult baseResult = new MchBaseResult();
            baseResult.setReturn_code("SUCCESS");
            baseResult.setReturn_msg("OK");
            response.getOutputStream().write(XMLConverUtil.convertToXML(baseResult).getBytes());
        } else {
            log.error("-------------支付回调---微信支付失败----------");
            MchBaseResult baseResult = new MchBaseResult();
            baseResult.setReturn_code("FAIL");
            baseResult.setReturn_msg("ERROR");
            response.getOutputStream().write(XMLConverUtil.convertToXML(baseResult).getBytes());
        }
        String mobile = request.getParameter("mobile");
        System.out.println("--------------正确请求到方法--------------------"+mobile);

    }

    /**
     * 微信支付下单
     * @param request
     * @return
     * @throws IOException 
     */
    @RequestMapping("/unifiedorder")
    public MchPayApp unifiedorderMethod(HttpServletRequest request)
            throws IOException {
        log.error("-------------------微信下单--------------------------------");
        Unifiedorder unifiedorder = new Unifiedorder();
        unifiedorder.setAppid(appid);
        unifiedorder.setMch_id(mch_id);
        unifiedorder.setNonce_str(UUID.randomUUID().toString().replace("-", ""));

        unifiedorder.setBody("商品信息1");
        unifiedorder.setOut_trade_no("12345623");
        unifiedorder.setTotal_fee("1");//单位分
        unifiedorder.setSpbill_create_ip(request.getRemoteAddr());//IP
        unifiedorder.setNotify_url("http://localhost:8080/pay/pay_result");//支付回调方法
        unifiedorder.setTrade_type("APP");//JSAPI，NATIVE，APP，WAP

        UnifiedorderResult unifiedorderResult = PayMchAPI.payUnifiedorder(unifiedorder, key);
         log.error("-------------------微信下单--------------------------------"+unifiedorderResult.toString());
        MchPayApp mchPayApp = PayUtil.generateMchAppData(unifiedorderResult.getPrepay_id(), mch_id, appid, key);
        return mchPayApp;
    }
}
