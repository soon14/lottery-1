package com.oruit.app.dao;

import com.oruit.app.dao.model.CaiPiao;

import java.util.List;
import java.util.Map;

public interface CaiPiaoMapper {
    int deleteByPrimaryKey(Integer caipiaoId);

    int insert(CaiPiao record);

    int insertSelective(CaiPiao record);

    CaiPiao selectByPrimaryKey(Integer caipiaoId);

    int updateByPrimaryKeySelective(CaiPiao record);

    int updateByPrimaryKey(CaiPiao record);
    /**
     * 查询彩种
     * @return
     */
    List<Map<String,Object>> queryLotteryCatalog();
}