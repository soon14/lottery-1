package com.oruit.app.service;/**
 * Created by wyt on 2017/8/25.
 */

import com.oruit.app.util.web.ResultBean;

import java.util.Map;

/**
 * @author
 * @create 2017-08-25 11:23
 */
public interface AccountService {
    /**
     * 账户明细
     * @param map
     * @return
     */
   ResultBean queryUserAccountStatement(Map<String, Object> map);
}
