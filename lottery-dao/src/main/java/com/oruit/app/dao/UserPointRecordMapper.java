package com.oruit.app.dao;

import com.oruit.app.dao.model.UserPointRecord;

import java.util.List;
import java.util.Map;

public interface UserPointRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserPointRecord record);

    int insertSelective(UserPointRecord record);

    UserPointRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserPointRecord record);

    int updateByPrimaryKey(UserPointRecord record);

    /**
     * 积分兑换列表
     * @param map
     * @return
     */
    List<Map<String,Object>> DuiHuanChangeList(Map<String,Object> map);
}