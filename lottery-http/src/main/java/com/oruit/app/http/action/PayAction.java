package com.oruit.app.http.action;
import com.oruit.app.dao.model.AlipayTradeLog;
import com.oruit.app.dao.model.OrderInfo;
import com.oruit.app.service.OrderService;
import com.oruit.app.service.ProductService;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 支付回调
 *
 * @author Liuk
 */
@Controller
public class PayAction {

    private static final Logger log = Logger.getLogger(PayAction.class);
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;

    /**
     * 支付宝充值回调
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("chongzhinotify_url")
    public String notifyUrl(HttpServletRequest request) throws UnsupportedEncodingException {
        //获取支付宝POST过来反馈信息
        Integer orderId = 0 ;
        int result = 0;
        Map<String, String> params = new HashMap<>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        // 订单支付金额
        BigDecimal total_fee = new BigDecimal(request.getParameter("total_amount"));

        BigDecimal buyer_pay_amount =new BigDecimal(request.getParameter("receipt_amount"));
        //商户订单号
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
        //支付宝交易号
        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
        log.info("----------支付宝回调-------------------");
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
        //交易状态
        AlipayTradeLog baseAlipayTradelog = new AlipayTradeLog();
        baseAlipayTradelog.setContent("out_trade_no:" + out_trade_no + "--trade_no:" + trade_no + "----:trade_status:"
                + trade_status + "----total_fee:" + total_fee.toString() +"-------buyer_pay_amount:" + buyer_pay_amount.toString());
        baseAlipayTradelog.setCreateTime(new Date());
        baseAlipayTradelog.setOrderId(Integer.parseInt(out_trade_no));
        baseAlipayTradelog.setTradeNo(trade_no);
        productService.saveBasePayLog(baseAlipayTradelog);
        switch (trade_status) {
            case "TRADE_FINISHED":{
                OrderInfo orderinfo = productService.queryOrderinfoByOrderCode(out_trade_no);
                System.out.println("--------------orderinfo---------------:"+orderinfo);
                if (orderinfo == null || "".equals(orderinfo)) {
                    log.info("-----------------支付宝回调，订单不存在！------------");
                    return "fail";
                } else if (orderinfo.getState() == 1) {
                    String amount = orderinfo.getAmount().toString();
                    orderinfo.setPayTime(new Date());
                    orderinfo.setState(Short.valueOf("2"));
                    orderinfo.setUpdateTime(new Date());
                    log.info("-----------------支付宝回调，订单存在---------！------------");
                    result = productService.updateOrder(orderinfo);

                    Integer integer = orderService.updateMoneyAlipay(orderinfo.getUserId(), new BigDecimal(amount));
                    System.out.println("----------------余额是否更新-----------------："+integer);
                    log.info("------------修改订单--------------" + result);
                    if (integer > 0) {
                        return "success";
                    } else {
                        return "fail";
                    }
                } else {
                    return "fail";
                }
            }
            case "TRADE_SUCCESS": {
                OrderInfo orderinfo = productService.queryOrderinfoByOrderCode(out_trade_no);
                System.out.println("--------------成功orderinfo---------------:"+orderinfo);
                if (orderinfo == null) {
                    log.info("-----------------支付宝回调，订单不存在！------------");
                    return "fail";
                } else if (orderinfo.getState() == 1) {
                    String amount = orderinfo.getAmount().toString();
                    log.info("----------444444不相同-------------------");
                    orderinfo.setPayTime(new Date());
                    orderinfo.setState(Short.valueOf("2"));
                    log.info("-----------------支付宝回调，订单存在！------------");
                    result = productService.updateOrder(orderinfo);
                    log.info("------------修改订单--------------" + result);
                    Integer integer = orderService.updateMoneyAlipay(orderinfo.getUserId(), new BigDecimal(amount));
                    System.out.println("----------------余额是否更新-----------------："+integer);
                    if (integer > 0) {
                        return "success";
                    } else {
                        return "fail";
                    }
                } else {
                    return "fail";
                }
            }
            default:
                //验证失败
                return "fail";
        }
    }
    @RequestMapping("h5notify_url")
    public String h5notify_url(HttpServletRequest request) throws Exception {
        //获取支付宝POST过来反馈信息
        Integer orderId = 0 ;
        int result = 0;
        Map<String, String> params = new HashMap<>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        // 订单支付金额
        BigDecimal total_fee = new BigDecimal(request.getParameter("total_amount"));

        BigDecimal buyer_pay_amount =new BigDecimal(request.getParameter("receipt_amount"));
        //商户订单号
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
        //支付宝交易号
        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
        log.info("----------支付宝回调-------------------");
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
        //交易状态
        AlipayTradeLog baseAlipayTradelog = new AlipayTradeLog();
        baseAlipayTradelog.setContent("out_trade_no:" + out_trade_no + "--trade_no:" + trade_no + "----:trade_status:"
                + trade_status + "----total_fee:" + total_fee.toString() +"-------buyer_pay_amount:" + buyer_pay_amount.toString());
        baseAlipayTradelog.setCreateTime(new Date());
        baseAlipayTradelog.setOrderId(orderId);
        baseAlipayTradelog.setTradeNo(trade_no);
        productService.saveBasePayLog(baseAlipayTradelog);
        switch (trade_status) {
            case "TRADE_FINISHED":
            {
                orderService.chongzhithree(out_trade_no);
            }
            case "TRADE_SUCCESS":
            {
                orderService.chongzhithree(out_trade_no);
            }
            default:
                //验证失败
                return "fail";
        }
    }

}
