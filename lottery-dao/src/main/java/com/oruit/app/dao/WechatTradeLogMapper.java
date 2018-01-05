package com.oruit.app.dao;

import com.oruit.app.dao.model.WechatTradeLog;

public interface WechatTradeLogMapper {
    int deleteByPrimaryKey(Integer logId);

    int insert(WechatTradeLog record);

    int insertSelective(WechatTradeLog record);

    WechatTradeLog selectByPrimaryKey(Integer logId);

    int updateByPrimaryKeySelective(WechatTradeLog record);

    int updateByPrimaryKeyWithBLOBs(WechatTradeLog record);

    int updateByPrimaryKey(WechatTradeLog record);
}