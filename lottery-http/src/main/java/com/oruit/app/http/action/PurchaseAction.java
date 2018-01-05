package com.oruit.app.http.action;/**
 * Created by wyt on 2017/9/4.
 */

import com.alibaba.fastjson.JSONObject;
import com.oruit.app.dao.model.KuaileshifenOrder;
import com.oruit.app.service.KLSFService;
import com.oruit.app.service.SSQService;
import com.oruit.app.util.web.ResponseUtils;
import com.oruit.app.util.web.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 双色球/快乐十分 购彩
 * @author
 * @create 2017-09-04 11:48
 */
@Controller
public class PurchaseAction {
    @Autowired
    private SSQService ssqService;
    @Autowired
    private KLSFService klsfService;
    @RequestMapping("LotteryChoose")
    public void accountDetails(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = ssqService.SSQPurchase(params);
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
    @RequestMapping("KLSFPurchase")
    public void KLSFPurchase(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result =klsfService.KLSFPurchase (params);
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
