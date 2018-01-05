package com.oruit.app.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oruit.app.dao.*;
import com.oruit.app.dao.model.CashRecord;
import com.oruit.app.dao.model.MessageInfo;
import com.oruit.app.dao.model.PushConfig;
import com.oruit.app.dao.model.PushMessage;
import com.oruit.app.message.MessagePushUtil;
import com.oruit.app.service.JGuangService;
import com.oruit.app.ssq.Issueno;
import com.oruit.app.ssq.shuagnseqiu;
import com.oruit.app.util.web.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wyt on 2017-11-13.
 */
@Service
public class JGuangServiceImpl implements JGuangService {
    @Autowired
    private UserPlayRecordMapper userPlayRecordMapper;
    @Autowired
    private PushConfigMapper pushConfigMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private ShuangseqiuPrizeMapper shuangseqiuPrizeMapper;
    @Autowired
    private KuaileshifenPrizeMapper kuaileshifenPrizeMapper;
    @Autowired
    private MessageInfoMapper messageInfoMapper;
    @Autowired
    private CashRecordMapper cashRecordMapper;
    @Autowired
    private PushMessageMapper pushMessageMapper;

    /**
     * 查询推送的列表
     * @param params
     * @return
     */
    @Override
    public ResultBean JPushHomeInfo(Map<String, Object> params) {
        String userid = params.get("userid").toString();
        List<Map<String, Object>> maps = pushConfigMapper.queryPushInfo(userid);
        if(maps.size()<=0){
            Integer integer = insertJPush(userid);
            if(integer>0){
                maps = pushConfigMapper.queryPushInfo(userid);
            }
        }
        return  new ResultBean("1000", "0|成功", maps, String.valueOf(maps.size()));
    }

    /**
     * 插入推送表
     * @param userId
     * @return
     */
    private Integer insertJPush(String userId) {
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
        return i ;
    }

    /**
     * 保存用户的状态
     * @param params
     * @return
     */
    @Override
    public ResultBean UpdateJPushSetting(Map<String, Object> params) {
        String userid = params.get("userid").toString();
        String parameter = params.get("parameter").toString();
        if(userid == null || "".equals(userid)){
            return new ResultBean("2000", "0|用户id为空！");
        }
        if(parameter == null || "".equals(parameter)){
            return new ResultBean("2000", "0|pushcode为空！");
        }
        JSONArray objects = JSONArray.parseArray(parameter);
        PushConfig pushConfig = new PushConfig();
        pushConfig.setUserId(userid);
        for (int i = 0 ; i < objects.size() ; i++) {
            JSONObject jsonObject = objects.getJSONObject(i);
            String pushcode = jsonObject.get("pushcode").toString();
            String pushenable = jsonObject.get("pushenable").toString();
            pushConfig.setPushCode(pushcode);
            pushConfig.setPushEnable(Integer.parseInt(pushenable));
                pushConfigMapper.updateByPrimaryKeySelectivePushConfig(pushConfig);
        }
        return new ResultBean("1000", "0|成功");
    }

