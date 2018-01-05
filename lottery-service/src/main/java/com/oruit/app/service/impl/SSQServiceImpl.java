package com.oruit.app.service.impl;/**
 * Created by wyt on 2017/9/4.
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oruit.app.dao.*;
import com.oruit.app.dao.model.*;
import com.oruit.app.service.SSQService;
import com.oruit.app.ssq.Issueno;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author wyt
 * @create 2017-09-04 14:11
 */
@Service
public class SSQServiceImpl implements SSQService{
    @Autowired
    private CaipiaoOrderInfoMapper caipiaoOrderInfoMapper;
    @Autowired
    private ShuangseqiuOrderMapper shuangseqiuOrderMapper;
    @Autowired
    private UserRedpacketMapper userRedpacketMapper;
    @Autowired
    private UserForbiddenMapper userForbiddenMapper;
    @Autowired
    private UserStatisticsMapper userStatisticsMapper;
    @Autowired
    private ConfigInfoMapper configInfoMapper;
    @Override
    public ResultBean SSQPurchase(Map<String, Object> map) throws Exception {
        String danma = "";//胆码
        String tuoma= "";//拖码
        String rednumber= "";//红球
        String bluenumber= "";//蓝球
        int redlength = 0 ;//红球数量
        int bluelength= 0 ;//蓝球数量
        Map<String,Object> maps = new HashMap<>();
        maps.put("lotteryname","双色球");
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
        if(!"11".equals(lotterytypeid)){
            return new ResultBean("2000", "0|彩票id错误！");
        }
        JSONArray objects = JSONArray.parseArray(parameter);
        Integer zhushu = 0 ;
        for (int i = 0 ; i < objects.size() ; i++){
            JSONObject jsonObject = objects.getJSONObject(i);
            String type = jsonObject.get("type").toString();
            if(Integer.parseInt(type)>3){
                return new ResultBean("2000", "0|选择的类型不正确！");
            }
            if("1".equals(type) || "2".equals(type)){
                bluenumber = jsonObject.get("bluenumber").toString();
                if(bluenumber == null || "".equals(bluenumber)){
                    return new ResultBean("2000", "0|蓝球为空！");
                }
                rednumber = jsonObject.get("rednumber").toString();
                if(rednumber == null || "".equals(rednumber)){
                    return new ResultBean("2000", "0|红球为空！");
                }
                 redlength = rednumber.split(" ").length;
                 bluelength = bluenumber.split(" ").length;
                System.out.println("-----------redlength---------------------"+redlength);
                System.out.println("-------------bluelength-------------------"+bluelength);
                if(redlength<6 || bluelength < 1){
                    return new ResultBean("2000", "1|请选择足够的蓝球或者红球！");
                }
                if("1".equals(type)){
                    if(redlength>6 && bluelength>1 ){
                        return new ResultBean("2000", "1|选择的类型和球的数量不符合！");
                    }
                }
                if("2".equals(type)){
                    if(redlength==6 && bluelength==1 ){
                        return new ResultBean("2000", "1|选择的类型和球的数量不符合！");
                    }
                }
                zhushu = zhushu + shuagnseqiu.danshizhushu(redlength, bluelength).intValue();

            }
            if("3".equals(type)){
                bluenumber = jsonObject.get("bluenumber").toString();
                if(bluenumber == null || "".equals(bluenumber)){
                    return new ResultBean("2000", "0|蓝球为空！");
                }
                danma = jsonObject.get("danma").toString();
                if(danma == null || "".equals(danma)){
                    return new ResultBean("2000", "0|胆码为空！");
                }
                tuoma = jsonObject.get("tuoma").toString();
                if(tuoma == null || "".equals(tuoma)){
                    return new ResultBean("2000", "0|拖码为空！");
                }
                int hqdm_count = danma.split(" ").length;
                int hqtm_count = tuoma.split(" ").length;
                int lqtm_count = bluenumber.split(" ").length;
                if(hqdm_count>5){
                    return new ResultBean("2000", "0|红球胆码数不可超过5个！");
                }else if(hqdm_count<1){
                    return new ResultBean("2000", "0|未选中红球胆码！");
                }else if(hqtm_count<1){
                    return new ResultBean("2000", "0|未选中红球拖码！");
                }else if(lqtm_count<1){
                    return new ResultBean("2000", "0|未选中蓝球拖码！");
                }else if((hqdm_count+hqtm_count)<7){
                    return new ResultBean("2000", "0|选中的红球不够6个！");
                }else {
                    zhushu = zhushu + shuagnseqiu.dantuozhushu(hqdm_count, hqtm_count, lqtm_count);
                }
            }
        }
        CaipiaoOrderInfo caipiaoOrderInfo = new CaipiaoOrderInfo();
        SimpleDateFormat ymdhms=new SimpleDateFormat("yyyy-MM-dd");
        caipiaoOrderInfo.setCaipiaoOrderId(UUIDUtils.uuid());
        caipiaoOrderInfo.setCaipiaoId(11);
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
        ShuangseqiuOrder shuangseqiuOrder = new ShuangseqiuOrder();
        for (int j = 0 ; j < objects.size() ; j++){
            JSONObject jsonObject = objects.getJSONObject(j);
            String type = jsonObject.get("type").toString();
            if("1".equals(type) || "2".equals(type)){
                bluenumber = jsonObject.get("bluenumber").toString();
                rednumber = jsonObject.get("rednumber").toString();
                redlength = rednumber.split(" ").length;
                bluelength = bluenumber.split(" ").length;
                zhushu = shuagnseqiu.danshizhushu(redlength, bluelength).intValue();
                System.out.println("-----rednumber---------:"+rednumber);
                System.out.println("-------bluenumber-------:"+bluenumber);
                System.out.println("-------jsonObject-------:"+jsonObject);
                boolean b = redlength == 6;
                boolean b1 = bluelength == 1;
                System.out.println("-------redlength==6-------:"+b);
                System.out.println("-------jsonObject-------:"+b1);

                boolean b2 = redlength > 6;
                boolean b3 = bluelength > 1;

                System.out.println("-------redlength>6 -------:"+b2);
                System.out.println("-------bluelength>1 -------:"+b3);
                if(redlength==6 && bluelength==1){
                    shuangseqiuOrder.setPlayType(1);
                    shuangseqiuOrder.setDanma("");
                    shuangseqiuOrder.setTuoma("");
                    shuangseqiuOrder.setHongqiu(rednumber);
                    shuangseqiuOrder.setLanqiu(bluenumber);
                }
                if(redlength>=6 && bluelength>1 || redlength>6 && bluelength>=1){
                    shuangseqiuOrder.setPlayType(2);
                    shuangseqiuOrder.setDanma(" ");
                    shuangseqiuOrder.setTuoma("");
                    shuangseqiuOrder.setHongqiu(rednumber);
                    shuangseqiuOrder.setLanqiu(bluenumber);
                }
            }
            if("3".equals(type)){
                bluenumber = jsonObject.get("bluenumber").toString();
                danma = jsonObject.get("danma").toString();
                tuoma = jsonObject.get("tuoma").toString();
                int hqdm_count = danma.split(" ").length;
                int hqtm_count = tuoma.split(" ").length;
                int lqtm_count = bluenumber.split(" ").length;
                shuangseqiuOrder.setPlayType(3);
                shuangseqiuOrder.setDanma(danma);
                shuangseqiuOrder.setTuoma(tuoma);
                shuangseqiuOrder.setHongqiu(" ");
                shuangseqiuOrder.setLanqiu(bluenumber);
                zhushu = shuagnseqiu.dantuozhushu(hqdm_count, hqtm_count, lqtm_count);
            }
            money = zhushu * Integer.parseInt(bettingmultiple) * Integer.parseInt(qishu) * 2;
            shuangseqiuOrder.setAmount(new BigDecimal(money));
            shuangseqiuOrder.setBeishu(Integer.parseInt(bettingmultiple));
            shuangseqiuOrder.setCaipiaoOrderId(caipiaoOrderId);
            shuangseqiuOrder.setCreateTime(new Date());
            shuangseqiuOrder.setQishu(Integer.parseInt(qishu));
            shuangseqiuOrder.setZhushu(zhushu);
            shuangseqiuOrder.setCaipiaoOrderSubCode(PayUtil.getTradeNo()+j);
            shuangseqiuOrder.setCaipiaoOrderSubId(UUIDUtils.uuid());
            shuangseqiuOrder.setRedpacketAmount(new BigDecimal(0));
            String s = Issueno.achieveNum();
            maps.put("issueno",s);
            shuangseqiuOrder.setIssueNo(s);
            shuangseqiuOrderMapper.insertSelective(shuangseqiuOrder);
            UserStatistics userStatistics = userStatisticsMapper.selectByPrimaryKey(userid);
            BigDecimal balanceAmount = userStatistics.getBalanceAmount();
            BigDecimal winningBalabceAmount = userStatistics.getWinningBalabceAmount();
            BigDecimal add = balanceAmount.add(winningBalabceAmount);
            String balance = String.valueOf(add);
            maps.put("balance",balance);
        }
        return  new ResultBean("1000", "0|待支付", maps, "1");
    }

}
