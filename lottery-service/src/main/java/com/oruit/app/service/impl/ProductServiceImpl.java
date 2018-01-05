package com.oruit.app.service.impl;/**
 * Created by wyt on 2017/8/28.
 */

import com.oruit.app.dao.AlipayTradeLogMapper;
import com.oruit.app.dao.OrderDetailMapper;
import com.oruit.app.dao.OrderInfoMapper;
import com.oruit.app.dao.model.AlipayTradeLog;
import com.oruit.app.dao.model.OrderDetail;
import com.oruit.app.dao.model.OrderInfo;
import com.oruit.app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Order;
import java.util.Date;

/**
 * @author wyt
 * @create 2017-08-28 10:53
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private AlipayTradeLogMapper alipayTradeLogMapper;
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Override
    public int saveBasePayLog(AlipayTradeLog alipayTradeLog) {
        return alipayTradeLogMapper.insertSelective(alipayTradeLog);
    }

    @Override
    public OrderInfo queryOrderinfoByOrderCode(String orderCode) {
        return orderInfoMapper.selectByOrderCode(orderCode);
    }
    @Override
    public int updateOrder(OrderInfo orderinfo) {
    return orderInfoMapper.updateByPrimaryKeySelective(orderinfo);

    }
}
