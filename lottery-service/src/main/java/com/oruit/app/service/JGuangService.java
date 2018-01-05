package com.oruit.app.service;

import com.oruit.app.util.web.ResultBean;

import java.util.Map;

/**
 * 极光的服务
 * Created by wyt on 2017-11-13.
 */

public interface JGuangService {
    /**
     *极光推送首页信息初始化
     */
    ResultBean JPushHomeInfo(Map<String,Object> params);
    /**
     *保存用户的状态
     */
    ResultBean UpdateJPushSetting(Map<String,Object> params);
    /**
     * 极光推送快乐十分的中奖通知
     */
    ResultBean JPushKLSF(Map<String,Object> params) throws Exception;
    /**
     * 定时器极光推送
     */
    ResultBean JPushTimer(Map<String,Object> params) throws Exception;
    /**
     * 定时器极光推送ssq开奖号码
     */
    ResultBean winSSQNumber(Map<String,Object> params) throws Exception;
    /**
     * 提现推送
     */
    ResultBean JPushtixian(Map<String,Object> params);

    /**
     * 消息列表
     * @param map
     * @return
     */
    ResultBean MessageList(Map<String,Object> map);

    /**
     * 更新消息状态
     * @param map
     * @return
     */
    ResultBean UpdateMessageState(Map<String,Object> map);
    /**
     * 一键阅读
     * @param map
     * @return
     */
    ResultBean UpdateMessageStateAll(Map<String,Object> map);

}
