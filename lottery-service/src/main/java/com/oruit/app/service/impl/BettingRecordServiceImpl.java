package com.oruit.app.service.impl;/**
 * Created by wyt on 2017/9/9.
 */

import com.alibaba.fastjson.JSONObject;
import com.oruit.app.dao.ConfigInfoMapper;
import com.oruit.app.dao.KuaileshifenPrizeMapper;
import com.oruit.app.dao.ShuangseqiuPrizeMapper;
import com.oruit.app.dao.UserPlayRecordMapper;
import com.oruit.app.dao.model.ConfigInfo;
import com.oruit.app.dao.model.KuaileshifenPrize;
import com.oruit.app.dao.model.ShuangseqiuPrize;
import com.oruit.app.dao.model.UserPlayRecord;
import com.oruit.app.service.BettingRecordService;
import com.oruit.app.ssq.Issueno;
import com.oruit.app.ssq.WinDate;
import com.oruit.app.ssq.shuagnseqiu;
import com.oruit.app.util.XMLClient;
import com.oruit.app.util.config.Constants;
import com.oruit.app.util.web.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author @wyt
 * @create 2017-09-09 16:22
 */
@Service
public class BettingRecordServiceImpl implements BettingRecordService {
    @Autowired
    private UserPlayRecordMapper userPlayRecordMapper;
    @Autowired
    private ConfigInfoMapper configInfoMapper;
    @Autowired
    private ShuangseqiuPrizeMapper shuangseqiuPrizeMapper;
    @Autowired
    private KuaileshifenPrizeMapper kuaileshifenPrizeMapper;

