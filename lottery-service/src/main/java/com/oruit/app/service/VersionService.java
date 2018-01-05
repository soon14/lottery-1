package com.oruit.app.service;/**
 * Created by wyt on 2017/9/22.
 */

import com.oruit.app.util.web.ResultBean;

import java.util.Map;

/**
 * 版本检测
 * @author @wyt
 * @create 2017-09-22 17:51
 */
public interface VersionService {
    /**
     * 版本检测
     * @param params
     * @return
     */
    ResultBean VersionCheck(Map<String,Object> params);
}
