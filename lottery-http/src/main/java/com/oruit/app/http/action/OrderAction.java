package com.oruit.app.http.action;/**
 * Created by wyt on 2017/8/28.
 */

import com.alibaba.fastjson.JSONObject;
import com.oruit.app.sendRequest.OpenULr;
import com.oruit.app.service.OrderService;
import com.oruit.app.util.web.ConfigUtil;
import com.oruit.app.util.web.ResponseUtils;
import com.oruit.app.util.web.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.Properties;

/**
 * 支付宝微信下单
 * @author
 * @create 2017-08-28 13:54
 */
@Controller
public class OrderAction {
    @Autowired
    private OrderService orderService;

    /**
     * 浏览器端下单快乐十分
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("OrderDetail")
    public void accountDetails(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = orderService.Order(params);
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
     * 微信端下单快乐十分
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("ClientOrder")
    public void ClientOrder(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        ResultBean result = orderService.ClientOrder(request);
        ResponseUtils.writeResult(request, response, result);
    }

    /**
     * app确认订单
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("ConfirmOrder")
    public void ConfirmOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = orderService.ConfirmOrder(params);
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
     * app确认订单充值
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("CommitOrder")
    public void CommitOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = orderService.Order(params);
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
     * h5选号提交
     * @param request
     * @param response
     */
    @RequestMapping("ClientOrderTwo")
    public void ClientOrderTwo(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        ResultBean result = orderService.ClientOrderTwo(request);
        ResponseUtils.writeResult(request, response, result);
    }
    /**
     * h5查询订单
     * @param request
     * @param response
     */
    @RequestMapping("queryThreeOrder")
    @ResponseBody
    public String queryThreeOrder(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        Properties pprVue = ConfigUtil.getPprVue("config.properties");
        String isdeletedb = pprVue.getProperty("paythree");
        String outtradeno = request.getParameter("outtradeno");
        String out_trade_no = "out_trade_no="+outtradeno;
        String s = OpenULr.sendGet(isdeletedb, out_trade_no);
       return s;
    }

    /**
     * 退款
     * @param request
     * @param response
     */
    @RequestMapping("threeInterfaceTuiKuan")
    public void threeInterfaceTuiKuan(HttpServletRequest request, HttpServletResponse response) throws Exception {
        orderService.Ordertuikuan(request);

    }

}
