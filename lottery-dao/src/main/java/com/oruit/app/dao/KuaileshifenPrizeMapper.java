package com.oruit.app.dao;

import com.oruit.app.dao.model.KuaileshifenPrize;
import com.oruit.app.dao.model.ShuangseqiuOrder;
import com.oruit.app.util.web.ResultBean;

import java.util.List;
import java.util.Map;

public interface KuaileshifenPrizeMapper {
    int deleteByPrimaryKey(Integer openId);

    int insert(KuaileshifenPrize record);

    int insertSelective(KuaileshifenPrize record);

    KuaileshifenPrize selectByPrimaryKey(Integer openId);

    KuaileshifenPrize selectwinnumberklsf(String issueNo);

    int updateByPrimaryKeySelective(KuaileshifenPrize record);

    int updateByPrimaryKey(KuaileshifenPrize record);

    /**
     * 快乐十分最新的开奖信息
     * @return
     */
    Map<String,Object> queryWinklsf();

    /**
     * 快乐十分开奖历史
     * @param map
     * @return
     */
    List<Map<String,Object>> KLSFHistoryList(Map<String,Object> map);
    /**
     * 快乐十分开奖详情
     * @param map
     * @return
     */
    Map<String,Object> KLSFDetails(Map<String,Object> map);
    /**
     * 快乐十分最新开奖信息
     * @return
     */
    Map<String,Object> KLSFNewInfo();

    /**
     * 快乐十分的遗漏期数
     * @return
     */
    List<Map<String,Object>> YiLouNum();

    /**
     * 查询期号是否存在
     * @param issueno
     * @return
     */
    String isquerywinklsf(String issueno);
    /**
     * 快乐十分顶部开奖信息H5
     * @return
     */
    Map<String,Object> TopWinInfoKLSF();
    /**
     * 查询开奖号码
     * @return
     */
    Map<String,Object> queryOpenWinNumberInfoKLSF(String issueno);
}