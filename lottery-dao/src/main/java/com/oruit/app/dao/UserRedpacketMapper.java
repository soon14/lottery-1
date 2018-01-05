package com.oruit.app.dao;

import com.oruit.app.dao.model.UserRedpacket;

import java.util.List;
import java.util.Map;

public interface UserRedpacketMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRedpacket record);

    int insertSelective(UserRedpacket record);

    UserRedpacket selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRedpacket record);

    int updateByPrimaryKey(UserRedpacket record);
    
    /**
     * 查询正常的红包
     * @param map
     * @return
     */
    List<Map<String,Object>> queryUserRedpacketNormal(Map<String, Object> map);
    /**
     * 查询已经过期的红包
     * @param map
     * @return
     */
    List<Map<String,Object>> queryUserRedpacketNoNormal(Map<String, Object> map);

    /**
     * 过期红包的数量
     * @param map
     * @return
     */
    Integer queryUserRedpacketNoNormalnum(Map<String, Object> map);

    /**
     *正常红包的数量
     * @param map
     * @return
     */
    Integer queryUserRedpacketNormalnum(Map<String, Object> map);

    /**
     * 查询是否领取过红包
     * @param userid
     * @return
     */
    String query(String userid);

    /**
     * 查询用户选择的红包作为抵扣
     * @param map
     * @return
     */
    String QueryChoiceRedPackage(Map<String,Object> map);
    /**
     * 查询用户选择的红包作为抵扣
     * @param map
     * @return
     */
    String QueryuserRedPackage(Map<String,Object> map);
}