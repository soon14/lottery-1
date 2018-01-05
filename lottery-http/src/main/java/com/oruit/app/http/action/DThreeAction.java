package com.oruit.app.http.action;

import com.alibaba.fastjson.JSONObject;
import com.oruit.app.service.DThreeService;
import com.oruit.app.util.web.ResponseUtils;
import com.oruit.app.util.web.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 3d
 * Created by wyt on 2017-11-06.
 */
@Controller
public class DThreeAction {
    @Autowired
    private DThreeService dThreeService;

    /**
     * 3D 选号
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("DThreeChoose")
    public void dThreeChoose(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = dThreeService.dThreeChoose(params);
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
