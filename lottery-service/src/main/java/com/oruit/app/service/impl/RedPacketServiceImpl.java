package com.oruit.app.service.impl;/**
 * Created by wyt on 2017/8/24.
 */

import com.alibaba.fastjson.JSONObject;
import com.oruit.app.dao.*;
import com.oruit.app.dao.model.ConfigInfo;
import com.oruit.app.dao.model.Redacket;
import com.oruit.app.dao.model.UserInfo;
import com.oruit.app.dao.model.UserRedpacket;
import com.oruit.app.service.RedPacketService;
import com.oruit.app.util.config.Constants;
import com.oruit.app.util.web.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wang
 * @create 2017-08-24 22:12
 */
@Service
public class RedPacketServiceImpl implements RedPacketService {
    @Autowired
    private UserRedpacketMapper userRedpacketMapper;
    @Autowired
    private UserAuthorizationMapper userAuthorizationMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private RedacketMapper redacketMapper;
    @Autowired
    private ConfigInfoMapper configInfoMapper;

    @Override
    public ResultBean RedPacket(Map<String, Object> map) throws ParseException {
        String userid = map.get("userid").toString();
        if(userid == null || "".equals(userid)){
            return new ResultBean("2000", "0|用户id为空！");
        }

        String type = map.get("type").toString();
        if(type == null || "".equals(type)){
            return new ResultBean("2000", "0|红包类型为空！");
        }

        String pageSize = map.get("pageSize").toString();
        if(pageSize == null || "".equals(pageSize)){
            return new ResultBean("2000", "0|分页大小为空！");
        }
        map.put("pageSize",Integer.parseInt(pageSize));

        String redpackageid = map.get("redpackageid").toString();
        if(redpackageid == null || "".equals(redpackageid)){
            return new ResultBean("2000", "0|红包id为空！");
        }
        Map<String,Object> maps = new HashMap<>();
        map.put("date",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        map.put("redpackageid",Integer.parseInt(redpackageid));
        List<Map<String, Object>> noNormalRed = new ArrayList<>();
        Integer noNormalnum = userRedpacketMapper.queryUserRedpacketNoNormalnum(map);
        Integer normalnum = userRedpacketMapper.queryUserRedpacketNormalnum(map);
        maps.put("noNormalnum",noNormalnum);
        maps.put("normalnum",normalnum);
        SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
        if("1".equals(type)){
            noNormalRed = userRedpacketMapper.queryUserRedpacketNormal(map);

        }
        if("2".equals(type)){
           noNormalRed = userRedpacketMapper.queryUserRedpacketNoNormal(map);
        }
        for(Map<String,Object> item : noNormalRed){
            item.put("begintime",ymd.format(ymd.parse(item.get("begintime").toString())));
            item.put("overtime",ymd.format(ymd.parse(item.get("overtime").toString())));
        }
        maps.put("item",noNormalRed);
        return new ResultBean("1000", "0|成功", maps, "1");
    }

    @Override
    public ResultBean ReceiveRedPacket(Map<String, Object> map) {
        ResultBean resultBean = null;
        String unionId = map.get("unionId").toString();
        if(unionId == null || "".equals(unionId)){
            return new ResultBean("2000", "0|unionId为空！");
        }
        String userId = userAuthorizationMapper.queryUserid(unionId);
        if(userId==null || "".equals(userId)){
            return new ResultBean("2000", "1|领取失败", "", "1");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());

        ConfigInfo configInfo = configInfoMapper.selectByConnfigCode(Constants.XINREN_CONFIG);
        String configValue = configInfo.getConfigValue();
        System.out.println("--------configValue"+configValue);
        JSONObject jsonObject = JSONObject.parseObject(configValue);
        String xinrenhongbao = jsonObject.get("xinrenhongbao").toString();
        Redacket redacket = redacketMapper.selectByPrimaryKey(Integer.parseInt(xinrenhongbao));
        Integer validDays = redacket.getValidDays();
        c.add(Calendar.DATE, validDays );
        UserRedpacket userRedpacket = new UserRedpacket();
        userRedpacket.setBeginTime(new Date());
        userRedpacket.setRedpacketId(redacket.getRedpacketId());
        userRedpacket.setCreateTime(new Date());
        userRedpacket.setState(0);
        userRedpacket.setUserId(userId);
        userRedpacket.setOverTime(c.getTime());
        int i = userRedpacketMapper.insertSelective(userRedpacket);
        if(i>0){
            resultBean = new ResultBean("1000", "1|领取成功", "", "1") ;
        }else{
            resultBean = new ResultBean("2000", "1|领取失败", "", "1") ;
        }
        return resultBean;
    }

    @Override
    public ResultBean ReceiveRedPacketh5(HttpServletRequest request) throws ParseException {
        ResultBean resultBean = null;
        String userId = request.getParameter("userId");
        if(userId == null || "".equals(userId)){
            return new ResultBean("2000", "0|userId为空！");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());

        ConfigInfo configInfo = configInfoMapper.selectByConnfigCode(Constants.XINREN_CONFIG);
        String configValue = configInfo.getConfigValue();
        System.out.println("--------configValue"+configValue);
        JSONObject jsonObject = JSONObject.parseObject(configValue);
        String xinrenhongbao = jsonObject.get("xinrenhongbao").toString();
        Redacket redacket = redacketMapper.selectByPrimaryKey(Integer.parseInt(xinrenhongbao));
        String query = userRedpacketMapper.query(userId);
        if(query!=null && !"".equals(query)){
            return new ResultBean("2000", "1|已经领取过红包！");
        }
        Integer validDays = redacket.getValidDays();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        c.add(Calendar.DATE, validDays );
        UserRedpacket userRedpacket = new UserRedpacket();
        userRedpacket.setBeginTime(new Date());
        userRedpacket.setRedpacketId(redacket.getRedpacketId());
        userRedpacket.setCreateTime(new Date());
        userRedpacket.setState(0);
        userRedpacket.setUserId(userId);
        userRedpacket.setOverTime(date.parse(date.format(c.getTime())));
        int i = userRedpacketMapper.insertSelective(userRedpacket);
        if(i>0){
            resultBean = new ResultBean("1000", "1|领取成功", "", "1") ;
        }else{
            resultBean = new ResultBean("2000", "1|领取失败", "", "1") ;
        }
        return resultBean;
    }
}
