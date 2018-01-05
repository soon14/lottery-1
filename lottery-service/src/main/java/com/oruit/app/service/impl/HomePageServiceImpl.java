package com.oruit.app.service.impl;/**
 * Created by wyt on 2017/8/24.
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oruit.app.dao.*;
import com.oruit.app.dao.model.ConfigInfo;
import com.oruit.app.dao.model.HomePageBanner;
import com.oruit.app.dao.model.NoticeBoard;
import com.oruit.app.service.HomePageService;
import com.oruit.app.ssq.Issueno;
import com.oruit.app.ssq.shuagnseqiu;
import com.oruit.app.util.BatTest;
import com.oruit.app.util.config.Constants;
import com.oruit.app.util.web.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wyt
 * @create 2017-08-24 16:47
 */
@Service
public class HomePageServiceImpl implements HomePageService {

    @Autowired
    private HomePageBannerMapper homePageBannerMapper;
    @Autowired
    private NoticeBoardMapper noticeBoardMapper;
    @Autowired
    private ConfigInfoMapper configInfoMapper;
    @Autowired
    private CaiPiaoMapper caiPiaoMapper;
    @Autowired
    private AppAuditVersonMapper appAuditVersonMapper;
    @Autowired
    private UserAccountStatementMapper userAccountStatementMapper;
    @Autowired
    private UserPlayRecordMapper userPlayRecordMapper;

