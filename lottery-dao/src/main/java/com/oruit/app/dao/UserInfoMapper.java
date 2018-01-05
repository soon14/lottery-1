package com.oruit.app.dao;

import com.oruit.app.dao.model.UserInfo;
import com.oruit.app.util.web.ResultBean;
import com.sun.org.apache.regexp.internal.RE;

import java.util.List;
import java.util.Map;

public interface UserInfoMapper {
    int deleteByPrimaryKey(String userId);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    /**
     * 查询手机号是否存在
     * @param mobile
     * @return
     */
    String CheckMobile(String mobile);

    /**
     * 根据手机号查询出userid
     * @param mobile
     * @return
     */
    String QueryMobileUserid(String mobile);


    /**
     * 用户注册（手机）
     * @param map
     * @return
     */
    Map<String,Object> UserRegister(Map<String,Object> map);

    /**
     * 用户登录
     * @param map
     * @return
     */
    Map<String,Object> UserLogin(Map<String,Object> map);

    /**
     * 判断用户是否是首次登录
     * @param userId
     * @return
     */
    String queryFirst(String userId);

    /**
     * 查询用户信息
     * @param userId
     * @return
     */
    Map<String,Object> QueryUserInfo(String userId);

    Map<String,Object> QueryUserInfoMy(String userId);

    /**
     * 取出用户的密码
     * @param userId
     * @return
     */
    String CheckPassword(String userId);

    /**
     * 七日英雄榜
     * @param
     * @return
     */
    List<Map<String,Object>> HeroesList();

    /**
     * 验证身份证的唯一性
     * @param idCard
     * @return
     */
    String QueryIDCard(String idCard);

    /**
     *查询所有的用户
     * @return
     */
    List<Map<String,Object>> queryUserAll();

}