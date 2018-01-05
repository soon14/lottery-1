package com.oruit.app.dao;

import com.oruit.app.dao.model.ShuangseqiuPrizeItem;

import java.util.List;
import java.util.Map;

public interface ShuangseqiuPrizeItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShuangseqiuPrizeItem record);

    int insertSelective(ShuangseqiuPrizeItem record);

    ShuangseqiuPrizeItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShuangseqiuPrizeItem record);

    int updateByPrimaryKey(ShuangseqiuPrizeItem record);
    /**
     * 双色球开奖结果
     * @param
     * @return
     */
    List<Map<String,Object>> SSQDetailsResult(Integer openid);
}