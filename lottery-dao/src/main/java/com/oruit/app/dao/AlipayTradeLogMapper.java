package com.oruit.app.dao;

import com.oruit.app.dao.model.AlipayTradeLog;

public interface AlipayTradeLogMapper {
    int deleteByPrimaryKey(Integer logId);

    int insert(AlipayTradeLog record);

    int insertSelective(AlipayTradeLog record);

    AlipayTradeLog selectByPrimaryKey(Integer logId);

    int updateByPrimaryKeySelective(AlipayTradeLog record);

    int updateByPrimaryKeyWithBLOBs(AlipayTradeLog record);

    int updateByPrimaryKey(AlipayTradeLog record);
}