    /**
     * 投注记录
     *
     * @param map
     * @return
     */
    @Override
    public ResultBean BettingRecord(Map<String, Object> map) throws Exception {
        String userid = map.get("userid").toString();
        if (userid == null || "".equals(userid)) {
            return new ResultBean("2000", "0|用户id号为空！");
        }
        String pageSize1 = map.get("pageSize").toString();
        if (pageSize1 == null || "".equals(pageSize1)) {
            return new ResultBean("2000", "0|分页的大小为空！");
        }
        int pageSize = Integer.parseInt(pageSize1);
        map.put("pageSize", pageSize);
        String type = map.get("type").toString();
        if (type == null || "".equals(type)) {
            return new ResultBean("2000", "0|类型为空！");
        }
        String playid = map.get("playid").toString();
        if (playid == null || "".equals(playid)) {
            return new ResultBean("2000", "0|分页的id为空！");
        }
        int i = Integer.parseInt(playid);
        map.put("playid", i);
        map.put("status", Integer.parseInt(type));
        List<Map<String, Object>> maps = new ArrayList<>();
        if (Integer.parseInt(type) == -1) {
            maps = userPlayRecordMapper.QueryUserPlayRecordALL(map);
        } else {
            maps = userPlayRecordMapper.QueryUserPlayRecord(map);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        for (Map<String, Object> item : maps) {
            Date createtime = simpleDateFormat.parse(item.get("createtime").toString());
            item.put("createtime", simpleDateFormat.format(createtime));
            String state = item.get("state").toString();
            String issue_no = item.get("issue_no").toString();
            String caipiao_id = item.get("caipiao_id").toString();
            String s = "";
            if("11".equals(caipiao_id)){
                s = Issueno.achieveNum();
            }
            if("114".equals(caipiao_id)){
                s = shuagnseqiu.KLSFissuenotuikuan();
                s = format.format(new Date()) + s;
            }

            if (!"".equals(state)) {
                if ("0".equals(state)) {
                    item.put("state", "等待出票");
                    if(Integer.parseInt(s) > Integer.parseInt(issue_no)){
                        item.put("state", "出票失败");
                    }
                }
                if ("3".equals(state)) {
                    item.put("state", "未中奖");
                }
                if ("4".equals(state)) {
                    item.put("state", "中奖");
                }
                if ("2".equals(state)) {
                    item.put("state", "等待开奖");
                }
                if ("1".equals(state)) {
                    item.put("state", "出票失败");
                }
                if ("5".equals(state)) {
                    item.put("state", "出票中");
                    if(Integer.parseInt(s) > Integer.parseInt(issue_no)){
                        item.put("state", "出票失败");
                    }
                }
            }

        }
        return new ResultBean("1000", "0|成功", maps, String.valueOf(maps.size()));
    }

    /***
     * 投注记录详情
     * @param map
     * @return
     * @throws ParseException
     */
    @Override
    public ResultBean BettingRecordDetails(Map<String, Object> map) throws Exception {
        String userid = map.get("userid").toString();
        if (userid == null || "".equals(userid)) {
            return new ResultBean("2000", "0|用户id号为空！");
        }
        String playid = map.get("playid").toString();
        if (playid == null || "".equals(playid)) {
            return new ResultBean("2000", "0|分页的id为空！");
        }
        int i = Integer.parseInt(playid);
        map.put("playid", i);
        Map<String, Object> item = userPlayRecordMapper.BettingRecordDetails(map);
        String ordertime1 = item.get("ordertime").toString();
        String issueno1 = item.get("issueno").toString();
        String ordertime = ordertime1.substring(0, ordertime1.length() - 2);
        item.put("ordertime", ordertime);
        String type = item.get("type").toString();
        String state = item.get("state").toString();
        String types = "SSQ";
        String caipiaoid = item.get("caipiaoid").toString();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String s = "";
        if("11".equals(caipiaoid)){
            s = Issueno.achieveNum();
        }
        if("114".equals(caipiaoid)){
            s = shuagnseqiu.KLSFissuenotuikuan();
            s = format.format(new Date()) + s;
        }
        if ("114".equals(caipiaoid)) {
            issueno1 = issueno1.substring(0, 8) + "0" + issueno1.substring(8, 10);
             types = "10";
        }
        if ("11".equals(caipiaoid)) {
             types = "SSQ";
        }
        String winnumber="";
        if (!"".equals(state)) {
            if ("0".equals(state)) {
                item.put("state", "等待出票");
                if(Integer.parseInt(s) > Integer.parseInt(item.get("issueno").toString())){
                    item.put("state", "出票失败");
                }
                item.put("image", "");
                item.put("winnumber","");
            } else if ("3".equals(state)) {
                item.put("state", "未中奖");
                item.put("image", "");
                winnumber = getString(issueno1, types, caipiaoid, winnumber);
                item.put("winnumber",winnumber);
            } else if ("4".equals(state)) {
                item.put("state", "中奖");
                ConfigInfo configInfo = configInfoMapper.selectByConnfigCode(Constants.BEETING_CONFIG);
                String configValue = configInfo.getConfigValue();
                System.out.println("--------configValue" + configValue);
                JSONObject jsonObject = JSONObject.parseObject(configValue);
                String zhongjiang = jsonObject.get("zhongjiang").toString();
                item.put("image", zhongjiang);
                winnumber = getString(issueno1, types, caipiaoid, winnumber);
                item.put("winnumber",winnumber);
            } else if ("2".equals(state)) {
                item.put("state", "等待开奖");
                item.put("image", "");
                item.put("winnumber","");
            } else if ("1".equals(state)) {
                item.put("state", "出票失败");
                item.put("image", "");
                item.put("winnumber","");
            } else if ("5".equals(state)) {
                item.put("state", "出票中");
                if(Integer.parseInt(s) > Integer.parseInt(item.get("issueno").toString())){
                    item.put("state", "出票失败");
                }
                item.put("image", "");
                item.put("winnumber","");
            } else {
                item.put("state", "");
                item.put("image", "");
                item.put("winnumber","");
            }
        }
        if (!"".equals(type)) {
            if ("1".equals(type)) {
                item.put("type", "单式");
            } else if ("2".equals(type)) {
                item.put("type", "复式");
            } else if ("3".equals(type)) {
                item.put("type", "胆拖");
            } else {
            }
        }
        if ("114".equals(item.get("caipiaoid").toString())) {
            String playmethod = item.get("playmethod").toString();
            if (!"".equals(playmethod)) {
                if ("r2".equals(playmethod)) {
                    item.put("playmethod", "任选二");
                }
                if ("r3".equals(playmethod)) {
                    item.put("playmethod", "任选三");
                }
                if ("r4".equals(playmethod)) {
                    item.put("playmethod", "任选四");
                }
                if ("r5".equals(playmethod)) {
                    item.put("playmethod", "任选五");
                }
                String issueno = item.get("issueno").toString();
                if (issueno != null && !"".equals(issueno)) {
                    String substring = issueno.substring(0, 8);
                    String substring1 = issueno.substring(8);
                    String getwindate = shuagnseqiu.getwindate(Integer.parseInt(substring1), substring);
                    item.put("lotterydate", getwindate);
                } else {
                    item.put("lotterydate", "");
                }
            }
        } else {
            item.put("playmethod", " ");
            String lotterydate = WinDate.date(ordertime);
            item.put("lotterydate", lotterydate);
        }
        return new ResultBean("1000", "0|成功", item, "1");
    }

    /**
     * 保存中奖明细
     * @return
     */
    @Override
    public void savewinmoney() {
        String responseCode = "1";
        List<Map<String, Object>> querywinmoney = userPlayRecordMapper.savewinmoney();
        for (Map<String,Object> item : querywinmoney){
            String caipiao_order_sub_code = item.get("caipiao_order_sub_code").toString();
            String winning_money = item.get("winning_money").toString();
            Map<String, Object> map1 = XMLClient.oltpSaveBonusDetailServlet(caipiao_order_sub_code, winning_money);
            responseCode = map1.get("responseCode").toString();
            if("0".equals(responseCode)){
                UserPlayRecord userPlayRecord = new UserPlayRecord();
                userPlayRecord.setCaipiaoOrderSubCode(caipiao_order_sub_code);
                userPlayRecord.setQueryLottery(1);
                userPlayRecordMapper.updateByQueryLottery(userPlayRecord);
            }
        }
    }

    private String getString(String issueno1, String types, String caipiaoid, String winnumber) {
        Map<String, Object> stringObjectMap = XMLClient.oltpQueryOpenResultServlet(types, issueno1);
        System.out.println("------------"+stringObjectMap!=null);
        System.out.println("------------"+!"".equals(stringObjectMap));
        System.out.println("------------"+!stringObjectMap.isEmpty()+"------------");
        if("11".equals(caipiaoid)){
            if(stringObjectMap!=null && !"".equals(stringObjectMap)&&!stringObjectMap.isEmpty()){
                String drawCode = stringObjectMap.get("drawCode").toString();
                if ("11".equals(caipiaoid)) {
                    winnumber = drawCode.substring(0, 2) + " " + drawCode.substring(2, 4) + " " + drawCode.substring(4, 6) + " " + drawCode.substring(6, 8) + " " + drawCode.substring(8, 10) + " " + drawCode.substring(10, 12) + " " + drawCode.substring(12, 14);

                } if ("114".equals(caipiaoid)) {
                    winnumber = drawCode.substring(0, 2) + " " + drawCode.substring(2, 4) + " " + drawCode.substring(4, 6) + " " + drawCode.substring(6, 8) + " " + drawCode.substring(8, 10) + " " + drawCode.substring(10, 12) + " " + drawCode.substring(12, 14) + " " + drawCode.substring(14, 16);
                }
            }else{
                ShuangseqiuPrize selectwinnumber = shuangseqiuPrizeMapper.selectwinnumber(issueno1);
                if(selectwinnumber!=null && !"".equals(selectwinnumber)){
                    String number = selectwinnumber.getNumber();
                    String referNumber = selectwinnumber.getReferNumber();
                    winnumber = number + " " + referNumber;
                }

            }
        }
        if("114".equals(caipiaoid)){
            if(stringObjectMap!=null && !"".equals(stringObjectMap)&&!stringObjectMap.isEmpty()){
                String drawCode = stringObjectMap.get("drawCode").toString();

                if ("11".equals(caipiaoid)) {
                    winnumber = drawCode.substring(0, 2) + " " + drawCode.substring(2, 4) + " " + drawCode.substring(4, 6) + " " + drawCode.substring(6, 8) + " " + drawCode.substring(8, 10) + " " + drawCode.substring(10, 12) + " " + drawCode.substring(12, 14);

                } if ("114".equals(caipiaoid)) {
                    winnumber = drawCode.substring(0, 2) + " " + drawCode.substring(2, 4) + " " + drawCode.substring(4, 6) + " " + drawCode.substring(6, 8) + " " + drawCode.substring(8, 10) + " " + drawCode.substring(10, 12) + " " + drawCode.substring(12, 14) + " " + drawCode.substring(14, 16);
                }
            }else{
                issueno1 = issueno1.substring(0, 8)  + issueno1.substring(9, 11);

                KuaileshifenPrize selectwinnumberklsf = kuaileshifenPrizeMapper.selectwinnumberklsf(issueno1);
                if(selectwinnumberklsf!=null && !"".equals(selectwinnumberklsf)){
                    winnumber = selectwinnumberklsf.getNumber();
                }


            }
        }

        return winnumber;
    }


}
