package com.oruit.app.service.impl;/**
 * Created by wyt on 2017/8/28.
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oruit.app.dao.*;
import com.oruit.app.dao.model.*;
import com.oruit.app.logic.util.ConstUtil;
import com.oruit.app.service.WithdrawService;
import com.oruit.app.service.util.AppEnum;
import com.oruit.app.util.JsonDealUtil;
import com.oruit.app.util.LuhmcheckBankCard;
import com.oruit.app.util.VCodeUtil;
import com.oruit.app.util.config.Constants;
import com.oruit.app.util.web.ResultBean;
import com.oruit.weixin.api.TransfersAPI;
import com.oruit.weixin.bean.TransferReqData;
import com.oruit.weixin.bean.TransfersResData;
import com.oruit.weixin.util.SignatureUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 提现相关接口
 * @author wyt
 * @create 2017-08-28 15:29
 */
@Service
public class WithdrawServiceImpl implements WithdrawService {
    private static final Logger log = Logger.getLogger(WithdrawServiceImpl.class);
    //微信支付商品号
    @Value("#{configProperties['mchId']}")
    private String mchId;

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserForbiddenMapper userForbiddenMapper;
    @Autowired
    private UserStatisticsMapper userStatisticsMapper;
    @Autowired
    private CashRecordMapper cashRecordMapper;
    @Autowired
    private UserAuthorizationMapper userAuthorizationMapper;
    @Autowired
    private AuthorizationMapper authorizationMapper;
    @Autowired
    private  ProductInfoMapper productInfoMapper;
    @Autowired
    private ConfigInfoMapper configInfoMapper;
    @Autowired
    private UserBankCardMapper userBankCardMapper;
    @Autowired
    private UserStatisticsLogMapper userStatisticsLogMapper;
    @Autowired
    private UserAccountStatementMapper userAccountStatementMapper;
    @Override
    public ResultBean withdrawals(Map<String, Object> params) throws ParseException {

        String userId = params.get("userid").toString();
        String user_id = userId;
        String AlipayId = params.get("alipayid").toString();
        if(AlipayId == null || "".equals(AlipayId)){
            return new ResultBean("2000", "0|AlipayId 为空！");
        }
        String productId = params.get("productid").toString();
        if(productId == null || "".equals(productId)){
            return new ResultBean("2000", "0|商品为空！");
        }
        String Payee = params.get("payee").toString();
        if(Payee == null || "".equals(Payee)){
            return new ResultBean("2000", "0|收款人姓名为空！");
        }
        String ip = params.get("remoteAddr").toString();

        UserInfo userinfo = userInfoMapper.selectByPrimaryKey(userId);
        if (userinfo == null || "".equals(userinfo)) {
            return new ResultBean("2000", "0|用户不存在！");
        }
        String UnionId = userAuthorizationMapper.queryUnionId(userId);
        if(UnionId==null || "".equals(UnionId)){
            return new ResultBean("2000", "1|操作失败请联系客服！");
        }
        String openid = authorizationMapper.queryOpenid(UnionId);
        if(openid==null || "".equals(openid)){
            return new ResultBean("2000", "1|操作失败请联系客服！");
        }
        String state = userForbiddenMapper.QueryUserForbiddenState(user_id);
        if(state != null && !"".equals(state)){
            if("2".equals(state)){
                return new ResultBean("2000", "1|禁止提现！");
            }
        }
        UserStatistics userStatistics = userStatisticsMapper.selectByPrimaryKey(userId);

        ConfigInfo configInfo = configInfoMapper.selectByConnfigCode(Constants.CHONGTI_CONFIG);
        String configValue = configInfo.getConfigValue();
        JSONObject jsonObject = JSONObject.parseObject(configValue);
        String tixian = jsonObject.get("tixian").toString();
        int productCatalogId = Integer.parseInt(tixian);
        Map<String,Object> maps = new HashMap<>();
        maps.put("productCatalogId",productCatalogId);
        maps.put("productId",Integer.parseInt(productId));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> map = productInfoMapper.queryPrice(maps);
        String price = "";
        if(map==null){
            return new ResultBean("2000", "0|商品不存在！");
        }else{
            price = map.get("price").toString();
        }

        BigDecimal winningBalabceAmount = userStatistics.getWinningBalabceAmount();
        if(winningBalabceAmount.compareTo(new BigDecimal(price)) == -1){
            return new ResultBean("2000", "1|中奖余额不足！");
        }
        CashRecord cashRecord = new CashRecord();
        cashRecord.setUserId(userId);
        cashRecord.setState(1);
        cashRecord.setIp(ip);
        cashRecord.setCreateTime(new Date());
        cashRecord.setUpdateTime(new Date());
        cashRecord.setAmount(new BigDecimal(price));
        cashRecord.setCounterfee(new BigDecimal(0));
        cashRecord.setCompleteTime(simpleDateFormat.parse("1990-01-01"));
        Map<String,Object> map1 = new HashMap<>();
        map1.put("money",price);
        map1.put("date",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        if("-1".equals(Payee)){
            map1.put("type","微信");
            cashRecord.setRemark("微信提现"+price+"元");
            cashRecord.setType(1);
            cashRecord.setPayee("-1");
            cashRecord.setAlipayId(openid);
            cashRecord.setWechatPrepayId("");
        }else {
            cashRecord.setRemark("支付宝提现"+price+"元");
            cashRecord.setType(2);
            cashRecord.setPayee(Payee);
            cashRecord.setAlipayId(AlipayId);
            cashRecord.setWechatPrepayId("");
            map1.put("type","支付宝");
        }
        int i = cashRecordMapper.insertSelective(cashRecord);


        /*int Money = 1 ;
        ConfigInfo configinfo = configInfoMapper.selectByConnfigCode("getmoneyconfig");
        JSONObject getMoneyConfig = JSONObject.parseObject(configinfo.getConfigValue());
        Integer realTime = getMoneyConfig.getInteger("1");
        Integer wxcheckname = getMoneyConfig.getInteger("wxcheckname");//微信提现是否实名认证 0 :不检查，1：检查
        String mchbillno = "";
        // 微信的场合存入微信订单id
        if ("-1".equals(Payee)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMDD");
            mchbillno = mchId + simpleDateFormat.format(new Date()) + VCodeUtil.getVCode(10);
        }
        if ("-1".equals(Payee) && realTime == 1) {// 如果提现实时到账开启，进入提现处理逻辑
            // 如果没有填实名信息直接返回
            if (wxcheckname == 1 && (userinfo.getWechatRealName() == null || "".equals(userinfo.getWechatRealName()))) {
                //您当前不是微信实名制用户，请前往微信做实名认证或用其他方式提现
                return new ResultBean("2000", "1|实名认证未通过，请先进行实名认证！");
            }
        }
        boolean oneYunDeal = false;
        if (!"-1".equals(Payee) && realTime == 1) {// 如果提现实时到账开启，进入提现处理逻辑
            // 如果没有填实名信息直接返回
            if (wxcheckname == 1 && (userinfo.getWechatRealName() == null || "".equals(userinfo.getWechatRealName()))) {
                //您当前不是支付宝实名制用户，请前往支付宝做实名认证或用其他方式提现
                return new ResultBean("2000", "1|支付宝认证未通过，请联系客服人员进行修改！");
            }
            oneYunDeal = true;
        }
        // 如果是支付宝的场合，并且已经用支付宝提现过，用数据库里面的账号，如果没有支付宝提现过，就用传过来的账号，微信的时候Payee=-1
        if(!"-1".equals(Payee))
        {
            if(StringUtils.isNotEmpty(userinfo.getAlipayRealName()) || StringUtils.isNotEmpty(userinfo.getAlipayAccount()))
            {
                AlipayId = userinfo.getAlipayAccount();
                Payee = userinfo.getAlipayRealName();
            }
        }
        UserStatistics userStatistics = userStatisticsMapper.selectByPrimaryKey(userId);
        int balabce = userStatistics.getWinningBalabceAmount().intValue();
        if(balabce <=0 || balabce< Money){
            return new ResultBean("2000", "1|操作失败,请联系客服！");
        }
        Map<String, Object> mParams = new HashMap<>();
        mParams.put("alipayid", AlipayId);
        mParams.put("userid", userId);
        mParams.put("payee", Payee);
        mParams.put("ip", ip);
        mParams.put("amount", Money);
        mParams.put("mchbillno", mchbillno);
        CashRecord cashRecord = new CashRecord();
        cashRecord.setAlipayId(AlipayId);
        cashRecord.setAmount(new BigDecimal(String.valueOf(Money)));
        cashRecord.setCounterfee(new BigDecimal(0));
        cashRecord.setCreateTime(new Date());
        cashRecord.setPayee(userinfo.getAlipayRealName());
        cashRecord.setType(1);
        cashRecord.setWechatPrepayId(mchbillno);
        cashRecord.setUpdateTime(new Date());
        cashRecord.setUserId(userId);
        int i = cashRecordMapper.insertSelective(cashRecord);
        if(i<=0){
            return new ResultBean("2000", "1|操作失败,请联系客服！");
        }
        Integer rewardgold = 0;
        if (oneYunDeal) {//提现实时到账
            // 先进行提现操作：再进行逻辑操作
           Map<String, Object> mapResult = oneYuanWithdrawCash(params, mchbillno, userinfo.getWechatRealName(), wxcheckname, Money, true);
            rewardgold = Integer.parseInt(mapResult.get("returnflag").toString());
            Integer recordid = 0 ;
            if (rewardgold > 0) {
                //实时到账
                RealTimeArrival(mapResult, recordid);
            } else {
                NotRealTimeArrival(userId, Money, mapResult, recordid);
            }
        }*/
        return  new ResultBean("1000", "0|成功", map1, "1");
    }

    /**
     * 提现详情
     * @param params
     * @return
     */
    @Override
    public ResultBean withdrawalsDetails(Map<String, Object> params) {
        String userid = params.get("userid").toString();
        if(userid == null || "".equals(userid)){
            return new ResultBean("2000", "0|用户id为空！");
        }
        String pagesize = params.get("pagesize").toString();
        if(pagesize == null || "".equals(pagesize)){
            return new ResultBean("2000", "0|分页大小为空！");
        }
        String recordid = params.get("recordid").toString();
        if(recordid == null || "".equals(recordid)){
            return new ResultBean("2000", "0|记录id为空！");
        }
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userid);
        if(userInfo==null || "".equals(userInfo)){
            return new ResultBean("2000", "0|用户不存在！");
        }
        List<Map<String, Object>> maps = cashRecordMapper.QueryCashRecord(userid);

         return  new ResultBean("1000", "0|成功", maps, "1");
    }

