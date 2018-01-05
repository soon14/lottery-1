package com.oruit.app.service.impl;/**
 * Created by wyt on 2017/9/12.
 */

import com.alibaba.fastjson.JSONObject;
import com.oruit.app.dao.*;
import com.oruit.app.dao.model.*;
import com.oruit.app.service.AppendRecordService;
import com.oruit.app.util.config.Constants;
import com.oruit.app.util.web.ResultBean;
import com.sun.imageio.plugins.common.I18N;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 追号记录
 *
 * @author @wyt
 * @create 2017-09-12 20:07
 */
@Service
public class AppendRecordServiceImpl implements AppendRecordService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserPlayPlanMapper userPlayPlanMapper;
    @Autowired
    private UserPlayRecordMapper userPlayRecordMapper;
    @Autowired
    private KuaileshifenOrderMapper kuaileshifenOrderMapper;
    @Autowired
    private ShuangseqiuOrderMapper shuangseqiuOrderMapper;
    @Autowired
    private ConfigInfoMapper configInfoMapper;
    @Autowired
    private UserStatisticsMapper userStatisticsMapper;
    @Autowired
    private UserStatisticsLogMapper userStatisticsLogMapper;
    @Autowired
    private UserAccountStatementMapper userAccountStatementMapper;

    /**
     * 追号记录列表
     *
     * @param map
     * @return
     */
    @Override
    public ResultBean AppendRecord(Map<String, Object> map) {
        String userid = map.get("userid").toString();
        if (userid == null || "".equals(userid)) {
            return new ResultBean("2000", "0|用户id为空！");
        }
        String type = map.get("type").toString();
        if (type == null || "".equals(type)) {
            return new ResultBean("2000", "0|类型为空！");
        }
        String pageSize1 = map.get("pageSize").toString();
        if (pageSize1 == null || "".equals(pageSize1)) {
            return new ResultBean("2000", "0|分页的大小为空！");
        }
        int pageSize = Integer.parseInt(pageSize1);
        map.put("pageSize", pageSize);
        String planid = map.get("planid").toString();
        if (planid == null || "".equals(planid)) {
            return new ResultBean("2000", "0|分页的id为空！");
        }
        int i = Integer.parseInt(planid);
        map.put("planid", i);
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userid);
        if (userInfo == null || "".equals(userInfo)) {
            return new ResultBean("2000", "0|用户不存在！");
        }
        List<Map<String, Object>> maps = new ArrayList<>();
        maps = userPlayPlanMapper.QueryAppendRecord(map);
        System.out.println("-----------------maps:" + maps);
        for (Map<String, Object> item : maps) {
            System.out.println("-----------------item:" + item);
            Integer queryissue = userPlayRecordMapper.queryissue(item);//已经投注的期数
            item.put("issuenopast", queryissue);
        }
        return new ResultBean("1000", "0|成功", maps, String.valueOf(maps.size()));
    }

    /**
     * 追号记录详情
     *
     * @param map
     * @return
     */
    @Override
    public ResultBean AppendRecordDetails(Map<String, Object> map) {
        String userid = map.get("userid").toString();
        if (userid == null || "".equals(userid)) {
            return new ResultBean("2000", "0|用户id为空！");
        }
        String planid = map.get("planid").toString();
        if (planid == null || "".equals(planid)) {
            return new ResultBean("2000", "0|追号记录id为空！");
        }
        Map<String, Object> item = userPlayPlanMapper.AppendRecordDetails(map);
        System.out.println("--------------item:"+item);
        if(item == null){
            return new ResultBean("2000", "0|追号记录为空！");
        }
        Integer planId = Integer.parseInt(planid);
        UserPlayPlan userPlayPlan = userPlayPlanMapper.selectByPrimaryKey(planId);
        Integer status1 = userPlayPlan.getStatus();//取出状态
        String caipiaoid = item.get("caipiaoid").toString();
        String caipiaoOrderSubId = item.get("caipiaoordersubid").toString();
        String qishu = item.get("qishu").toString();
        int i = Integer.parseInt(qishu);
        String issueNo = "";
        String endIssueno = "";
        if ("11".equals(caipiaoid)) {
            ShuangseqiuOrder shuangseqiuOrder = shuangseqiuOrderMapper.selectByPrimaryKey(caipiaoOrderSubId);
            issueNo = shuangseqiuOrder.getIssueNo();
            endIssueno = String.valueOf(i + Integer.parseInt(issueNo)-1);
        }
        if ("114".equals(caipiaoid)) {
            KuaileshifenOrder kuaileshifenOrder = kuaileshifenOrderMapper.selectByPrimaryKey(caipiaoOrderSubId);
            issueNo = kuaileshifenOrder.getIssueNo();
            String substring1 = issueNo.substring(0,8);
            String substring = issueNo.substring(8);
            int i1 = Integer.parseInt(substring);
            endIssueno = substring1+String.valueOf(i + i1-1);
            String playmethod = item.get("playmethod").toString();
            if(!"".equals(playmethod)){
                if("r2".equals(playmethod)){
                    item.put("playmethod","(任选二)");
                }
                if("r3".equals(playmethod)){
                    item.put("playmethod","(任选三)");
                }
                if("r4".equals(playmethod)){
                    item.put("playmethod","(任选四)");
                }
                if("r5".equals(playmethod)){
                    item.put("playmethod","(任选五)");
                }
            }
        }
        String str = issueNo + "~" + endIssueno;
        item.put("issueno", str);
        Integer queryissue = userPlayRecordMapper.queryissue(map);//已经投注的期数
        String money = item.get("money").toString();
        System.out.println("------------------------:"+money);
        BigDecimal amount = new BigDecimal(money);
        BigDecimal usedMoney = amount.divide(new BigDecimal(qishu)).multiply(BigDecimal.valueOf(queryissue));
        item.put("usedMoney", usedMoney);
        item.put("issuenopast", queryissue);
        String type = item.get("type").toString();
        if("1".equals(type)){
            item.put("type","单式");
        }
        if("2".equals(type)){
            item.put("type","复式");
        }
        String ordertime = item.get("ordertime").toString().substring(0,16);
        item.put("ordertime",ordertime);
        Map<String,Object> params = new HashMap<>();
        params.put("caipiaoid",Integer.parseInt(caipiaoid));
        params.put("planid",Integer.parseInt(planid));
        List<Map<String, Object>> maps = userPlayRecordMapper.QueryWinmoney(params);
        BigDecimal winamount = BigDecimal.valueOf(0);
        for(Map<String, Object> map1 : maps){
            String winningmoney = map1.get("winningmoney").toString();
            BigDecimal bigDecimal = new BigDecimal(winningmoney);
            winamount = winamount.add(bigDecimal);
        }
        item.put("winmoney",winamount);


        List<Map<String, Object>> maps1 = userPlayRecordMapper.QueryUserzhuihaotoushuRecord(map);
        for (Map<String, Object> stringObjectMap : maps1){
            String status = stringObjectMap.get("status").toString();
            if("0".equals(status)){
                stringObjectMap.put("status","等待出票");
                stringObjectMap.put("orderstatus","已追号");
            }else if("1".equals(status)){
                stringObjectMap.put("status","出票失败");
                stringObjectMap.put("orderstatus","追号失败");
            }else if("2".equals(status)){
                stringObjectMap.put("status","等待开奖");
                stringObjectMap.put("orderstatus","已追号");
            }else if("3".equals(status)){
                stringObjectMap.put("status","未中奖");
                stringObjectMap.put("orderstatus","已追号");
            }else if("5".equals(status)){
                stringObjectMap.put("status","出票中");
                stringObjectMap.put("orderstatus","已追号");
            }else{
                stringObjectMap.put("status",stringObjectMap.get("winningmoney").toString());
                stringObjectMap.put("orderstatus","已追号");
            }
        }
        Map<String, Object> stringObjectMap = maps1.get(maps1.size() - 1);
        String caipiaoordersubcode = "";


        String issueno = stringObjectMap.get("issueno").toString();

        if(issueno.length() == 7){
            Integer qishus = Integer.parseInt(issueno);
            issueno = String.valueOf(qishus + 1) ;
        }
        if(issueno.length() == 10){
            String substring2 = issueno.substring(0, 8);
            String substring3 = issueno.substring(8);
            int i1 = Integer.parseInt(substring3);
            issueno = substring2 + String.format("%02d", i1 + 1);

        }
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("winningmoney","");
        objectMap.put("caipiaoordersubcode",caipiaoordersubcode);
        objectMap.put("issueno",issueno);
        if(status1 == 0){
            objectMap.put("orderstatus","已撤单");
            ConfigInfo configInfo = configInfoMapper.selectByConnfigCode(Constants.BEETING_CONFIG);
            String configValue = configInfo.getConfigValue();
            System.out.println("--------configValue"+configValue);
            JSONObject jsonObject = JSONObject.parseObject(configValue);
            String tingzhi = jsonObject.get("tingzhi").toString();
            item.put("image",tingzhi);
        }
        if(status1 == 1){
            objectMap.put("orderstatus","待追号");
            item.put("image","");
        }
        maps1.add(objectMap);
        item.put("item",maps1);
        return new ResultBean("1000", "0|成功", item, "1");
    }

    /**
     * 停止追号
     * @param map
     * @return
     */
    @Override
    public ResultBean StopAppend(Map<String, Object> map) {
        String userid = map.get("userid").toString();
        if (userid == null || "".equals(userid)) {
            return new ResultBean("2000", "0|用户id为空！");
        }
        String planid = map.get("planid").toString();
        if (planid == null || "".equals(planid)) {
            return new ResultBean("2000", "0|追号记录id为空！");
        }
        map.put("planid",Integer.parseInt(planid));
        Map<String, Object> item = userPlayPlanMapper.AppendRecordDetails(map);
        Integer queryissue = userPlayRecordMapper.queryissue(map);//已经投注的期数
        item.put("qishu",queryissue);
        System.out.println("--------------item:"+item);
        if(item == null){
            return new ResultBean("2000", "0|追号记录为空！");
        }
        if("0".equals(item.get("status").toString())){
            return new ResultBean("2000", "0|已经停止！");
        }
        String caipiaoOrderSubId = item.get("caipiaoordersubid").toString();
        String caipiaoid = item.get("caipiaoid").toString();
        BigDecimal amount = new BigDecimal(0);
        Map<String,Object> params = new HashMap<>();
        if("11".equals(caipiaoid)){
            ShuangseqiuOrder shuangseqiuOrder = shuangseqiuOrderMapper.selectByPrimaryKey(caipiaoOrderSubId);
            amount = shuangseqiuOrder.getAmount();
            params.put("lotteryname","双色球");
        }
        if("114".equals(caipiaoid)){
            KuaileshifenOrder kuaileshifenOrder = kuaileshifenOrderMapper.selectByPrimaryKey(caipiaoOrderSubId);
            amount = kuaileshifenOrder.getAmount();
            params.put("lotteryname","十一选五");
        }


        UserPlayPlan userPlayPlan = userPlayPlanMapper.selectByPrimaryKey(Integer.parseInt(planid));
        userPlayPlan.setStatus(0);
        userPlayPlan.setPlanId(Integer.parseInt(planid));
        userPlayPlan.setUpdateTime(new Date());
        userPlayPlanMapper.updateByPrimaryKeySelective(userPlayPlan);


        Integer qishu = userPlayPlan.getQishu();
        params.put("qishu",qishu);
        Integer queryissues = userPlayRecordMapper.queryissue(map);//已经投注的期数
        params.put("queryissue",queryissues);
        String queryissuemoney = userPlayRecordMapper.queryissuemoney(map);//每期的钱数
        BigDecimal bigDecimal = new BigDecimal(queryissuemoney);
        BigDecimal bigDecimal1 = new BigDecimal(queryissue);
        BigDecimal appendmoney = bigDecimal.multiply(bigDecimal1);//已经投注的钱数
        BigDecimal subtract = amount.subtract(appendmoney);//需要返回的钱数

        UserStatistics userStatistics = userStatisticsMapper.selectByPrimaryKey(userid);
        BigDecimal balanceAmount = userStatistics.getBalanceAmount();
        userStatistics.setUserId(userid);
        BigDecimal add = balanceAmount.add(subtract);
        userStatistics.setBalanceAmount(add);
        userStatistics.setUpdateTime(new Date());
        userStatisticsMapper.updateByPrimaryKeySelective(userStatistics);

        UserStatisticsLog userStatisticsLog = new UserStatisticsLog();
        userStatisticsLog.setUserId(userid);
        userStatisticsLog.setBalanceAmount(add);
        userStatisticsLog.setWinningBalabceAmount(userStatistics.getWinningBalabceAmount());
        userStatisticsLog.setWinningNum(userStatistics.getWinningNum());
        userStatisticsLog.setTusunNum(userStatistics.getTusunNum());
        userStatisticsLog.setTudiNum(userStatistics.getTudiNum());
        userStatisticsLog.setBettingNum(userStatistics.getBettingNum());
        userStatisticsLog.setCreateTime(new Date());
        userStatisticsLog.setTotalAmount(userStatistics.getTotalAmount());
        userStatisticsLog.setWinningTotalAmount(userStatistics.getWinningTotalAmount());
        userStatisticsLogMapper.insertSelective(userStatisticsLog);
        //账户明细
        userAccount("tuikuan",userid, subtract);
        return new ResultBean("1000", "0|成功", params, "1");
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
}