    /**
     * 首页信息初始化
     * @param maps
     * @return
     */
    @Override
    public ResultBean HomeInfo(Map<String,Object> maps) throws Exception {

        String appversion = maps.get("appversion").toString();
        String systemtype = maps.get("systemtype").toString();
        Map<String,Object> map = new HashMap<>();
        List<Map<String, Object>> BulletinBoard = new ArrayList<>();
        Map<String, Object> BulletinBoards = new HashMap<>();
        List<Map<String, Object>> Carousel = new ArrayList<>();
        String iscarousel = "1";
        String isbulletinBoard = "1";
        ConfigInfo configValuekefu = configInfoMapper.selectByConnfigCode(Constants.HOME_CONFIG);
        String service = configValuekefu.getConfigValue();
        JSONObject jsonObject = JSONObject.parseObject(service);

        String BulletinBoardFalg = jsonObject.get("BulletinBoardFalg").toString();

        String iscustomerservice = jsonObject.get("CustomerServiceFlag").toString();
        String isLotteryCategoriesList = jsonObject.get("LotteryCategoriesListFlag").toString();
        String isQuick = jsonObject.get("QuickFlag").toString();
        String linkssq = "";
        String linkklsf = "";
        //审核状态
        String state = null;
        Map<String, Object> stringObjectMap = appAuditVersonMapper.queryCheckExamins(appversion);
        if(stringObjectMap == null || "".equals(stringObjectMap)){
            state = "2" ;
        }else {
            if("android".equals(systemtype)){
                state = stringObjectMap.get("androidstate").toString();
            }
            if("ios".equals(systemtype)){
                state = stringObjectMap.get("iosstate").toString();
            }
        }

        if("1".equals(state)){
            //审核状态咨询
            ConfigInfo Examinslink = configInfoMapper.selectByConnfigCode(Constants.EXAMINELINK_CONFIG);
            String examinss = Examinslink.getConfigValue();
            JSONObject examinsstates = JSONObject.parseObject(examinss);
             linkssq = examinsstates.get("ssq").toString();
             linkklsf = examinsstates.get("klsf").toString();
        }
        List<Map<String, Object>> LotteryCategoriesList = caiPiaoMapper.queryLotteryCatalog();
        String str[]={"7","1","2","3","4","5","6"};//字符串数组
        Calendar rightNow= Calendar.getInstance();
        int day=rightNow.get(rightNow.DAY_OF_WEEK);//获取时间
        String isLotteryToday = "1" ;
        for (Map<String,Object> item : LotteryCategoriesList){
            String displayorder = item.get("displayorder").toString();
            item.put("displayorder",displayorder);
            if("11".equals(item.get("lotterytypeid").toString())){
                if(str[day-1] == "2" || str[day-1] == "4" || str[day-1] == "7"){
                    item.put("isLotteryToday","1");
                }else {
                    item.put("isLotteryToday","0");
                   // item.put("lotterydayicon"," ");
                    //item.remove("lotterydayicon");
                }
                if("2".equals(state)){
                    item.put("link","");
                }
                if("1".equals(state)){
                    item.put("link",linkssq);
                }

            }else{
                item.put("isLotteryToday","0");
                if("2".equals(state)){
                    item.put("link","");
                }
                if("1".equals(state)){
                    item.put("link",linkklsf);
                }
            }
        }
        String red = BatTest.getString(33, 6);
        String blue = BatTest.getString(16, 1);
        ConfigInfo configValuekefu1 = configInfoMapper.selectByConnfigCode(Constants.Quick_CONFIG);
        String service1 = configValuekefu1.getConfigValue();
        JSONObject jsonObject1 = JSONObject.parseObject(service1);
        JSONArray quick = jsonObject1.getJSONArray("quick");
        String s = quick.get(0).toString();
        JSONObject jsonObject2 = JSONObject.parseObject(s);
        jsonObject2.put("rednumber",red);
        jsonObject2.put("bluenumber",blue);


        map.put("quick",jsonObject2);

        map.put("BulletinBoard",BulletinBoard);
        map.put("isLotteryToday",isLotteryToday);
        map.put("LotteryCategoriesList",LotteryCategoriesList);
        map.put("isLotteryCategoriesList",isLotteryCategoriesList);//是否显示彩种列表（0不显示 1 显示）

        if("1".equals(state)){
            map.put("examinsstatus","1");

        }
        if("2".equals(state)){
            map.put("examinsstatus","0");
            if(BulletinBoardFalg!=null && !"".equals(BulletinBoardFalg)){
                isbulletinBoard = BulletinBoardFalg ;
                BulletinBoards = noticeBoardMapper.queryNoticeBoardlimit();
                if(BulletinBoards == null || "".equals(BulletinBoards)){
                    /**
                     * 查询账户明细
                     */
                    BulletinBoard = getBulletinBoard(map);
                }else{
                    String createtime = BulletinBoards.get("createtime").toString();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    if(!"".equals(createtime) && createtime!=null){
                        Date parse = format.parse(createtime);
                        long l = new Date().getTime() - parse.getTime();
                        long i = 60 * 1000 * 10;
                        if(l>=i){
                            /**
                             * 大于10分钟查询账户明细
                             */
                            BulletinBoard = getBulletinBoard(map);
                        }else {
                            /**
                             * 查询轮播表
                             */
                            BulletinBoard = noticeBoardMapper.queryNoticeBoard();
                        }

                    }
                    map.put("BulletinBoard",BulletinBoard);
                }

            }
        }

        if("2".equals(state)){
            String configValuebanner = jsonObject.get("CarouselFlag").toString();
            if(configValuebanner!=null && !"".equals(configValuebanner)){
                iscarousel = configValuebanner;
                Carousel = homePageBannerMapper.QueryBanner();
                for(Map<String,Object> result : Carousel){
                    String displayorder = result.get("displayorder").toString();
                    result.put("displayorder",displayorder);
                }

            }
            map.put("isbulletinBoard",isbulletinBoard);//是否显示公告（0不显示 1 显示）
            map.put("isQuick",isQuick);//是否显示快速投注（0不显示 1 显示）
            map.put("iscustomerservice",iscustomerservice);//是否显示客服（0不显示 1 显示）
        }
        if("1".equals(state)){
            String configValuebanner = jsonObject.get("CarouselFlag").toString();
            if(configValuebanner!=null && !"".equals(configValuebanner)){
                iscarousel = configValuebanner;
                Carousel = homePageBannerMapper.QueryBannerExamins();
                for(Map<String,Object> result : Carousel){
                    String displayorder = result.get("displayorder").toString();
                    result.put("displayorder",displayorder);
                }
            }
            map.put("isbulletinBoard","0");//是否显示公告（0不显示 1 显示）
            map.put("isQuick","0");//是否显示快速投注（0不显示 1 显示）
            map.put("iscustomerservice","0");//是否显示客服（0不显示 1 显示）

        }
        map.put("Carousel",Carousel);
        map.put("iscarousel",iscarousel);//是否显示轮播突图片（0不显示 1 显示）

        /**
         * 2017-11-15
         * 新增消息的数量
         */
        map.put("messagecount","0");
        return new ResultBean("1000", "0|成功", map, "1");
    }

