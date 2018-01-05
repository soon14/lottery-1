package com.oruit.app.http.action;

import com.alibaba.fastjson.JSONObject;
import com.oruit.app.service.MakeMoneyService;
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
 * Created by wyt on 2017-11-23.
 */
@Controller
public class MakeMoneyAction {

    @Autowired
    private MakeMoneyService makeMoneyService;

    /**
     * 分享首页
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("ShareHomeInfo")
    public void ShareHomeInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String decode = URLDecoder.decode(IOJSONObject.getRequestParams(request));
        JSONObject jsonObject = URLStringJSON.StringJSON(decode);
        String infversion = jsonObject.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = makeMoneyService.ShareHomeInfo(jsonObject);
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
     * 分享到朋友圈
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("ShareCircleOfFriends")
    public void ShareCircleOfFriends(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = makeMoneyService.ShareCircleOfFriends(params);
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
     * 分享到朋友圈
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("MakeMoneyHomeInfo")
    public void MakeMoneyHomeInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    //result = makeMoneyService.MakeMoneyHomeInfo(params);
                    result = null;
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
     * 积分兑换
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("JiFenChange")
    public void JiFenChange(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = makeMoneyService.JiFenChange(params);
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
     * 积分兑换首页
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("JiFenChangeHome")
    public void JiFenChangeHome(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = makeMoneyService.JiFenChangeHome(params);
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
     * 积分兑换列表
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("DuiHuanChangeList")
    public void DuiHuanChangeList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = makeMoneyService.DuiHuanChangeList(params);
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
     * 赚钱首页
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("JIFENHome")
    public void JIFENHome(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String decode = URLDecoder.decode(IOJSONObject.getRequestParams(request));
        JSONObject jsonObject = URLStringJSON.StringJSON(decode);
        String infversion = jsonObject.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    //result = makeMoneyService.JIFENHome(jsonObject);
                    result = null;
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
