package com.oruit.app.dao;

import com.oruit.app.dao.model.UserPlayPlan;

import java.util.List;
import java.util.Map;

public interface UserPlayPlanMapper {
    int deleteByPrimaryKey(Integer planId);

    int insert(UserPlayPlan record);

    int insertSelective(UserPlayPlan record);

    UserPlayPlan selectByPrimaryKey(Integer planId);

    int updateByPrimaryKeySelective(UserPlayPlan record);

    int updateByPrimaryKey(UserPlayPlan record);

    /**
     * 追号记录
     * @param map
     * @return
     */
    List<Map<String,Object>> QueryAppendRecord(Map<String,Object> map);
    /**
     * 追号记录详情
     * @param map
     * @return
     */
    Map<String,Object> AppendRecordDetails(Map<String,Object> map);
}