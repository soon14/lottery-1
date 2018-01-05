package com.oruit.app.service.impl;/**
 * Created by wyt on 2017/8/26.
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oruit.app.dao.ConfigInfoMapper;
import com.oruit.app.dao.UserInfoMapper;
import com.oruit.app.dao.UserShareMapper;
import com.oruit.app.dao.model.ConfigInfo;
import com.oruit.app.dao.model.UserInfo;
import com.oruit.app.dao.model.UserShare;
import com.oruit.app.service.ShareService;
import com.oruit.app.util.config.Constants;
import com.oruit.app.util.web.ResultBean;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author wyt
 * @create 2017-08-26 18:37
 */
@Service
public class ShareServiceImpl implements ShareService {
    @Autowired
    private UserShareMapper userShareMapper;
    @Autowired
    private ConfigInfoMapper configInfoMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Override
    public ResultBean ShareFriend(Map<String,Object> map) {
        ResultBean resultBean = null;
        String userId = map.get("userid").toString();
        if(userId == null || "".equals(userId)){
            return new ResultBean("2000", "0|用户id为空！");
        }
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);
        if(userInfo==null || "".equals(userInfo)){
            return new ResultBean("2000", "0|没有此用户！");
        }
        UserShare userShare = userShareMapper.queryUserid(userId);
        Integer shareid = 0;
        ConfigInfo configinfo = configInfoMapper.selectByConnfigCode(Constants.SHAREURL_CONFIG);
        String configValue = configinfo.getConfigValue();
        JSONObject jsonObject = JSONObject.parseObject(configValue);
        JSONArray paytype = jsonObject.getJSONArray("parameter");
        int size = paytype.size();
        Random random = new Random();
        int i1 = random.nextInt(size);
        String s = paytype.get(i1).toString();
        JSONObject jsonObject1 = JSONObject.parseObject(s);
        if(userShare==null || "".equals(userShare)){
            UserShare userShare1 = new UserShare();
            userShare1.setCreateTime(new Date());
            userShare1.setUserId(userId);
            int i = userShareMapper.insertSelective(userShare1);
            if(i>0){
                shareid = userShare1.getId();
                String id = String.valueOf(shareid);
                jsonObject1.put("url",jsonObject1.get("url") + "&id="+id);
                resultBean = new ResultBean("1000", "0|成功", jsonObject1, "1");
            }else{
                resultBean = new ResultBean("2000", "0|失败", "", "1");
            }
        }else{
            shareid = userShare.getId();
            String id = String.valueOf(shareid);
            System.out.println(jsonObject1.get("url")+"-----------------------");
            jsonObject1.put("url",jsonObject1.get("url") + "&id="+id);
            resultBean = new ResultBean("1000", "0|成功", jsonObject1, "1");
            userShare.setUserId(userId);
            userShare.setUpdateTime(new Date());
            userShareMapper.updateByPrimaryKey(userShare);
        }
        return resultBean;
    }
}
