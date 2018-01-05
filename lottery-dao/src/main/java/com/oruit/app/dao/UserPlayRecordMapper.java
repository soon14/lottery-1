package com.oruit.app.dao;

import com.oruit.app.dao.model.UserPlayRecord;

import java.util.List;
import java.util.Map;

public interface UserPlayRecordMapper {
    int deleteByPrimaryKey(Integer playId);

    int insert(UserPlayRecord record);

    int insertSelective(UserPlayRecord record);

    UserPlayRecord selectByPrimaryKey(Integer playId);

    int updateByPrimaryKeySelective(UserPlayRecord record);


    int updateByPrimaryKey(UserPlayRecord record);
    /**
     * 查询投注记录
     * @param map
     * @return
     */
    List<Map<String,Object>> QueryUserPlayRecord(Map<String, Object> map);
    /**
     * 查询全部投注记录
     * @param map
     * @return
     */
    List<Map<String,Object>> QueryUserPlayRecordALL(Map<String, Object> map);
    /**
     * 查询全部投注记录H5
     * @param map
     * @return
     */
    List<Map<String,Object>> QueryUserPlayRecordALLH5(Map<String, Object> map);
    /**
     * 查询投注记录详情
     * @param map
     * @return
     */
    Map<String,Object> BettingRecordDetails(Map<String, Object> map);

    /**
     * 查询投注的期数
     * @param map
     * @return
     */
    Integer queryissue(Map<String, Object> map);

    /**
     * 查询投注的期数每期的钱数
     * @param map
     * @return
     */
    String queryissuemoney(Map<String, Object> map);

    /**
     * 查询中奖金额列表
     * @param map
     * @return
     */
    List<Map<String,Object>> WinmoneyList(Map<String, Object> map);

    /**
     * 查询中奖金总金额
     * @param map
     * @return
     */
    List<Map<String,Object>> QueryWinmoney(Map<String, Object> map);


    /**
     * 查询用户的追号投注记录
     * @param map
     * @return
     */
    List<Map<String,Object>> QueryUserzhuihaotoushuRecord(Map<String, Object> map);

    /**
     * 中奖金额H5
     * @return
     */
    List<Map<String,Object>> WinmoneyListH5();
    /**
     * 保存中奖明细
     * @return
     */
    List<Map<String,Object>> savewinmoney();

    /**
     * 更新调取第三方的接口
     * @param record
     * @return
     */
    int updateByQueryLottery(UserPlayRecord record);
    /**
     * 查询应该退款的用户
     * @param map
     * @return
     */
    List<Map<String,Object>> QueryUsertuikuan(Map<String, Object> map);

    /**
     * 发送中奖信息
     * @return
     */
    List<Map<String,Object>> sendWinNotice(String issueno);
    /**
     * 查询应该退款的用户期数
     * @param map
     * @return
     */
    Integer QueryUsertuikuanqishu(Map<String, Object> map);

    /**
     * 查询所有用户的中奖信息
     * @param
     * @return
     */
    List<Map<String,Object>> queryUserWInningInfoAll(Map<String,Object> map);

    /**
     * 查询用户是否中奖
     * @param items
     * @return
     */
    Integer queryIswin(Map<String,Object> items);


}