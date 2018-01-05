package com.oruit.app.service;/**
 * Created by wyt on 2017/8/26.
 */

import com.oruit.app.util.web.ResultBean;

import java.util.Map;

/**
 * @author
 * @create 2017-08-26 11:35
 */
public interface RechargeService {
    /**
     * 充值列表金额
     * @param map
     * @return
     */
    ResultBean GetRechargeProductList(Map<String,Object> map);
}
