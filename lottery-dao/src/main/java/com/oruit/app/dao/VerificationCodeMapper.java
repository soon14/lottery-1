package com.oruit.app.dao;

import com.oruit.app.dao.model.VerificationCode;

import java.util.Map;

public interface VerificationCodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VerificationCode record);

    int insertSelective(VerificationCode record);

    VerificationCode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VerificationCode record);

    int updateByPrimaryKey(VerificationCode record);

    VerificationCode selectByMobileAndTimes(String mobile);

    /**
     * 检验验证码
     * @param map
     * @return
     */
    VerificationCode checkVcode(Map<String,Object> map);


}