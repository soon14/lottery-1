package com.oruit.app.dao;

import com.oruit.app.dao.model.UserForbidden;

public interface UserForbiddenMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserForbidden record);

    int insertSelective(UserForbidden record);

    UserForbidden selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserForbidden record);

    int updateByPrimaryKey(UserForbidden record);

    /**
     * 查询用户是否被禁止登录
     * @param user_id
     * @return
     */
    String queryUserid(String user_id);

    /**
     * 查询用户是否可以购彩
     * @param userId
     * @return
     */
    String QueryUserForbidden(String userId);

    /**
     * 查询用户的状态
     * @param userId
     * @return
     */
    String QueryUserForbiddenState(String userId);
}