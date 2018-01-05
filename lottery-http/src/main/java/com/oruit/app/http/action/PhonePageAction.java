package com.oruit.app.http.action;/**
 * Created by wyt on 2017/9/1.
 */

import com.alibaba.fastjson.JSONObject;
import com.oruit.app.service.BettingRecordService;
import com.oruit.app.service.OrderService;
import com.oruit.app.service.PhonePageService;
import com.oruit.app.util.IOJSONObject;
import com.oruit.app.util.URLStringJSON;
import com.oruit.app.util.web.ResponseUtils;
import com.oruit.app.util.web.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;

/**
 * 手机浏览器相关页面
 * @author
 * @create 2017-09-01 14:51
 */
@Controller
public class PhonePageAction {
    @Autowired
    private PhonePageService phonePageService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private BettingRecordService bettingRecordService;
    /**
     * 手机端注册
     */
    @RequestMapping("PhonePageRegister")
    public void PhonePageRegister(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        ResultBean result = phonePageService.PhonePageRegister(request);
        ResponseUtils.writeResult(request, response, result);
    }

    /**
     * 浏览器下单
     * @param request
     * @param response
     * @throws IOException
     * @throws ParseException
     */
    @RequestMapping("BrowserChoose")
    public void BrowserChoose(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        ResultBean result = phonePageService.BrowserChoose(request);
        ResponseUtils.writeResult(request, response, result);
    }

    /**
     * 浏览器确认订单
     * @param request
     * @param response
     * @throws IOException
     * @throws ParseException
     */
    @RequestMapping("BrowserOrders")
    public void BrowserOrders(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        ResultBean result = orderService.BrowserOrders(request);
        ResponseUtils.writeResult(request, response, result);
    }

    /**
     * 浏览器确认订单
     * @param request
     * @param response
     */
    @RequestMapping("TopWinInfoH5")
    public void TopWinInfoH5(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResultBean result = phonePageService.TopWinInfoH5(request);
        ResponseUtils.writeResult(request, response, result);
    }
    /**
     * 浏览器充值下单
     * @param request
     * @param response
     */
    @RequestMapping("CommitOrderH5")
    public void CommitOrderH5(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResultBean result = orderService.CommitOrderH5(request);
        ResponseUtils.writeResult(request, response, result);
    }
    /**
     * 获取推广的期号
     * @param request
     * @param response
     */
    @RequestMapping("getIssueno")
    public void getIssueno(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResultBean result = phonePageService.getIssueno(request);
        ResponseUtils.writeResult(request, response, result);
    }
    /**
     * H5首页初始化
     * @param request
     * @param response
     */
    @RequestMapping("H5HomeInfo")
    public void H5HomeInfo(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        ResultBean result = phonePageService.H5Guide(request);
        ResponseUtils.writeResult(request, response, result);
    }
    /**
     * H5首页初始化
     * @param request
     * @param response
     */
    @RequestMapping("H5HomeInfoSSQ")
    public void H5HomeInfoSSQ(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ResultBean result = phonePageService.H5GuideSSQ(request);
        ResponseUtils.writeResult(request, response, result);
    }
    /**
     * 投注记录H5
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("BettingrecordH5")
    public void BettingrecordH5(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String decode = URLDecoder.decode(IOJSONObject.getRequestParams(request));
        JSONObject jsonObject = URLStringJSON.StringJSON(decode);
        String infversion = jsonObject.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = bettingRecordService.BettingRecord(jsonObject);
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
     * 投注记录H5
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("BettingRecordDetailsH5")
    public void BettingRecordDetailsH5(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String decode = URLDecoder.decode(IOJSONObject.getRequestParams(request));
        JSONObject jsonObject = URLStringJSON.StringJSON(decode);
        String infversion = jsonObject.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = bettingRecordService.BettingRecordDetails(jsonObject);
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
     * 投注记录H5
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("BangdingH5")
    public void BangdingH5(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String decode = URLDecoder.decode(IOJSONObject.getRequestParams(request));
        JSONObject jsonObject = URLStringJSON.StringJSON(decode);
        String infversion = jsonObject.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = phonePageService.BangdingH5(jsonObject);
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
     * 双色球选号H5
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("SSQPurchaseH5")
    public void SSQPurchaseH5(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userid = request.getParameter("userid");
        String type = request.getParameter("type");
        String bettingmultiple = request.getParameter("bettingmultiple");
        String bluenumber = request.getParameter("bluenumber");
        String rednumber = request.getParameter("rednumber");
        String remoteAddr = request.getRemoteAddr();
        String infversion = request.getParameter("infversion");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userid",userid);
        jsonObject.put("remoteAddr",remoteAddr);
        jsonObject.put("type",type);
        jsonObject.put("bettingmultiple",bettingmultiple);
        jsonObject.put("bluenumber",bluenumber);
        jsonObject.put("rednumber",rednumber);
        System.out.println("----------:"+remoteAddr);
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = orderService.SSQPurchaseH5(jsonObject);
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
    @RequestMapping("chongzhithree")
    public void chongzhithree(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String params = request.getParameter("params");
        orderService.chongzhithree(params);
    }
}
