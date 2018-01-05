package com.oruit.app.http.action;/**
 * Created by wyt on 2017/8/24.
 */

import com.alibaba.fastjson.JSONObject;
import com.oruit.app.service.PersonalInfoService;
import com.oruit.app.util.web.ResponseUtils;
import com.oruit.app.util.web.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;

/**
 * 个人信息相关
 * @author
 * @create 2017-08-24 9:03
 */
@Controller
public class
PersonalInfoAction {
    @Autowired
    private PersonalInfoService personalInfoService;

    @RequestMapping("CommonSendVCode")
    public void commonSendVCode(HttpServletRequest request, HttpServletResponse response) throws IOException {

        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = personalInfoService.CommonSendVCode(params);
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
    @RequestMapping("CheckVcode")
    public void CheckVcode(HttpServletRequest request, HttpServletResponse response) throws IOException {

        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = personalInfoService.CheckVcode(params);
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

    @RequestMapping("CommonSendVCodeH5")
    public void commonSendVCodeh5(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResultBean result = personalInfoService.CommonSendVCodeh5(request);
        ResponseUtils.writeResult(request, response, result);
    }
    @RequestMapping("UserLogin")
    public void userLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = personalInfoService.UserLogin(params);
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
     * 微信授权登录
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("WxAuthoriorLogin")
    public void wxAuthoriorLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = personalInfoService.WxAuthoriorLogin(params);
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
     * 微信授权登录h5-----不用
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("WxAuthoriorLoginH5")
    public void wxAuthoriorLoginh5(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        ResultBean result = personalInfoService.WxAuthoriorLoginh5(request);
        ResponseUtils.writeResult(request, response, result);
    }
    @RequestMapping("PersonInfo")
    public void personInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = personalInfoService.PersonInfo(params);
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
    @RequestMapping("ModifyPassword")
    public void modifyPassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = personalInfoService.ModifyPassword(params);
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
    @RequestMapping("RealNameAuthentication")
    public void realNameAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = personalInfoService.RealNameAuthen(params);
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
    @RequestMapping("WxBindingMobile")
    public void wxBindingMobile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = personalInfoService.WxBindingMobile(params);
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
     * 绑定手机号H5
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("WxBindingMobileH5")
    public void wxBindingMobileh5(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResultBean result = personalInfoService.WxBindingMobileh5(request);
        ResponseUtils.writeResult(request, response, result);
    }
    @RequestMapping("SeekPassword")
    public void seekPassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = personalInfoService.SeekPassword(params);
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
     * 交接
     * @param request
     * @param response
     * @throws IOException
     * @throws ParseException
     */
    @RequestMapping("WXAuthorizationH5")
    public void wxAuthorizationH5(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {

        ResultBean result = null;
        result = personalInfoService.WXAuthorizationH5(request);

        ResponseUtils.writeResult(request, response, result);
    }
    @RequestMapping("QuickLogin")
    public void QuickLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = personalInfoService.QuickLogin(params);
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
     * 手机号注册后绑定微信
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("MobileBindingWX")
    public void MobileBindingWX(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = personalInfoService.MobileBindingWX(params);
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
     * 手机号注册后绑定支付宝
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("MobileBindingAlipay")
    public void MobileBindingAlipay(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = personalInfoService.MobileBindingAlipay(params);
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
