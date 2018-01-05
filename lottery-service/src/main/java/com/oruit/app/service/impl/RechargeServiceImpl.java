package com.oruit.app.service.impl;/**
 * Created by wyt on 2017/8/26.
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oruit.app.dao.ConfigInfoMapper;
import com.oruit.app.dao.ProductInfoMapper;
import com.oruit.app.dao.model.ConfigInfo;
import com.oruit.app.service.RechargeService;
import com.oruit.app.util.config.Constants;
import com.oruit.app.util.web.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wyt
 * @create 2017-08-26 11:36
 */
@Service
public class RechargeServiceImpl implements RechargeService {
    @Autowired
    private ProductInfoMapper productInfoMapper;
    @Autowired
    private ConfigInfoMapper configInfoMapper;

    @Override
    public ResultBean GetRechargeProductList(Map<String, Object> map) {

        String userId = map.get("userId").toString();
        if(userId == null || "".equals(userId)){
            return new ResultBean("2000", "0|用户id为空！");
        }
        String type = map.get("type").toString();
        if(type == null || "".equals(type)){
            return new ResultBean("2000", "0|类型为空！");
        }
        map.put("productCatalogId",type);
        Map<String,Object> maps = new HashMap<>();
        List<Map<String, Object>> list = productInfoMapper.GetRechargeProductList(map);
        ConfigInfo configInfo = configInfoMapper.selectByConnfigCode(Constants.RECHARGE_CONFIG);
        String configValue = configInfo.getConfigValue();
        JSONObject jsonObject = JSONObject.parseObject(configValue);
        JSONArray paytype = jsonObject.getJSONArray("paytype");
        maps.put("product",list);
        maps.put("paytype",paytype);
        return new ResultBean("1000", "0|成功", maps, "1");
    }
}
