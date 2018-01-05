package com.oruit.app.service.impl;/**
 * Created by wyt on 2017/8/24.
 */

import cn.idchecker.check.Checker;
import com.alibaba.fastjson.JSONObject;
import com.oruit.app.client.sms.AliyunSmlHelper;
import com.oruit.app.dao.*;
import com.oruit.app.dao.model.*;
import com.oruit.app.logic.util.ConstUtil;
import com.oruit.app.message.MessagePushUtil;
import com.oruit.app.oruitkey.OruitMD5;
import com.oruit.app.qrcode.QRCodeUtil;
import com.oruit.app.service.PersonalInfoService;
import com.oruit.app.util.ChineseCheck;
import com.oruit.app.util.EmojiFilterUtils;
import com.oruit.app.util.SendMessage;
import com.oruit.app.util.VCodeUtil;
import com.oruit.app.util.config.Constants;
import com.oruit.app.util.web.RequestUtils;
import com.oruit.app.util.web.ResultBean;
import com.oruit.app.util.web.UUIDUtils;
import com.oruit.weixin.api.GetAccessTokenRsp;
import com.oruit.weixin.api.WeiXinAccessTokenUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.oruit.app.util.MobileUtil.matchesPhoneNumber;

/**
 * @author wyt
 * @create 2017-08-24 9:19
 */
@Service
public class PersonalInfoServiceImpl implements PersonalInfoService {

    private static final Logger log = Logger.getLogger(PersonalInfoServiceImpl.class);
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private ConfigInfoMapper configInfoMapper;
    @Autowired
    private VerificationCodeMapper verificationCodeMapper;
    @Autowired
    private UserForbiddenMapper userForbiddenMapper;
    @Autowired
    private UserStatisticsMapper userStatisticsMapper;
    @Autowired
    private UserAuthorizationMapper userAuthorizationMapper;
    @Autowired
    private AuthorizationMapper authorizationMapper;
    @Autowired
    private AppMenuMapper appMenuMapper;
    @Autowired
    private  UserRedpacketMapper userRedpacketMapper;
    @Autowired
    private CaipiaoOrderInfoMapper caipiaoOrderInfoMapper;
    @Autowired
    private  UserShareMapper userShareMapper;
    @Autowired
    private UserStatisticsLogMapper userStatisticsLogMapper;
    @Autowired
    private AppAuditVersonMapper appAuditVersonMapper;
    @Autowired
    private PushConfigMapper pushConfigMapper;
    @Autowired
    private RedacketMapper redacketMapper;
    @Autowired
    private MessageInfoMapper messageInfoMapper;


    /**
     * 删除
     * @param map
     * @return
     * @throws ParseException
     */
    @Override
    public ResultBean UserRegister(Map<String, Object> map) throws ParseException {
        ResultBean resultBean = null;
        String mobile = map.get("mobile").toString();
        if(mobile == null || "".equals(mobile)){
          return new ResultBean("2000", "1|手机号为空！");
        }
        if(matchesPhoneNumber(mobile)<1){
            return new ResultBean("2000", "1|手机号不正确！");
        }
        String password = map.get("password").toString();
        if(password == null || "".equals(password)){
            return new ResultBean("2000", "1|密码为空！");
        }
        String verificationcode = map.get("verificationcode").toString();
        if(verificationcode == null || "".equals(verificationcode)){
            return new ResultBean("2000", "1|验证码为空！");
        }
        String existence = userInfoMapper.CheckMobile(mobile);
        if(existence!=null && !"".equals(existence)){
            return new ResultBean("2000", "1|手机号已经注册请直接登录！");
        }
        Map<String,Object> map1 = new HashMap<>();
        map1.put("mobile",mobile);
        map1.put("verificationcode",verificationcode);
        map1.put("vtype",1);
        VerificationCode verificationCode = verificationCodeMapper.checkVcode(map1);
        System.out.println("verificationCode"+verificationCode);
        if(verificationCode == null || "".equals(verificationCode)){
            return new ResultBean("2000", "1|验证码不正确！");
        }
        String temp = UUIDUtils.uuid();
        password = OruitMD5.getMD5UpperCaseStr(OruitMD5.getMD5UpperCaseStr(password));
        Date parse = new SimpleDateFormat("yyyy-MM-dd").parse("1990-01-01");
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(temp);
        userInfo.setMobile(mobile);
        userInfo.setPassword(password);
        userInfo.setUserState(1);
        userInfo.setParentId(0);
        userInfo.setUserShipLevel(1);
        userInfo.setFirstLoginTime(new Date());
        userInfo.setUpdateTime(new Date());
        userInfo.setJoinDate(new Date());
        userInfo.setWechatAuthorized(0);
        int i = userInfoMapper.insertSelective(userInfo);
        if(i>0){
            UserStatistics userStatistics = new UserStatistics();
            userStatistics.setUserId(temp);
            userStatistics.setWinningBalabceAmount(new BigDecimal("0.00"));
            userStatistics.setUpdateTime(new Date());
            userStatistics.setCreateTime(new Date());
            userStatisticsMapper.insertSelective(userStatistics);
           resultBean =  new ResultBean("1000", "1|注册成功", "", "1");
        }else{
            resultBean =  new ResultBean("2000", "1|注册失败！");
        }
        return resultBean;
    }

    /**
     * 删除
     * @param map
     * @return
     */
    @Override
    public ResultBean UserLogin(Map<String, Object> map) {
        ResultBean resultBean = null;
        String mobile = map.get("mobile").toString();
        if(mobile == null || "".equals(mobile)){
            return new ResultBean("2000", "1|手机号为空！");
        }
        if(matchesPhoneNumber(mobile)<1){
            return new ResultBean("2000", "1|手机号不正确！");
        }
        String password = map.get("password").toString();
        if(password == null || "".equals(password)){
            return new ResultBean("2000", "1|密码为空！");
        }
        password = OruitMD5.getMD5UpperCaseStr(OruitMD5.getMD5UpperCaseStr(password));
        map.put("password",password);
        Map<String, Object> maps = userInfoMapper.UserLogin(map);
        if (maps != null && !maps.isEmpty()) {
            maps.put("mobile", maps.get("mobile").toString().substring(0, 3) + "****" + maps.get("mobile").toString().substring(7, 11));
            String user_id = maps.get("userid").toString();
            if(user_id != null && !"".equals(user_id)){
                UserStatistics userStatistics = userStatisticsMapper.selectByPrimaryKey(user_id);
                if(userStatistics!=null && !"".equals(userStatistics)){
                    maps.put("rechargebalance",userStatistics.getBalanceAmount());
                    maps.put("winningbalance",userStatistics.getWinningBalabceAmount());
                }
                String s = userForbiddenMapper.queryUserid(user_id);
                if(s == null || "".equals(s)){
                    String s1 = userInfoMapper.queryFirst(user_id);
                    UserInfo userInfo = new UserInfo();
                    if("1990-01-01 00:00:00.0".equals(s1)){
                        userInfo.setFirstLoginTime(new Date());
                        userInfo.setUpdateTime(new Date());
                    }else{
                        userInfo.setUpdateTime(new Date());
                    }
                    userInfo.setUserId(user_id);
                    userInfoMapper.updateByPrimaryKeySelective(userInfo);

                    resultBean =  new ResultBean("1000", "1|登录成功", maps, "1");
                }else{
                    resultBean =  new ResultBean("2000", "1|登录失败！");
                }
            }
        }else{
            resultBean =  new ResultBean("2000", "1|用户名或密码错误", "", "0");
        }
        return resultBean;
    }

