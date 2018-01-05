package com.oruit.app.service.impl;/**
 * Created by wyt on 2017/9/1.
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oruit.app.dao.*;
import com.oruit.app.dao.model.*;
import com.oruit.app.message.MessagePushUtil;
import com.oruit.app.service.PhonePageService;
import com.oruit.app.ssq.Issueno;
import com.oruit.app.ssq.shuagnseqiu;
import com.oruit.app.util.DateRandom;
import com.oruit.app.util.PayUtil;
import com.oruit.app.util.config.Constants;
import com.oruit.app.util.web.ResultBean;
import com.oruit.app.util.web.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.oruit.app.util.XMLClient.oltpQueryTermInfoServlet;

/**
 * 手机端相关接口
 *
 * @author wyt
 * @create 2017-09-01 15:11
 */
@Service
public class PhonePageServiceImpl implements PhonePageService {
    @Autowired
    private VerificationCodeMapper verificationCodeMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserRedpacketMapper userRedpacketMapper;
    @Autowired
    private CaipiaoOrderInfoMapper caipiaoOrderInfoMapper;
    @Autowired
    private UserStatisticsMapper userStatisticsMapper;
    @Autowired
    private RedacketMapper redacketMapper;
    @Autowired
    private ConfigInfoMapper configInfoMapper;
    @Autowired
    private UserShareMapper userShareMapper;
    @Autowired
    private UserStatisticsLogMapper userStatisticsLogMapper;
    @Autowired
    private KuaileshifenOrderMapper kuaileshifenOrderMapper;
    @Autowired
    private ShuangseqiuPrizeMapper shuangseqiuPrizeMapper;
    @Autowired
    private KuaileshifenPrizeMapper kuaileshifenPrizeMapper;
    @Autowired
    private UserAccountStatementMapper userAccountStatementMapper;
    @Autowired
    private NoticeBoardMapper noticeBoardMapper;
    @Autowired
    private MessageInfoMapper messageInfoMapper;
    @Autowired
    private ShuangseqiuOrderMapper shuangseqiuOrderMapper;
    @Autowired
    private UserForbiddenMapper userForbiddenMapper;

