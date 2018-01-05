package com.oruit.app.service;/**
 * Created by wyt on 2017/8/31.
 */

import com.oruit.app.util.web.ResultBean;

import java.text.ParseException;
import java.util.Map;

/**
 * @author
 * @create 2017-08-31 12:47
 */
public interface WinningService {
    /**
     * 开奖信息首页
     * @param map
     * @return
     */
    ResultBean WinHome(Map<String,Object> map);

    /**
     * 插入数据双色球
     * @param map
     * @return
     */
    ResultBean InsertDate(Map<String,Object> map) throws ParseException;
    /**
     * 插入数据快乐十分
     * @param map
     * @return
     */
    ResultBean InsertDateKLSF(Map<String,Object> map);
    /**
     * 开奖历史
     * @param map
     * @return
     */
    ResultBean LotteryInfoList(Map<String,Object> map) throws ParseException;
    /**
     * 开奖详情
     * @param map
     * @return
     */
    ResultBean LotteryDetails(Map<String,Object> map) throws ParseException;


    /**
     * 快乐十分顶部开奖信息
     * @param map
     * @return
     */
    ResultBean ElevenPickFiveWinInfo(Map<String,Object> map) throws ParseException;
    /**
     * 中奖金额
     * @param map
     * @return
     */
    ResultBean WinmoneyList(Map<String,Object> map);

    /**
     * 中奖金额H5
     * @return
     */
    ResultBean WinmoneyListH5();


}
