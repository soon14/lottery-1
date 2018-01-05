package com.oruit.app.service;/**
 * Created by wyt on 2017/8/24.
 */

import com.oruit.app.alipay.util.httpClient.HttpRequest;
import com.oruit.app.util.web.ResultBean;
import com.sun.org.apache.regexp.internal.RE;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Map;

/**
 * 个人信息
 * @author
 * @create 2017-08-24 9:17
 */

public interface PersonalInfoService {
    /**
     * 用户注册（手机）
     * @param map
     * @return
     */
    ResultBean UserRegister(Map<String,Object> map) throws ParseException;

    /**
     * 用户登录
     * @param map
     * @return
     */
    ResultBean  UserLogin(Map<String,Object> map);
    /**
     * 快速登录
     * @param map
     * @return
     */
    ResultBean  QuickLogin(Map<String,Object> map) throws ParseException;

    /**
     * 获取手机验证码
     * @param map
     * @return
     */
    ResultBean CommonSendVCode(Map<String,Object> map);
    /**
     * 获取手机验证码h5
     * @param request
     * @return
     */
    ResultBean CommonSendVCodeh5(HttpServletRequest request);

    /**
     * 微信授权登录
     * @param map
     * @return
     */
    ResultBean WxAuthoriorLogin(Map<String,Object> map) throws ParseException, UnsupportedEncodingException;
    /**
     * 微信授权登录h5
     * @param request
     * @return
     */
    ResultBean WxAuthoriorLoginh5(HttpServletRequest request) throws ParseException, UnsupportedEncodingException;

    /**
     * 个人信息列表
     * @param map
     * @return
     */
    ResultBean PersonInfo(Map<String,Object> map);

    /**
     * 修改密码
     * @param map
     * @return
     */
    ResultBean ModifyPassword(Map<String,Object> map);

    /**
     * 身份验证
     * @param map
     * @return
     */
    ResultBean RealNameAuthen(Map<String,Object> map);

    /**
     * 微信绑定手机号
     * @param map
     * @return
     */
    ResultBean WxBindingMobile(Map<String,Object> map);
    /**
     * 微信绑定手机号h5
     * @param request
     * @return
     */
    ResultBean WxBindingMobileh5(HttpServletRequest request);

    /**
     * 忘记密码
     * @param map
     * @return
     */
    ResultBean SeekPassword(Map<String,Object> map);

    /**
     * 检查验证码是否正确
     * @param map
     * @return
     */
    ResultBean CheckVcode(Map<String,Object> map);
    /**
     * 微信授权H5
     * @param
     * @return
     */
    ResultBean WXAuthorizationH5(HttpServletRequest request) throws ParseException, UnsupportedEncodingException;

    /**
     * 手机号注册绑定微信
     * @param
     * @return
     */
    ResultBean MobileBindingWX(Map<String,Object> map) throws ParseException, UnsupportedEncodingException;
    /**
     * 手机号注册绑定支付宝
     * @param
     * @return
     */
    ResultBean MobileBindingAlipay(Map<String,Object> map) throws ParseException, UnsupportedEncodingException;





}
