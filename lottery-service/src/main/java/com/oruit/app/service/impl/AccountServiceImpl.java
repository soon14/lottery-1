package com.oruit.app.service.impl;/**
 * Created by wyt on 2017/8/25.
 */

import com.alibaba.fastjson.JSONObject;
import com.oruit.app.dao.ConfigInfoMapper;
import com.oruit.app.dao.UserAccountStatementMapper;
import com.oruit.app.dao.model.ConfigInfo;
import com.oruit.app.service.AccountService;
import com.oruit.app.util.config.Constants;
import com.oruit.app.util.web.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author wyt
 * @create 2017-08-25 11:24
 */
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private UserAccountStatementMapper userAccountStatementMapper;
    @Autowired
    private ConfigInfoMapper configInfoMapper;
    @Override
    public ResultBean queryUserAccountStatement(Map<String, Object> map) {
        ResultBean resultBean = null;
        String userid = map.get("userid").toString();
        if(userid == null || "".equals(userid)){
            return new ResultBean("2000", "0|用户id为空！");
        }

        String type = map.get("type").toString();
        if("0".equals(type)){
            map.remove("type");
        }
        if("1".equals(type)){
            map.put("type","goucai");
        }
        if("2".equals(type)){
            map.put("type","chongzhi");
        }
        if("3".equals(type)){
            map.put("type","paijiang");
        }
        if("4".equals(type)){
            map.put("type","tixian");
        }
        if("5".equals(type)){
            map.put("type","tuikuan");
        }if("6".equals(type)){
            map.put("type","jifenduihuan");
        }
        String pageSize = map.get("pageSize").toString();
        if(pageSize == null || "".equals(pageSize)){
            return new ResultBean("2000", "0|分页大小为空！");
        }
        map.put("pageSize",Integer.parseInt(pageSize));

        String recordid = map.get("recordid").toString();
        if(recordid == null || "".equals(recordid)){
            return new ResultBean("2000", "0|分页id为空！");
        }

        String date = new SimpleDateFormat("yyyyMM").format(new Date());
        String tableName = "user_account_statement_" + date;
        Integer integer = userAccountStatementMapper.existTable(tableName);
        if(integer<=0){
            return  new ResultBean("2000", "0|失败", "", "1");
        }
        map.put("tableName",tableName);
        ConfigInfo configInfo = configInfoMapper.selectByConnfigCode(Constants.ACCOUNT_CONFIG);
        String configValue = configInfo.getConfigValue();
        System.out.println("--------configValue"+configValue);
        JSONObject jsonObject = JSONObject.parseObject(configValue);
        System.out.println("--------------jsonObject:"+jsonObject);
        List<Map<String, Object>> maps = userAccountStatementMapper.queryUserAccountStatement(map);
        if(!maps.isEmpty() && maps.size()!=0){
            for (Map<String,Object> item : maps){
                String state = item.get("state").toString();
                item.put("state",state);
                String datet = item.get("datet").toString();
                datet =  datet.substring(0,datet.length()-2);
                String[] split = datet.split(" ");
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String format1 = format.format(new Date());
                boolean yy = split[0].substring(0, 4).equals(format1.substring(0, 4));
                boolean mm = split[0].substring(5, 7).equals(format1.substring(5, 7));
                boolean dd = split[0].substring(8, 10).equals(format1.substring(8, 10));
                boolean ymd = split[0].equals(format1);
                if(ymd){
                    item.put("agodate","");
                    item.put("afterdate",split[1]);
                }else if((yy&&mm&&!dd) || (yy&&!mm) ||(!mm&&dd)){
                    item.put("agodate",split[0].substring(5,10));
                    item.put("afterdate",split[1]);
                }else{
                    item.put("agodate",split[0]);
                    item.put("afterdate",split[1]);
                }

                item.remove("datet");
                if("goucai".equals(item.get("type").toString())){
                    item.put("photo",jsonObject.get("goucai").toString());
                }
                if("chongzhi".equals(item.get("type").toString())){
                    item.put("photo",jsonObject.get("chongzhi").toString());
                }
                if("paijiang".equals(item.get("type").toString())){
                    item.put("photo",jsonObject.get("paijiang").toString());
                }
                if("tixian".equals(item.get("type").toString())){
                    item.put("photo",jsonObject.get("tixian").toString());
                }
                if("tuikuan".equals(item.get("type").toString())){
                    item.put("photo",jsonObject.get("tuikuan").toString());
                }
                if("jifenduihuan".equals(item.get("type").toString())){
                    item.put("photo",jsonObject.get("jifenduihuan").toString());
                }
            }
            resultBean = new ResultBean("1000", "0|成功", maps, String.valueOf(maps.size()));
        }else {
            resultBean = new ResultBean("1000", "0|成功", maps, String.valueOf(maps.size()));
        }


        return  resultBean ;
    }
}
