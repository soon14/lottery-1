package com.oruit.app.dao;

import com.oruit.app.dao.model.UserShare;

public interface UserShareMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserShare record);

    int insertSelective(UserShare record);

    UserShare selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserShare record);

    int updateByPrimaryKey(UserShare record);

    UserShare queryUserid(String userId);
}