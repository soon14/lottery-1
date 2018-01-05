package com.oruit.app.service;

import com.oruit.app.util.web.ResultBean;

import java.text.ParseException;
import java.util.Map;

/**
 * Created by wyt on 2017-11-09.
 */
public interface SendWinNoticeService {
    /**
     * 发送中奖信息
     * @return
     */
    void sendWinNotice() throws ParseException;
}