    /**
     * 提现到银行卡页面初始化
     * @param params
     * @return
     */
    @Override
    public ResultBean BankCardInitialization(Map<String, Object> params) {
        Map<String,Object> maps = new HashMap<>();
        String userid = params.get("userid").toString();
        if(userid == null || "".equals(userid)){
            return new ResultBean("2000", "0|用户id为空！");
        }
        UserStatistics userStatistics = userStatisticsMapper.selectByPrimaryKey(userid);
        BigDecimal winningBalabceAmount = userStatistics.getWinningBalabceAmount();
        maps.put("balance",String.valueOf(winningBalabceAmount));
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userid);
        String realName = userInfo.getRealName();
        if(realName==null || "".equals(realName)){
            realName = "";
        }
        maps.put("name",realName);
        String bankcard = userBankCardMapper.queryCard(userid);
        if(bankcard==null || "".equals(bankcard)){
            bankcard = "";
        }
        maps.put("bankcard",bankcard);
        ConfigInfo configinfo = configInfoMapper.selectByConnfigCode(Constants.CONFIG_FEE_FLAG_CODE);
        String fee = configinfo.getConfigValue();
        maps.put("fee",fee);
        return  new ResultBean("1000", "0|成功", maps, "1");
    }

    /**
     * 提现到银行卡
     * @param params
     * @return
     */
    @Override
    public ResultBean BankCardWithdrawals(Map<String, Object> params) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String userid = params.get("userid").toString();
        if(userid == null || "".equals(userid)){
            return new ResultBean("2000", "0|用户id为空！");
        }
        String money = params.get("money").toString();
        if(money == null || "".equals(money)){
            return new ResultBean("2000", "1|提现金额为空！");
        }
        if(new BigDecimal(money).compareTo(new BigDecimal("10.00"))==-1){
            return new ResultBean("2000", "1|提现金额元不能小于10！");
        }
        String bankcard = params.get("bankcard").toString();
        if(bankcard == null || "".equals(bankcard)){
            return new ResultBean("2000", "1|银行卡号为空！");
        }
        boolean b = LuhmcheckBankCard.checkBankCard(bankcard);
        if(!b){
            return new ResultBean("2000", "1|请输入正确的银行卡号！");
        }
        String card = userBankCardMapper.queryisCard(params);
        if(card==null || "".equals(card)){
            UserBankCard userBankCard = new UserBankCard();
            userBankCard.setCardNumber(bankcard);
            userBankCard.setCreateTime(new Date());
            userBankCard.setUserId(userid);
            userBankCardMapper.insertSelective(userBankCard);
        }

        UserStatistics userStatistics = userStatisticsMapper.selectByPrimaryKey(userid);
        BigDecimal winningBalabceAmount = userStatistics.getWinningBalabceAmount();
        if(winningBalabceAmount.compareTo(new BigDecimal(money)) == -1){
            return new ResultBean("2000", "1|提现金额不能大于中奖余额！");
        }
        BigDecimal subtract = winningBalabceAmount.subtract(new BigDecimal(money));
        String state = userForbiddenMapper.QueryUserForbiddenState(userid);
        if(state != null && !"".equals(state)){
            if("2".equals(state)){
                return new ResultBean("2000", "1|你已经被禁止提现，请联系客服！");
            }
        }
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userid);
        Integer idAuthorized = userInfo.getIdAuthorized();
        if(idAuthorized == 0){
            return new ResultBean("2000", "1|请先实名认证，在进行提现操作！");
        }
        String realName = userInfo.getRealName();
        CashRecord cashRecord = new CashRecord();
        cashRecord.setUserId(userid);
        cashRecord.setState(1);
        cashRecord.setIp(params.get("remoteAddr").toString());
        cashRecord.setAmount(new BigDecimal(money));
        cashRecord.setCounterfee(new BigDecimal(0));
        cashRecord.setCompleteTime(format.parse("1990-01-01"));
        cashRecord.setCreateTime(new Date());
        cashRecord.setUpdateTime(new Date());
        cashRecord.setRemark(" ");
        cashRecord.setAlipayId(" ");
        cashRecord.setPayee(realName);
        cashRecord.setWechatPrepayId(" ");
        cashRecord.setType(3);
        cashRecord.setCardNumber(bankcard);
        cashRecordMapper.insertSelective(cashRecord);

        //更新用户的账户信息
        userStatistics.setWinningBalabceAmount(subtract);
        userStatistics.setUserId(userid);
        userStatistics.setUpdateTime(new Date());
        int i1 = userStatisticsMapper.updateByPrimaryKeySelective(userStatistics);
        if(i1>0){
            //插入日志
            UserStatisticsLog userStatisticsLog = new UserStatisticsLog();
            userStatisticsLog.setTotalAmount(userStatistics.getTotalAmount());
            userStatisticsLog.setBalanceAmount(userStatistics.getBalanceAmount());
            userStatisticsLog.setBettingNum(userStatistics.getBettingNum());
            userStatisticsLog.setWinningNum(userStatistics.getWinningNum());
            userStatisticsLog.setUserId(userid);
            userStatisticsLog.setWinningBalabceAmount(userStatistics.getWinningBalabceAmount());
            userStatisticsLog.setWinningTotalAmount(userStatistics.getTotalAmount());
            userStatisticsLog.setCreateTime(new Date());
            userStatisticsLog.setTudiNum(userStatistics.getTudiNum());
            userStatisticsLog.setTusunNum(userStatistics.getTusunNum());
            userStatisticsLogMapper.insertSelective(userStatisticsLog);
        }

        //账户明细
        userAccount("tixian",userid, new BigDecimal(money));

        Map<String,Object> maps = new HashMap<>();
        maps.put("money",money);
        maps.put("name",realName);
        maps.put("bankcard",bankcard);
        maps.put("datetime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return  new ResultBean("1000", "0|成功", maps, "1");
    }
    /**
     * 账户明细
     * @param type
     * @param userid
     * @param account
     */
    private void userAccount(String type,String userid, BigDecimal account) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format1 = format.format(new Date());
        UserAccountStatementAccount userAccountStatement = new UserAccountStatementAccount();
        userAccountStatement.setAmount(account);
        userAccountStatement.setCreateTime(format1);
        userAccountStatement.setState(1);
        userAccountStatement.setType(type);
        userAccountStatement.setUpdateTime(format1);
        userAccountStatement.setUserId(userid);

        String date = new SimpleDateFormat("yyyyMM").format(new Date());
        String tableName = "user_account_statement_" + date;
        Integer integer = userAccountStatementMapper.existTable(tableName);
        if(integer>0){
            userAccountStatement.setTableName(tableName);
            userAccountStatementMapper.insertSelectiveAccount(userAccountStatement);
        }
    }

    /**
     * 提现到银行卡记录
     * @param params
     * @return
     */
    @Override
    public ResultBean BankCardWithdrawalsList(Map<String, Object> params) {
        ResultBean resultBean = null;
        String userid = params.get("userid").toString();
        if(userid == null || "".equals(userid)){
            return new ResultBean("2000", "0|用户id为空！");
        }
        String pageSize = params.get("pageSize").toString();
        if(pageSize == null || "".equals(pageSize)){
            return new ResultBean("2000", "0|用户id为空！");
        }
        String recordid = params.get("recordid").toString();
        if(recordid == null || "".equals(recordid)){
            return new ResultBean("2000", "0|用户id为空！");
        }
        params.put("pageSize",Integer.parseInt(pageSize));
        params.put("recordid",Integer.parseInt(recordid));
        List<Map<String, Object>> maps = cashRecordMapper.BankCardWithdrawalsList(params);
        if(maps.size()>0&&maps!=null){
            for (Map<String, Object> item : maps){
                String datetimes = item.get("datetimes").toString();
                item.put("datetimes",datetimes.substring(0,datetimes.length()-2));
                String state = item.get("state").toString();
                if("3".equals(state)){
                    item.put("state","失败");
                   // item.put("remark","您好，因为你输入的银行卡号和开户名不相符，导致提现未成功。请您重新输入与开户名相匹配的银行卡号重新提交！");
                }else if("2".equals(state)){
                    item.put("state","成功");
                    item.put("remark","");
                }else {
                    item.put("state","处理中");
                    item.put("remark","");
                }
            }
            resultBean =   new ResultBean("1000", "0|成功", maps, String.valueOf(maps.size()));
        }else{
            maps  = new ArrayList<>();
            resultBean =   new ResultBean("1000", "0|成功", maps, "1");
        }
        return resultBean;
    }

    private void RealTimeArrival(Map<String, Object> mapResult, Integer recordid) {
        CashRecord cashRecord = new CashRecord();
        cashRecord.setState(2);
        cashRecord.setRecordId(recordid);
        cashRecordMapper.updateByPrimaryKeySelective(cashRecord);
    }

    private JSONObject NotRealTimeArrival(String userId, Integer money, Map<String, Object> mapResult, Integer recordid) {
        Map<String, Object> mParams = new HashMap<>();
        CashRecord cashRecord = new CashRecord();
        cashRecord.setState(3);
        cashRecord.setRecordId(recordid);
        cashRecordMapper.updateByPrimaryKeySelective(cashRecord);
        // 将余额返还
        mParams = new HashMap<>();
        mParams.put("userId", userId);
        mParams.put("money", -money);
        userStatisticsMapper.updateBalance(mParams);
        return JsonDealUtil.getErrJSONObject("1|操作失败,请联系客服");
    }

    /**
     *
     * @param params
     * @param partnertradeno
     * @param wusername
     * @param checkname
     * @param money 单位元
     * @return
     */
    private Map<String, Object> oneYuanWithdrawCash(Map<String,Object> params, String partnertradeno, String wusername, Integer checkname, Integer money, boolean isgguser) {
        Integer userid = Integer.parseInt(params.get("UserId").toString());
        Map<String, Object> resultMap = new HashMap<>();
        String yy_appid = params.get("appid").toString();
        String yy_mchId = params.get("mchId").toString();
        String yy_key = params.get("key").toString();
        String yy_keystorPath = params.get("keystorPath").toString();
        String AlipayId = params.get("AlipayId").toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        TransferReqData transferReqData = new TransferReqData();
        transferReqData.setAmount(money);

        // NO_CHECK：不校验真实姓名
        // FORCE_CHECK：强校验真实姓名（未实名认证的用户会校验失败，无法转账）
        // OPTION_CHECK：针对已实名认证的用户才校验真实姓名（未实名认证用户不校验，可以转账成功）
        if (checkname == 1) {
            transferReqData.setCheck_name("FORCE_CHECK");
            transferReqData.setRe_user_name(wusername);
        } else {
            transferReqData.setCheck_name("NO_CHECK");
            transferReqData.setRe_user_name("");
        }
        transferReqData.setDesc(sdf.format(new Date()) + "提现");
        transferReqData.setMch_appid(yy_appid);
        transferReqData.setMchid(yy_mchId);
        transferReqData.setNonce_str(UUID.randomUUID().toString().replace("-", ""));

        log.info("--------------openid-------" + AlipayId);
        transferReqData.setOpenid(AlipayId);
        transferReqData.setPartner_trade_no(partnertradeno);
        transferReqData.setSpbill_create_ip(ConstUtil.WX_PAY_IP_FOR_INTERFACE);

        transferReqData.setSign(SignatureUtil.generateSign1(transferReqData.toMap(), yy_key));
        TransfersResData transfersResData = TransfersAPI.TransfersPay(yy_mchId, yy_keystorPath, transferReqData);
        log.info("-----企业支付回调----" + transfersResData.toString());

        // 支付返回成功，但是内容错误：真实姓名对不上
        if ("SUCCESS".equals(transfersResData.getReturn_code())
                && "FAIL".equals(transfersResData.getResult_code())
                && "NAME_MISMATCH".equals(transfersResData.getErr_code())) {
            //企业支付失败
            resultMap.put("returnflag", -11);
            resultMap.put("returnmsg", transfersResData.toString());
            return resultMap;
        }
        // 企业支付直接失败(调用直接返回失败)
        if ("FAIL".equals(transfersResData.getReturn_code()) || !"SUCCESS".equals(transfersResData.getResult_code())) {
            //企业支付失败
            resultMap.put("returnflag", -10);
            resultMap.put("returnmsg", transfersResData.toString());
            return resultMap;
        }
        resultMap.put("returnmsg", transfersResData.toString());
        return resultMap;
    }
}
