package com.oruit.app.dao.model;

import java.util.Date;

public class UserInfo {
    private String userId;

    private String mobile;

    private String password;

    private Integer userState;

    private Integer parentId;

    private Integer userShipLevel;

    private String userShipPath;

    private String cmCode;

    private Date firstLoginTime;

    private Date updateTime;

    private Date joinDate;

    private Integer wechatAuthorized;

    private String wechatNickName;

    private String wechatPhoto;

    private String wechatGender;

    private String wechatProvince;

    private String wechatCity;

    private String wechatRealName;

    private String wechatAccount;

    private Integer alipayAuthorized;

    private String alipayAccount;

    private String alipayNickName;

    private String alipayPhoto;

    private String alipayRealName;

    private Integer channelId;

    private String realName;

    private String idCard;

    private Integer idAuthorized;

    private String qrCode;

    private String shareQrCode;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getUserState() {
        return userState;
    }

    public void setUserState(Integer userState) {
        this.userState = userState;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getUserShipLevel() {
        return userShipLevel;
    }

    public void setUserShipLevel(Integer userShipLevel) {
        this.userShipLevel = userShipLevel;
    }

    public String getUserShipPath() {
        return userShipPath;
    }

    public void setUserShipPath(String userShipPath) {
        this.userShipPath = userShipPath == null ? null : userShipPath.trim();
    }

    public String getCmCode() {
        return cmCode;
    }

    public void setCmCode(String cmCode) {
        this.cmCode = cmCode == null ? null : cmCode.trim();
    }

    public Date getFirstLoginTime() {
        return firstLoginTime;
    }

    public void setFirstLoginTime(Date firstLoginTime) {
        this.firstLoginTime = firstLoginTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public Integer getWechatAuthorized() {
        return wechatAuthorized;
    }

    public void setWechatAuthorized(Integer wechatAuthorized) {
        this.wechatAuthorized = wechatAuthorized;
    }

    public String getWechatNickName() {
        return wechatNickName;
    }

    public void setWechatNickName(String wechatNickName) {
        this.wechatNickName = wechatNickName == null ? null : wechatNickName.trim();
    }

    public String getWechatPhoto() {
        return wechatPhoto;
    }

    public void setWechatPhoto(String wechatPhoto) {
        this.wechatPhoto = wechatPhoto == null ? null : wechatPhoto.trim();
    }

    public String getWechatGender() {
        return wechatGender;
    }

    public void setWechatGender(String wechatGender) {
        this.wechatGender = wechatGender == null ? null : wechatGender.trim();
    }

    public String getWechatProvince() {
        return wechatProvince;
    }

    public void setWechatProvince(String wechatProvince) {
        this.wechatProvince = wechatProvince == null ? null : wechatProvince.trim();
    }

    public String getWechatCity() {
        return wechatCity;
    }

    public void setWechatCity(String wechatCity) {
        this.wechatCity = wechatCity == null ? null : wechatCity.trim();
    }

    public String getWechatRealName() {
        return wechatRealName;
    }

    public void setWechatRealName(String wechatRealName) {
        this.wechatRealName = wechatRealName == null ? null : wechatRealName.trim();
    }

    public String getWechatAccount() {
        return wechatAccount;
    }

    public void setWechatAccount(String wechatAccount) {
        this.wechatAccount = wechatAccount == null ? null : wechatAccount.trim();
    }

    public Integer getAlipayAuthorized() {
        return alipayAuthorized;
    }

    public void setAlipayAuthorized(Integer alipayAuthorized) {
        this.alipayAuthorized = alipayAuthorized;
    }

    public String getAlipayAccount() {
        return alipayAccount;
    }

    public void setAlipayAccount(String alipayAccount) {
        this.alipayAccount = alipayAccount == null ? null : alipayAccount.trim();
    }

    public String getAlipayNickName() {
        return alipayNickName;
    }

    public void setAlipayNickName(String alipayNickName) {
        this.alipayNickName = alipayNickName == null ? null : alipayNickName.trim();
    }

    public String getAlipayPhoto() {
        return alipayPhoto;
    }

    public void setAlipayPhoto(String alipayPhoto) {
        this.alipayPhoto = alipayPhoto == null ? null : alipayPhoto.trim();
    }

    public String getAlipayRealName() {
        return alipayRealName;
    }

    public void setAlipayRealName(String alipayRealName) {
        this.alipayRealName = alipayRealName == null ? null : alipayRealName.trim();
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public Integer getIdAuthorized() {
        return idAuthorized;
    }

    public void setIdAuthorized(Integer idAuthorized) {
        this.idAuthorized = idAuthorized;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode == null ? null : qrCode.trim();
    }

    public String getShareQrCode() {
        return shareQrCode;
    }

    public void setShareQrCode(String shareQrCode) {
        this.shareQrCode = shareQrCode == null ? null : shareQrCode.trim();
    }
}