    /**
     * 浏览器端手机注册
     *
     * @param request
     * @return
     * @throws ParseException
     */
    @Override
    @Transactional
    public ResultBean PhonePageRegister(HttpServletRequest request) throws ParseException {
        ResultBean resultBean = null;
        String cid = request.getParameter("cid");
        if (cid == null || "".equals(cid)) {
            return new ResultBean("2000", "0|渠道id为空！");
        }
        String id = request.getParameter("id");
        if (id == null || "".equals(id)) {
            return new ResultBean("2000", "0|用户id为空！");
        }
        String mobile = request.getParameter("mobile");
        if (mobile == null || "".equals(mobile)) {
            return new ResultBean("2000", "1|手机号为空！");
        }
        String verificationcode = request.getParameter("verificationcode");
        Map<String, Object> map = new HashMap<>();
        String userid = "";
        if ("".equals(verificationcode) || verificationcode == null) {
            String s = userInfoMapper.CheckMobile(mobile);
            if (s != null && !"".equals(s)) {
                userid = userInfoMapper.QueryMobileUserid(mobile);
                String query = userRedpacketMapper.query(userid);
                String s1 = caipiaoOrderInfoMapper.queryUseridOrder(userid);
                if (s1 != null && !"".equals(s1)) {
                    map.put("userid", userid);
                    return new ResultBean("1000", "1|104", map, "1");
                }
                if (query != null && !"".equals(query)) {
                    map.put("userid", userid);
                    return new ResultBean("1000", "1|101", map, "1");
                }
                map.put("userid", userid);
                return new ResultBean("1000", "1|领取成功", map, "1");
            } else {
                return new ResultBean("2000", "1|注册失败！");
            }
        } else {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("mobile", mobile);
            map1.put("verificationcode", verificationcode);
            map1.put("vtype", 1);
            VerificationCode verificationCode = verificationCodeMapper.checkVcode(map1);
            System.out.println("verificationCode" + verificationCode);
            if (verificationCode == null || "".equals(verificationCode)) {
                return new ResultBean("2000", "1|验证码不正确！");
            }
            String s = userInfoMapper.CheckMobile(mobile);
            if (s != null && !"".equals(s)) {
                return new ResultBean("2000", "1|手机号已经注册过！");
            }
            String temp = UUIDUtils.uuid();
            Date parse = new SimpleDateFormat("yyyy-MM-dd").parse("1990-01-01");
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(temp);
            userInfo.setMobile(mobile);
            userInfo.setUserState(1);
            userInfo.setFirstLoginTime(parse);
            userInfo.setUpdateTime(new Date());
            userInfo.setJoinDate(new Date());
            userInfo.setWechatAuthorized(0);

            if (Integer.parseInt(id) == 0 && Integer.parseInt(cid) == 0) {
                userInfo.setParentId(0);
                userInfo.setUserShipLevel(1);
            }
            if ((Integer.parseInt(id) != 0 && Integer.parseInt(cid) == 0) || (Integer.parseInt(id) != 0 && Integer.parseInt(cid) != 0)) {
                UserShare userShare = userShareMapper.selectByPrimaryKey(Integer.parseInt(id));
                String userId1 = userShare.getUserId();//根据id取出userid
                UserInfo userInfo3 = userInfoMapper.selectByPrimaryKey(userId1);
                Integer userShipLevel = userInfo3.getUserShipLevel();
                userShipLevel = userShipLevel + 1;
                System.out.println("------userShipLevel--------:" + userShipLevel);
                userInfo.setUserShipLevel(userShipLevel);
                userInfo.setParentId(Integer.parseInt(id));

                //查询是否存在父id
                String userShipPath = "";
                Integer parentId = userInfo3.getParentId();
                if (parentId != 0) {
                    userShipPath = userInfo3.getUserShipPath();
                }

                UserStatistics userStatistics = userStatisticsMapper.selectByPrimaryKey(userId1);
                Integer tudiNum = userStatistics.getTudiNum();
                Integer tusunNum = userStatistics.getTusunNum();

                if (userShipPath != null && !"".equals(userShipPath)) {
                    userShipPath = userShipPath + "|" + userId1 + "|";
                    userStatistics.setTudiNum(tudiNum + 1);
                } else {
                    userShipPath = userId1 + "|";
                    userStatistics.setTusunNum(tusunNum + 1);
                }
                userStatistics.setUpdateTime(new Date());
                int i1 = userStatisticsMapper.updateByPrimaryKeySelective(userStatistics);
                if (i1 > 0) {
                    UserStatisticsLog userStatisticsLog = new UserStatisticsLog();
                    userStatisticsLog.setUserId(userId1);
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
                String content = "";
                Map<String, String> messageMap = new HashMap<>();
                messageMap.put("Id", "0");
                messageMap.put("Title", "红包奖励");
                messageMap.put("Type", "3");
                messageMap.put("Content", content);
                messageMap.put("CreateTime", new Date().toString());
                messageMap.put("From", "0");
                messageMap.put("IsRead", "0");
                List<String> userList = new ArrayList<>();
                MessageInfo messageinfo = new MessageInfo();
                userList.add(userid.toString());
                //推送
                String title = "红包奖励";
                String publisher = "0";
                Integer msgtype = 3;
                //插入消息列表
                content = "恭喜您成功邀请好友" + userid + "特奖励红包一个";
                InsertMessage(content, messageinfo, userid, title, publisher, msgtype);
                content = "你又一个红包入账";
                messageMap.put("Content", content);
                //推送
                MessagePushUtil.SendPushV3(userList, messageMap, content, "系统消息", 1);

            }

            if (Integer.parseInt(id) == 0 && Integer.parseInt(cid) != 0) {
                userInfo.setParentId(0);
                userInfo.setUserShipLevel(1);
                userInfo.setChannelId(Integer.parseInt(cid));
            }
            int i = userInfoMapper.insertSelective(userInfo);
            String userId = userInfo.getUserId();
            if (i > 0) {
                UserStatistics userStatistics = new UserStatistics();
                userStatistics.setUserId(temp);
                userStatistics.setWinningBalabceAmount(new BigDecimal("0.00"));
                userStatistics.setUpdateTime(new Date());
                userStatistics.setCreateTime(new Date());
                userStatisticsMapper.insertSelective(userStatistics);
                //领取红包
                int i1 = registerRedPacker(userId);
                if (i1 > 0) {
                    map.put("userid", userId);
                    return new ResultBean("1000", "1|领取成功", map, "1");
                } else {
                    return new ResultBean("2000", "1|领取失败！");
                }
            } else {
                resultBean = new ResultBean("2000", "1|注册失败！");
            }
        }
        return resultBean;
    }

    /**
     * 插入消息列表
     *
     * @param content
     * @param messageinfo
     * @param userid
     * @param title
     * @param publisher
     * @param msgtype
     */
    private void InsertMessage(String content, MessageInfo messageinfo, String userid, String title, String publisher, Integer msgtype) {
        messageinfo.setUserId(userid);
        messageinfo.setContent(content);
        messageinfo.setCreateTime(new Date());
        messageinfo.setDeleted(0);
        messageinfo.setIsRead(0);
        messageinfo.setPublisher(publisher);
        messageinfo.setMsgType(msgtype);
        messageinfo.setTitle(title);
        messageinfo.setFlag("");
        messageinfo.setUpdateTime(new Date());
        messageInfoMapper.insertSelective(messageinfo);
    }

    private int registerRedPacker(String userId) throws ParseException {
        ConfigInfo configInfo = configInfoMapper.selectByConnfigCode(Constants.XINREN_CONFIG);
        String configValue = configInfo.getConfigValue();
        System.out.println("--------configValue" + configValue);
        JSONObject jsonObject = JSONObject.parseObject(configValue);
        String xinrenhongbao = jsonObject.get("xinrenhongbao").toString();
        Redacket redacket = redacketMapper.selectByPrimaryKey(Integer.parseInt(xinrenhongbao));
        Integer validDays = redacket.getValidDays();
        Date createTime = redacket.getCreateTime();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(createTime);
        c.add(Calendar.DATE, validDays);
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

    /**
     * 浏览器端快乐十分选号
     *
     * @param request
     * @return
     */
    @Override
    public ResultBean BrowserChoose(HttpServletRequest request) throws ParseException {
        ResultBean resultBean = null;
        String userid = request.getParameter("userid");
        if (userid == null || "".equals(userid)) {
            return new ResultBean("2000", "0|用户id为空！");
        }
        String number = request.getParameter("number");
        if (number == null || "".equals(number)) {
            return new ResultBean("2000", "1|请选择号码！");
        }
        String s = caipiaoOrderInfoMapper.queryUseridOrder(userid);
        if (s != null && !"".equals(s)) {
            return new ResultBean("2000", "1|已经购买过彩票！");
        }
        String[] split = number.split(" ");
        int length = split.length;
        if (length < 2) {
            return new ResultBean("2000", "1|请选择足够的号码！");
        }
        CaipiaoOrderInfo caipiaoOrderInfo = new CaipiaoOrderInfo();
        SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd");
        caipiaoOrderInfo.setCaipiaoOrderId(UUIDUtils.uuid());
        caipiaoOrderInfo.setCaipiaoId(114);
        caipiaoOrderInfo.setCaipiaoOrderCode(PayUtil.getTradeNo());
        caipiaoOrderInfo.setUserId(userid);
        caipiaoOrderInfo.setState(1);
        caipiaoOrderInfo.setPayType(1);
        caipiaoOrderInfo.setUsedRedpacket(1);
        caipiaoOrderInfo.setRedpacketId(1);
        caipiaoOrderInfo.setRedpacketAmount(new BigDecimal(1));
        caipiaoOrderInfo.setCreateTime(new Date());
        caipiaoOrderInfo.setUpdateTime(ymdhms.parse("1990-01-01"));
        caipiaoOrderInfo.setPayTime(ymdhms.parse("1990-01-01"));
        caipiaoOrderInfo.setTotal(new BigDecimal("0"));
        caipiaoOrderInfo.setAmount(new BigDecimal("0"));
        int i = caipiaoOrderInfoMapper.insertSelective(caipiaoOrderInfo);
        String caipiaoOrderId = caipiaoOrderInfo.getCaipiaoOrderId();
        if (i <= 0) {
            return new ResultBean("2000", "0|下单失败！！！");
        }
        KuaileshifenOrder kuaileshifenOrder = new KuaileshifenOrder();
        kuaileshifenOrder.setAmount(new BigDecimal("2"));
        kuaileshifenOrder.setBeishu(1);
        kuaileshifenOrder.setCaipiaoOrderId(caipiaoOrderId);
        kuaileshifenOrder.setCaipiaoOrderSubCode(PayUtil.getTradeNo() + 1);
        kuaileshifenOrder.setCreateTime(new Date());
        kuaileshifenOrder.setZhushu(1);
        kuaileshifenOrder.setBeishu(1);
        kuaileshifenOrder.setPlayType(1);
        kuaileshifenOrder.setNumber(number);
        kuaileshifenOrder.setIssueNo(shuagnseqiu.KLSFissueno());
        kuaileshifenOrder.setQishu(1);
        kuaileshifenOrder.setRedpacketAmount(new BigDecimal(1));
        kuaileshifenOrder.setCaipiaoOrderSubId(UUIDUtils.uuid());
        if (length == 2) {
            kuaileshifenOrder.setPlayMethod("r2");
        }
        if (length == 3) {
            kuaileshifenOrder.setPlayMethod("r3");
        }
        if (length == 4) {
            kuaileshifenOrder.setPlayMethod("r4");
        }
        if (length == 5) {
            kuaileshifenOrder.setPlayMethod("r5");
        }
        int i1 = kuaileshifenOrderMapper.insertSelective(kuaileshifenOrder);
        Map<String, Object> items = new HashMap<>();
        if (i1 > 0) {
            String caipiaoOrderId1 = kuaileshifenOrder.getCaipiaoOrderId();
            items.put("userid", userid);
            items.put("orderid", caipiaoOrderId1);
        } else {
            return new ResultBean("2000", "0|下单失败！！！");
        }
        return new ResultBean("1000", "0|成功！", items, "1");
    }

    /**
     * H5顶部开奖信息
     *
     * @param request
     * @return
     */
    @Override
    public ResultBean TopWinInfoH5(HttpServletRequest request) {
        String type = request.getParameter("type");
        if (type == null || "".equals(type)) {
            return new ResultBean("2000", "0|类型为空！！！");
        }
        Map<String, Object> stringObjectMap = new HashMap<>();
        if ("1".equals(type)) {//双色球
            stringObjectMap = shuangseqiuPrizeMapper.TopWinInfoSSQ();
        }
        if ("2".equals(type)) {//快乐十分
            stringObjectMap = kuaileshifenPrizeMapper.TopWinInfoKLSF();
        }
        return new ResultBean("1000", "0|成功！", stringObjectMap, "1");
    }

    /**
     * 获取推广的期号
     *
     * @param request
     * @return
     */
    @Override
    public ResultBean getIssueno(HttpServletRequest request) {
        Map<String, Object> dateRandom = DateRandom.getDateRandom();
        return new ResultBean("1000", "0|成功！", dateRandom, "1");
    }

    @Override
    public ResultBean H5Guide(HttpServletRequest request) throws ParseException {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> maps = new HashMap<>();
        List<Map<String, Object>> BulletinBoard = new ArrayList<>();
        Map<String, Object> BulletinBoards = new HashMap<>();
        BulletinBoards = noticeBoardMapper.queryNoticeBoardlimit();
        if (BulletinBoards == null || "".equals(BulletinBoards)) {
            /**
             * 查询账户明细
             */
            BulletinBoard = getBulletinBoard(map);
        } else {
            String createtime = BulletinBoards.get("createtime").toString();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (!"".equals(createtime) && createtime != null) {
                Date parse = format.parse(createtime);
                long l = new Date().getTime() - parse.getTime();
                long i = 60 * 1000 * 10;
                if (l >= i) {
                    /**
                     * 大于10分钟查询账户明细
                     */
                    BulletinBoard = getBulletinBoard(map);
                    BulletinBoard = noticeBoardMapper.queryNoticeBoard();
                    if (BulletinBoard.size() <= 0) {
                        BulletinBoard = noticeBoardMapper.queryNoticeBoard();
                    }
                } else {
                    /**
                     * 查询轮播表
                     */
                    BulletinBoard = noticeBoardMapper.queryNoticeBoard();
                }
            }
            map.put("BulletinBoard", BulletinBoard);
        }
        SimpleDateFormat format11 = new SimpleDateFormat("yyyyMMdd");
        String format1 = format11.format(new Date());
        Map<String, Object> stringObjectMap = kuaileshifenPrizeMapper.KLSFNewInfo();
        String s = shuagnseqiu.isKLSFissueno();
        if ("false".equals(s)) {
            s = shuagnseqiu.KLSFissuenoquery();
        }
        String format = String.format("%02d", Integer.parseInt(s));
        String issueno = format1 + "0" + format;
        System.out.println("-------issueno------------:" + issueno + "---------");
        Map<String, Object> stringObject = oltpQueryTermInfoServlet("10", issueno);
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar nowTime = Calendar.getInstance();
        if (stringObject == null || "".equals(stringObject) || stringObject.isEmpty()) {
            Map<String, Object> date = new HashMap<>();
            if (Integer.parseInt(format) > 84 && !"1000".equals(format)) {
                date = shuagnseqiu.getDate(0, "1");
            } else {
                date = shuagnseqiu.getDate(1, format);
            }
            stringObjectMap.put("startdate", date.get("startdate").toString());
            stringObjectMap.put("enddate", date.get("enddate").toString());
        } else {
            String startTime = stringObject.get("startTime").toString();
            if (startTime == null || "".equals(startTime)) {
                startTime = "";
            } else {
                startTime = getString(format2, nowTime, startTime);
            }
            String endTime = stringObject.get("endTime").toString();
            if (endTime == null || "".equals(endTime)) {
                endTime = "";
            } else {
                endTime = getString(format2, nowTime, endTime);
            }
            stringObjectMap.put("startdate", startTime);
            stringObjectMap.put("enddate", endTime);
        }
        stringObjectMap.put("servertime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        if (Integer.parseInt(format) > 84 && !"1000".equals(format)) {
            issueno = getString(format11, format1);
        }
        stringObjectMap.put("issueno", issueno);
        if ("1000".equals(s)) {
            long time = new Date().getTime();//当前时间
            SimpleDateFormat format4 = new SimpleDateFormat("yyyy-MM-dd");
            String format5 = format4.format(new Date());
            String s1 = format5 + " 08:59:00";
            String s2 = format5 + " 00:00:00";
            Date parse = format2.parse(s1);
            Date parse1 = format2.parse(s2);
            if (time - parse1.getTime() > 0 && parse.getTime() - time > 0) {
                issueno = getString(format11, format1);
                nowTime.setTime(new Date());
                nowTime.add(Calendar.DATE, -1);
                Date times = nowTime.getTime();
                String startTime = format4.format(times);
                startTime = startTime + " 23:00:00";
                stringObjectMap.put("startdate", startTime);
                stringObjectMap.put("enddate", s1);
                stringObjectMap.put("issueno", issueno);
            }
        }
        maps.putAll(stringObjectMap);
        maps.putAll(map);
        return new ResultBean("1000", "0|成功", maps, "1");
    }

    @Override
    public ResultBean H5GuideSSQ(HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> maps = new HashMap<>();
        List<Map<String, Object>> BulletinBoard = new ArrayList<>();
        Map<String, Object> BulletinBoards = new HashMap<>();
        BulletinBoards = noticeBoardMapper.queryNoticeBoardlimit();
        if (BulletinBoards == null || "".equals(BulletinBoards)) {
            /**
             * 查询账户明细
             */
            BulletinBoard = getBulletinBoard(map);
        } else {
            String createtime = BulletinBoards.get("createtime").toString();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (!"".equals(createtime) && createtime != null) {
                Date parse = format.parse(createtime);
                long l = new Date().getTime() - parse.getTime();
                long i = 60 * 1000 * 10;
                if (l >= i) {
                    /**
                     * 大于10分钟查询账户明细
                     */
                    BulletinBoard = getBulletinBoard(map);
                    BulletinBoard = noticeBoardMapper.queryNoticeBoard();
                    if (BulletinBoard.size() <= 0) {
                        BulletinBoard = noticeBoardMapper.queryNoticeBoard();
                    }
                } else {
                    /**
                     * 查询轮播表
                     */
                    BulletinBoard = noticeBoardMapper.queryNoticeBoard();
                }
            }
            map.put("BulletinBoard", BulletinBoard);
        }
        Map<String, Object> querywinning = shuangseqiuPrizeMapper.querywinning();
        String winnumber = "";
        String refernumber = "";
        String issueno = "";
        if (querywinning != null) {
            winnumber = querywinning.get("winnumber").toString();
            refernumber = querywinning.get("refernumber").toString();
            issueno = querywinning.get("issueno").toString();
        }
        maps.put("winnumber", winnumber);
        maps.put("refernumber", refernumber);
        maps.put("winissueno", issueno);
        String s = Issueno.achieveNum();
        maps.put("issueno", s);

        maps.putAll(map);
        return new ResultBean("1000", "0|成功", maps, "1");
    }

    /**
     * H5是否绑定手机号和可提现金额
     *
     * @param params
     * @return
     */
    @Override
    public ResultBean BangdingH5(Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        String userid = params.get("userid").toString();
        if (userid == null || "".equals(userid)) {
            return new ResultBean("2000", "0|用户id为空！");
        }
        UserStatistics userStatistics = userStatisticsMapper.selectByPrimaryKey(userid);
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userid);
        if (userStatistics == null || "".equals(userStatistics)) {
            return new ResultBean("2000", "0|用户不存在！");
        }
        if (userInfo == null || "".equals(userInfo)) {
            return new ResultBean("2000", "0|用户不存在！");
        }
        BigDecimal winningBalabceAmount = userStatistics.getWinningBalabceAmount();
        String mobile = userInfo.getMobile();
        result.put("winningBalabceAmount", winningBalabceAmount);
        if (mobile == null || "".equals(mobile)) {
            result.put("isbangding", "0");
        } else {
            result.put("isbangding", "1");
        }
        return new ResultBean("1000", "0|成功！", result, "1");
    }




    private String getString(SimpleDateFormat format11, String format1) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s1 = format1.substring(0, 4) + "-" + format1.substring(4, 6) + "-" + format1.substring(6, 8);
        String s = s1 + " 23:00:00";
        System.out.println("---------l:" + s);
        Date parse = format.parse(s);
        long l = parse.getTime() - new Date().getTime();
        System.out.println("---------l:" + l);
        String issueno = "";
        if (l < 0) {
            Calendar nowTimes = Calendar.getInstance();
            nowTimes.setTime(format11.parse(format1));
            nowTimes.add(Calendar.DATE, 1);
            Date time = nowTimes.getTime();
            issueno = format11.format(time) + "01";
        } else {
            issueno = format11.format(format11.parse(format1)) + "01";
        }
        System.out.println(issueno);
        System.out.println("--------------" + format1);

        return issueno;
    }

