package com.oruit.app.service.impl;

import com.oruit.app.dao.UserPlayRecordMapper;
import com.oruit.app.service.SendWinNoticeService;
import com.oruit.app.ssq.shuagnseqiu;
import com.oruit.app.util.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wyt on 2017-11-09.
 */
@Service
public class SendWinNoticeServiceImpl implements SendWinNoticeService {
    @Autowired
    private UserPlayRecordMapper userPlayRecordMapper;
    @Override
    public void sendWinNotice() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyMMdd");
        String format1 = format.format(new Date());
        String s = shuagnseqiu.KLSFissuenotuikuan();
        s = String.valueOf(Integer.parseInt(s) - 1);
        String issueno1 = format1 + s ;
        List<Map<String, Object>> maps = userPlayRecordMapper.sendWinNotice(issueno1);
        if(maps.size()>0 && maps!=null){
            for (Map<String,Object> items : maps){
                String mobile = items.get("mobile").toString();
                String issueno = items.get("issueno").toString();
                String  name = items.get("caipiao_name").toString();
                if((mobile!=null || !"".equals(mobile)) && (issueno!=null || !"".equals(issueno)) && (name!=null || !"".equals(name))){
                    SendMessage.SendTemplateWinSMS(mobile,issueno,name);
                }
            }
        }

    }
}