    /**
     * 极光推送快乐十分的中奖通知
     */
    @Override
    public ResultBean JPushKLSF(Map<String, Object> params) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyMMdd");
        String format1 = format.format(new Date());
        String userid = params.get("userid").toString();
        String s = shuagnseqiu.KLSFissuenotuikuan();
        s = String.valueOf(Integer.parseInt(s) - 1);
        s = String.format("%02d", Integer.parseInt(s));
        String issueno = format1 + s ;
        params.put("issueno",issueno);
        List<Map<String,Object>> maps = userPlayRecordMapper.queryUserWInningInfoAll(params);
        String content = "" ;//内容
        String issuenos = "";//期号
        Map<String, String> messageMap = new HashMap<>();
        messageMap.put("Id", "0");
        messageMap.put("Title", "中奖消息");
        messageMap.put("Type", "1");
        messageMap.put("Content",content );
        messageMap.put("CreateTime", new Date().toString());
        messageMap.put("From", "0");
        messageMap.put("IsRead", "0");
        List<String> userList = new ArrayList<>();
        MessageInfo messageinfo = new MessageInfo();
        PushMessage pushMessage = new PushMessage();
        for(Map<String,Object> item : maps){
            content = "您购买的第"+issueno+"期快乐十分已中奖，点击查看";
            String userid1 = item.get("userid").toString();
            userList.add(userid1);
            //推送
            String title = "中奖消息" ;
            String publisher = "0" ;
            Integer msgtype = 1 ;
            //插入消息列表
            InsertMessage(content, messageinfo, userid, title, publisher, msgtype);
            messageMap.put("Content",content);
            //推送
            String result = MessagePushUtil.SendPushV3(userList, messageMap, content, "中奖消息", 1);
            String pushcode = "zhongjiangtongzhi";
            //插入推送消息
            insertPushcode(result,content, pushMessage, userid1, pushcode);
            userList = new ArrayList<>();

        }
        /*Map<String, Object> map = kuaileshifenPrizeMapper.queryOpenWinNumberInfoKLSF(issueno);
        if(map!=null){
            messageMap.put("Title", "开奖消息");
            String number = map.get("number").toString();
            String res = "第"+issueno+"期开奖号码为："+number;
            messageMap.put("Content",res);
            //推送
            String s1 = MessagePushUtil.SendPushALL(messageMap, res);
            //插入推送消息
            insertPushcode(s1,res, pushMessage, "all", "caizhonghuodong");

        }*/
        return new ResultBean("1000", "0|成功");
    }

    private void insertPushcode(String remark,String content, PushMessage pushMessage, String userid1, String pushcode) {
        pushMessage.setPushCode(pushcode);
        pushMessage.setPushContent(content);
        pushMessage.setRemark(remark);
        if("success".equals(remark)){
            pushMessage.setPushStatus(1);
        }else{
            pushMessage.setPushStatus(0);
        }
        pushMessage.setPushTime(new Date());
        pushMessage.setToUser(userid1);
        pushMessageMapper.insertSelective(pushMessage);
    }

    /**
     *双色球的推送
     * @param params
     * @throws Exception
     */
    @Override
    public ResultBean JPushTimer(Map<String, Object> params) throws Exception {
        String s1 = Issueno.achieveNum();
        s1 = String.valueOf(Integer.parseInt(s1) - 1);
        params.put("issueno",s1);
        List<Map<String,Object>> maps = userPlayRecordMapper.queryUserWInningInfoAll(params);
        String content = "" ;//内容
        Map<String, String> messageMap = new HashMap<>();
        messageMap.put("Id", "0");
        messageMap.put("Title", "中奖消息");
        messageMap.put("Type", "1");
        messageMap.put("CreateTime", new Date().toString());
        messageMap.put("From", "0");
        messageMap.put("IsRead", "0");
        List<String> userList = new ArrayList<>();
        MessageInfo messageinfo = new MessageInfo();
        PushMessage pushMessage = new PushMessage();
        if(maps.size()>0){
            for(Map<String,Object> item : maps){
                String userid = item.get("userid").toString();
                userList.add(userid);
                content = "您购买的第"+s1+"期双色球已中奖，点击查看";
                String title = "中奖消息" ;
                String publisher = "0" ;
                Integer msgtype = 1 ;
                //插入消息列表
                InsertMessage(content, messageinfo, userid, title, publisher, msgtype);
                messageMap.put("Content",content);
                //推送
                String sendPushV3 = MessagePushUtil.SendPushV3(userList, messageMap, content, "中奖消息", 1);
                //插入推送消息
                String pushcode = "zhongjiangtongzhi";
                insertPushcode(sendPushV3,content, pushMessage, userid, pushcode);
                userList = new ArrayList<>();
            }
        }

        return new ResultBean("1000", "0|成功");
    }
    /**
     *双色球的开奖号码推送
     * @param params
     * @throws Exception
     */
    @Override
    public ResultBean winSSQNumber(Map<String, Object> params) throws Exception {
        String s1 = Issueno.achieveNum();
        s1 = String.valueOf(Integer.parseInt(s1) - 1);
        Map<String, String> messageMap = new HashMap<>();
        messageMap.put("Id", "0");
        messageMap.put("Title", "中奖消息");
        messageMap.put("Type", "5");
        messageMap.put("CreateTime", new Date().toString());
        messageMap.put("From", "0");
        messageMap.put("IsRead", "0");
        PushMessage pushMessage = new PushMessage();
        Map<String, Object> map = shuangseqiuPrizeMapper.queryOpenWinNumberInfo(s1);
        messageMap.put("Title", "开奖消息");
        if(map!=null){
            String number = map.get("number").toString();
            String refer_number = map.get("refer_number").toString();
            String open_id = map.get("open_id").toString();
            String res = "第"+s1+"期开奖号码为："+number+"|"+refer_number;
            messageMap.put("Content",res);
            messageMap.put("Id", open_id);
            //推送
            String mess = MessagePushUtil.SendPushALL(messageMap, res);
            //插入推送消息
            insertPushcode(mess,res, pushMessage, "all", "shuangseqiu");
        }
        return new ResultBean("1000", "0|成功");
    }

    /**
     * 推送
     * @param maps
     * @param content
     * @param messageMap
     * @param userList
     * @return
     */
    private static List<String> getStrings(List<Map<String, Object>> maps, String content, Map<String, String> messageMap, List<String> userList) {
        int iDealNum = 1;
        int ilsMapLength = maps.size();
        if ((iDealNum % 1000 == 0 || iDealNum == ilsMapLength) || (iDealNum<1000 && iDealNum == ilsMapLength)) {
            MessagePushUtil.SendPushV3(userList, messageMap, content, "系统消息", 1);
            userList = new ArrayList<>();
            try {
                Thread.sleep(100);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
        iDealNum++;
        return userList;
    }


    /**
     * 提现的推送
     * @param params
     */
    @Override
    public ResultBean JPushtixian(Map<String, Object> params) {
        String userid = params.get("userid").toString();
        String recordid = params.get("recordid").toString();
        CashRecord cashRecord = cashRecordMapper.queryUsercashrecord(params);
        Integer state = cashRecord.getState();
        String res = "";
        String result = cashRecord.getRemark();
        if(state == 2 ){
            res = "成功";
            result = "";
        }else if(state == 3){
            res = "失败";
            result = result;
        }else  if (state == 1){
            res = "处理中";
            result = "";
        }
        if(cashRecord!=null && !"".equals(cashRecord)){
            Map<String, String> messageMap = new HashMap<>();
            messageMap.put("Id", "0");
            messageMap.put("Title", "提现消息");
            messageMap.put("Type", "2");
            messageMap.put("CreateTime", new Date().toString());
            messageMap.put("From", "0");
            messageMap.put("IsRead", "0");
            List<String> userList = new ArrayList<>();
            MessageInfo messageinfo = new MessageInfo();
            userList.add(userid);
            String content = "您的提现已"+res+"点击查看";
            String title = "提现消息" ;
            String publisher = "0" ;
            Integer msgtype = 2 ;
            //推送
            MessagePushUtil.SendPushV3(userList, messageMap, content, "提现消息", 1);
            //插入消息列表
            if(state == 3 ){
                content = "您的提现已" + res + "原因："+ result ;
            }
            InsertMessage(content, messageinfo, userid, title, publisher, msgtype);
        }
        return new ResultBean("1000", "0|成功");
    }

    /**
     * 消息列表
     * @param map
     * @return
     */
    @Override
    public ResultBean MessageList(Map<String, Object> map) {
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
        String msgid = map.get("msgid").toString();
        if (msgid == null || "".equals(msgid)) {
            return new ResultBean("2000", "0|分页的id为空！");
        }
        int i = Integer.parseInt(msgid);
        map.put("msgid", i);
        List<Map<String, Object>> maps = messageInfoMapper.queryMessageList(map);
        if(maps.size()>0){
            for(Map<String, Object> item : maps){
                String createtime = item.get("createtime").toString();
                createtime = createtime.substring(0, createtime.length() - 2);
                String[] split = createtime.split(" ");
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String format1 = format.format(new Date());
                boolean yy = split[0].substring(0, 4).equals(format1.substring(0, 4));
                boolean mm = split[0].substring(5, 7).equals(format1.substring(5, 7));
                boolean dd = split[0].substring(8, 10).equals(format1.substring(8, 10));
                boolean ymd = split[0].equals(format1);
                if(ymd){
                    item.put("createtime",split[1]);
                }else if((yy&&mm&&!dd) || (yy&&!mm) ||(!mm&&dd)){
                    item.put("createtime",split[0].substring(5,10)+" "+split[1]);
                }else{
                    item.put("createtime",createtime);
                }
            }
            return new ResultBean("1000", "0|成功",maps,String.valueOf(maps.size()));
        }else{
            return new ResultBean("1000", "0|失败");
        }

    }

    /**
     * 更新消息状态
     * @param map
     * @return
     */
    @Override
    public ResultBean UpdateMessageState(Map<String, Object> map) {
        String userid = map.get("userid").toString();
        if (userid == null || "".equals(userid)) {
            return new ResultBean("2000", "0|用户id号为空！");
        }
        String msgid = map.get("msgid").toString();
        if (msgid == null || "".equals(msgid)) {
            return new ResultBean("2000", "0|分页的id为空！");
        }
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setMsgId(Integer.parseInt(msgid));
        messageInfo.setUserId(userid);
        messageInfo.setIsRead(1);
        int i = messageInfoMapper.updateByPrimaryKeySelective(messageInfo);
        if(i > 0){
            return new ResultBean("1000", "0|成功",i,"1");
        }else{
            return new ResultBean("2000", "0|失败");
        }

    }

    @Override
    public ResultBean UpdateMessageStateAll(Map<String, Object> map) {
        String userid = map.get("userid").toString();
        if (userid == null || "".equals(userid)) {
            return new ResultBean("2000", "0|用户id号为空！");
        }
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setUserId(userid);
        messageInfo.setIsRead(1);
        Integer integer = messageInfoMapper.updateByMessageStatusAll(messageInfo);
        if(integer>0){
            return new ResultBean("1000", "0|成功");
        }else{
            return new ResultBean("2000", "0|失败");
        }
    }

    /**
     * 插入消息列表
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
}
