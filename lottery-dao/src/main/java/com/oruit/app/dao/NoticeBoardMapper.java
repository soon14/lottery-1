package com.oruit.app.dao;

import com.oruit.app.dao.model.NoticeBoard;

import java.util.List;
import java.util.Map;

public interface NoticeBoardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NoticeBoard record);

    int insertSelective(NoticeBoard record);

    NoticeBoard selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NoticeBoard record);

    int updateByPrimaryKey(NoticeBoard record);

    /**
     * 查询公告
     * @return
     */
    List<Map<String,Object>> queryNoticeBoard();

    /**
     * 查询第一条
     * @return
     */
    Map<String,Object> queryNoticeBoardlimit();
}