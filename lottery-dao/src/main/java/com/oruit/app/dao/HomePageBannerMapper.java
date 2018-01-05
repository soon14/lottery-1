package com.oruit.app.dao;

import com.oruit.app.dao.model.HomePageBanner;

import java.util.List;
import java.util.Map;

public interface HomePageBannerMapper {
    int insert(HomePageBanner record);

    int insertSelective(HomePageBanner record);

    /**
     * 查询所有的轮播图片
     * @return
     */
    List<Map<String,Object>> QueryBanner();
    /**
     * 查询所有的轮播图片审核
     * @return
     */
    List<Map<String,Object>> QueryBannerExamins();
}