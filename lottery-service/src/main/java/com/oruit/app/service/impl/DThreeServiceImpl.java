package com.oruit.app.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oruit.app.dao.*;
import com.oruit.app.dao.model.CaipiaoOrderInfo;
import com.oruit.app.dao.model.ConfigInfo;
import com.oruit.app.dao.model.D3Order;
import com.oruit.app.dao.model.UserStatistics;
import com.oruit.app.service.DThreeService;
import com.oruit.app.threeD.D3Issueno;
import com.oruit.app.threeD.D3ZhuShu;
import com.oruit.app.util.PayUtil;
import com.oruit.app.util.config.Constants;
import com.oruit.app.util.web.ResultBean;
import com.oruit.app.util.web.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**D3
 * Created by wyt on 2017-11-03.
 */
@Service
public class DThreeServiceImpl implements DThreeService {
    @Autowired
    private UserForbiddenMapper userForbiddenMapper;
    @Autowired
    private CaipiaoOrderInfoMapper caipiaoOrderInfoMapper;
    @Autowired
    private ConfigInfoMapper configInfoMapper;
    @Autowired
    private D3OrderMapper d3OrderMapper;
    @Autowired
    private UserStatisticsMapper userStatisticsMapper;
    @Autowired
    private UserRedpacketMapper userRedpacketMapper;
    /**
     * 3D的选号
     * @param maps
     * @return
     */
    @Override
    public ResultBean dThreeChoose(Map<String, Object> maps) throws Exception {
        Map<String,Object> result = new HashMap<>();
        result.put("lotteryname","3D");
        String userid = maps.get("userid").toString();
        if(userid == null || "".equals(userid)){
            return new ResultBean("2000", "0|用户id为空！");
        }
        String s1 = userForbiddenMapper.QueryUserForbidden(userid);
        if(s1!=null && !"".equals(s1)){
            return new ResultBean("2000", "1|非法操作禁止购彩！");
        }
        String lotterytypeid = maps.get("lotterytypeid").toString();
        if(lotterytypeid == null || "".equals(lotterytypeid)){
            return new ResultBean("2000", "0|彩种类型为空！");
        }
        boolean equals = "12".equals(lotterytypeid);
        if(!equals){
            return new ResultBean("2000", "0|彩种类型不正确！");
        }
        String bettingmultiple = maps.get("bettingmultiple").toString();
        if(bettingmultiple == null || "".equals(bettingmultiple)){
            return new ResultBean("2000", "0|倍数为空！");
        }
        if(Integer.parseInt(bettingmultiple)<1){
            return new ResultBean("2000", "1|倍数最小为1！");
        }
        if(Integer.parseInt(bettingmultiple)>99){
            return new ResultBean("2000", "1|倍数最大为99！");
        }
        String parameter = maps.get("parameter").toString();
        if(parameter == null || "".equals(parameter)){
            return new ResultBean("2000", "0|参数为空！");
        }
        String qishu = maps.get("qishu").toString();
        if(qishu == null || "".equals(qishu)){
            return new ResultBean("2000", "0|期数为空！");
        }
        if(Integer.parseInt(qishu)<1){
            return new ResultBean("2000", "1|期数最小为1！");
        }
        if(Integer.parseInt(qishu)<1){
            return new ResultBean("2000", "1|期数最大为50！");
        }
        Map<String,Object> map = new HashMap<>();
        map.put("userid",userid);
        map.put("date",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        Integer integer = userRedpacketMapper.queryUserRedpacketNormalnum(map);
        result.put("redpackagenum",integer);
        JSONArray objects = JSONArray.parseArray(parameter);
        Integer zhushu = 0 ;//注数

        for (int i = 0 ; i < objects.size() ; i++){
            JSONObject jsonObject = objects.getJSONObject(i);
            String type = jsonObject.get("type").toString();
            String baiwei = jsonObject.get("baiwei").toString();
            String shiwei = jsonObject.get("shiwei").toString();
            String gewei = jsonObject.get("gewei").toString();
            String[] baiweisplit = baiwei.split(" ");
            String[] shiweisplit = shiwei.split(" ");
            String[] geweisplit = gewei.split(" ");
            String[][] resultsplit = {baiweisplit,shiweisplit,geweisplit};
            String[][] resultsplitzs = {shiweisplit,shiweisplit,geweisplit};
            int baiweinum = baiweisplit.length;
            int shiweinum = shiweisplit.length;
            int geweinum = geweisplit.length;
            if(type == null || "".equals(type)){
                return new ResultBean("2000", "0|玩法为空！");
            }
            System.out.println("baiweinum:"+baiweinum);
            System.out.println("shiweinum:"+shiweinum);
            System.out.println("geweinum:"+geweinum);
            if("1".equals(type) || "2".equals(type)){
                if(baiweinum<1 || shiweinum<1 || geweinum<1){
                    return new ResultBean("2000", "0|选择求得数量和类型不匹配1！");
                }
                if("2".equals(type)){
                    if(baiweinum+shiweinum+geweinum<=3){
                        return new ResultBean("2000", "0|选择求得数量和类型不匹配2！");
                    }
                }
                zhushu = zhushu + D3ZhuShu.getZhuShu("zx",resultsplit);
            }else if("3".equals(type)){
                if( shiweinum<1 || geweinum<1){
                    return new ResultBean("2000", "0|选择求得数量和类型不匹配3！");
                }
                if(shiweinum==1 && geweinum==1){
                    if(shiwei.equals(gewei)){
                        return new ResultBean("2000", "0|选择求得数量和类型不匹配4！");
                    }
                }
                zhushu = zhushu + D3ZhuShu.getZhuShu("zhuxuan",resultsplitzs);
            }else if("4".equals(type)){
                if(geweinum<3){
                    return new ResultBean("2000", "0|选择求得数量和类型不匹配5！");
                }
                zhushu = zhushu + D3ZhuShu.zhuliu(geweinum);
            }else{
                return new ResultBean("2000", "0|玩法不正确！");
            }
        }
        CaipiaoOrderInfo caipiaoOrderInfo = new CaipiaoOrderInfo();
        SimpleDateFormat ymdhms=new SimpleDateFormat("yyyy-MM-dd");
        caipiaoOrderInfo.setCaipiaoOrderId(UUIDUtils.uuid());
        caipiaoOrderInfo.setCaipiaoId(12);
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
        result.put("money",String.valueOf(money)+".00");
        caipiaoOrderInfo.setTotal(new BigDecimal(money));
        caipiaoOrderInfo.setAmount(new BigDecimal(money));
        int i = caipiaoOrderInfoMapper.insertSelective(caipiaoOrderInfo);
        String caipiaoOrderId = caipiaoOrderInfo.getCaipiaoOrderId();
        result.put("orderid",caipiaoOrderId);
        D3Order d3Order = new D3Order();
        String issueno = D3Issueno.getIssueno();
        String yyyy = new SimpleDateFormat("yyyy").format(new Date());
        issueno = yyyy + issueno;
        for (int j = 0 ; j < objects.size() ; j++){
            JSONObject jsonObject = objects.getJSONObject(j);
            String type = jsonObject.get("type").toString();
            String baiwei = jsonObject.get("baiwei").toString();
            String shiwei = jsonObject.get("shiwei").toString();
            String gewei = jsonObject.get("gewei").toString();
            String[] baiweisplit = baiwei.split(" ");
            String[] shiweisplit = shiwei.split(" ");
            String[] geweisplit = gewei.split(" ");
            int geweinum = geweisplit.length;
            String[][] resultsplit = {baiweisplit,shiweisplit,geweisplit};
            String[][] resultsplitzs = {shiweisplit,shiweisplit,geweisplit};
            d3Order.setIssueNo(issueno);
            d3Order.setCaipiaoOrderSubId(UUIDUtils.uuid());
            d3Order.setCaipiaoOrderSubCode(PayUtil.getTradeNo()+j);
            d3Order.setCaipiaoOrderId(caipiaoOrderId);
            d3Order.setBaiwei(baiwei);
            d3Order.setShiwei(shiwei);
            d3Order.setGewei(gewei);
            if(Integer.parseInt(type) == 2){
                d3Order.setPlayType(2);
            }else {
                d3Order.setPlayType(1);
            }
            if("1".equals(type) || "2".equals(type)){
                d3Order.setPlayMethod("zhixuan");
                zhushu =  D3ZhuShu.getZhuShu("zx",resultsplit);
            }else if("3".equals(type)){
                d3Order.setPlayMethod("zusan");
                zhushu = D3ZhuShu.getZhuShu("zhuxuan",resultsplitzs);
                d3Order.setBaiwei(shiwei);
            }else if("4".equals(type)){
                d3Order.setPlayMethod("zuliu");
                zhushu =  D3ZhuShu.zhuliu(geweinum);
            }else{
                return new ResultBean("2000", "0|玩法不正确！");
            }
            System.out.println("zhushu-------------:"+zhushu);
            money = zhushu * Integer.parseInt(bettingmultiple) * Integer.parseInt(qishu) * 2;
            d3Order.setDanma("");
            d3Order.setTuoma("");

            d3Order.setHezhi("");
            d3Order.setQishu(Integer.parseInt(qishu));
            d3Order.setBeishu(Integer.parseInt(bettingmultiple));
            d3Order.setZhushu(zhushu);
            d3Order.setAmount(new BigDecimal(money));
            d3Order.setRedpacketAmount(new BigDecimal(0));
            d3Order.setCreateTime(new Date());
            d3OrderMapper.insertSelective(d3Order);
        }
        UserStatistics userStatistics = userStatisticsMapper.selectByPrimaryKey(userid);
        BigDecimal balanceAmount = userStatistics.getBalanceAmount();
        BigDecimal winningBalabceAmount = userStatistics.getWinningBalabceAmount();
        BigDecimal add = balanceAmount.add(winningBalabceAmount);
        String balance = String.valueOf(add);
        result.put("balance",balance);

        return  new ResultBean("1000", "0|待支付", result, "1");
    }
}
