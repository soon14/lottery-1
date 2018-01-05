package com.oruit.app.service.impl;/**
 * Created by wyt on 2017/8/31.
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oruit.app.aliyun.aly;
import com.oruit.app.dao.*;
import com.oruit.app.dao.model.ConfigInfo;
import com.oruit.app.dao.model.KuaileshifenPrize;
import com.oruit.app.dao.model.ShuangseqiuPrize;
import com.oruit.app.dao.model.ShuangseqiuPrizeItem;
import com.oruit.app.service.WinningService;
import com.oruit.app.ssq.shuagnseqiu;
import com.oruit.app.util.config.Constants;
import com.oruit.app.util.web.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.oruit.app.util.XMLClient.oltpQueryTermInfoServlet;

/**
 * @author wyt
 * @create 2017-08-31 12:48
 */
@Service
public class WinningServiceImpl implements WinningService {

    @Autowired
    private ShuangseqiuPrizeMapper shuangseqiuPrizeMapper;
    @Autowired
    private KuaileshifenPrizeMapper kuaileshifenPrizeMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private ConfigInfoMapper configInfoMapper;
    @Autowired
    private ShuangseqiuPrizeItemMapper shuangseqiuPrizeItemMapper;
    @Autowired
    private UserPlayRecordMapper userPlayRecordMapper;

