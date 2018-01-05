package com.oruit.app.http.action;/**
 * Created by wyt on 2017/8/26.
 */

import com.alibaba.fastjson.JSONObject;
import com.oruit.app.service.ShareService;
import com.oruit.app.util.web.ResponseUtils;
import com.oruit.app.util.web.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 分享相关
 * @author
 * @create 2017-08-26 17:06
 */
@Controller
public class ShareAction {
    @Autowired
    private ShareService shareService;
    @RequestMapping("ShareFriend")
    public void accountDetails(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                   result = shareService.ShareFriend(params);
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
