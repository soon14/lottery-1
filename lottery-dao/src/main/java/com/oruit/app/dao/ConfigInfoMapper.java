package com.oruit.app.dao;

import com.oruit.app.dao.model.ConfigInfo;

public interface ConfigInfoMapper {
    int deleteByPrimaryKey(Integer configId);

    int insert(ConfigInfo record);

    int insertSelective(ConfigInfo record);

    ConfigInfo selectByPrimaryKey(Integer configId);

    int updateByPrimaryKeySelective(ConfigInfo record);

    int updateByPrimaryKey(ConfigInfo record);

    /**
     * 查询验证码的开关
     * @param configCode
     * @return
     */
    ConfigInfo selectByConnfigCode(String configCode);
}