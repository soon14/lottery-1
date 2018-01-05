package com.oruit.app.dao;

import com.oruit.app.dao.model.AppAuditVerson;

import java.util.Map;

public interface AppAuditVersonMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AppAuditVerson record);

    int insertSelective(AppAuditVerson record);

    AppAuditVerson selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AppAuditVerson record);

    int updateByPrimaryKey(AppAuditVerson record);

    /**
     * 版本审核
     * @param app_ver
     * @return
     */
    Map<String,Object> queryCheckExamins(String appversion);
}