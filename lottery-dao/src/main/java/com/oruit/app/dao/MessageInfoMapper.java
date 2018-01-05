package com.oruit.app.dao;

import com.oruit.app.dao.model.MessageInfo;

import java.util.List;
import java.util.Map;

public interface MessageInfoMapper {
    int deleteByPrimaryKey(Integer msgId);

    int insert(MessageInfo record);

    int insertSelective(MessageInfo record);

    MessageInfo selectByPrimaryKey(Integer msgId);

    int updateByPrimaryKeySelective(MessageInfo record);

    int updateByPrimaryKey(MessageInfo record);

    /**
     * 查询消息的条数
     * @param userid
     * @return
     */
    Integer queryMessageCount(String userid);

    /**
     * 查询消息的列表
     * @param maps
     * @return
     */
    List<Map<String,Object>> queryMessageList(Map<String,Object> maps);

    /**
     * 一键阅读
     * @param record
     * @return
     */
    Integer updateByMessageStatusAll(MessageInfo record);

}