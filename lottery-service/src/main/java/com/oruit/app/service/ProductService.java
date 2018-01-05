package com.oruit.app.service;/**
 * Created by wyt on 2017/8/28.
 */

import com.oruit.app.dao.model.AlipayTradeLog;
import com.oruit.app.dao.model.OrderInfo;

/**
 * @author
 * @create 2017-08-28 10:53
 */
public interface ProductService {
    /**
     * 保存支付回调返回信息
     * @param alipayTradeLog
     * @return
     */
     int saveBasePayLog(AlipayTradeLog alipayTradeLog);
    /**
     * 查询订单信息
     * @param orderCode 订单编号
     * @return
     */
     OrderInfo queryOrderinfoByOrderCode(String orderCode);
    /**
     * 修改订单状态
     * @param orderinfo
     * @return
     */
     int updateOrder(OrderInfo orderinfo);
}
