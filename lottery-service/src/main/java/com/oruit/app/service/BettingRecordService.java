package com.oruit.app.service;/**
 * Created by wyt on 2017/9/9.
 */

import com.oruit.app.util.web.ResultBean;

import java.text.ParseException;
import java.util.Map;

/**
 * 投注记录
 * @author wyt
 * @create 2017-09-09 16:20
 */
public interface BettingRecordService {
    /**
     * 用户的投注记录列表
     * @param map
     * @return
     */
    ResultBean BettingRecord(Map<String,Object> map) throws Exception;

    /**
     * 用户的投注记录详情
     * @param map
     * @return
     */
    ResultBean BettingRecordDetails(Map<String,Object> map) throws Exception;

    /**
     * 保存中奖明细
     * @return
     */
    void savewinmoney();
}
