package com.oruit.app.service;/**
 * Created by wyt on 2017/8/24.
 */

import com.oruit.app.util.web.ResultBean;

import java.text.ParseException;
import java.util.Map;


/**
 * @author
 * @create 2017-08-24 16:46
 */
public interface HomePageService {

    /**
     * 查询所首页信息
     * @return
     */
    ResultBean HomeInfo(Map<String,Object> maps) throws Exception;

    /**
     * 用户是否中奖
     * @param maps
     * @return
     */
    ResultBean userWinInfo(Map<String,Object> maps) throws Exception;

    /**
     * ios设么专用
     * @param map
     * @return
     */
    ResultBean IosExamine (Map<String,Object> map);

}
