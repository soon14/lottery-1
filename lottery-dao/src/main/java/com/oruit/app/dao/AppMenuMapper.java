package com.oruit.app.dao;

import com.oruit.app.dao.model.AppMenu;

import java.util.List;
import java.util.Map;

public interface AppMenuMapper {
    int deleteByPrimaryKey(Integer menuId);

    int insert(AppMenu record);

    int insertSelective(AppMenu record);

    AppMenu selectByPrimaryKey(Integer menuId);

    int updateByPrimaryKeySelective(AppMenu record);

    int updateByPrimaryKey(AppMenu record);

    /**
     * 查询菜单列表
     * @return
     */
    List<Map<String,Object>> queryAppMenu();
    /**
     * 查询菜单列表
     * @return
     */
    List<Map<String,Object>> queryAppMenuEximins();
}