package com.nbplus.vnpy.domain;

/**
 * @Description  登录连接CTP方向实体
 * @Author gongtan
 * @Date 2019/11/21 15:24
 * @Version 1.0
 **/
public class CTPLogin {

    private String userID; // 账号
    private String password; // 密码
    private String brokerID; // 经纪商代码
    private String address; // 服务器地址
    private String authCode;
    private String userProductInfo;



    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBrokerID() {
        return brokerID;
    }

    public void setBrokerID(String brokerID) {
        this.brokerID = brokerID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getUserProductInfo() {
        return userProductInfo;
    }

    public void setUserProductInfo(String userProductInfo) {
        this.userProductInfo = userProductInfo;
    }
}
