package com.oruit.app.service;/**
 * Created by wyt on 2017/8/26.
 */

import com.oruit.app.util.web.ResultBean;

import java.util.Map;

/**
 * 分享
 * @author
 * @create 2017-08-26 17:07
 */
public interface ShareService {

    ResultBean ShareFriend(Map<String,Object> map);
}
