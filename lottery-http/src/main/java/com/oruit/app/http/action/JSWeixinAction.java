/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.http.action;

import com.oruit.app.client.XMLConverUtil;
import com.oruit.app.service.OrderService;
import com.oruit.app.service.WechatTradeLogService;
import com.oruit.weixin.api.PayMchAPI;
import com.oruit.weixin.paymch.bean.MchBaseResult;
import com.oruit.weixin.paymch.bean.MchPayNotify;
import com.oruit.weixin.paymch.bean.Unifiedorder;
import com.oruit.weixin.paymch.bean.UnifiedorderResult;
import com.oruit.weixin.util.ExpireSet;
import com.oruit.weixin.util.PayUtil;
import com.oruit.weixin.util.SignatureUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * js微信支付
 *
 * @author Liuk
 */
@Controller
@RequestMapping("/WXpay")
public class JSWeixinAction extends BaseAction {
    @Autowired
    private OrderService orderService;
  @Autowired
    private WechatTradeLogService wechatTradeLogService;
    
    //重复通知过滤  时效60秒
    private static ExpireSet<String> expireSet = new ExpireSet<>(60);
    /**
     * 微信h5快乐十分下单支付回调
     * @param request
     * @param response
     * @throws IOException 
     */
    @RequestMapping("paynotify")
    public void payReturn(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取请求数据
        MchPayNotify payNotify = XMLConverUtil.convertToObject(MchPayNotify.class, request.getInputStream());
        //已处理 去重
        if (expireSet.contains(payNotify.getTransaction_id())) {
            return;
        }
        wechatTradeLogService.SaveWechatOrderLog(payNotify.getOut_trade_no(),payNotify.getTransaction_id(),payNotify.toString());
        log.info("微信支付回调：" + payNotify.toString());
        //签名验证
        //Map<String,Object> map = new HashMap<>();

        if (SignatureUtil.validateAppSignature(payNotify, PartnerKey)) {
            expireSet.add(payNotify.getTransaction_id());
            orderService.chongzhithree(payNotify.getOut_trade_no());
            MchBaseResult baseResult = new MchBaseResult();
            baseResult.setReturn_code("SUCCESS");
            baseResult.setReturn_msg("OK");
            response.getOutputStream().write(XMLConverUtil.convertToXML(baseResult).getBytes());
        } else {
            orderService.updateOrder(payNotify.getAttach(), 3);
            MchBaseResult baseResult = new MchBaseResult();
            baseResult.setReturn_code("FAIL");
            baseResult.setReturn_msg("ERROR");
            response.getOutputStream().write(XMLConverUtil.convertToXML(baseResult).getBytes());
        }
    }
    /**
     *app 微信支付回调
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("apppaynotify")
    public void apppayReturn(HttpServletRequest request, HttpServletResponse response) throws IOException {

        System.out.println("-------------------进入回调---------------------------------");
        //获取请求数据
        MchPayNotify payNotify = XMLConverUtil.convertToObject(MchPayNotify.class, request.getInputStream());
        //已处理 去重
        if (expireSet.contains(payNotify.getTransaction_id())) {
            return;
        }
        wechatTradeLogService.SaveWechatOrderLog(payNotify.getOut_trade_no(),payNotify.getTransaction_id(),payNotify.toString());
        log.info("微信支付回调：" + payNotify.toString());
        //签名验证
        //Map<String,Object> map = new HashMap<>();

        if (SignatureUtil.validateAppSignature(payNotify, PartnerKey)) {
            expireSet.add(payNotify.getTransaction_id());
            orderService.updateOrder(payNotify.getAttach(), 2);
            orderService.updateMoney(payNotify.getOpenid(),new BigDecimal(payNotify.getTotal_fee()));
            MchBaseResult baseResult = new MchBaseResult();
            baseResult.setReturn_code("SUCCESS");
            baseResult.setReturn_msg("OK");
            response.getOutputStream().write(XMLConverUtil.convertToXML(baseResult).getBytes());
        } else {
            orderService.updateOrder(payNotify.getAttach(), 3);
            //orderService.updateFailMoney(payNotify.getOpenid(),new BigDecimal(payNotify.getTotal_fee()));
            MchBaseResult baseResult = new MchBaseResult();
            baseResult.setReturn_code("FAIL");
            baseResult.setReturn_msg("ERROR");
            response.getOutputStream().write(XMLConverUtil.convertToXML(baseResult).getBytes());
        }
    }
}
