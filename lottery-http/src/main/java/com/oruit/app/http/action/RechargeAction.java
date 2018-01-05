package com.oruit.app.http.action;/**
 * Created by wyt on 2017/8/26.
 */

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.oruit.app.alipay.config.AlipayConfig;
import com.oruit.app.service.RechargeService;
import com.oruit.app.util.PayUtil;
import com.oruit.app.util.web.ResponseUtils;
import com.oruit.app.util.web.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 充值
 * @author
 * @create 2017-08-26 11:27
 */
@Controller
public class RechargeAction {
    @Autowired
    private RechargeService rechargeService;
    @RequestMapping("GetRechargeProductList")
    public void getRechargeProductList (HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                   // result = rechargeService.GetRechargeProductList(params);
                    result =null;
                    break;
                default:
                    //ResponseUtils.writeResult(request, response, result);
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        ResponseUtils.writeResult(request, response, result);
    }

    /**
     * 支付宝H5支付
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("RechargeH5")
    public void RechargeH5 (HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResultBean result = null;

        String subject1 = request.getParameter("subject");// 订单名称，必填
        String productid = request.getParameter("productid");// 商品id，必填

        // 商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = PayUtil.getTradeNo();
        // 订单名称，必填
        String subject = "个人测试";
        System.out.println(subject);
        // 付款金额，必填
        String total_amount="0.01";
        // 商品描述，可空
        String body = "";
        // 超时时间 可空
        String timeout_express="2m";
        // 销售产品码 必填
        String product_code="QUICK_WAP_PAY";
        /**********************/
        // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
        //调用RSA签名方式
        AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY,AlipayConfig.SIGNTYPE);
        AlipayTradeWapPayRequest alipay_request=new AlipayTradeWapPayRequest();

        // 封装请求支付信息
        AlipayTradeWapPayModel model=new AlipayTradeWapPayModel();
        model.setOutTradeNo(out_trade_no);
        model.setSubject(subject);
        model.setTotalAmount(total_amount);
        model.setBody(body);
        model.setTimeoutExpress(timeout_express);
        model.setProductCode(product_code);
        alipay_request.setBizModel(model);
        // 设置异步通知地址
        alipay_request.setNotifyUrl(AlipayConfig.notify_url);
        // 设置同步地址
        alipay_request.setReturnUrl(AlipayConfig.return_url);
        // form表单生产
        String form = null;
        try {
            // 调用SDK生成表单
            form = client.pageExecute(alipay_request).getBody();
            response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
            response.getWriter().write(form);//直接将完整的表单html输出到页面
            response.getWriter().println(form);//直接将完整的表单html输出到页面
            response.getWriter().flush();
            response.getWriter().close();
            System.out.println("-----------");
            System.out.println(form);
            System.out.println("---------------");
        } catch (AlipayApiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
