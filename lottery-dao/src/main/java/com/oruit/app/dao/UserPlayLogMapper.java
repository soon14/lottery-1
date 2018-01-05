package com.oruit.app.dao;

import com.oruit.app.dao.model.UserPlayLog;

public interface UserPlayLogMapper {
    int deleteByPrimaryKey(Long logId);

    int insert(UserPlayLog record);

    int insertSelective(UserPlayLog record);

    UserPlayLog selectByPrimaryKey(Long logId);

    int updateByPrimaryKeySelective(UserPlayLog record);

    int updateByPrimaryKeyWithBLOBs(UserPlayLog record);

    int updateByPrimaryKey(UserPlayLog record);
}