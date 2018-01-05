package com.oruit.app.dao.model;

import java.util.Date;

public class ProductCatalog {
    private Integer productCatalogId;

    private String productCatalogName;

    private Integer parentId;

    private Integer displayOrder;

    private Date createTime;

    public Integer getProductCatalogId() {
        return productCatalogId;
    }

    public void setProductCatalogId(Integer productCatalogId) {
        this.productCatalogId = productCatalogId;
    }

    public String getProductCatalogName() {
        return productCatalogName;
    }

    public void setProductCatalogName(String productCatalogName) {
        this.productCatalogName = productCatalogName == null ? null : productCatalogName.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}