    public static int ii = 0;
    /**
     * 开奖信息首页
     * @param map
     * @return
     */
    @Override
    public ResultBean WinHome(Map<String, Object> map) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> querywinning = shuangseqiuPrizeMapper.querywinning();
        querywinning.put("resultdate",querywinning.get("resultdate").toString().substring(5));
        Map<String, Object> queryWinklsf = kuaileshifenPrizeMapper.queryWinklsf();
        queryWinklsf.put("resultdate",queryWinklsf.get("resultdate").toString().substring(5));
        list.add(querywinning);
        list.add(queryWinklsf);
        ConfigInfo configValuekefu = configInfoMapper.selectByConnfigCode(Constants.HEROESLIST_CONFIG);
        String service = configValuekefu.getConfigValue();
        JSONObject jsonObject = JSONObject.parseObject(service);
        //七日英雄榜
        List<Map<String, Object>> heroeslist = userInfoMapper.HeroesList();
        DecimalFormat df = new DecimalFormat("#,###");
        for (Map<String, Object> items : heroeslist){
            String money = items.get("money").toString();
            items.put("money",df.format(Float.parseFloat(money)));
            String name = items.get("name").toString();
            if(name == null || "".equals(name)){
                items.put("name", items.get("mobile").toString().substring(0, 3) + "****" + items.get("mobile").toString().substring(7, 11));

            }
        }
       for(int i = 0 ; i<heroeslist.size();i++){
           Map<String, Object> map1 = heroeslist.get(i);
           if(i<3){
               if(i == 0 ){
                   map1.put("picpath",jsonObject.get("jinpai").toString());
               }
               if(i == 1 ){
                   map1.put("picpath",jsonObject.get("yinpai").toString());
               }
               if(i == 2 ){
                   map1.put("picpath",jsonObject.get("tongpai").toString());
               }
           }else{
               map1.put("picpath","");
           }
           map1.put("ranking",i+1);
       }
        Map<String,Object> result = new HashMap<>();
        result.put("lotteryinfolist",list);
        result.put("heroeslist",heroeslist);
        return new ResultBean("1000", "0|成功", result, "1");
    }

    /**
     * 双色球
     * @param map
     * @return
     * @throws ParseException
     */
    @Override
    public ResultBean InsertDate(Map<String, Object> map) throws ParseException {
        String result = aly.test("history","11");
        JSONObject json = JSONObject.parseObject(result);
        if (json.getInteger("status") != 0) {
            System.out.println(json.getString("msg"));
        } else {
            JSONObject resultarr = json.getJSONObject("result");
            if (resultarr.get("list") != null) {
                JSONArray list = resultarr.getJSONArray("list");
                //System.out.println(list);
                for (int j = list.size() - 1; j >=0; j--) {
                    JSONObject list_ = list.getJSONObject(j);
                    String issueno1 = shuangseqiuPrizeMapper.isquerywin(list_.getString("issueno"));
                    if("".equals(issueno1) || issueno1==null){
                        String opendate = list_.getString("opendate");
                        SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
                        Date parse;
                        try{
                            parse = ymdhms.parse(opendate);
                        }catch (Exception e){
                            parse = ymd.parse(opendate);
                            String format = ymd.format(parse);
                            String s = format + " 21:15:00";
                            parse = ymdhms.parse(s);
                        }
                        String issueno = list_.getString("issueno");
                        String number = list_.getString("number");
                        String refernumber = list_.getString("refernumber");
                        String saleamount = list_.getString("saleamount");
                        String totalmoney = list_.getString("totalmoney");
                        System.out.println(
                                opendate + " " + issueno + " " + number + " " + refernumber + " " + saleamount+ " " + totalmoney);
                        ShuangseqiuPrize shuangseqiuPrize = new ShuangseqiuPrize();
                        Calendar c = Calendar.getInstance();
                        c.setTime(parse);
                        c.add(Calendar.DAY_OF_MONTH, 60);
                        shuangseqiuPrize.setCaipiaoId(11);
                        shuangseqiuPrize.setCreateTime(new Date());
                        shuangseqiuPrize.setDeadLine(ymdhms.format( c.getTime()));
                        shuangseqiuPrize.setIssueNo(issueno);
                        shuangseqiuPrize.setNumber(number);
                        shuangseqiuPrize.setOpenDate(ymdhms.format(parse));
                        shuangseqiuPrize.setReferNumber(refernumber);
                        shuangseqiuPrize.setSaleAmount(new BigDecimal(saleamount));
                        shuangseqiuPrize.setTotalMoney(new BigDecimal(totalmoney));
                        shuangseqiuPrizeMapper.insertSelective(shuangseqiuPrize);
                        Integer openId = shuangseqiuPrize.getOpenId();
                        if (list_.get("prize") != null) {
                            JSONArray prize = list_.getJSONArray("prize");
                            if(prize!=null){
                                for (int i = prize.size()-1; i >= 0; i--) {
                                    JSONObject obj = (JSONObject) prize.get(i);
                                    String prizename = obj.getString("prizename");
                                    String require = obj.getString("require");
                                    String num = obj.getString("num");
                                    String singlebonus = obj.getString("singlebonus");
                                    System.out.println(prizename + " " + require + " " + num + " " + singlebonus);
                                    ShuangseqiuPrizeItem shuangseqiuPrizeItem = new ShuangseqiuPrizeItem();
                                    shuangseqiuPrizeItem.setCreateTime(new Date());
                                    shuangseqiuPrizeItem.setPrize(" ");
                                    shuangseqiuPrizeItem.setPrizeName(prizename);
                                    shuangseqiuPrizeItem.setSingleBonus(new BigDecimal(singlebonus));
                                    shuangseqiuPrizeItem.setWinningNum(Integer.parseInt(num));
                                    shuangseqiuPrizeItem.setWinningRequire(require);
                                    shuangseqiuPrizeItem.setOpenId(openId);
                                    shuangseqiuPrizeItemMapper.insertSelective(shuangseqiuPrizeItem);
                                }
                            }
                        }
                    }
                }
            }
        }
        return new ResultBean("1000", "0|成功", "", "1");
    }

    /**
     * 快乐十分
     * @param map
     * @return
     */
    @Override
    public ResultBean InsertDateKLSF(Map<String, Object> map) {

        String result = aly.test("history","114");
        JSONObject json = JSONObject.parseObject(result);
        if (json.getInteger("status") != 0) {
            System.out.println(json.getString("msg"));
        } else {
            JSONObject resultarr = json.getJSONObject("result");
            if (resultarr.get("list") != null) {
                JSONArray list = resultarr.getJSONArray("list");
                System.out.println(list);
                for (int j = list.size()-1; j >0; j--) {
                    JSONObject list_ = list.getJSONObject(j);
                    String issueno1 = kuaileshifenPrizeMapper.isquerywinklsf(list_.getString("issueno"));
                    System.out.println(issueno1==null+"-------------------");
                    System.out.println(issueno1==""+"-------------------");
                    if("".equals(issueno1) || issueno1==null){
                        String opendate = list_.getString("opendate");
                        String issueno = list_.getString("issueno");
                        String number = list_.getString("number");
                        String refernumber = list_.getString("refernumber");
                        String saleamount = list_.getString("saleamount");
                        String totalmoney = list_.getString("totalmoney");
                        System.out.println(
                                opendate + " " + issueno + " " + number + " " + refernumber + " " + saleamount+ " " + totalmoney);
                        KuaileshifenPrize kuaileshifenPrize = new KuaileshifenPrize();
                        kuaileshifenPrize.setCaipiaoId(114);
                        kuaileshifenPrize.setCreateTime(new Date());
                        kuaileshifenPrize.setDeadLine(" ");
                        kuaileshifenPrize.setIssueNo(issueno);
                        kuaileshifenPrize.setNumber(number);
                        kuaileshifenPrize.setPrize(" ");
                        kuaileshifenPrize.setReferNumber(" ");
                        kuaileshifenPrize.setOpenDate(opendate);
                        kuaileshifenPrize.setSaleAmount(new BigDecimal("0"));
                        kuaileshifenPrize.setTotalMoney(new BigDecimal("0"));
                        kuaileshifenPrizeMapper.insertSelective(kuaileshifenPrize);
                    }

                }
            }
        }
        return new ResultBean("1000", "0|成功", "", "1");
    }

    @Override
    public ResultBean LotteryInfoList(Map<String, Object> map) throws ParseException {
        ResultBean resultBean = null;
        String lotterytypeid = map.get("lotterytypeid").toString();
        if(lotterytypeid == null || "".equals(lotterytypeid)){
            return new ResultBean("2000", "0|彩种id为空！");
        }
        String pageSize = map.get("pageSize").toString();
        if(pageSize == null || "".equals(pageSize)){
            return new ResultBean("2000", "0|分页大小为空！");
        }
        String lotteryid = map.get("lotteryid").toString();
        if(lotteryid == null || "".equals(lotteryid)){
            return new ResultBean("2000", "0|彩票id为空！");
        }
        map.put("lotteryid",Integer.parseInt(lotteryid));
        map.put("lottepageSizeryid",Integer.parseInt(lotterytypeid));
        map.put("pageSize",Integer.parseInt(pageSize));
        List<Map<String, Object>> maps = new ArrayList<>();
        int caipiaoId = Integer.parseInt(lotterytypeid);
        SimpleDateFormat yymmdd = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat ymdhm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if(caipiaoId == 11){
            maps = shuangseqiuPrizeMapper.SSQHistoryList(map);
            /*System.out.println("-----------------"+maps+"-----------------");
            for(Map<String,Object> item : maps){
                item.put("resultdate",yymmdd.format(yymmdd.parse(item.get("resultdate").toString())));
            }*/
        }
        if(caipiaoId == 114){
            maps= kuaileshifenPrizeMapper.KLSFHistoryList(map);
            /*for(Map<String,Object> item : maps){
                item.put("resultdate",ymdhm.format(ymdhm.parse(item.get("resultdate").toString())));
            }*/
        }
        if(maps.size()>0&&!maps.isEmpty()){
            resultBean  = new ResultBean("1000", "0|成功", maps, String.valueOf(maps.size()));
        }else{
            resultBean  = new ResultBean("1000", "0|成功", maps, "0");
        }

        return resultBean;
    }

    @Override
    public ResultBean LotteryDetails(Map<String, Object> map) throws ParseException {
        ResultBean resultBean = null;
        String lotterytypeid = map.get("lotterytypeid").toString();
        if(lotterytypeid == null || "".equals(lotterytypeid)){
            return new ResultBean("2000", "0|彩种id为空！");
        }
        String lotteryid = map.get("lotteryid").toString();
        if(lotteryid == null || "".equals(lotteryid)){
            return new ResultBean("2000", "0|彩票id为空！");
        }
        int caipiaoId = Integer.parseInt(lotterytypeid);
        int openid = Integer.parseInt(lotteryid);
        map.put("lotterytypeid",caipiaoId);
        map.put("lotteryid",openid);
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> details;
        List<Map<String, Object>> DetailsResult;
        List<Map<String, Object>> DetailsResult1  = new ArrayList<>();
        SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
        DecimalFormat df = new DecimalFormat("#,###");
        if(caipiaoId == 11){
            details = shuangseqiuPrizeMapper.SSQDetails(map);
            String saleAmount = details.get("saleAmount").toString();
            String totalmoney = details.get("totalmoney").toString();
            saleAmount = df.format(Float.parseFloat(saleAmount));
            totalmoney = df.format(Float.parseFloat(totalmoney));
            details.put("saleAmount",saleAmount);
            details.put("totalmoney",totalmoney);
            details.put("opendate",ymd.format(ymd.parse(details.get("opendate").toString())));
            DetailsResult = shuangseqiuPrizeItemMapper.SSQDetailsResult(openid);
            for(Map<String, Object> items : DetailsResult){
                String bettingNumMoney = items.get("bettingNumMoney").toString();
                String substring = bettingNumMoney.substring(0, bettingNumMoney.indexOf("."));
                if(substring.length()>3){
                    substring = df.format(Float.parseFloat(substring));
                }
                items.put("bettingNumMoney",substring);
            }
            if(details!=null){
                result.putAll(details);
            }
            if(DetailsResult.size()>0&&!DetailsResult.isEmpty()){
                result.put("item",DetailsResult);
            }else {
                result.put("item"," ");
            }
        }
        if(caipiaoId == 114){
            details = kuaileshifenPrizeMapper.KLSFDetails(map);
            if(details!=null){
                result.putAll(details);
            }
            result.put("item",DetailsResult1);
        }
        return new ResultBean("1000", "0|成功", result, "1");
    }

    /**
     * 快乐十分顶部开奖信息
     * @param map
     * @return
     */
    @Override
    public ResultBean ElevenPickFiveWinInfo(Map<String, Object> map) throws ParseException {
        SimpleDateFormat format11 = new SimpleDateFormat("yyyyMMdd");
        String format1 = format11.format(new Date());
        Map<String, Object> stringObjectMap = kuaileshifenPrizeMapper.KLSFNewInfo();
        String s = shuagnseqiu.isKLSFissueno();
        if("false".equals(s)){
            s = shuagnseqiu.KLSFissuenoquery();
        }
        String format = String.format("%02d", Integer.parseInt(s));
        String issueno = format1 + "0"+format ;
        System.out.println("-------issueno------------:"+issueno+"---------");
        Map<String, Object> stringObject = oltpQueryTermInfoServlet("10", issueno);
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar nowTime = Calendar.getInstance();
        if(stringObject == null || "".equals(stringObject)){
            Map<String, Object> date = new HashMap<>();
            if(Integer.parseInt(format)>84 && !"1000".equals(format)){
                date = shuagnseqiu.getDate(0,"1");
            }else{
                 date = shuagnseqiu.getDate(1,format);
            }

            stringObjectMap.put("startdate",date.get("startdate").toString());
            stringObjectMap.put("enddate",date.get("enddate").toString());
        }else{
            String startTime = stringObject.get("startTime").toString();
            if(startTime == null || "".equals(startTime)){
                startTime = "";
            }else{
                startTime = getString(format2, nowTime, startTime);
            }

            String endTime = stringObject.get("endTime").toString();
            if(endTime == null || "".equals(endTime)){
                endTime = "";
            }else{
                endTime = getString(format2, nowTime, endTime);
            }
            stringObjectMap.put("startdate",startTime);
            stringObjectMap.put("enddate",endTime);
        }
        stringObjectMap.put("servertime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        if(Integer.parseInt(format)>84 && !"1000".equals(format)){
            issueno = getString(format11, format1);

        }
        stringObjectMap.put("issueno",issueno);
        if("1000".equals(s)){
            long time = new Date().getTime();//当前时间
            SimpleDateFormat format4 = new SimpleDateFormat("yyyy-MM-dd");
            String format5 = format4.format(new Date());
            String s1 = format5 + " 08:59:00";
            String s2 = format5 + " 00:00:00";
            Date parse = format2.parse(s1);
            Date parse1 = format2.parse(s2);
            if(time-parse1.getTime()>0 &&  parse.getTime() - time >0){
                issueno = getString(format11, format1);
                nowTime.setTime(new Date());
                nowTime.add(Calendar.DATE, -1);
                Date times = nowTime.getTime();
                String startTime = format4.format(times);
                startTime = startTime + " 23:00:00";
                stringObjectMap.put("startdate",startTime);
                stringObjectMap.put("enddate",s1);
                stringObjectMap.put("issueno",issueno);
            }
        }
        return new ResultBean("1000", "0|成功", stringObjectMap, "1");
    }

    private String getString(SimpleDateFormat format2, Calendar nowTime, String startTime) throws ParseException {
        Date parse = format2.parse(startTime);
        nowTime.setTime(parse);
        nowTime.add(Calendar.MINUTE, -1);
        Date time = nowTime.getTime();
        startTime = format2.format(time);
        return startTime;
    }

    private String getString(SimpleDateFormat format11, String format1) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s1 = format1.substring(0, 4) + "-" + format1.substring(4, 6) + "-" + format1.substring(6, 8);
        String s = s1 + " 23:00:00";
        System.out.println("---------l:"+s);
        Date parse = format.parse(s);
        long l = parse.getTime() - new Date().getTime();
        System.out.println("---------l:"+l);
        String issueno = "";
        if(l<0){
            Calendar nowTimes = Calendar.getInstance();
            nowTimes.setTime(format11.parse(format1));
            nowTimes.add(Calendar.DATE, 1);
            Date time = nowTimes.getTime();
            issueno = format11.format(time)+"01";
        }else {
            issueno = format11.format(format11.parse(format1))+"01";
        }
        System.out.println(issueno);
        System.out.println("--------------"+format1);

        return issueno;
    }


    /**
     * 中奖金额
     * @param map
     * @return
     */
    @Override
    public ResultBean WinmoneyList(Map<String, Object> map) {
        ResultBean resultBean = null;
        String userId = map.get("userId").toString();
        if(userId == null || "".equals(userId)){
            return new ResultBean("2000", "0|彩种id为空！");
        }
        String playid = map.get("playid").toString();
        if(playid == null || "".equals(playid)){
            return new ResultBean("2000", "0|彩种id为空！");
        }
        String pageSize1 = map.get("pageSize").toString();
        if(pageSize1 == null || "".equals(pageSize1)){
            return new ResultBean("2000", "0|分页的大小为空！");
        }
        int pageSize = Integer.parseInt(pageSize1);
        map.put("pageSize",pageSize);
        List<Map<String, Object>> maps = userPlayRecordMapper.WinmoneyList(map);
        for(Map<String, Object> item : maps){
            String buyingtime = item.get("buyingtime").toString();
            String substring = buyingtime.substring(0, 10);
            item.put("buyingtime",substring);
        }

        return new ResultBean("1000", "0|成功", maps, String.valueOf(maps.size()));
    }

    @Override
    public ResultBean WinmoneyListH5() {
        List<Map<String, Object>> maps = userPlayRecordMapper.WinmoneyListH5();
        if(maps.size()>0 && maps!=null){
            for (Map<String, Object> item : maps){
                String windate1 = item.get("windate").toString();
                String windate = windate1.substring(5,windate1.length()-2);
                item.put("windate",windate);
                item.put("mobile", item.get("mobile").toString().substring(0, 3) + "****" + item.get("mobile").toString().substring(7, 11));
            }
        }
        return new ResultBean("1000", "0|成功", maps, String.valueOf(maps.size()));
    }
}
