package com.oruit.app.service.impl;/**
 * Created by wyt on 2017/9/22.
 */

import com.oruit.app.dao.AppVersionMapper;
import com.oruit.app.service.VersionService;
import com.oruit.app.util.web.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 版本检测
 * @author @wyt
 * @create 2017-09-22 17:52
 */
@Service
public class VersionServiceImpl implements VersionService {

    @Autowired
    private AppVersionMapper appVersionMapper;
    /**
     * 版本检测
     * @param params
     * @return
     */
    @Override
    public ResultBean VersionCheck(Map<String, Object> params) {
        ResultBean resultBean = null;
        String versioncode = params.get("versioncode").toString();
        if (versioncode == null || "".equals(versioncode)) {
            return new ResultBean("2000", "0|版本号为空！");
        }
        String systemtype = params.get("systemtype").toString();
        if (systemtype == null || "".equals(systemtype)) {
            return new ResultBean("2000", "0|系统类型为空！");
        }
        Map<String, Object> resultMap = appVersionMapper.selectByCodeAndType(params);
        if(resultMap == null){
            resultBean =  new ResultBean("1000", "0|成功", "", "1");
        }else{
            resultBean =  new ResultBean("1000", "0|成功", resultMap, "1");
        }
        return resultBean;
    }
}