    private String getString(SimpleDateFormat format2, Calendar nowTime, String startTime) throws ParseException {
        Date parse = format2.parse(startTime);
        nowTime.setTime(parse);
        nowTime.add(Calendar.MINUTE, -1);
        Date time = nowTime.getTime();
        startTime = format2.format(time);
        return startTime;
    }

    private List<Map<String, Object>> getBulletinBoard(Map<String, Object> maps) {
        List<Map<String, Object>> BulletinBoard;
        String date = new SimpleDateFormat("yyyyMM").format(new Date());
        String tableName = "user_account_statement_" + date;
        maps.put("tableName", tableName);
        BulletinBoard = userAccountStatementMapper.queryUserAccountStatementBulletinBoard(maps);
        for (Map<String, Object> item : BulletinBoard) {
            item.remove("createtime");
            item.remove("bulletinboardid");
            String mobile = item.get("mobile").toString();
            mobile = mobile.substring(0, 3) + "****" + mobile.substring(7, 11);
            String mony = item.get("mony").toString();
            String result = "恭喜用户" + mobile + "中奖" + mony + "元";
            NoticeBoard noticeBoard = new NoticeBoard();
            noticeBoard.setCreateTime(new Date());
            noticeBoard.setDeleted(0);
            noticeBoard.setTitle(result);
            noticeBoardMapper.insertSelective(noticeBoard);
        }
        maps.remove("tableName");
        return BulletinBoard;
    }


    /**
     * 账户明细
     *
     * @param type
     * @param userid
     * @param account
     */
    private void userAccount(String type, String userid, BigDecimal account, Integer playId) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format1 = format.format(new Date());
        UserAccountStatementAccount userAccountStatement = new UserAccountStatementAccount();
        userAccountStatement.setAmount(account);
        userAccountStatement.setCreateTime(format1);
        userAccountStatement.setState(1);
        userAccountStatement.setType(type);
        userAccountStatement.setUpdateTime(format1);
        userAccountStatement.setUserId(userid);
        if (playId != 0) {
            userAccountStatement.setRemark(String.valueOf(playId));
        }
        System.out.println("//////////////////////////////////:" + playId);
        String date = new SimpleDateFormat("yyyyMM").format(new Date());
        String tableName = "user_account_statement_" + date;
        Integer integer = userAccountStatementMapper.existTable(tableName);
        if (integer > 0) {
            userAccountStatement.setTableName(tableName);
            userAccountStatementMapper.insertSelectiveAccount(userAccountStatement);
        }
    }


}
