package com.oruit.app.service;

import com.oruit.app.util.web.ResultBean;

import java.text.ParseException;
import java.util.Map;

/**
 * 师徒，分享，积分
 * Created by wyt on 2017-11-22.
 */
public interface MakeMoneyService {

    /**
     * 我的徒弟
     * @param params
     * @return
     */
    ResultBean MyApprentice(Map<String,Object> params);

    /**
     * 分享首页
     * @param params
     * @return
     */
    ResultBean ShareHomeInfo(Map<String,Object> params);

    /**
     * 分享到朋友圈
     * @param params
     * @return
     */
    ResultBean ShareCircleOfFriends(Map<String,Object> params);

    /**
     * 积分首页
     * @param params
     * @return
     */
    ResultBean JIFENHome(Map<String,Object> params) throws ParseException;

    /**
     * 积分兑换首页
     * @param params
     * @return
     */
    ResultBean JiFenChangeHome(Map<String,Object> params);

    /**
     * 积分兑换
     * @param params
     * @return
     */
    ResultBean JiFenChange(Map<String,Object> params);

    /**
     * 积分兑换列表
     * @param params
     * @return
     */
    ResultBean DuiHuanChangeList(Map<String,Object> params);

    /**
     * 赚钱首页App
     * @param params
     * @return
     */
    ResultBean MakeMoneyHomeInfo(Map<String,Object> params);


}
