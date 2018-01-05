package com.oruit.app.dao;

import com.oruit.app.dao.model.CaipiaoOrderInfo;

import java.util.Map;

public interface CaipiaoOrderInfoMapper {
    int deleteByPrimaryKey(String caipiaoOrderId);

    int insert(CaipiaoOrderInfo record);

    int insertSelective(CaipiaoOrderInfo record);

    CaipiaoOrderInfo selectByPrimaryKey(String caipiaoOrderId);

    int updateByPrimaryKeySelective(CaipiaoOrderInfo record);

    int updateByPrimaryKey(CaipiaoOrderInfo record);

    /**
     * 查询用户是否购买过彩票 浏览器端
     * @param userId
     * @return
     */
    String queryUseridOrder(String userId);

    /**
     * 查询订单是否存在
     * @param map
     * @return
     */
    String querycaipiaoordercode(Map<String,Object> map);

    /**
     * 查询用户购买了几次
     * @param userid
     * @return
     */
    String Queryordernum(String userid);

    /**
     * 根据订单号查询用户的id
     * @param orderid
     * @return
     */
    Map<String,Object> queryUseridbyOrder(String orderid);

}