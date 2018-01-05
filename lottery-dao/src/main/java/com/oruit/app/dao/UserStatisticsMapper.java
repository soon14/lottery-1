package com.oruit.app.dao;

import com.oruit.app.dao.model.UserStatistics;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface UserStatisticsMapper {
    int deleteByPrimaryKey(String userId);

    int insert(UserStatistics record);

    int insertSelective(UserStatistics record);

    UserStatistics selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(UserStatistics record);

    int updateByPrimaryKey(UserStatistics record);

    int updateBalance(Map<String,Object> map);
    /**
     * 更新充值金额
     * @param userId
     * @param totalAmount
     * @return
     */
    int updateMoney(String userId , BigDecimal totalAmount);

    /**
     * 富豪榜
     * @param map
     * @return
     */
    List<Map<String,Object>> Fuhaobang(Map<String,Object> map);
}