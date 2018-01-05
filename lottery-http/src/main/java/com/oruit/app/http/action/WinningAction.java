package com.oruit.app.http.action;/**
 * Created by wyt on 2017/8/31.
 */

import com.alibaba.fastjson.JSONObject;
import com.oruit.app.dao.KuaileshifenPrizeMapper;
import com.oruit.app.service.WinningService;
import com.oruit.app.util.web.ResponseUtils;
import com.oruit.app.util.web.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.oruit.app.util.Missing.GetMissing;

/**
 * 开奖信息
 * @author
 * @create 2017-08-31 12:46
 */
@Controller
public class WinningAction {
    @Autowired
    private WinningService winningService;
    @Autowired
    private KuaileshifenPrizeMapper kuaileshifenPrizeMapper;

    @RequestMapping("LotteryWinInfo")
    public void seekPassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = winningService.WinHome(params);
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
    @RequestMapping("InsertDate")
    public void InsertDate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = winningService.InsertDate(params);
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
    @RequestMapping("InsertDateKLSF")
    public void InsertDateKLSF(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = winningService.InsertDateKLSF(params);
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
     * 开奖历史
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("LotteryInfoList")
    public void LotteryInfoList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = winningService.LotteryInfoList(params);
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
     * 开奖详情
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("LotteryDetails")
    public void LotteryDetails(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = winningService.LotteryDetails(params);
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
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("winningList")
    public void winningList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                   /* result = winningService.WinmoneyList(params);*/
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
     * 快乐十分顶部开奖信息
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("ElevenPickFiveWinInfo")
    public void ElevenPickFiveWinInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = winningService.ElevenPickFiveWinInfo(params);
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
    @RequestMapping("WinmoneyListH5")
    public void WinmoneyListH5(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResultBean result = winningService.WinmoneyListH5();
        ResponseUtils.writeResult(request, response, result);
    }

    /**
     * 快乐十分的遗漏期数
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("YiLouNum")
    public void YiLouNum(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResultBean result = null;
        List<Map<String, Object>> testklsf = kuaileshifenPrizeMapper.YiLouNum();
        List<String> lists = new ArrayList<>();
        List<String> results = new ArrayList<>();
        for (Map<String, Object> item : testklsf){
            String winnumber = item.get("winnumber").toString();
            lists.add(winnumber);
        }
        for (int i = lists.size()-1 ; i >=0;i--){
            results.add(lists.get(i));
        }
        Map<String, Object> stringObjectMap = GetMissing(results);
        System.out.println(stringObjectMap);
        result = new ResultBean("1000", "0|成功", stringObjectMap, "1");
        ResponseUtils.writeResult(request, response, result);
    }
}
