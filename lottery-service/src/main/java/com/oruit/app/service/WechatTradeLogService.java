package com.oruit.app.service;/**
 * Created by wyt on 2017/9/4.
 */

import com.oruit.app.util.web.ResultBean;

import java.util.Map;

/**
 * 微信订单的保存
 * @author
 * @create 2017-09-04 10:44
 */
public interface WechatTradeLogService {
    /**
     * 保存微信订单
     * @param orderId
     * @param wechatPrepayId
     * @param content
     * @return
     */
    Integer SaveWechatOrderLog(String orderId,String wechatPrepayId,String content);
}
