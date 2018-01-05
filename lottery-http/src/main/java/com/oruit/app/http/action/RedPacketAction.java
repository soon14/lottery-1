package com.oruit.app.http.action;/**
 * Created by wyt on 2017/8/24.
 */

import com.alibaba.fastjson.JSONObject;
import com.oruit.app.service.RedPacketService;
import com.oruit.app.util.web.ResponseUtils;
import com.oruit.app.util.web.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

/**
 * 红包相关接口
 * @author
 * @create 2017-08-24 22:09
 */
@Controller
public class RedPacketAction {
    @Autowired
    private RedPacketService redPacketService;
    @RequestMapping("redPacket")
    public void redpacket(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = redPacketService.RedPacket(params);
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
     * 领取红包
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("ReceiveRedPacket")
    public void receiveRedPacket(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = redPacketService.ReceiveRedPacket(params);
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
     * 领取红包h5
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("ReceiveRedPacketH5")
    public void receiveRedPacketh5(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        ResultBean result = redPacketService.ReceiveRedPacketh5(request);
        ResponseUtils.writeResult(request, response, result);
    }
}
