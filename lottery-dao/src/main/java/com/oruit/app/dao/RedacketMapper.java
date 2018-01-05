package com.oruit.app.dao;

import com.oruit.app.dao.model.Redacket;

public interface RedacketMapper {
    int deleteByPrimaryKey(Integer redpacketId);

    int insert(Redacket record);

    int insertSelective(Redacket record);

    Redacket selectByPrimaryKey(Integer redpacketId);

    int updateByPrimaryKeySelective(Redacket record);

    int updateByPrimaryKey(Redacket record);
}