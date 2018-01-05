package com.oruit.app.service.impl;/**
 * Created by wyt on 2017/9/8.
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oruit.app.dao.*;
import com.oruit.app.dao.model.CaipiaoOrderInfo;
import com.oruit.app.dao.model.ConfigInfo;
import com.oruit.app.dao.model.KuaileshifenOrder;
import com.oruit.app.dao.model.UserStatistics;
import com.oruit.app.service.KLSFService;
import com.oruit.app.ssq.shuagnseqiu;
import com.oruit.app.util.PayUtil;
import com.oruit.app.util.config.Constants;
import com.oruit.app.util.web.ResultBean;
import com.oruit.app.util.web.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wyt
 * @create 2017-09-08 10:18
 */
@Service
public class KLSFServiceImpl implements KLSFService {

    @Autowired
    private UserRedpacketMapper userRedpacketMapper;
    @Autowired
    private UserForbiddenMapper userForbiddenMapper;
    @Autowired
    private CaipiaoOrderInfoMapper caipiaoOrderInfoMapper;
    @Autowired
    private KuaileshifenOrderMapper kuaileshifenOrderMapper;
    @Autowired
    private UserStatisticsMapper userStatisticsMapper;
    @Autowired
    private ConfigInfoMapper configInfoMapper;
    /**
     *快乐十分
     * @param map
     * @return
     */
    @Override
    public ResultBean KLSFPurchase(Map<String, Object> map) throws ParseException {
        String number= "";//红球
        int numberlength = 0 ;//球的数量

        Map<String,Object> maps = new HashMap<>();
        maps.put("lotteryname","快乐十分");
        String userid = map.get("userid").toString();
        if(userid == null || "".equals(userid)){
            return new ResultBean("2000", "0|用户id为空！");
        }
        String s1 = userForbiddenMapper.QueryUserForbidden(userid);
        if(s1!=null && !"".equals(s1)){
            return new ResultBean("2000", "1|非法操作禁止购彩！");
        }
        map.put("date",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        Integer integer = userRedpacketMapper.queryUserRedpacketNormalnum(map);
        maps.put("redpackagenum",integer);
        String lotterytypeid = map.get("lotterytypeid").toString();
        if(lotterytypeid == null || "".equals(lotterytypeid)){
            return new ResultBean("2000", "0|彩种类型为空！");
        }
        String bettingmultiple = map.get("bettingmultiple").toString();
        if(bettingmultiple == null || "".equals(bettingmultiple)){
            return new ResultBean("2000", "0|倍数为空！");
        }
        if(Integer.parseInt(bettingmultiple)<1){
            return new ResultBean("2000", "1|倍数最小为1！");
        }
        if(Integer.parseInt(bettingmultiple)>99){
            return new ResultBean("2000", "1|倍数最大为99！");
        }
        String parameter = map.get("parameter").toString();
        if(parameter == null || "".equals(parameter)){
            return new ResultBean("2000", "0|参数为空！");
        }
        String qishu = map.get("qishu").toString();
        if(qishu == null || "".equals(qishu)){
            return new ResultBean("2000", "0|期数为空！");
        }
        if(Integer.parseInt(qishu)<1){
            return new ResultBean("2000", "1|期数最小为1！");
        }
        if(Integer.parseInt(qishu)<1){
            return new ResultBean("2000", "1|期数最大为50！");
        }

        JSONArray objects = JSONArray.parseArray(parameter);
        Integer zhushu = 0 ;
        for (int i = 0 ; i < objects.size() ; i++){
            JSONObject jsonObject = objects.getJSONObject(i);
            String type = jsonObject.get("type").toString();
            number = jsonObject.get("number").toString();
            if(number == null || "".equals(number)){
                return new ResultBean("2000", "0|请选择足够的球！");
            }
            numberlength = number.split(" ").length;
            int num = Integer.parseInt(type);
            if(num <2 || num > 5){
                return new ResultBean("2000", "1|玩法类型不正确！");
            }
            if(numberlength<num){
                return new ResultBean("2000", "1|请选择足够的球！");
            }
            if(numberlength > 20){
                return new ResultBean("2000", "1|选择对的球的数量过多！");
            }
            zhushu = zhushu + shuagnseqiu.kuaileshifen(numberlength, num) ;
        }
        CaipiaoOrderInfo caipiaoOrderInfo = new CaipiaoOrderInfo();
        SimpleDateFormat ymdhms=new SimpleDateFormat("yyyy-MM-dd");
        caipiaoOrderInfo.setCaipiaoOrderId(UUIDUtils.uuid());
        caipiaoOrderInfo.setCaipiaoId(114);
        caipiaoOrderInfo.setCaipiaoOrderCode(PayUtil.getTradeNo());
        caipiaoOrderInfo.setUserId(userid);
        caipiaoOrderInfo.setState(1);
        caipiaoOrderInfo.setPayType(3);
        caipiaoOrderInfo.setUsedRedpacket(0);
        caipiaoOrderInfo.setRedpacketId(0);
        caipiaoOrderInfo.setRedpacketAmount(new BigDecimal(0));
        caipiaoOrderInfo.setCreateTime(new Date());
        caipiaoOrderInfo.setUpdateTime(new Date());
        caipiaoOrderInfo.setPayTime(ymdhms.parse("1990-01-01"));
        //money  =  注数  *  倍数  * 追的期数  * 2
        int money = zhushu * Integer.parseInt(bettingmultiple) * Integer.parseInt(qishu) * 2;
        ConfigInfo configInfo = configInfoMapper.selectByConnfigCode(Constants.MAXMONEY_CONFIG);
        String configValue = configInfo.getConfigValue();
        if(new BigDecimal(money).compareTo(new BigDecimal(configValue)) == 1){
            return new ResultBean("2000", "1|为了您的财产安全，请选择较小的金额！");
        }
        maps.put("money",String.valueOf(money)+".00");
        caipiaoOrderInfo.setTotal(new BigDecimal(money));
        caipiaoOrderInfo.setAmount(new BigDecimal(money));
        int i = caipiaoOrderInfoMapper.insertSelective(caipiaoOrderInfo);
        String caipiaoOrderId = caipiaoOrderInfo.getCaipiaoOrderId();
        maps.put("orderid",caipiaoOrderId);

        //插入快乐十分的订单
        //String yyyyMMdd = new SimpleDateFormat("yyyyMMdd").format(new Date());
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String yyyyMMdd = format.format(new Date());
        KuaileshifenOrder kuaileshifenOrder = new KuaileshifenOrder();
        for (int j = 0 ; j < objects.size() ; j++){
            JSONObject jsonObject = objects.getJSONObject(j);
            String type = jsonObject.get("type").toString();
            number = jsonObject.get("number").toString();
            numberlength = number.split(" ").length;
            int num = Integer.parseInt(type);
            zhushu = shuagnseqiu.kuaileshifen(numberlength, num) ;
            money = zhushu * Integer.parseInt(bettingmultiple) * Integer.parseInt(qishu) * 2;

            String integer1 = shuagnseqiu.isKLSFissueno();
            if("false".equals(integer1)){
                integer1 = shuagnseqiu.KLSFissuenoquery();
            }
            System.out.println("-----------------yyyyMMdd------------------:"+yyyyMMdd);
            if(Integer.parseInt(integer1)>84 && !"1000".equals(integer1)){
                Calendar nowTime = Calendar.getInstance();
                nowTime.setTime(format.parse(yyyyMMdd));
                nowTime.add(Calendar.DATE, 1);
                Date time = nowTime.getTime();
                yyyyMMdd = format.format(time);
                integer1="01";
            }
            if("1000".equals(integer1)){
                integer1="01";
            }
            System.out.println("-----------------integer1------------------:"+integer1);
            System.out.println("-----------------yyyyMMdd------------------:"+yyyyMMdd);
            String issuno = yyyyMMdd + integer1;
            kuaileshifenOrder.setIssueNo(issuno);
            kuaileshifenOrder.setCaipiaoOrderSubId(UUIDUtils.uuid());
            kuaileshifenOrder.setCaipiaoOrderSubCode(PayUtil.getTradeNo()+j);
            kuaileshifenOrder.setCaipiaoOrderId(caipiaoOrderId);
            System.out.println("--------------Integer.parseInt(type)----------------------:"+Integer.parseInt(type));
            System.out.println("--------------numberlength----------------------:"+numberlength);
            if(Integer.parseInt(type) == numberlength){
                kuaileshifenOrder.setPlayType(1);
            }else {
                kuaileshifenOrder.setPlayType(2);
            }
            kuaileshifenOrder.setPlayMethod("r"+type);
            kuaileshifenOrder.setNumber(number);
            kuaileshifenOrder.setQishu(Integer.parseInt(qishu));
            kuaileshifenOrder.setBeishu(Integer.parseInt(bettingmultiple));
            kuaileshifenOrder.setZhushu(zhushu);
            kuaileshifenOrder.setAmount(new BigDecimal(money));
            kuaileshifenOrder.setRedpacketAmount(new BigDecimal(0));
            kuaileshifenOrder.setCreateTime(new Date());
            maps.put("issueno",issuno);
            kuaileshifenOrderMapper.insertSelective(kuaileshifenOrder);
        }

        UserStatistics userStatistics = userStatisticsMapper.selectByPrimaryKey(userid);
        BigDecimal balanceAmount = userStatistics.getBalanceAmount();
        BigDecimal winningBalabceAmount = userStatistics.getWinningBalabceAmount();
        BigDecimal add = balanceAmount.add(winningBalabceAmount);
        String balance = String.valueOf(add);
        maps.put("balance",balance);

        return  new ResultBean("1000", "0|待支付", maps, "1");
    }
}
