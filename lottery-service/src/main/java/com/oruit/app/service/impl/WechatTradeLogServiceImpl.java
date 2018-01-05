package com.oruit.app.service.impl;/**
 * Created by wyt on 2017/9/4.
 */

import com.oruit.app.dao.WechatTradeLogMapper;
import com.oruit.app.dao.model.WechatTradeLog;
import com.oruit.app.service.WechatTradeLogService;
import com.oruit.app.util.web.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * 微信订单状态
 * @author wyt
 * @create 2017-09-04 11:31
 */
@Service
public class WechatTradeLogServiceImpl implements WechatTradeLogService {
    @Autowired
    private WechatTradeLogMapper wechatTradeLogMapper;
    @Override
    public Integer SaveWechatOrderLog(String orderId,String wechatPrepayId,String content) {
        WechatTradeLog wechatTradeLog = new WechatTradeLog();
        wechatTradeLog.setContent(content);
        wechatTradeLog.setCreateTime(new Date());
        wechatTradeLog.setOrderId(orderId);
        wechatTradeLog.setWechatPrepayId(wechatPrepayId);
        int i = wechatTradeLogMapper.insertSelective(wechatTradeLog);

        return i;
    }
}