    /**
     * 快速登录
     * @param map
     * @return
     */
    @Override
    public ResultBean QuickLogin(Map<String, Object> map) throws ParseException {
        ResultBean resultBean = null;
        String mobile = map.get("mobile").toString();
        if(mobile == null || "".equals(mobile)){
            return new ResultBean("2000", "1|手机号为空！");
        }
        if(matchesPhoneNumber(mobile)<1){
            return new ResultBean("2000", "1|手机号不正确！");
        }
        String verificationcode = map.get("verificationcode").toString();
        if(verificationcode == null || "".equals(verificationcode)){
            return new ResultBean("2000", "1|验证码号为空！");
        }
        Map<String,Object> map1 = new HashMap<>();
        map1.put("mobile",mobile);
        map1.put("verificationcode",verificationcode);
        map1.put("vtype",4);
        VerificationCode verificationCode = verificationCodeMapper.checkVcode(map1);
        System.out.println("verificationCode"+verificationCode);
        if(verificationCode == null || "".equals(verificationCode)){
            return new ResultBean("2000", "1|验证码不正确！");
        }
        Map<String,Object> maps = new HashMap<>();
        String userId = "";
        String s = userInfoMapper.CheckMobile(mobile);
        Map<String, Object> map2 = new HashMap<>();
        if(s == null || "".equals(s)){
            //注册
            String temp = UUIDUtils.uuid();
            Date parse = new SimpleDateFormat("yyyy-MM-dd").parse("1990-01-01");
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(temp);
            userInfo.setMobile(mobile);
            userInfo.setUserState(1);
            userInfo.setParentId(0);
            userInfo.setUserShipLevel(1);
            userInfo.setFirstLoginTime(new Date());
            userInfo.setUpdateTime(new Date());
            userInfo.setJoinDate(new Date());
            userInfo.setWechatAuthorized(0);
            userInfo.setIdCard(" ");
            int i = userInfoMapper.insertSelective(userInfo);
            System.out.println("*************************************:"+i);
             userId = userInfo.getUserId();

            if(i>0) {
                UserStatistics userStatistics = new UserStatistics();
                userStatistics.setUserId(temp);
                userStatistics.setWinningBalabceAmount(new BigDecimal("0.00"));
                userStatistics.setUpdateTime(new Date());
                userStatistics.setCreateTime(new Date());
                userStatisticsMapper.insertSelective(userStatistics);
                //插入推送表
                insertJPush(userId);
                String userForbiddenState = userForbiddenMapper.QueryUserForbiddenState(userId);
                 map2 = userInfoMapper.QueryUserInfo(userId);
                if(userForbiddenState==null || "".equals(userForbiddenState)){
                    map2.put("userForbiddenState"," ");
                }else{
                    return new ResultBean("2000", "1|您已经被禁止登录！");
                }
                resultBean =  new ResultBean("1000", "1|注册成功！", map2, "1");
            }
            map2.put("mobile", map2.get("mobile").toString().substring(0, 3) + "****" + map2.get("mobile").toString().substring(7, 11));
            if("1".equals(map2.get("idauthorized").toString())){
                map2.put("idcard", map2.get("idcard").toString().substring(0, 10) + "****" + map2.get("idcard").toString().substring(14, 16)+"**");
            }
            map2.put("rechargebalance","0.00");
            map2.put("winningbalance","0.00");
        }else {
            //登录
            String userid = userInfoMapper.QueryMobileUserid(mobile);
            System.out.println("-----------------userid----:"+userid);
            map2 = userInfoMapper.QueryUserInfo(userid);
            map2.put("mobile", map2.get("mobile").toString().substring(0, 3) + "****" + map2.get("mobile").toString().substring(7, 11));
            if("1".equals(map2.get("idauthorized").toString())){
                map2.put("idcard", map2.get("idcard").toString().substring(0, 10) + "****" + map2.get("idcard").toString().substring(14, 16)+"**");
            }
            String userForbiddenState = userForbiddenMapper.QueryUserForbiddenState(userId);
            map2.put("userForbiddenState",userForbiddenState);

        }
        return  new ResultBean("1000", "1|登录成功！", map2, "1");
    }

    private void insertJPush(String userId) {
        PushConfig pushConfig = new PushConfig();
        pushConfig.setUserId(userId);
        pushConfig.setPushEnable(1);
        pushConfig.setCreateTime(new Date());
        pushConfig.setParentCode("");
        pushConfig.setPushItems("");
        pushConfig.setPushType("system");
        pushConfig.setPushCode("zhongjiangtongzhi");
        pushConfig.setPushName("中奖通知");
        pushConfigMapper.insertSelective(pushConfig);
        pushConfig.setPushCode("kaijianggonggao");
        pushConfig.setPushName("开奖公告");
        pushConfigMapper.insertSelective(pushConfig);
        pushConfig.setPushCode("shuangseqiu");
        pushConfig.setPushName("双色球");
        pushConfig.setParentCode("kaijianggonggao");
        pushConfigMapper.insertSelective(pushConfig);
        pushConfig.setPushCode("kuaileshifen");
        pushConfig.setPushName("快乐十分");
        pushConfig.setParentCode("kaijianggonggao");
        pushConfigMapper.insertSelective(pushConfig);
        pushConfig.setPushCode("miandaraoshijian");
        pushConfig.setPushName("免打扰时间");
        pushConfig.setPushItems("23,8");
        pushConfig.setParentCode("");
        int i = pushConfigMapper.insertSelective(pushConfig);
    }

