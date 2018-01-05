package com.oruit.app.dao;

import com.oruit.app.dao.model.KuaileshifenOrder;

import java.util.List;
import java.util.Map;

public interface KuaileshifenOrderMapper {
    int deleteByPrimaryKey(String caipiaoOrderSubId);

    int insert(KuaileshifenOrder record);

    int insertSelective(KuaileshifenOrder record);

    KuaileshifenOrder selectByPrimaryKey(String caipiaoOrderSubId);

    int updateByPrimaryKeySelective(KuaileshifenOrder record);

    int updateByPrimaryKey(KuaileshifenOrder record);
    /**
     * 获取的注数
     * @param caipiaoOrderId
     * @return
     */
    List<Map<String,Object>> queryKLSFZhushu(String caipiaoOrderId);
    /**
     * 查询快乐十分的订单
     * @param caipiaoOrderId
     * @return
     */
    List<Map<String,Object>> querKLSFOrder(String caipiaoOrderId);
    /**
     * 查询订单的期号
     * @param map
     * @return
     */
    String querycaipiaoissueno(Map<String,Object> map);

    /**
     * 更新期数
     * @param record
     * @return
     */
    int updateByPrimaryKeySelectiveIssueno(KuaileshifenOrder record);
    /**
     *
     * @param map
     * @return
     */
    Integer queryredpackageid(Map<String,Object> map);
}