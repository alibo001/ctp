package com.nbplus.vnpy.CTPservice;

import com.nbplus.vnpy.domain.CTPLogin;
import com.nbplus.vnpy.event.Event;
import com.nbplus.vnpy.event.EventType;
import com.nbplus.vnpy.trader.VtLogData;
import ctp.thosttraderapi.*;

/**
 * @Description
 * @Author gongtan
 * @Date 2019/11/21 16:18
 * @Version 1.0
 **/
public class CtpTdSpi extends CThostFtdcTraderSpi {


    private CThostFtdcTraderApi m_traderapi;

    //登录实体类
    private CTPLogin ctpLogin;

   /* // ctp  状态
    private boolean connectionStatus; // 连接状态
    private boolean loginStatus; // 登录状态
    private boolean authStatus; // 验证状态
    private boolean loginFailed; // 登录失败（账号密码错误）*/


    /**
     * @Description  其他java 文件 直接创建对象  用于接受此类中所要执行的参数
     * @author gt_vv
     * @date 2019/11/25
     * @param traderapi
     * @param paramLogin
     * @return
     */
    public CtpTdSpi(CThostFtdcTraderApi traderapi,CTPLogin paramLogin) {
        m_traderapi = traderapi;
        ctpLogin = paramLogin;
    }


    /**
     * @Description  当客户端与交易托管系统建立起通信连接，客户端需要进行登录    连接前 所执行的 方法
     * @author gt_vv
     * @date 2019/11/25
     * @param
     * @return void
     */
    @Override
    public void OnFrontConnected() {
        System.out.println("On Front Connected");
        CThostFtdcReqAuthenticateField field = new CThostFtdcReqAuthenticateField();
        field.setBrokerID(ctpLogin.getM_BrokerId());
        field.setUserID(ctpLogin.getM_UserId());
        field.setAppID(ctpLogin.getM_AppId());
        field.setAuthCode(ctpLogin.getM_AuthCode());
        m_traderapi.ReqAuthenticate(field, 0);
        System.out.println("Send ReqAuthenticate ok");
    }

    /**
     * @Description  当客户端与交易托管系统通信连接断开时，该方法被调用
     * @author gt_vv
     * @date 2019/11/25
     * @param nReason
     * @return void
     */
    public void OnFrontDisconnected(int nReason) {
        System.out.printf("OnFrontDisconnected nReason[%d]\n", nReason);
    }

    /**
     * @Description   当客户端发出登录请求之后，该方法会被调用，通知客户端登录是否成功
     * @author gt_vv
     * @date 2019/11/25
     * @param pRspUserLogin
     * @param pRspInfo
     * @param nRequestID
     * @param bIsLast
     * @return void
     */
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
        qryTradingAccount.setBrokerID(ctpLogin.getM_BrokerId());
        qryTradingAccount.setCurrencyID(ctpLogin.getM_CurrencyId());
        qryTradingAccount.setInvestorID(ctpLogin.getM_InvestorId());
        //m_traderapi.ReqQryTradingAccount(qryTradingAccount, 1);

        //请求查询投资者结算结果
        CThostFtdcQrySettlementInfoField qrysettlement = new CThostFtdcQrySettlementInfoField();
        qrysettlement.setBrokerID(ctpLogin.getM_BrokerId());
        qrysettlement.setInvestorID(ctpLogin.getM_InvestorId());
        qrysettlement.setTradingDay(ctpLogin.getM_TradingDay());
        qrysettlement.setAccountID(ctpLogin.getM_AccountId());
        qrysettlement.setCurrencyID(ctpLogin.getM_CurrencyId());
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
        field.setBrokerID(ctpLogin.getM_BrokerId());
        field.setUserID(ctpLogin.getM_UserId());
        field.setPassword(ctpLogin.getM_PassWord());
        m_traderapi.ReqUserLogin(field, 0);
        System.out.println("Send login ok");
    }

    /*// 日志推送
    public void onLog(VtLogData log) {
        // 通用事件
        Event event1 = new Event(EventType.EVENT_LOG);
        event1.getEventDict().put("data", log);
        this.eventEngine.put(event1);
    }*/
}
