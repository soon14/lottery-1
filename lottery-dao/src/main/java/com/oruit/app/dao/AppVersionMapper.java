package com.oruit.app.dao;

import com.oruit.app.dao.model.AppVersion;

import java.util.Map;

public interface AppVersionMapper {
    int deleteByPrimaryKey(Integer vid);

    int insert(AppVersion record);

    int insertSelective(AppVersion record);

    AppVersion selectByPrimaryKey(Integer vid);

    int updateByPrimaryKeySelective(AppVersion record);

    int updateByPrimaryKey(AppVersion record);

    /**
     * 查询版本和类型
     * @param paraMap
     * @return
     */
    Map<String, Object> selectByCodeAndType(Map<String,Object> paraMap);
}