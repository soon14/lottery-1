package com.oruit.app.dao;

import com.oruit.app.dao.model.D3Order;

import java.util.List;
import java.util.Map;

public interface D3OrderMapper {
    int deleteByPrimaryKey(String caipiaoOrderSubId);

    int insert(D3Order record);

    int insertSelective(D3Order record);

    D3Order selectByPrimaryKey(String caipiaoOrderSubId);

    int updateByPrimaryKeySelective(D3Order record);

    int updateByPrimaryKey(D3Order record);

    /**
     * 查询3d的订单
     * @param caipiaoOrderId
     * @return
     */
    List<Map<String, Object>> queryDThreeOrders(String caipiaoOrderId);
}