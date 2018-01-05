package com.oruit.app.dao;

import com.oruit.app.dao.model.PushMessage;

public interface PushMessageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PushMessage record);

    int insertSelective(PushMessage record);

    PushMessage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PushMessage record);

    int updateByPrimaryKey(PushMessage record);
}