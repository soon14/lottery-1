package com.oruit.app.dao;

import com.oruit.app.dao.model.UserAuthorization;

public interface UserAuthorizationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserAuthorization record);

    int insertSelective(UserAuthorization record);

    UserAuthorization selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserAuthorization record);

    int updateByPrimaryKey(UserAuthorization record);

    /**
     * 查询用户书否已经授权
     * @param unionId
     * @return
     */
    String queryUserid(String unionId);

    /**
     * 根据userid 查询出uninoid
     * @param userId
     * @return
     */
    String queryUnionId(String userId);
}