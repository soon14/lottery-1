package com.oruit.app.dao;

import com.oruit.app.dao.model.PushConfig;

import java.util.List;
import java.util.Map;

public interface PushConfigMapper {
    int deleteByPrimaryKey(String pushCode);

    int insert(PushConfig record);

    int insertSelective(PushConfig record);

    PushConfig selectByPrimaryKey(String pushCode);

    int updateByPrimaryKeySelective(PushConfig record);
    int updateByPrimaryKeySelectivePushConfig(PushConfig record);

    int updateByPrimaryKey(PushConfig record);

    /**
     * 推送首页展示
     * @param userid
     * @return
     */
    List<Map<String,Object>> queryPushInfo(String userid);
}