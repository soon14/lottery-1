package com.oruit.app.service;/**
 * Created by wyt on 2017/9/1.
 */

import com.oruit.app.util.web.ResultBean;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Map;

/**
 * @author
 * @create 2017-09-01 15:08
 */
public interface PhonePageService {

    /**
     * 浏览器端手机号注册领取红包
     * @param request
     * @return
     */
    ResultBean PhonePageRegister(HttpServletRequest request) throws ParseException;

    /**
     * 浏览器端快乐十分选号
     * @param request
     * @return
     */
    ResultBean BrowserChoose(HttpServletRequest request) throws ParseException;

    /**
     * 顶部开奖信息
     * @param request
     * @return
     */
    ResultBean TopWinInfoH5(HttpServletRequest request);

    /**
     * 推广页获取期号
     * @param request
     * @return
     */
    ResultBean getIssueno(HttpServletRequest request);
    /**
     * h5导流
     * @param request
     * @return
     */
    ResultBean H5Guide(HttpServletRequest request) throws ParseException;
    /**
     * h5导流
     * @param request
     * @return
     */
    ResultBean H5GuideSSQ(HttpServletRequest request) throws Exception;

    /**
     * h5是否绑定
     * @param params
     * @return
     */
     ResultBean BangdingH5(Map<String, Object> params);




}
