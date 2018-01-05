package com.oruit.app.http.action;/**
 * Created by wyt on 2017/8/25.
 */

import com.alibaba.fastjson.JSONObject;
import com.oruit.app.service.AccountService;
import com.oruit.app.util.web.ResponseUtils;
import com.oruit.app.util.web.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Action;
import java.io.IOException;

/**
 * @author
 * @create 2017-08-25 11:21
 */

@Controller
public class AccountAction {
    @Autowired
    private AccountService accountService;

    @RequestMapping("AccountDetails")
    public void accountDetails(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject params = (JSONObject) request.getAttribute("inputJsonObject");
        String infversion = params.getString("infversion");
        ResultBean result = null;
        try {
            switch (infversion) {
                case "1.0":
                    result = accountService.queryUserAccountStatement(params);
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
