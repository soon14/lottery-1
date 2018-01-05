package com.oruit.app.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oruit.app.dao.*;
import com.oruit.app.dao.model.*;
import com.oruit.app.image.util.ImageHandleHelper;
import com.oruit.app.qrcode.QRCodeUtil;
import com.oruit.app.service.MakeMoneyService;
import com.oruit.app.util.config.Constants;
import com.oruit.app.util.web.ResultBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wyt on 2017-11-23.
 */
@Service
public class MakeMoneyServiceImpl implements MakeMoneyService {
    private static final Logger log = Logger.getLogger(MakeMoneyServiceImpl.class);
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserStatisticsMapper userStatisticsMapper;
    @Autowired
    private ConfigInfoMapper configInfoMapper;
    @Autowired
    private UserShareMapper userShareMapper;
    @Autowired
    private UserStatisticsLogMapper userStatisticsLogMapper;
    @Autowired
    private ProductInfoMapper productInfoMapper;
    @Autowired
    private UserPointRecordMapper userPointRecordMapper;
    @Autowired
    private UserAccountStatementMapper userAccountStatementMapper;


    //二维码上传地址
    @Value("#{configProperties['uploadPath']}")
    public String uploadPath;
    /**
     * 我的徒弟
     * @param params
     * @return
     */
    @Override
    public ResultBean MyApprentice(Map<String, Object> params) {
        return null;
    }

