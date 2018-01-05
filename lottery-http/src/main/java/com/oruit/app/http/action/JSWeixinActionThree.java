/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.http.action;

import com.alibaba.fastjson.JSONObject;
import com.oruit.app.client.XMLConverUtil;
import com.oruit.app.dao.model.WechatTradeLog;
import com.oruit.app.service.OrderService;
import com.oruit.app.service.PhonePageService;
import com.oruit.app.service.WechatTradeLogService;
import com.oruit.weixin.paymch.bean.MchBaseResult;
import com.oruit.weixin.paymch.bean.MchPayNotify;
import com.oruit.weixin.paymch.bean.MchPayNotifyThree;
import com.oruit.weixin.util.ExpireSet;
import com.oruit.weixin.util.SignatureUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

/**
 * js微信支付
 *
 * @author Liuk
 */
@Controller
@RequestMapping("/WXpayThree")
public class JSWeixinActionThree extends BaseAction {
  @Autowired
    private WechatTradeLogService wechatTradeLogService;
  @Autowired
  private OrderService orderService;
    
    //重复通知过滤  时效60秒
    private static ExpireSet<String> expireSet = new ExpireSet<>(60);
    /**
     * 微信h5快乐十分下单支付回调
     * @param request
     * @param response
     * @throws IOException 
     */
    @RequestMapping("paynotify")
    @ResponseBody
    public String payReturn(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String queryString = request.getQueryString();
        String out_trade_no  = request.getParameter("out_trade_no");
        String result  = request.getParameter("result");
        String out_transaction_id = request.getParameter("out_transaction_id");
        System.out.println("-------queryString---:"+queryString);
        System.out.println("-------out_trade_no---:"+out_trade_no);
        if("0".equals(result)){
            //保存支付的日志
            wechatTradeLogService.SaveWechatOrderLog(out_trade_no,out_transaction_id,queryString);
            //保存账户明细
            orderService.chongzhithree(out_trade_no);
            String replace = "success";
            return replace;
        }
        return "fail";
    }
    @RequestMapping("paynotifytest")
    @ResponseBody
    public String payReturns(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "failthisistring";
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
            System.out.println("---------------------------"+builder.toString());
        }
//        return JSONObject.parseObject(builder.toString());
        return builder.toString();
    }

}
