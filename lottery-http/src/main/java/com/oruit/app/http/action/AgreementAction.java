package com.oruit.app.http.action;/**
 * Created by wyt on 2017/9/14.
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * @author @wyt
 * @create 2017-09-14 19:52
 */
@Controller
@RequestMapping("Agreement")
public class AgreementAction {
    @RequestMapping("/ServiceAgreement")
    private String progressDetailzfb () {
        return "Agreement/ServiceAgreement";
    }
   @RequestMapping("/Privacy")
    private String Privacy () {
        return "Agreement/privacy";
    }
}
