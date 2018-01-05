package com.oruit.app.dao;

import com.oruit.app.dao.model.UserAccountStatement;
import com.oruit.app.dao.model.UserAccountStatementAccount;

import java.util.List;
import java.util.Map;

public interface UserAccountStatementMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserAccountStatement record);

    int insertSelective(UserAccountStatement record);

    UserAccountStatement selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserAccountStatement record);

    int updateByPrimaryKey(UserAccountStatement record);

    /**
     * 账户明细
     * @param map
     * @return
     */
    List<Map<String,Object>> queryUserAccountStatement(Map<String, Object> map);
    /**
     * 账户明细轮播
     * @param map
     * @return
     */
    List<Map<String,Object>> queryUserAccountStatementBulletinBoard(Map<String, Object> map);

    /**
     * 查询表是否存在
     * @param tableName
     * @return
     */
    Integer existTable(String tableName);

    /**
     * 插入数据
     * @param record
     * @return
     */
    int insertSelectiveAccount(UserAccountStatementAccount record);
}