    @Override
    public ResultBean CommonSendVCode(Map<String, Object> map) {
        ResultBean resultBean = null;
        String vCode = "000000";
        String mobile = map.get("mobile").toString();
        if(mobile == null || "".equals(mobile)){
            return new ResultBean("2000", "1|手机号为空！");
        }
        if(matchesPhoneNumber(mobile)<1){
            return new ResultBean("2000", "1|手机号不正确！");
        }
        String type = map.get("type").toString();
        if(type == null || "".equals(type)){
            return new ResultBean("2000", "1|验证码类型为空！");
        }
        int i = Integer.parseInt(type);
        if( i == 1){
            String s = userInfoMapper.CheckMobile(mobile);
            if(s!=null && !"".equals(s)){
                return new ResultBean("2000", "1|手机号已经被注册！！！");
            }
        }
        ConfigInfo configinfo = configInfoMapper.selectByConnfigCode(Constants.CONFIG_VCODE_FLAG_CODE);
        VerificationCode info = verificationCodeMapper.selectByMobileAndTimes(mobile);
        if(!"13888888888".equals(mobile)){
            if ("1".equals(configinfo.getConfigValue())) {  // 短信通道1
                if (info == null) {
                    vCode = VCodeUtil.getVCode();
                    AliyunSmlHelper.sendSmsNew(mobile, vCode);
                }
            }
            if ("2".equals(configinfo.getConfigValue())) {  // 短信通道2
                if (info == null) {
                    vCode = VCodeUtil.getVCode();
                    SendMessage.SendTemplateSMS(mobile,vCode);
                }
            }
        }
        Date date = new Date();
        VerificationCode verificationcode = new VerificationCode();
        verificationcode.setVcode(vCode);
        verificationcode.setCreateTime(date);
        verificationcode.setMobile(mobile);
        verificationcode.setVtype(Integer.parseInt(type));
        ConfigInfo configInfo = configInfoMapper.selectByConnfigCode(Constants.CONFIG_OUT_TIME_CODE);
        Date afterDate = new Date(date.getTime() + Integer.valueOf(configInfo.getConfigValue()) * 60000);
        verificationcode.setOverTime(afterDate);
        int insert = verificationCodeMapper.insert(verificationcode);
        if(insert>0){
            resultBean =  new ResultBean("1000", "1|验证码发送成功！", "", "1");
        }
        else {
            resultBean =  new ResultBean("2000", "1|验证码发送失败！");
        }
        return resultBean;
    }
    @Override
    public ResultBean CommonSendVCodeh5(HttpServletRequest request) {
        System.out.println("-------------------------");
        ResultBean resultBean = null;
        String vCode = "000000";
        String mobile = request.getParameter("mobile");
        if(mobile == null || "".equals(mobile)){
            return new ResultBean("2000", "1|手机号为空！");
        }
        if(matchesPhoneNumber(mobile)<1){
            return new ResultBean("2000", "1|手机号不正确！");
        }
        String type = request.getParameter("type");
        if(type == null || "".equals(type)){
            return new ResultBean("2000", "0|验证码类型为空！");
        }
        int i = Integer.parseInt(type);
        if( i == 1){
            String s = userInfoMapper.CheckMobile(mobile);
            if(s!=null && !"".equals(s)){
                return new ResultBean("2000", "1|此手机号已经注册过！！！");
            }
        }
        if( i == 3){
            String s = userInfoMapper.CheckMobile(mobile);
            if(s!=null && !"".equals(s)){
                return new ResultBean("2000", "1|手机号已经被绑定！！！");
            }
        }

        ConfigInfo configinfo = configInfoMapper.selectByConnfigCode(Constants.CONFIG_VCODE_FLAG_CODE);
        VerificationCode info = verificationCodeMapper.selectByMobileAndTimes(mobile);
        //System.out.println("------configinfo.getConfigValue()--------------:"+configinfo.getConfigValue());
        if ("1".equals(configinfo.getConfigValue())) {  // 短信通道1
            if (info == null) {
                vCode = VCodeUtil.getVCode();
                AliyunSmlHelper.sendSmsNew(mobile, vCode);
            }
        }
        if ("2".equals(configinfo.getConfigValue())) {  // 短信通道1
            if (info == null) {
                vCode = VCodeUtil.getVCode();
                SendMessage.SendTemplateSMS(mobile,vCode);
            }

        }

        Date date = new Date();
        VerificationCode verificationcode = new VerificationCode();
        verificationcode.setVcode(vCode);
        verificationcode.setCreateTime(date);
        verificationcode.setMobile(mobile);
        verificationcode.setVtype(i);
        ConfigInfo configInfo = configInfoMapper.selectByConnfigCode(Constants.CONFIG_OUT_TIME_CODE);
        Date afterDate = new Date(date.getTime() + Integer.valueOf(configInfo.getConfigValue()) * 60000);
        verificationcode.setOverTime(afterDate);
        int insert = verificationCodeMapper.insert(verificationcode);
        if(insert>0){
            resultBean =  new ResultBean("1000", "1|验证码发送成功！", "", "1");
        }
        else {
            resultBean =  new ResultBean("2000", "1|验证码发送失败！");
        }
        return resultBean;
    }