    /**
     * 分享首页
     * @param params
     * @return
     */
    @Override
    public ResultBean ShareHomeInfo(Map<String, Object> params) {
        Map<String,Object> map = new HashMap<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String userid = params.get("userid").toString();
        if(userid == null || "".equals(userid)){
            return new ResultBean("2000", "0|用户id为空！");
        }
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userid);
        if(userInfo == null || "".equals(userInfo)){
            return new ResultBean("2000", "0|用户不存在！");
        }
        String wechatPhoto = userInfo.getWechatPhoto();
        String wechatNickName = userInfo.getWechatNickName();
        String joinDate = format.format(userInfo.getJoinDate());
        String mobile = userInfo.getMobile();
        String qrCode = userInfo.getQrCode();
        if(wechatNickName==null || "".equals(wechatNickName)){
            if(mobile==null || "".equals(mobile)){
                map.put(mobile,"");
            }else{
                map.put("mobile", mobile.substring(0, 3) + "****" + mobile.substring(7, 11));
            }
        }
        map.put("photo",wechatPhoto);
        map.put("nickname",wechatNickName);
        map.put("joindate",joinDate);
        UserStatistics userStatistics = userStatisticsMapper.selectByPrimaryKey(userid);
        Integer tudiNum = userStatistics.getTudiNum();
        Integer tusunNum = userStatistics.getTusunNum();
        Integer totalPoints = userStatistics.getTotalPoints();
        map.put("tudinum",tudiNum);
        map.put("tusunnum",tusunNum);
        map.put("jifen",totalPoints);
        if(qrCode == null || "".equals(qrCode)){
            qrCode = getString(userid);

        }
        String shareQrCode = userInfo.getShareQrCode();
        ConfigInfo configInfos = configInfoMapper.selectByConnfigCode(Constants.BACKFROUND_CONFIG);
        String configValues = configInfos.getConfigValue();
        //获取带有背景的二维码
        shareQrCode = getShareOrCode(userid, userInfo, configValues, shareQrCode);
        map.put("shareQrCode",shareQrCode);
        return new ResultBean("1000", "0|成功！", map, "1");
    }

    private String getString(String userid) {
        UserShare userShare = userShareMapper.queryUserid(userid);
        Integer id = 0 ;
        if(userShare!=null&&!"".equals(userShare)){
            id = userShare.getId();
        }else{
            UserShare userShare1 = new UserShare();
            userShare1.setCreateTime(new Date());
            userShare1.setUpdateTime(new Date());
            userShare1.setUserId(userid);
            userShareMapper.insertSelective(userShare1);
            id = userShare1.getId();
        }
        String qrCode;
        qrCode = saveQrcode(id, userid, uploadPath);
        return qrCode;
    }

    private String saveQrcode(Integer id, String userid, String uploadPath) {
        String tempPath;
        ConfigInfo configinfo = configInfoMapper.selectByConnfigCode(Constants.QRCODE_URL);
        String qrcodeUrl = configinfo.getConfigValue();
        ConfigInfo qrcodelogo = configInfoMapper.selectByConnfigCode(Constants.HEADLOGO_URL);
        String headlogo = qrcodelogo.getConfigValue();
        qrcodeUrl += id;
        tempPath = CreateQrCode(qrcodeUrl, uploadPath, headlogo);
        UserInfo userinfo = new UserInfo();
        userinfo.setQrCode(tempPath);
        userinfo.setUserId(userid);
        userInfoMapper.updateByPrimaryKeySelective(userinfo);
        return tempPath;
    }
    /**
     * 生成二维码
     *
     * @param savePath
     * @param headLogo
     * @return
     */
    public String CreateQrCode(String qrcodeUrl, String savePath, String headLogo) {
        try {
            String fileName = String.valueOf(System.currentTimeMillis()) + ".png";
            Calendar a = Calendar.getInstance();
            String dPath = "/qrcode/";
            dPath += String.valueOf(a.get(Calendar.YEAR));
            dPath += "/";
            dPath += String.valueOf(a.get(Calendar.MONTH)+1);
            dPath += "/";
            dPath += String.valueOf(a.get(Calendar.DAY_OF_MONTH));
            dPath += "/";
            savePath += dPath;
            String oospath = dPath.substring(1,dPath.length()-1);
            String encode = QRCodeUtil.encode(oospath,qrcodeUrl, headLogo, savePath, fileName, true);
            return encode;
        } catch (Exception ex) {
            log.info("WeixinUserService.CreateQrCode异常：" + ex.getMessage());
            return "";
        }
    }

    /**
     * 分享到朋友圈
     * @param params
     * @return
     */
    @Override
    public ResultBean ShareCircleOfFriends(Map<String, Object> params) {
        Map<String,Object> result = new HashMap<>();
        String userid = params.get("userid").toString();
        if(userid == null || "".equals(userid)){
            return new ResultBean("2000", "0|用户id为空！");
        }
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userid);
        if(userInfo == null || "".equals(userInfo)){
            return new ResultBean("2000", "0|用户不存在！");
        }
        //1.获取推广语
        ConfigInfo configInfo = configInfoMapper.selectByConnfigCode(Constants.TUIGUANG_CONFIG);
        String configValue = configInfo.getConfigValue();
        JSONObject jsonObject = JSONObject.parseObject(configValue);
        JSONArray tuiguangyu = jsonObject.getJSONArray("parameter");
        //2.获取图片
        ConfigInfo fenxiangshoutujifen = configInfoMapper.selectByConnfigCode(Constants.FXSTJF_CONFIG);
        String fenxiangshoutujf = fenxiangshoutujifen.getConfigValue();
        ConfigInfo fenxiangsoutuzhongjiang = configInfoMapper.selectByConnfigCode(Constants.FXSTZJ_CONFIG);
        String fenxiangsoutuzj = fenxiangsoutuzhongjiang.getConfigValue();
        ConfigInfo bk = configInfoMapper.selectByConnfigCode(Constants.BACKFROUND_CONFIG);
        String backgroundimage = bk.getConfigValue();
        String shareQrCode = userInfo.getShareQrCode();
        //获取带有背景的二维码
        shareQrCode = getShareOrCode(userid, userInfo, backgroundimage, shareQrCode);
        //链接
        ConfigInfo shareurls = configInfoMapper.selectByConnfigCode(Constants.QRCODE_URL);
        String shareurl = shareurls.getConfigValue();
        UserShare userShare = userShareMapper.queryUserid(userid);
        if(userShare!=null&&!"".equals(userShare)){
            Integer id = userShare.getId();
            shareurl += id;
        }else{
            UserShare userShare1 = new UserShare();
            userShare1.setCreateTime(new Date());
            userShare1.setUserId(userid);
            int i = userShareMapper.insertSelective(userShare1);
            shareurl += userShare1.getId();
        }
       List<Map<String,Object>> resu = new ArrayList<>();
        Map<String,Object> maps = new HashMap<>();
        maps.put("image",fenxiangshoutujf);
        Map<String,Object> maps1 = new HashMap<>();
        maps1.put("image",fenxiangsoutuzj);
        Map<String,Object> maps2 = new HashMap<>();
        maps2.put("image",shareQrCode);
        resu.add(maps);
        resu.add(maps1);
        resu.add(maps2);
        result.put("image",resu);
        result.put("tuiguangyu",tuiguangyu);
        result.put("shareurl",shareurl);

        return new ResultBean("1000","0|成功",result,"1");
    }

    private String getShareOrCode(String userid, UserInfo userInfo, String backgroundimage, String shareQrCode) {
        if(shareQrCode==null || "".equals(shareQrCode)){
            //3.合并图片
            String qrCode = userInfo.getQrCode();
            if(qrCode == null || "".equals(qrCode)){
                qrCode = getString(userid);
            }
            String fileName = "bgqrc" + String.valueOf(System.currentTimeMillis()) + ".png";
            Calendar a = Calendar.getInstance();
            String dPath = "/multiimgshare/";
            dPath += String.valueOf(a.get(Calendar.YEAR));
            dPath += "/";
            dPath += String.valueOf(a.get(Calendar.MONTH)+1);
            dPath += "/";
            dPath += String.valueOf(a.get(Calendar.DAY_OF_MONTH));
            dPath += "/";
            uploadPath += dPath;
            QRCodeUtil.mkdirs(uploadPath);
            File f = new File(uploadPath + fileName);
            try {
                f.createNewFile();
            } catch (IOException ex) {
                log.error("错误信息------>>>>>>------ex:"+ex);
            }
            String oospath = dPath.substring(1,dPath.length()-1);
            shareQrCode = ImageHandleHelper.overlapImage(oospath,backgroundimage, qrCode, uploadPath + fileName);
            UserInfo userInfo1 = new UserInfo();
            userInfo1.setShareQrCode(shareQrCode);
            userInfo1.setUserId(userid);
            userInfo1.setUpdateTime(new Date());
            userInfoMapper.updateByPrimaryKeySelective(userInfo1);
        }
        return shareQrCode;
    }


    /**
     * 积分首页
     * @param params
     * @return
     */
    @Override
    public ResultBean JIFENHome(Map<String, Object> params) throws ParseException {
        Map<String,Object> map = new HashMap<>();
        String userid = params.get("userid").toString();
        if(userid == null || "".equals(userid)){
            return new ResultBean("2000", "0|用户id为空！");
        }
        UserStatistics userStatistics = userStatisticsMapper.selectByPrimaryKey(userid);
        Integer totalPoints = userStatistics.getTotalPoints();
        map.put("jifen",totalPoints);
        String getpoint = getpoint();
        map.put("zongjifen",getpoint);
        ConfigInfo configValuekefu = configInfoMapper.selectByConnfigCode(Constants.HEROESLIST_CONFIG);
        String service = configValuekefu.getConfigValue();
        JSONObject jsonObject = JSONObject.parseObject(service);
        //富豪榜
        List<Map<String, Object>> fuhaobang = userStatisticsMapper.Fuhaobang(params);
        for(int i = 0 ; i<fuhaobang.size();i++){
            Map<String, Object> map1 = fuhaobang.get(i);
            String mobile = map1.get("mobile").toString();
            map1.put("mobile", mobile.substring(0, 3) + "****" + mobile.substring(7, 11));
            if(i<3){
                if(i == 0 ){
                    map1.put("picpath",jsonObject.get("jinpai").toString());
                }else if(i == 1 ){
                    map1.put("picpath",jsonObject.get("yinpai").toString());
                }else if(i == 2 ){
                    map1.put("picpath",jsonObject.get("tongpai").toString());
                }else{
                    map1.put("picpath","");
                }
            }else{
                map1.put("picpath","");
            }
            map1.put("ranking",i+1);
        }
        int i1 = new Random().nextInt(10);
        String userid1 = fuhaobang.get(i1).get("userid").toString();
        UserStatistics userStatistics1 = userStatisticsMapper.selectByPrimaryKey(userid1);
        userStatistics1.setUserId(userid1);
        int j = (int) (Math.random() * 50) + 10;
        userStatistics1.setTotalPoints(userStatistics1.getTotalPoints()+j);
        userStatisticsMapper.updateByPrimaryKeySelective(userStatistics1);
        for(Map<String,Object> item : fuhaobang){
            item.remove("userid");
        }
        map.put("fuhaobang",fuhaobang);
        return new ResultBean("1000","0|成功",map,"1");
    }

    /**
     * 积分兑换首页
     * @param params
     * @return
     */
    @Override
    public ResultBean JiFenChangeHome(Map<String, Object> params) {
        Map<String,Object> map = new HashMap<>();
        String userid = params.get("userid").toString();
        if(userid == null || "".equals(userid)){
            return new ResultBean("2000", "0|用户id为空！");
        }
        UserStatistics userStatistics = userStatisticsMapper.selectByPrimaryKey(userid);
        if(userStatistics==null || "".equals(userStatistics)){
            return new ResultBean("2000", "0|用户不存在！");
        }
        Integer totalPoints = userStatistics.getTotalPoints();
        Integer balancePoints = userStatistics.getBalancePoints();
        map.put("productCatalogId",4);
        Map<String,Object> maps = new HashMap<>();
        List<Map<String, Object>> list = productInfoMapper.GetRechargeProductList(map);
        for(int i = 0 ; i<list.size();i++){
            Map<String, Object> map1 = list.get(i);
            String productprice = map1.get("productprice").toString();
            map1.put("productprice",productprice.substring(0,productprice.lastIndexOf("."))+"元");
            map1.put("displayorder",i+1);
        }
        map.remove("productCatalogId");
        map.put("historiclaintegral",totalPoints);
        map.put("canbeusedintegral",balancePoints);
        map.put("product",list);
        ConfigInfo configValuekefu = configInfoMapper.selectByConnfigCode(Constants.DUIHUANSHUOMING_CONFIG);
        String pointrule = configValuekefu.getConfigValue();
        /*String[] split = pointrule.split("#");
        String str = "";
        for(int i =  0; i<split.length ; i++){
            str = str + split[i] + "\n\n";
        }
        str = str.substring(0, str.length() - 2);*/
        pointrule = pointrule.replaceAll("\\r", "").replaceAll("\\n","").replaceAll("\\t","").replaceAll("\"","");

        map.put("instructions",pointrule);
        return new ResultBean("1000","0|成功",map,"1");
    }

    /**
     * 积分兑换
     * @param params
     * @return
     */
    @Override
    @Transactional
    public ResultBean JiFenChange(Map<String, Object> params) {
        Map<String,Object> map = new HashMap<>();
        String userid = params.get("userid").toString();
        if(userid == null || "".equals(userid)){
            return new ResultBean("2000", "0|用户id为空！");
        }
        String productid = params.get("productid").toString();
        if(productid == null || "".equals(productid)){
            return new ResultBean("2000", "0|商品id为空！");
        }
        int shopid = Integer.parseInt(productid);

        String jifen = params.get("jifen").toString();
        if(jifen == null || "".equals(jifen)){
            return new ResultBean("2000", "0|积分为空！");
        }
        int jf = Integer.parseInt(jifen);
        if(jf<1000||jf>100000||jf%100!=0){
            return new ResultBean("2000", "0|输入的积分数量不对！");
        }
        UserStatistics userStatistics = userStatisticsMapper.selectByPrimaryKey(userid);
        if(userStatistics==null || "".equals(userStatistics)){
            return new ResultBean("2000", "0|用户不存在！");
        }
        Integer balancePoints = userStatistics.getBalancePoints();
        if(jf>balancePoints){
            return new ResultBean("2000", "0|积分数量不足！");
        }
        ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(shopid);
        int i = productInfo.getPrice().intValue();
        int duihuanmoney = jf / 1000;
        if(i-duihuanmoney!=0){
            return new ResultBean("2000", "0|积分数量不对！");
        }
        BigDecimal balabceAmount = userStatistics.getBalanceAmount();
        balancePoints = balancePoints - jf;
        map.put("balancePoints",balancePoints);
        UserStatistics userStatistics1 = new UserStatistics();
        userStatistics1.setUserId(userid);
        userStatistics1.setBalancePoints(balancePoints);
        BigDecimal add = balabceAmount.add(new BigDecimal(duihuanmoney));
        userStatistics1.setBalanceAmount(add);
        userStatisticsMapper.updateByPrimaryKeySelective(userStatistics1);
        userStatistics = userStatisticsMapper.selectByPrimaryKey(userid);
        UserStatisticsLog userStatisticsLog = new UserStatisticsLog();
        userStatisticsLog.setTotalAmount(userStatistics.getTotalAmount());
        userStatisticsLog.setBettingNum(userStatistics.getBettingNum());
        userStatisticsLog.setWinningNum(userStatistics.getWinningNum());
        userStatisticsLog.setUserId(userid);
        userStatisticsLog.setBalanceAmount(userStatistics.getBalanceAmount());
        userStatisticsLog.setWinningBalabceAmount(userStatistics.getWinningBalabceAmount());
        userStatisticsLog.setWinningTotalAmount(userStatistics.getTotalAmount());
        userStatisticsLog.setCreateTime(new Date());
        userStatisticsLog.setTudiNum(userStatistics.getTudiNum());
        userStatisticsLog.setTusunNum(userStatistics.getTusunNum());
        userStatisticsLog.setTotalPoints(userStatistics.getTotalPoints());
        userStatisticsLog.setBalancePoints(userStatistics.getBalancePoints());
        userStatisticsLogMapper.insertSelective(userStatisticsLog);
        UserPointRecord userPointRecord = new UserPointRecord();
        userPointRecord.setCreateTime(new Date());
        userPointRecord.setFromUserId(userid);
        userPointRecord.setIntro("积分兑换");
        userPointRecord.setPoints(jf);
        userPointRecord.setUserId(userid);
        userPointRecord.setPointType("jifenduihuanyue");
        userPointRecord.setRemark("");
        userPointRecordMapper.insertSelective(userPointRecord);
        userAccount("jifenduihuan", userid, new BigDecimal(String.valueOf(i)), 0);
        return new ResultBean("1000","0|兑换成功",map,"1");
    }
    /**
     * 账户明细
     *
     * @param type
     * @param userid
     * @param account
     */
    private void userAccount(String type, String userid, BigDecimal account, Integer playId) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format1 = format.format(new Date());
        UserAccountStatementAccount userAccountStatement = new UserAccountStatementAccount();
        userAccountStatement.setAmount(account);
        userAccountStatement.setCreateTime(format1);
        userAccountStatement.setState(1);
        userAccountStatement.setType(type);
        userAccountStatement.setUpdateTime(format1);
        userAccountStatement.setUserId(userid);
        if (playId != 0) {
            userAccountStatement.setRemark(String.valueOf(playId));
        }
        System.out.println("//////////////////////////////////:" + playId);
        String date = new SimpleDateFormat("yyyyMM").format(new Date());
        String tableName = "user_account_statement_" + date;
        Integer integer = userAccountStatementMapper.existTable(tableName);
        if (integer > 0) {
            userAccountStatement.setTableName(tableName);
            userAccountStatementMapper.insertSelectiveAccount(userAccountStatement);
        }
    }
    /**
     * 积分兑换列表
     * @param params
     * @return
     */
    @Override
    public ResultBean DuiHuanChangeList(Map<String, Object> params) {
        String userid = params.get("userid").toString();
        if(userid == null || "".equals(userid)){
            return new ResultBean("2000", "0|用户id为空！");
        }
        String recordid = params.get("recordid").toString();
        if(recordid == null || "".equals(recordid)){
            return new ResultBean("2000", "0|记录id为空！");
        }
        String pageSize = params.get("pageSize").toString();
        if(pageSize == null || "".equals(pageSize)){
            return new ResultBean("2000", "0|分页id为空！");
        }
        params.put("point_type","jifenduihuanyue");
        params.put("pageSize",Integer.parseInt(pageSize));
        List<Map<String, Object>> maps = userPointRecordMapper.DuiHuanChangeList(params);
        for (Map<String, Object> item : maps){
            String createtime = item.get("createtime").toString();
            String nianyueri = createtime.substring(0, 10);
            String shifenmiao = createtime.substring(11, createtime.length()-2);
            item.put("nianyueri",nianyueri);
            item.put("shifenmiao",shifenmiao);
            item.remove("createtime");
        }
        return new ResultBean("1000","0|成功",maps,String.valueOf(maps.size()));
    }

    /**
     * 赚钱首页app
     * @param params
     * @return
     */
    @Override
    public ResultBean MakeMoneyHomeInfo(Map<String, Object> params) {
        Map<String,Object> map = new HashMap<>();
        String userid = params.get("userid").toString();
        if(userid == null || "".equals(userid)){
            return new ResultBean("2000", "0|用户id为空！");
        }
        UserStatistics userStatistics = userStatisticsMapper.selectByPrimaryKey(userid);
        if(userStatistics==null || "".equals(userStatistics)){
            return new ResultBean("2000", "0|用户不存在！");
        }
        Integer balancePoints = userStatistics.getBalancePoints();
        ConfigInfo configValuekefu = configInfoMapper.selectByConnfigCode(Constants.JIANGLI_CONFIG);
        String pointrule = configValuekefu.getConfigValue();
        pointrule = pointrule.replaceAll("\\r", "").replaceAll("\\n","").replaceAll("\\t","").replaceAll("\"","");

        ConfigInfo win = configInfoMapper.selectByConnfigCode(Constants.MAKEMONEYHTML_CONFIG);
        String wintitle = win.getConfigValue();
        wintitle = "【"+wintitle+"】";
        String winurl = win.getValue1();
        map.put("pointrule",pointrule);
        map.put("wintitle",wintitle);
        map.put("winurl",winurl);
        map.put("balancepoints",balancePoints);
        return new ResultBean("1000","0|成功",map,"1");
    }

    /**
     * 计算出平台送出去的总积分
     * @return
     * @throws ParseException
     */
    private String getpoint() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = format.parse("2017-11-22 15:00:00");
        long l = System.currentTimeMillis();
        long time = parse.getTime();
        long l1 = l - time;
        long l2 = l1 / 1000 ;
        double v =  l2 * (10000/864)+10000;
        long round = Math.round(v);
        return String.valueOf(round);
    }

    public static void main(String[] args) {
        JSONObject jsonObject = JSONObject.parseObject("{\n" +
                "\"parameter\": [\n" +
                "{\n" +
                "\"id\":\"1\",\n" +
                "\"intro\": \"给大家推荐一款既能购买彩票又能赚钱的APP,我身边的朋友都在玩，它不仅可以实时购买彩票，更大的亮点是可以免费购彩，中奖金额随时可提现，想知道奥秘吗，扫码下载跟我一起来赚钱！\"\n" +
                "},\n" +
                "{\n" +
                "\"id\":\"2\",\n" +
                "\"intro\": \"给大奖讲个真事，我一朋友是资深彩民，他一直在用呱呱彩免费购彩，隔三差五中奖，天天提现收米。想知道为什么吗？因为呱呱彩拥有庞大的积分体系，分享给好友就能获取积分，积分可兑换购彩，购彩就有几率中奖，是不是很刺激心动不如行动！！！\"\n" +
                "},{\n" +
                "\"id\":\"3\",\n" +
                "\"intro\": \"震惊！！最近貌似被 呱呱彩 三个字洗脑了，身边的朋友跟疯了似的都在玩，听他们说呱呱彩上手快，彩种全，中奖率高！重点是他们都在免费购彩，还中奖了！厉害了\"\n" +
                "}\n" +
                "]\n" +
                "}");
        JSONArray tuiguangyu = jsonObject.getJSONArray("parameter");
        System.out.println(tuiguangyu);
    }


}