package com.oruit.app.dao;

import com.oruit.app.dao.model.ShuangseqiuPrize;
import com.oruit.app.util.web.ResultBean;

import java.util.List;
import java.util.Map;

public interface ShuangseqiuPrizeMapper {
    int deleteByPrimaryKey(Integer openId);

    int insert(ShuangseqiuPrize record);

    int insertSelective(ShuangseqiuPrize record);

    ShuangseqiuPrize selectByPrimaryKey(Integer openId);

    ShuangseqiuPrize selectwinnumber(String issueNo);

    int updateByPrimaryKeySelective(ShuangseqiuPrize record);

    int updateByPrimaryKey(ShuangseqiuPrize record);

    /**
     * 双色球最近一期的开奖信息
     * @return
     */
    Map<String, Object> querywinning();

    /**
     * 双色球开奖历史
     * @param map
     * @return
     */
    List<Map<String,Object>> SSQHistoryList(Map<String,Object> map);
    /**
     * 双色球开奖详情
     * @param map
     * @return
     */
    Map<String,Object> SSQDetails(Map<String,Object> map);

    /**
     * 查询期号是否存在
     * @param issueno
     * @return
     */
    String isquerywin(String issueno);

    /**
     * 双色球顶部开奖信息H5
     * @return
     */
    Map<String,Object> TopWinInfoSSQ();
    /**
     * 查询开奖号码
     * @return
     */
    Map<String,Object> queryOpenWinNumberInfo(String issueno);

}