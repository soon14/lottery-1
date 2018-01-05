package com.oruit.app.dao;

import com.oruit.app.dao.model.UserStatisticsLog;

import java.math.BigDecimal;

public interface UserStatisticsLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserStatisticsLog record);

    int insertSelective(UserStatisticsLog record);

    UserStatisticsLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserStatisticsLog record);

    int updateByPrimaryKey(UserStatisticsLog record);


}