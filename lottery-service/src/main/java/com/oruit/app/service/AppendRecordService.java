package com.oruit.app.service;/**
 * Created by wyt on 2017/9/12.
 */

import com.oruit.app.util.web.ResultBean;

import java.util.Map;

/**
 * 追号记录
 * @author wyt
 * @create 2017-09-12 20:05
 */
public interface AppendRecordService {

    /**
     * 追号记录列表
     * @param map
     * @return
     */
    ResultBean AppendRecord(Map<String,Object> map);
    /**
     * 追号记录详情
     * @param map
     * @return
     */
    ResultBean AppendRecordDetails(Map<String,Object> map);
    /**
     * 停止追号
     * @param map
     * @return
     */
    ResultBean StopAppend(Map<String,Object> map);
}
