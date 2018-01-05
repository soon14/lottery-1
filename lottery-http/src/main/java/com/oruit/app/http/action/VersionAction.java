package com.oruit.app.http.action;/**
 * Created by wyt on 2017/9/25.
 */

import com.alibaba.fastjson.JSONObject;
import com.oruit.app.service.VersionService;
import com.oruit.app.util.web.ResponseUtils;
import com.oruit.app.util.web.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 版本相关
 * @author @wyt
 * @create 2017-09-25 14:23
 */
@Controller
public class VersionAction {
    @Autowired
    private VersionService versionService;
    @RequestMapping("CheckVersion")
    public void CheckVersion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = versionService.VersionCheck(params);
                    break;
                default:
                    //ResponseUtils.writeResult(request, response, result);
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        ResponseUtils.writeResult(request, response, result);
    }
}
