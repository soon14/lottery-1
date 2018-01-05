package com.oruit.app.dao;

import com.oruit.app.dao.model.OrderInfo;
import com.oruit.app.util.web.ResultBean;

import java.util.Map;

public interface OrderInfoMapper {
    int deleteByPrimaryKey(Integer orderId);

    int insert(OrderInfo record);

    int insertSelective(OrderInfo record);

    OrderInfo selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);


    OrderInfo selectByOrderCode(String ordercode);
    /**
     * 修改订单状态
     * @param map
     * @return
     */
    Integer updateOrder(Map<String,Object> map);

    /**
     * 查询图书的订单
     * @param map
     * @return
     */
    Map<String,Object> QueryBookOrder(Map<String,Object> map);
}