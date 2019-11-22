package com.nbplus.vnpy.CTPservice;

import ctp.thosttraderapi.CThostFtdcReqUserLoginField;
import ctp.thosttraderapi.CThostFtdcTraderApi;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description
 * @Author gongtan
 * @Date 2019/11/21 16:18
 * @Version 1.0
 **/
@Service
public class CtpTdSpi {

    @Resource
    private CThostFtdcTraderApi traderApi;

    // ctp  状态
    private boolean connectionStatus; // 连接状态
    private boolean loginStatus; // 登录状态
    private boolean authStatus; // 验证状态
    private boolean loginFailed; // 登录失败（账号密码错误）

    public void login(){
        CThostFtdcReqUserLoginField cThostFtdcReqUserLoginField = new CThostFtdcReqUserLoginField();
        cThostFtdcReqUserLoginField.setBrokerID("9999");
        cThostFtdcReqUserLoginField.setUserID("110208");
        cThostFtdcReqUserLoginField.setPassword("thorp");
        cThostFtdcReqUserLoginField.setUserProductInfo("simnow_client_test");
        int i = traderApi.ReqUserLogin(cThostFtdcReqUserLoginField,0);
        System.out.println(i);
    }
}