    @Override
    public ResultBean WxAuthoriorLogin(Map<String, Object> map) throws ParseException, UnsupportedEncodingException {
        ResultBean resultBean = null;
        System.out.println(map);
        String unionId = map.get("unionId").toString();
        if(unionId == null || "".equals(unionId)){
            return new ResultBean("2000", "0|unionId为空！");
        }
        String openId = map.get("openId").toString();
        if(openId == null || "".equals(openId)){
            return new ResultBean("2000", "0|openId！");
        }
        //判断是否是同一个公众号
        String sop = authorizationMapper.queryOpenid(unionId);
        if(sop != null && !"".equals(sop)){
            if(!openId.equals(sop)){
                //更新openid
                Authorization authorization = new Authorization();
                authorization.setOpenId(openId);
                authorization.setUnionId(unionId);
                authorizationMapper.updateopenid(authorization);
            }
        }

        String userid = userAuthorizationMapper.queryUserid(unionId);
        UserInfo userInfo1 = null;
        Map<String, Object> map1 = new HashMap<>();
        System.out.println("userid:"+userid);
        if(userid ==null || "".equals(userid)){
            userInfo1 = userInfoMapper.selectByPrimaryKey(userid);
            String authorization = authorization(map, unionId, userid, userInfo1,"0","0");
            if(authorization!=null && !"".equals(authorization)){
                System.out.println("+++++++++++++++++++++:"+authorization);
                map1 = userInfoMapper.QueryUserInfo(authorization);
                map1.put("mobile","");
                if("1".equals(map1.get("idauthorized").toString())){
                    map1.put("idcard", map1.get("idcard").toString().substring(0, 10) + "****" + map1.get("idcard").toString().substring(14, 16)+"**");
                }
                String userForbiddenState = userForbiddenMapper.QueryUserForbiddenState(authorization);
                if(userForbiddenState==null || "".equals(userForbiddenState)){
                   map1.put("userForbiddenState","");
                }else{
                    return new ResultBean("2000", "1|您已经被禁止登录！");
                }
                resultBean =  new ResultBean("1000", "1|授权成功", map1, "1");
            }else{
                resultBean =  new ResultBean("2000", "1|授权失败！");
            }
        }else{
            //1.登录
            UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userid);
            if(userInfo != null || !"".equals(userInfo)){
                map1 = userInfoMapper.QueryUserInfo(userid);
                String s = map1.get("mobile").toString();
                if(s==null || "".equals(s)){
                    map1.put("mobile","");
                }else{
                    map1.put("mobile", map1.get("mobile").toString().substring(0, 3) + "****" + map1.get("mobile").toString().substring(7, 11));
                }

                if("1".equals(map1.get("idauthorized").toString())){
                    map1.put("idcard", map1.get("idcard").toString().substring(0, 10) + "****" + map1.get("idcard").toString().substring(14, 16)+"**");
                }
                String userForbiddenState = userForbiddenMapper.QueryUserForbiddenState(userid);
                if(userForbiddenState==null || "".equals(userForbiddenState)){
                    map1.put("userForbiddenState"," ");
                }else{
                    return new ResultBean("2000", "1|您已经被禁止登录！");
                }
                resultBean =  new ResultBean("1000", "1|授权成功", map1, "1");
            }else{
                //重新授权
                String authorization = authorization(map, unionId, userid, userInfo1,"0","0");
                if(authorization!=null && !"".equals(authorization)){
                    map1 = userInfoMapper.QueryUserInfo(authorization);

                    String s = map1.get("mobile").toString();
                    if(s==null || "".equals(s)){
                        map1.put("mobile","");
                    }else{
                        map1.put("mobile", map1.get("mobile").toString().substring(0, 3) + "****" + map1.get("mobile").toString().substring(7, 11));
                    }
                    if("1".equals(map1.get("idauthorized").toString())){
                        map1.put("idcard", map1.get("idcard").toString().substring(0, 10) + "****" + map1.get("idcard").toString().substring(14, 16)+"**");
                    }
                    String userForbiddenState = userForbiddenMapper.QueryUserForbiddenState(authorization);
                    if(userForbiddenState==null || "".equals(userForbiddenState)){
                        map1.put("userForbiddenState","");
                    }else{
                        return new ResultBean("2000", "1|您已经被禁止登录！");
                    }
                    resultBean =  new ResultBean("1000", "1|授权成功", map1, "1");
                }else{
                    resultBean =  new ResultBean("2000", "1|授权失败！");
                }
            }
        }
        return resultBean;
    }

    @Override
    public ResultBean WxAuthoriorLoginh5(HttpServletRequest request) throws ParseException, UnsupportedEncodingException {
        ResultBean resultBean = null;
        Map<String,Object> map = new HashMap<>();
        String unionId = request.getParameter("unionId");
        String nickname = request.getParameter("nickname");
        String wechatAccount = request.getParameter("wechatAccount");
        String photo = request.getParameter("photo");
        String sex = request.getParameter("sex");
        String province = request.getParameter("province");
        String city = request.getParameter("city");
        String wechatRealName = request.getParameter("wechatRealName");
        if(unionId == null || "".equals(unionId)){
            return new ResultBean("2000", "0|unionId为空！");
        }
        map.put("unionId",unionId);


        String nicknames = EmojiFilterUtils.filterEmoji(nickname);
        nicknames = new String(nicknames.getBytes(), "UTF-8");
        map.put("nickname",nicknames);

        map.put("wechatAccount",wechatAccount);
        map.put("photo",photo);
        map.put("sex",sex);
        map.put("province",province);
        map.put("city",city);
        map.put("wechatRealName",wechatRealName);
        String userid = userAuthorizationMapper.queryUserid(unionId);
        System.out.println("+++++++++++++userid:"+userid);
        UserInfo userInfo1 = null;
        Map<String, Object> map1 = null;
        System.out.println("userid:"+userid);
        String authorization = "";
        if(userid ==null || "".equals(userid)){
            userInfo1 = userInfoMapper.selectByPrimaryKey(userid);
            authorization = authorization(map, unionId, userid, userInfo1,"0","0");

            map1 = userInfoMapper.QueryUserInfo(userid);

            if(authorization!=null && !"".equals(authorization)){
                resultBean =  new ResultBean("1000", "1|100", authorization, "1");//授权成功
            }else{
                resultBean =  new ResultBean("2000", "1|授权失败！");
            }
        }else{
            //1.登录
            UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userid);
            if(userInfo != null || !"".equals(userInfo)){
                resultBean =  new ResultBean("1000", "1|101", authorization, "1");//01已注册过
                if(userInfo.getMobile()!=null && !"".equals(userInfo.getMobile())){
                    resultBean =  new ResultBean("1000", "1|102", authorization, "1");//已绑定过手机
                }
                String query = userRedpacketMapper.query(userid);
                if(query!=null && !"".equals(query)){
                    resultBean =  new ResultBean("1000", "1|103", authorization, "1");//103|已领取过红包
                }
            }else{
                //重新授权
                authorization = authorization(map, unionId, userid, userInfo1,"0","0");
                if(authorization!=null && !"".equals(authorization)){
                    resultBean =  new ResultBean("1000", "1|100", authorization, "1");
                }else{
                    resultBean =  new ResultBean("2000", "1|授权失败！");
                }
            }
        }
        return resultBean;
    }

    /**
     * 个人信息
     * @param map
     * @return
     */
    @Override
    public ResultBean PersonInfo(Map<String, Object> map) {
        ResultBean resultBean = null;
        Map<String, Object> maps = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();
        String userId = map.get("userId").toString();
        String appversion = map.get("appversion").toString();
        String systemtype = map.get("systemtype").toString();
        System.out.println("---------------userId"+userId);
        if(userId==null || " ".equals(userId) || "0".equals(userId)){
            maps.put("mobile","");
            maps.put("rechargebalance","0.00");
            maps.put("winningbalance","0.00");
            maps.put("redphoto"," ");
            maps.put("userid"," ");
        }else {
            map1 = userInfoMapper.QueryUserInfo(userId);
            String mobile = map1.get("mobile").toString();
            if(!"".equals(mobile)&&mobile!=null){
                map1.put("mobile", map1.get("mobile").toString().substring(0, 3) + "****" + map1.get("mobile").toString().substring(7, 11));
            }else{
                maps.put("mobile","");
            }
            if("1".equals(map1.get("idauthorized").toString())){
                map1.put("idcard", map1.get("idcard").toString().substring(0, 10) + "****" + map1.get("idcard").toString().substring(14, 16)+"**");
            }
        }
        if(map1 !=null){
            maps.putAll(map1);
        }else {
            maps.put("mobile"," ");
            maps.put("rechargebalance","0.00");
            maps.put("winningbalance","0.00");
            maps.put("redphoto"," ");
        }
        String userForbiddenState = userForbiddenMapper.QueryUserForbiddenState(userId);
        maps.put("userForbiddenState",userForbiddenState);

        ConfigInfo configInfo = configInfoMapper.selectByConnfigCode(Constants.ServiceMobile_CONFIG);
        String configValue = configInfo.getConfigValue();
        System.out.println("--------configValue"+configValue);
        JSONObject jsonObject = JSONObject.parseObject(configValue);
        String servicemobile = jsonObject.get("mobile").toString();
        maps.put("servicemobile",servicemobile);

        //审核状态
        String state = null;
        Map<String, Object> stringObjectMap = appAuditVersonMapper.queryCheckExamins(appversion);
        if(stringObjectMap == null || "".equals(stringObjectMap)){
            state = "2" ;
        }else {
            if("android".equals(systemtype)){
                state = stringObjectMap.get("androidstate").toString();
            }
            if("ios".equals(systemtype)){
                state = stringObjectMap.get("iosstate").toString();
            }
        }

        System.out.println("-------------state------------------------------:"+state);
        if("2".equals(state)){
            List<Map<String, Object>> item = appMenuMapper.queryAppMenu();
            if(!item.isEmpty()&&item.size()>0){
                maps.put("item",item);
            }else {
                maps.put("item","");
            }
        }else {
            List<Map<String, Object>> item = appMenuMapper.queryAppMenuEximins();
            for(Map<String,Object> result : item){
                String displayorder = result.get("displayorder").toString();
                result.put("displayorder",displayorder);
                String menucode = result.get("menucode").toString();
                result.put("menucode",menucode);
                String menutype = result.get("menutype").toString();
                result.put("menutype",menutype);
                String menuid = result.get("menuid").toString();
                result.put("menuid",menuid);
                String parentid = result.get("parentid").toString();
                result.put("parentid",parentid);
            }
            if(!item.isEmpty()&&item.size()>0){
                maps.put("item",item);
            }else {
                maps.put("item","");
            }
        }
        //新增消息的条数
        Integer integer = messageInfoMapper.queryMessageCount(userId);
        maps.put("messagecount",integer);
        //奖励规则
        ConfigInfo jiagnli = configInfoMapper.selectByConnfigCode(Constants.JIANGLI_CONFIG);
        String jiagnliguize = jiagnli.getConfigValue();
        String s = jiagnliguize.replaceAll("\\r", "").replaceAll("\\n","").replaceAll("\\t","").replaceAll("\"","");
        maps.put("jiagnliguize", s);
        return new ResultBean("1000", "0|成功", maps, "1");
    }


    /**
     * 修改密码
     * @param map
     * @return
     */
    @Override
    public ResultBean ModifyPassword(Map<String, Object> map) {
        ResultBean resultBean = null;
        String userId = map.get("userId").toString();
        if(userId == null || "".equals(userId)){
            return new ResultBean("2000", "0|用户id为空！");
        }
        String oldPassword = map.get("oldPassword").toString();
        if(oldPassword == null || "".equals(oldPassword)){
            return new ResultBean("2000", "1|原始密码为空！");
        }
        oldPassword = OruitMD5.getMD5UpperCaseStr(OruitMD5.getMD5UpperCaseStr(oldPassword));
        String newPassword = map.get("newPassword").toString();
        if(newPassword == null || "".equals(newPassword)){
            return new ResultBean("2000", "1|新密码为空！");
        }
        newPassword = OruitMD5.getMD5UpperCaseStr(OruitMD5.getMD5UpperCaseStr(newPassword));
        if(oldPassword.equals(newPassword)){
            return new ResultBean("2000", "1|原始密码和新密码不能相同！");
        }
        String password = userInfoMapper.CheckPassword(userId);
        if(password!=null && !"".equals(password)){
            if(password.equals(oldPassword)){
                UserInfo userInfo = new UserInfo();
                userInfo.setUserId(userId);
                userInfo.setPassword(newPassword);
                int i = userInfoMapper.updateByPrimaryKeySelective(userInfo);
                if(i>0){
                    resultBean =  new ResultBean("1000", "1|修改密码成功");
                }else {
                    resultBean =  new ResultBean("1000", "1|修改密码失败！", "", "1");
                }
            }else{
                return new ResultBean("2000", "1|原始密码不正确！");
            }
        }else {
            resultBean =  new ResultBean("1000", "1|修改密码失败");
        }
        return resultBean;
    }


    /**
     * 实名认证
     * @param map
     * @return
     */
    @Override
    public ResultBean RealNameAuthen(Map<String, Object> map) {
        ResultBean resultBean = null;
        String userId = map.get("userId").toString();
        if(userId == null || "".equals(userId)){
            return new ResultBean("2000", "0|用户id为空！");
        }
        String name = map.get("realName").toString();
        if(name == null || "".equals(name)){
            return new ResultBean("2000", "1|用户姓名为空！");
        }
        if(name.length()<2){
            return new ResultBean("2000", "1|用户姓名长度不正确！");
        }
        if(!ChineseCheck.isChinese(name)){
            return new ResultBean("2000", "1|用户姓名格式不正确！");
        }
        String idCard = map.get("idCard").toString();
        if(idCard == null || "".equals(idCard)){
            return new ResultBean("2000", "1|用户身份证为空！");
        }

        UserInfo userInfo1 = userInfoMapper.selectByPrimaryKey(userId);
        Integer idAuthorized = userInfo1.getIdAuthorized();
        Map<String,Object> maps = new HashMap<>();
        if(idAuthorized==1){
            maps.put("realname",userInfo1.getRealName());
            maps.put("idcard",userInfo1.getIdCard());
            return new ResultBean("1000", "1|不能重复实名认证！", maps, "1");
        }
        UserInfo userInfo = new UserInfo();
        Checker checker = new Checker(idCard);
        boolean check = checker.check();
        if(!check){
            return new ResultBean("2000", "1|身份证号码错误！");
        }
       /* String idCard1 = userInfoMapper.QueryIDCard(idCard);
        if(idCard1!=null&&!"".equals(idCard1)){
            return new ResultBean("2000", "1|此身份证号码已经被认证过！");
        }*/
        if(idCard.indexOf("x")>0 || idCard.indexOf("X")>0){
            idCard = idCard.toUpperCase();
        }
        userInfo.setUserId(userId);
        userInfo.setIdCard(idCard);
        userInfo.setRealName(name);
        userInfo.setIdAuthorized(1);
        int i = userInfoMapper.updateByPrimaryKeySelective(userInfo);
        if(i>0){
            resultBean =  new ResultBean("1000", "1|实名认证成功！", "", "1");
        }else{
            resultBean =  new ResultBean("2000", "1|实名认证失败！", "", "1");
        }

        return resultBean;
    }
    /**
     * 微信绑定
     * @param
     * @return
     */
    @Override
    public ResultBean WxBindingMobile(Map<String, Object> map) {
        ResultBean resultBean = null;
        String userId = map.get("userId").toString();
        if(userId == null || "".equals(userId)){
            return new ResultBean("2000", "0|用户id为空！");
        }
        String mobile = map.get("mobile").toString();
        if(mobile == null || "".equals(mobile)){
            return new ResultBean("2000", "1|手机号为空！");
        }
        if(matchesPhoneNumber(mobile)<1){
            return new ResultBean("2000", "1|手机号不正确！");
        }
        String verificationcode = map.get("verificationcode").toString();
        if(verificationcode == null || "".equals(verificationcode)){
            return new ResultBean("2000", "1|验证码为空！");
        }
        UserInfo userInfo1 = userInfoMapper.selectByPrimaryKey(userId);
        String mobile1 = userInfo1.getMobile();
        if(mobile1!=null&&!"".equals(mobile1)){
            return new ResultBean("2000", "1|已经绑定过手机号不能再次绑定！");
        }
        String s = userInfoMapper.CheckMobile(mobile);
        if(s!=null && !"".equals(s)){
            return new ResultBean("2000", "1|手机号已经被其他人绑定！！！");
        }
        Map<String,Object> map1 = new HashMap<>();
        map1.put("mobile",mobile);
        map1.put("verificationcode",verificationcode);
        map1.put("overTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        map1.put("vtype",3);
        VerificationCode verificationCode = verificationCodeMapper.checkVcode(map1);
        System.out.println("verificationCode"+verificationCode);
        if(verificationCode == null || "".equals(verificationCode)){
            return new ResultBean("2000", "1|验证码不正确！");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setMobile(mobile);
        int i = userInfoMapper.updateByPrimaryKeySelective(userInfo);


        if(i>0){
            map1.clear();
            map1 = userInfoMapper.QueryUserInfo(userId);
            String ss = map1.get("mobile").toString();
            if(ss==null || "".equals(ss)){
                map1.put("mobile","");
            }else{
                map1.put("mobile", map1.get("mobile").toString().substring(0, 3) + "****" + map1.get("mobile").toString().substring(7, 11));
            }

            if("1".equals(map1.get("idauthorized").toString())){
                map1.put("idcard", map1.get("idcard").toString().substring(0, 10) + "****" + map1.get("idcard").toString().substring(14, 16)+"**");
            }
            String userForbiddenState = userForbiddenMapper.QueryUserForbiddenState(userId);
            if(userForbiddenState==null || "".equals(userForbiddenState)){
                map1.put("userForbiddenState"," ");
            }else{
                return new ResultBean("2000", "1|您已经被禁止登录！");
            }
            resultBean =  new ResultBean("1000", "1|手机绑定成功！", map1, "1");
        }else {
            resultBean =  new ResultBean("2000", "1|手机绑定失败！", "", "1");
        }
        return resultBean;
    }

    /**
     * 微信绑定h5
     * @param request
     * @return
     */
    @Override
    public ResultBean WxBindingMobileh5(HttpServletRequest request) {
        ResultBean resultBean = null;
        String userId = request.getParameter("userId");
        if(userId == null || "".equals(userId)){
            return new ResultBean("2000", "0|用户id为空！");
        }
        String mobile = request.getParameter("mobile");
        if(mobile == null || "".equals(mobile)){
            return new ResultBean("2000", "1|手机号为空！");
        }
        if(matchesPhoneNumber(mobile)<1){
            return new ResultBean("2000", "1|手机号不正确！");
        }
        String verificationcode = request.getParameter("verificationcode");
        if(verificationcode == null || "".equals(verificationcode)){
            return new ResultBean("2000", "1|验证码为空！");
        }
        String s = userInfoMapper.CheckMobile(mobile);
        if(s!=null && !"".equals(s)){
            return new ResultBean("2000", "1|手机号已经被其他人绑定！！！");
        }
        Map<String,Object> map1 = new HashMap<>();
        map1.put("mobile",mobile);
        map1.put("verificationcode",verificationcode);
        map1.put("vtype",3);
        VerificationCode verificationCode = verificationCodeMapper.checkVcode(map1);
        System.out.println("verificationCode"+verificationCode);
        if(verificationCode == null || "".equals(verificationCode)){
            return new ResultBean("2000", "1|验证码不正确！");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setMobile(mobile);
        int i = userInfoMapper.updateByPrimaryKeySelective(userInfo);
        if(i>0){
            resultBean =  new ResultBean("1000", "1|手机绑定成功！", "", "1");
        }else {
            resultBean =  new ResultBean("2000", "1|手机绑定失败！", "", "1");
        }
        return resultBean;
    }

    /**
     * XIUGAI密码
     * @param map
     * @return
     */
    @Override
    public ResultBean SeekPassword(Map<String, Object> map) {
        ResultBean resultBean = null;
        String userId = map.get("userId").toString();
        if(userId == null || "".equals(userId)){
            return new ResultBean("2000", "0|用户id为空！");
        }
        String newPassword = map.get("newPassword").toString();
        if(newPassword == null || "".equals(newPassword)){
            return new ResultBean("2000", "1|密码为空！");
        }
        String twoPassword = map.get("twoPassword").toString();
        if(twoPassword == null || "".equals(twoPassword)){
            return new ResultBean("2000", "1|密码为空！");
        }
        if(!twoPassword.equals(newPassword)){
            return new ResultBean("2000", "1|两次密码不相同！");
        }
        newPassword = OruitMD5.getMD5UpperCaseStr(OruitMD5.getMD5UpperCaseStr(newPassword));
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setPassword(newPassword);
        int i = userInfoMapper.updateByPrimaryKeySelective(userInfo);
        if(i>0){
            resultBean =  new ResultBean("1000", "1|成功", "", "1");
        }else {
            resultBean =  new ResultBean("2000", "1|修改失败", "", "1");
        }
        return resultBean;
    }

    @Override
    public ResultBean CheckVcode(Map<String, Object> map) {
        ResultBean resultBean = null;
        String mobile = map.get("mobile").toString();
        if(mobile == null || "".equals(mobile)){
            return new ResultBean("2000", "1|手机号为空！");
        }
        String verificationcode = map.get("verificationcode").toString();
        if(verificationcode == null || "".equals(verificationcode)){
            return new ResultBean("2000", "1|验证码为空！");
        }
        Map<String,Object> map1 = new HashMap<>();
        map1.put("mobile",mobile);
        map1.put("verificationcode",verificationcode);
        map1.put("vtype",2);
        VerificationCode verificationCode = verificationCodeMapper.checkVcode(map1);
        System.out.println("verificationCode"+verificationCode);
        if(verificationCode == null || "".equals(verificationCode)){
            resultBean =   new ResultBean("2000", "1|验证码不正确！");
        }else{
            resultBean =   new ResultBean("1000", "0|正确！");
        }
        return resultBean;
    }

    /**
     * 微信授权登录  ----->  H5
     * @param request
     * @return
     * @throws ParseException
     * @throws UnsupportedEncodingException
     */
    @Override
    public ResultBean WXAuthorizationH5(HttpServletRequest request) throws ParseException, UnsupportedEncodingException {
        ResultBean resultBean = null;
        String code = request.getParameter("code");
        if(code == null || "".equals(code)){
            return new ResultBean("2000", "0|code为空！");
        }
        String cid = request.getParameter("cid");
        if(cid == null || "".equals(cid)){
            return new ResultBean("2000", "0|code为空！");
        }
        String id = request.getParameter("id");
        if(id == null || "".equals(id)){
            return new ResultBean("2000", "0|code为空！");
        }
        GetAccessTokenRsp weiXinAccessToken = WeiXinAccessTokenUtil.getWeiXinAccessToken(code);
        JSONObject info = WeiXinAccessTokenUtil.info(weiXinAccessToken.getAccessToken(), weiXinAccessToken.getOpenid());
        System.out.println("-----------------weiXinAccessToken:"+weiXinAccessToken);
        System.out.println("-----------------info:"+info);

        Map<String,Object> map = new HashMap<>();
        String unionId = info.get("unionid").toString();
        String openid = info.get("openid").toString();
        map.put("openId",openid);
        map.put("unionId",unionId);
        //判断是否是同一个公众号
        String s = authorizationMapper.queryOpenid(unionId);
        if(s != null && !"".equals(s)){
            if(!openid.equals(s)){
                //更新openid
                Authorization authorization = new Authorization();
                authorization.setOpenId(openid);
                authorization.setUnionId(unionId);
                authorizationMapper.updateopenid(authorization);
            }
        }
        String nickname = info.get("nickname").toString();
        if(nickname != null && !"".equals(nickname)){
            nickname = EmojiFilterUtils.filterEmoji(nickname);
            nickname = new String(nickname.getBytes("ISO-8859-1"), "utf-8");
        }else{
            nickname = "";
        }
        System.out.println("-----------:"+nickname);
        map.put("nickname",nickname);
        map.put("photo",info.get("headimgurl").toString());
        map.put("sex",info.get("sex").toString());
        map.put("province", info.get("province").toString());
        map.put("city",info.get("city").toString());
        String userid = userAuthorizationMapper.queryUserid(unionId);
        System.out.println("+++++++++++++userid:"+userid);
        UserInfo userInfo1 = null;
        Map<String, Object> map1 = null;
        System.out.println("userid:"+userid);
        String authorization = "";
        if(userid ==null || "".equals(userid)){
             authorization = authorization(map, unionId, userid, userInfo1,id,cid);
            System.out.println("---------------------------"+authorization);
            if(authorization!=null && !"".equals(authorization)){
                resultBean =  new ResultBean("1000", "1|100", authorization, "1");//授权成功
            }else{
                resultBean =  new ResultBean("2000", "1|授权失败！");
            }
        }else{
            //1.登录
            UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userid);
            if(userInfo != null && !"".equals(userInfo)){
                resultBean =  new ResultBean("1000", "1|101", userid, "1");//01已注册过

                String query = userRedpacketMapper.query(userid);
                if(query!=null && !"".equals(query)){
                    resultBean =  new ResultBean("1000", "1|103", userid, "1");//103|已领取过红包
                }
                String s1 = caipiaoOrderInfoMapper.queryUseridOrder(userid);
                if(s1!=null && !"".equals(s1)){
                    resultBean =  new ResultBean("1000", "1|104", userid, "1");//104|已购买
                }
                if(userInfo.getMobile()!=null && !"".equals(userInfo.getMobile())){
                    resultBean =  new ResultBean("1000", "1|102", userid, "1");//已绑定过手机
                }
            }else{
                //重新授权
                 authorization = authorization(map, unionId, userid, userInfo1,id,cid);
                System.out.println("----------------1111111111-----------"+authorization);
                if(authorization!=null && !"".equals(authorization)){
                    resultBean =  new ResultBean("1000", "1|100", authorization, "1");
                }else{
                    resultBean =  new ResultBean("2000", "1|授权失败！");
                }
            }
        }
        return resultBean;
    }

    public static void main(String[] args) {
       String str = "\\3\\5";
        System.out.println(str.replaceAll("\\\\",""));
    }

    /**
     * 手机号注册绑定微信
     * @param
     * @return
     */
    @Override
    public ResultBean MobileBindingWX(Map<String,Object> map) throws ParseException, UnsupportedEncodingException {
        ResultBean resultBean = null;
        String unionId = map.get("unionId").toString();
        if(unionId == null || "".equals(unionId)){
            return new ResultBean("2000", "0|unionId为空！");
        }
        String userid = map.get("userid").toString();
        if(userid == null || "".equals(userid)){
            return new ResultBean("2000", "0|用户id为空！");
        }
        UserInfo userInfo1 = userInfoMapper.selectByPrimaryKey(userid);
        Integer wechatAuthorized = userInfo1.getWechatAuthorized();
        if(wechatAuthorized == 1){
            return new ResultBean("2000", "1|微信和手机号不能重复绑定！");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userid);
        userInfo.setUpdateTime(new Date());
        userInfo.setWechatAuthorized(1);
        String nickname = EmojiFilterUtils.filterEmoji(map.get("nickname").toString());
        System.out.println("微信号昵称：-----------："+nickname);
        System.out.println("微信号昵称map.get(\"nickname\").toString()：-----------："+map.get("nickname").toString());
      //  nickname = new String(nickname.getBytes("ISO-8859-1"), "UTF-8");
        userInfo.setWechatNickName(nickname);
        userInfo.setWechatPhoto(map.get("photo").toString()!=null ? map.get("photo").toString() : " " );
        userInfo.setWechatGender(map.get("sex").toString()!=null ? map.get("sex").toString() : " " );
        userInfo.setWechatProvince(map.get("province").toString()!=null ? map.get("province").toString() : " " );
        userInfo.setWechatCity(map.get("city").toString()!=null ? map.get("city").toString() : " " );
        int i = userInfoMapper.updateByPrimaryKeySelective(userInfo);
        if(i > 0){
            resultBean =  new ResultBean("1000", "1|绑定成功", userid, "1");
        }else{
            resultBean =  new ResultBean("1000", "1|绑定失败", userid, "1");
        }

        //保存授权信息
        UserAuthorization userAuthorization = new UserAuthorization();
        userAuthorization.setAuthType(1);
        userAuthorization.setCreateTime(new Date());
        userAuthorization.setUnionId(unionId);
        userAuthorization.setUserId(userid);
        userAuthorizationMapper.insertSelective(userAuthorization);

        Authorization authorization= new Authorization();
        authorization.setOpenId(map.get("openId").toString());
        authorization.setCreateTime(new Date());
        authorization.setUnionId(unionId);
        authorizationMapper.insertSelective(authorization);
        return resultBean;
    }
    /**
     * 手机号注册绑定支付宝
     * @param
     * @return
     */
    @Override
    public ResultBean MobileBindingAlipay(Map<String,Object> map) throws ParseException, UnsupportedEncodingException {
        ResultBean resultBean = null;
        String alipayaccount = map.get("alipayaccount").toString();
        String alipaynickname = map.get("alipaynickname").toString();
        String alipayphoto = map.get("alipayphoto").toString();
        String alipayrealname = map.get("alipayrealname").toString();

        String userid = map.get("userid").toString();
        if(userid == null || "".equals(userid)){
            return new ResultBean("2000", "0|用户id为空！");
        }
        UserInfo userInfo1 = userInfoMapper.selectByPrimaryKey(userid);
        Integer wechatAuthorized = userInfo1.getWechatAuthorized();
        if(wechatAuthorized == 1){
            return new ResultBean("2000", "1|微信和手机号不能重复绑定！");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userid);
        userInfo.setUpdateTime(new Date());
        userInfo.setAlipayAuthorized(1);
        userInfo.setAlipayAccount(alipayaccount);
        userInfo.setAlipayNickName(alipaynickname);
        userInfo.setAlipayPhoto(alipayphoto);
        userInfo.setAlipayRealName(alipayrealname);
        int i = userInfoMapper.updateByPrimaryKeySelective(userInfo);
        if(i > 0){
            resultBean =  new ResultBean("1000", "1|绑定成功", userid, "1");
        }else{
            resultBean =  new ResultBean("1000", "1|绑定失败", userid, "1");
        }
        return resultBean;
    }

    /**
     * 授权 ---> 微信的授权
     * @param map
     * @param unionId
     * @param userid
     * @param userInfo1
     * @param id
     * @param cid
     * @return
     * @throws ParseException
     * @throws UnsupportedEncodingException
     */
    private String authorization(Map<String, Object> map, String unionId, String userid, UserInfo userInfo1,String id, String cid) throws ParseException, UnsupportedEncodingException {
        int i = 0 ;
        String userId = "";
        if(userInfo1==null || "".equals(unionId)){
            //1.进行授权
            userid = UUIDUtils.uuid();
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(userid);
            userInfo.setMobile("");
            userInfo.setUserState(1);
            if(Integer.parseInt(id) == 0 && Integer.parseInt(cid) == 0){
                userInfo.setParentId(0);
                userInfo.setUserShipLevel(1);
                userInfo.setChannelId(0);
            }
            if((Integer.parseInt(id) != 0 && Integer.parseInt(cid) == 0) || (Integer.parseInt(id) != 0 && Integer.parseInt(cid) != 0)){
                UserShare userShare = userShareMapper.selectByPrimaryKey(Integer.parseInt(id));
                String userId1 = userShare.getUserId();//根据id取出userid
                UserInfo userInfo3 = userInfoMapper.selectByPrimaryKey(userId1);
                Integer userShipLevel = userInfo3.getUserShipLevel();
                userShipLevel = userShipLevel + 1 ;
                System.out.println("------userShipLevel--------:"+userShipLevel);
                userInfo.setUserShipLevel(userShipLevel);
                userInfo.setParentId(Integer.parseInt(id));
                //查询是否存在父id
                String userShipPath = "";
                Integer parentId = userInfo3.getParentId();
                if(parentId!=0){
                    userShipPath = userInfo3.getUserShipPath();
                }
                UserStatistics userStatistics = userStatisticsMapper.selectByPrimaryKey(userId1);
                Integer tudiNum = userStatistics.getTudiNum();
                Integer tusunNum = userStatistics.getTusunNum();
                if(userShipPath != null && !"".equals(userShipPath)){
                    userShipPath =userShipPath + "|" + userId1 + "|";
                    userStatistics.setTudiNum(tudiNum+1);
                }else{
                    userShipPath = userId1 +"|";
                    userStatistics.setTusunNum(tusunNum+1);
                }
                userStatistics.setUpdateTime(new Date());
                int i1 = userStatisticsMapper.updateByPrimaryKeySelective(userStatistics);
                if(i1>0){
                    UserStatisticsLog userStatisticsLog = new UserStatisticsLog();
                    userStatisticsLog.setUserId(userid);
                    userStatisticsLog.setBalanceAmount(userStatistics.getBalanceAmount());
                    userStatisticsLog.setWinningBalabceAmount(userStatistics.getWinningBalabceAmount());
                    userStatisticsLog.setWinningNum(userStatistics.getWinningNum());
                    userStatisticsLog.setTusunNum(userStatistics.getTusunNum());
                    userStatisticsLog.setTudiNum(userStatistics.getTudiNum());
                    userStatisticsLog.setBettingNum(userStatistics.getBettingNum());
                    userStatisticsLog.setCreateTime(new Date());
                    userStatisticsLog.setTotalAmount(userStatistics.getTotalAmount());
                    userStatisticsLog.setWinningTotalAmount(userStatistics.getWinningTotalAmount());
                    userStatisticsLogMapper.insertSelective(userStatisticsLog);
                }
                userInfo.setUserShipPath(userShipPath);
                userInfo.setChannelId(0);

                //领取红包
                registerRedPacker(userId1);


            }
            if(Integer.parseInt(id) == 0 && Integer.parseInt(cid) != 0){
                userInfo.setParentId(0);
                userInfo.setUserShipLevel(1);
                userInfo.setChannelId(Integer.parseInt(cid));
            }
            userInfo.setFirstLoginTime(new Date());
            userInfo.setUpdateTime(new Date());
            userInfo.setJoinDate(new Date());
            userInfo.setAlipayAuthorized(0);
            userInfo.setWechatAuthorized(1);
            String nickname = map.get("nickname").toString();
            if(nickname != null && !"".equals(nickname)){
                nickname = EmojiFilterUtils.filterEmoji(nickname);
            }else{
                nickname = "";
            }
            //nickname = new String(nickname.getBytes(), "UTF-8");
            userInfo.setWechatNickName(nickname);
            userInfo.setWechatPhoto(map.get("photo").toString()!=null ? map.get("photo").toString() : " " );
            userInfo.setWechatGender(map.get("sex").toString()!=null ? map.get("sex").toString() : " " );
            userInfo.setWechatProvince(map.get("province").toString()!=null ? map.get("province").toString() : " " );
            userInfo.setWechatCity(map.get("city").toString()!=null ? map.get("city").toString() : " " );


            i = userInfoMapper.insertSelective(userInfo);
            userId = userInfo.getUserId();
            System.out.println("--------------------usetid:"+userId);
        }
        UserStatistics userStatistics = new UserStatistics();
        userStatistics.setUserId(userId);
        userStatistics.setWinningBalabceAmount(new BigDecimal("0.00"));
        userStatistics.setUpdateTime(new Date());
        userStatistics.setCreateTime(new Date());
        userStatisticsMapper.insertSelective(userStatistics);

        //保存授权信息
        UserAuthorization userAuthorization = new UserAuthorization();
        userAuthorization.setAuthType(1);
        userAuthorization.setCreateTime(new Date());
        userAuthorization.setUnionId(unionId);
        userAuthorization.setUserId(userId);
        userAuthorizationMapper.insertSelective(userAuthorization);

        Authorization authorization= new Authorization();
        authorization.setOpenId(map.get("openId").toString());
        authorization.setCreateTime(new Date());
        authorization.setUnionId(unionId);
        authorizationMapper.insertSelective(authorization);
        //tuisong
        insertJPush(userId);
        return userId;
    }

    private int registerRedPacker(String userId) throws ParseException {
        ConfigInfo configInfo = configInfoMapper.selectByConnfigCode(Constants.XINREN_CONFIG);
        String configValue = configInfo.getConfigValue();
        System.out.println("--------configValue"+configValue);
        JSONObject jsonObject = JSONObject.parseObject(configValue);
        String xinrenhongbao = jsonObject.get("xinrenhongbao").toString();
        Redacket redacket = redacketMapper.selectByPrimaryKey(Integer.parseInt(xinrenhongbao));
        Integer validDays = redacket.getValidDays();
        Date createTime = redacket.getCreateTime();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(createTime);
        c.add(Calendar.DATE, validDays );
        UserRedpacket userRedpacket = new UserRedpacket();
        userRedpacket.setState(0);
        userRedpacket.setCreateTime(new Date());
        userRedpacket.setBeginTime(new Date());
        userRedpacket.setUserId(userId);
        userRedpacket.setRedpacketId(1);
        Date time = c.getTime();
        userRedpacket.setOverTime(date.parse(date.format(time)));
        return userRedpacketMapper.insertSelective(userRedpacket);
    }
}
