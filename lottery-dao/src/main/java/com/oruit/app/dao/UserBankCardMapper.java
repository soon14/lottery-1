package com.oruit.app.dao;

import com.oruit.app.dao.model.UserBankCard;

import java.util.List;
import java.util.Map;

public interface UserBankCardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserBankCard record);

    int insertSelective(UserBankCard record);

    UserBankCard selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserBankCard record);

    int updateByPrimaryKey(UserBankCard record);

    /**
     * 查询卡号
     * @param userid
     * @return
     */
    String queryCard(String userid);
    /**
     * 查询卡号是否存在
     * @param params
     * @return
     */
    String queryisCard(Map<String ,Object> params);


}