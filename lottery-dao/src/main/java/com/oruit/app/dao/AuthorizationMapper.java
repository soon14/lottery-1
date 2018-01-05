package com.oruit.app.dao;

import com.oruit.app.dao.model.Authorization;

public interface AuthorizationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Authorization record);

    int insertSelective(Authorization record);

    Authorization selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Authorization record);


    int updateByPrimaryKey(Authorization record);
    /**
     * 根据uninoid查出openid
     * @param unionId
     * @return
     */
    String queryOpenid(String unionId);

    /**
     *
     * @param unionId
     * @return
     */
    String queryunionid(String unionId);

    /**
     * 更新openid
     * @param record
     * @return
     */
    int updateopenid(Authorization record);

}