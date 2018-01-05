package com.oruit.app.service;/**
 * Created by wyt on 2017/9/8.
 */

import com.oruit.app.util.web.ResultBean;

import java.text.ParseException;
import java.util.Map;

/**
 * 快乐十分的下单
 * @author
 * @create 2017-09-08 9:46
 */
public interface KLSFService {

    /**
     * 快乐十分的购彩
     * @param map
     * @return
     * @throws Exception
     */
    ResultBean KLSFPurchase(Map<String,Object> map) throws ParseException;
}


