package com.oruit.app.http.action;

import com.alibaba.fastjson.JSONObject;
import com.oruit.app.service.JGuangService;
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

/**
 * Created by wyt on 2017-11-14.
 */
@Controller
public class JPushAction {
    @Autowired
    private JGuangService jGuangService;
    @RequestMapping("JPushHomeInfo")
    public void jPushHomeInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = jGuangService.JPushHomeInfo(params);
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
    @RequestMapping("UpdateJPushSetting")
    public void updateJPushSetting(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = jGuangService.UpdateJPushSetting(params);
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
     * 提现推送
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("JPushtixian")
    public void JPushtixian(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userid = request.getParameter("userid");
        String recordid = request.getParameter("recordid");
        String infversion1 = request.getParameter("infversion");
        JSONObject params = new JSONObject();
        params.put("userid",userid);
        params.put("recordid",recordid);
        params.put("infversion",infversion1);
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = jGuangService.JPushtixian(params);
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
     * 快乐十分推送
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("JPushKLSF")
    public void JPushKLSF(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userid = request.getParameter("userid");
        String infversion1 = request.getParameter("infversion");
        System.out.println("-----------------infversion1:"+infversion1);
        System.out.println("-----------------userid:"+userid);
        JSONObject params = new JSONObject();
        params.put("userid",userid);
        params.put("infversion",infversion1);
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = jGuangService.JPushKLSF(params);
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
     * 双色球推送
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("JPushTimer")
    public void JPushTimer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userid = request.getParameter("userid");
        String infversion1 = request.getParameter("infversion");
        JSONObject params = new JSONObject();
        params.put("userid",userid);
        params.put("infversion",infversion1);
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = jGuangService.JPushTimer(params);
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
     * 双色球推送快奖结果
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("winSSQNumber")
    public void winSSQNumber(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String infversion1 = request.getParameter("infversion");
        JSONObject params = new JSONObject();
        params.put("infversion",infversion1);
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = jGuangService.winSSQNumber(params);
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
     * 消息列表
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("MessageList")
    public void MessageList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = jGuangService.MessageList(params);
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
     * 更新消息的状态
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("UpdateMessageState")
    public void UpdateMessageState(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = jGuangService.UpdateMessageState(params);
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
     * 一键阅读
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("UpdateMessageStateAll")
    public void UpdateMessageStateAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = jGuangService.UpdateMessageStateAll(params);
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
}
