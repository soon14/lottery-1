package com.oruit.app.dao;

import com.oruit.app.dao.model.ProductCatalog;

public interface ProductCatalogMapper {
    int deleteByPrimaryKey(Integer productCatalogId);

    int insert(ProductCatalog record);

    int insertSelective(ProductCatalog record);

    ProductCatalog selectByPrimaryKey(Integer productCatalogId);

    int updateByPrimaryKeySelective(ProductCatalog record);

    int updateByPrimaryKey(ProductCatalog record);
}