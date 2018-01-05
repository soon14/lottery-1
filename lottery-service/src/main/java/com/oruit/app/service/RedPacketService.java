package com.oruit.app.service;/**
 * Created by wyt on 2017/8/24.
 */

import com.oruit.app.util.web.ResultBean;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Map;

/**
 * 红包相关
 * @author
 * @create 2017-08-24 22:11
 */
public interface RedPacketService {

    ResultBean RedPacket(Map<String,Object> map) throws ParseException;

    /**
     * 领取红包
     * @param map
     * @return
     */
    ResultBean ReceiveRedPacket(Map<String,Object> map);
    /**
     * 领取红包h5
     * @param request
     * @return
     */
    ResultBean ReceiveRedPacketh5(HttpServletRequest request) throws ParseException;
}
