package com.oruit.app.dao;

import com.oruit.app.dao.model.KuaileshifenOrder;
import com.oruit.app.dao.model.ShuangseqiuOrder;

import java.util.List;
import java.util.Map;

public interface ShuangseqiuOrderMapper {
    int deleteByPrimaryKey(String caipiaoOrderSubId);

    int insert(ShuangseqiuOrder record);

    int insertSelective(ShuangseqiuOrder record);

    ShuangseqiuOrder selectByPrimaryKey(String caipiaoOrderSubId);

    int updateByPrimaryKeySelective(ShuangseqiuOrder record);

    int updateByPrimaryKey(ShuangseqiuOrder record);

    /**
     * 获取双色球的注数
     * @param caipiaoOrderId
     * @return
     */
    List<Map<String,Object>> querySSQZhushu(String caipiaoOrderId);

    /**
     * 查询双色球的订单
     * @param caipiaoOrderId
     * @return
     */
    List<Map<String,Object>> querySSQOrder(String caipiaoOrderId);
    /**
     * 查询双色球的订单H5
     * @param caipiaoOrderId
     * @return
     */
    Map<String,Object> querySSQOrderH5(String caipiaoOrderId);
    /**
     * 更新期数
     * @param record
     * @return
     */
    int updateByPrimaryKeySelectiveIssuenossq(ShuangseqiuOrder record);
    /**
     *
     * @param map
     * @return
     */
    Integer queryredpackageidssq(Map<String,Object> map);
}