    @Override
    public ResultBean userWinInfo(Map<String, Object> maps) throws Exception {
        Map<String,Object> map = new HashMap<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyMMdd");
        String format1 = format.format(new Date());
        String issueno = shuagnseqiu.KLSFissuenotuikuan();
        issueno = String.valueOf(Integer.parseInt(issueno) - 1);
        issueno = format1 + issueno ;
        String userid = maps.get("userid").toString();
        map.put("userid",userid);
        if(userid == null || "".equals(userid)){
            map.put("iswinning","0");
            map.put("winurl","");
            map.put("issueno","");
            map.put("lotterytype","");
            map.put("userid","");
        }else {
            Map<String,Object> res = new HashMap<>(2);
            res.put("issueno",issueno);
            res.put("userid",userid);
            Integer integer = userPlayRecordMapper.queryIswin(res);
            ConfigInfo configwinphoto = configInfoMapper.selectByConnfigCode(Constants.WINURL_CONFIG);
            String winurl = configwinphoto.getConfigValue();
            if(integer>0){
                map.put("iswinning","1");
                map.put("winurl",winurl);
                map.put("issueno",issueno);
                map.put("lotterytype","klsf");
            }else{
                String s1 = Issueno.achieveNum();
                s1 = String.valueOf(Integer.parseInt(s1) - 1);
                res.put("issueno",s1);
                integer = userPlayRecordMapper.queryIswin(res);
                if(integer>0){
                    map.put("iswinning","1");
                    map.put("winurl",winurl);
                    map.put("issueno",s1);
                    map.put("lotterytype","ssq");
                }else{
                    map.put("iswinning","0");
                    map.put("winurl","");
                    map.put("issueno","");
                    map.put("lotterytype","");
                    map.put("userid",userid);
                }
            }
        }
        return new ResultBean("1000", "0|成功", map, "1");
    }

    /**
     * ios 审核专用
     * @param map
     * @return
     */
    @Override
    public ResultBean IosExamine(Map<String, Object> map) {
        Map<String ,Object> result = new HashMap<>();
        String appversion = map.get("appversion").toString();
        //审核状态
        String state = null;
        Map<String, Object> stringObjectMap = appAuditVersonMapper.queryCheckExamins(appversion);
        if(stringObjectMap == null || "".equals(stringObjectMap)){
            state = "1" ;
        }else {
            state = stringObjectMap.get("iosstate").toString();
            if("2".equals(state)){
                state = "0";
            }
        }
        result.put("state",state);

        return new ResultBean("1000", "0|成功", result, "1");
    }

    private List<Map<String, Object>> getBulletinBoard(Map<String, Object> maps) {
        List<Map<String, Object>> BulletinBoard;
        String date = new SimpleDateFormat("yyyyMM").format(new Date());
        String tableName = "user_account_statement_" + date;
        maps.put("tableName",tableName);
        BulletinBoard = userAccountStatementMapper.queryUserAccountStatementBulletinBoard(maps);
        for(Map<String,Object> item : BulletinBoard){
            String mobile = item.get("mobile").toString();
            mobile = mobile.substring(0, 3) + "****" + mobile.substring(7, 11);
            String mony = item.get("mony").toString();
            String result = "恭喜用户"+mobile+"中奖"+mony+"元";
            NoticeBoard noticeBoard = new NoticeBoard();
            noticeBoard.setCreateTime(new Date());
            noticeBoard.setDeleted(0);
            noticeBoard.setTitle(result);
            noticeBoardMapper.insertSelective(noticeBoard);
        }
        return BulletinBoard;
    }

    public static void main(String[] args) {
        ArrayList list = new ArrayList();
        for (char c = 'a'; c <= 'z'; c++) {
            list.add(c);
        }
        String str = "";
        for (int j = 0 ; j < 30 ; j++){
            for (int i = 0; i < 4 ; i++) {
                int num = (int) (Math.random() * 26);
                str = str + list.get(num);

            }
            System.out.println(str);
            str = "";
        }

    }

}
