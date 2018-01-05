package com.oruit.app.dao;

import com.oruit.app.dao.model.CashRecord;

import java.util.List;
import java.util.Map;

public interface CashRecordMapper {
    int deleteByPrimaryKey(Integer recordId);

    int insert(CashRecord record);

    int insertSelective(CashRecord record);

    CashRecord selectByPrimaryKey(Integer recordId);


    int updateByPrimaryKeySelective(CashRecord record);

    int updateByPrimaryKey(CashRecord record);

    /**
     * 查询提现的记录
     * @param userId
     * @return
     */
    List<Map<String,Object>> QueryCashRecord(String userId);

    /**
     * 提现到银行卡记录
     * @param params
     * @return
     */
    List<Map<String,Object>> BankCardWithdrawalsList(Map<String,Object> params);

    /**
     * 查询用户的提现记录
     * @param map
     * @return
     */
    CashRecord queryUsercashrecord(Map<String,Object> map);
}