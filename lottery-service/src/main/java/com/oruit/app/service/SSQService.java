package com.oruit.app.service;/**
 * Created by wyt on 2017/9/4.
 */

import com.oruit.app.util.web.ResultBean;

import java.text.ParseException;
import java.util.Map;

/**
 * 双色球购彩
 * @author
 * @create 2017-09-04 14:08
 */
public interface SSQService {
    /**
     * 双色球的购彩
     * @param map
     * @return
     * @throws Exception
     */
    ResultBean SSQPurchase(Map<String,Object> map) throws Exception;


    /**
     * 遗漏期数
     * @return
     */
   /* ResultBean yilou();*/
}
