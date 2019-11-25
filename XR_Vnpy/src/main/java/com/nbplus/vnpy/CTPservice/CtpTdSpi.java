package com.nbplus.vnpy.CTPservice;

import com.nbplus.vnpy.CTPservice.impl.TraderSpiImpl;
import ctp.thostmduserapi.CThostFtdcMdSpi;
import ctp.thosttraderapi.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description
 * @Author gongtan
 * @Date 2019/11/21 16:18
 * @Version 1.0
 **/
class CtpTdSpi extends CThostFtdcTraderSpi {


    private CThostFtdcTraderApi m_traderapi;


    final static String m_BrokerId = "9999";
    final static String m_UserId = "110208";
    final static String m_PassWord = "thorp";
    final static String m_InvestorId = "110208";
    final static String m_TradingDay = "20191123";
    final static String m_AccountId = "110208";
    final static String m_CurrencyId = "CNY";
    final static String m_AppId = "simnow_client_test";
    final static String m_AuthCode = "0000000000000000";

   /* // ctp  状态
    private boolean connectionStatus; // 连接状态
    private boolean loginStatus; // 登录状态
    private boolean authStatus; // 验证状态
    private boolean loginFailed; // 登录失败（账号密码错误）*/


    CtpTdSpi(CThostFtdcTraderApi traderapi) {
        m_traderapi = traderapi;
    }


    // 当客户端与交易托管系统建立起通信连接，客户端需要进行登录
    //连接前 所执行的 方法
    @Override
    public void OnFrontConnected() {
        System.out.println("On Front Connected");
        CThostFtdcReqAuthenticateField field = new CThostFtdcReqAuthenticateField();
        field.setBrokerID(m_BrokerId);
        field.setUserID(m_UserId);
        field.setAppID(m_AppId);
        field.setAuthCode(m_AuthCode);
        m_traderapi.ReqAuthenticate(field, 0);
        System.out.println("Send ReqAuthenticate ok");
    }

    // 当客户端与交易托管系统通信连接断开时，该方法被调用
    public void OnFrontDisconnected(int nReason) {
        System.out.printf("OnFrontDisconnected nReason[%d]\n", nReason);
    }

    // 当客户端发出登录请求之后，该方法会被调用，通知客户端登录是否成功
    @Override
    public void OnRspUserLogin(CThostFtdcRspUserLoginField pRspUserLogin, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
        if (pRspInfo != null && pRspInfo.getErrorID() != 0) {
            //登录失败的  打印错误提示
            System.out.printf("Login ErrorID[%d] ErrMsg[%s]\n", pRspInfo.getErrorID(), pRspInfo.getErrorMsg());
            return;
        }
        //登录成功
        System.out.println("Login success!!!");

        //请求查询资金账户
        CThostFtdcQryTradingAccountField qryTradingAccount = new CThostFtdcQryTradingAccountField();
        qryTradingAccount.setBrokerID(m_BrokerId);
        qryTradingAccount.setCurrencyID(m_CurrencyId);
        qryTradingAccount.setInvestorID(m_InvestorId);
        //m_traderapi.ReqQryTradingAccount(qryTradingAccount, 1);

        //请求查询投资者结算结果
        CThostFtdcQrySettlementInfoField qrysettlement = new CThostFtdcQrySettlementInfoField();
        qrysettlement.setBrokerID(m_BrokerId);
        qrysettlement.setInvestorID(m_InvestorId);
        qrysettlement.setTradingDay(m_TradingDay);
        qrysettlement.setAccountID(m_AccountId);
        qrysettlement.setCurrencyID(m_CurrencyId);
        m_traderapi.ReqQrySettlementInfo(qrysettlement, 2);
        System.out.println("Query success!!!");
    }

    @Override
    public void OnRspAuthenticate(CThostFtdcRspAuthenticateField pRspAuthenticateField, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
        System.out.println(pRspInfo.getErrorID());
        if (pRspInfo != null && pRspInfo.getErrorID() != 0) {
            System.out.printf("Login ErrorID[%d] ErrMsg[%s]\n", pRspInfo.getErrorID(), pRspInfo.getErrorMsg());

            return;
        }
        System.out.println("OnRspAuthenticate success!!!");
        CThostFtdcReqUserLoginField field = new CThostFtdcReqUserLoginField();
        field.setBrokerID(m_BrokerId);
        field.setUserID(m_UserId);
        field.setPassword(m_PassWord);
        m_traderapi.ReqUserLogin(field, 0);
        System.out.println("Send login ok");
    }




}
