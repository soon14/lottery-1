package com.oruit.app.http.action;/**
 * Created by wyt on 2017/9/9.
 */

import com.alibaba.fastjson.JSONObject;
import com.oruit.app.service.BettingRecordService;
import com.oruit.app.util.web.ResponseUtils;
import com.oruit.app.util.web.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 投注记录
 * @author @wyt
 * @create 2017-09-09 17:04
 */
@Controller
public class BettingRecordAction {
    @Autowired
    private BettingRecordService bettingRecordService;

    @RequestMapping("BettingRecord")
    public void BettingRecord(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = bettingRecordService.BettingRecord(params);
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

    @RequestMapping("BettingRecordDetails")
    public void BettingRecordDetails(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = bettingRecordService.BettingRecordDetails(params);
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
     * 保存投注详情
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("savewinmoney")
    public void savewinmoney(HttpServletRequest request, HttpServletResponse response) throws IOException {
        bettingRecordService.savewinmoney();
    }
}
