package com.oruit.app.http.action;

import com.oruit.app.service.SendWinNoticeService;
import com.oruit.app.util.SendMessage;
import com.oruit.app.util.VCodeUtil;
import com.oruit.app.util.web.ResponseUtils;
import com.oruit.app.util.web.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * Created by wyt on 2017-11-09.
 */
@Controller
public class NoticeAction {
    @Autowired
   private SendWinNoticeService sendWinNoticeService;
    /**
     * 中奖信息通知
     */
    @RequestMapping("sendWinNotice")
    @ResponseBody
    public String sendWinNotice() throws Exception {
        sendWinNoticeService.sendWinNotice();
        return "success";
    }
    @RequestMapping("integral")
    public void integral(HttpServletRequest request, HttpServletResponse response) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = format.parse("2017-11-22 15:00:00");
        long l = System.currentTimeMillis();
        long time = parse.getTime();
        long l1 = l - time;
        long l2 = l1 / 1000 ;
        double v =  l2 * (10000/864)+10000;
        long round = Math.round(v);
        ResultBean result = new ResultBean();
        result.setCode("1000");
        result.setTotal("1");
        result.setMsg("0|成功");
        result.setData(round);
        ResponseUtils.writeResult(request, response, result);
    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = format.parse("2017-11-22 15:00:00");
        long l = System.currentTimeMillis();
        long time = parse.getTime();
        long l1 = l - time;
        long l2 = l1 / 1000 ;
        double v =  l2 * (10000/864)+10000;
        long round = Math.round(v);
        System.out.println(round);
    }
}
