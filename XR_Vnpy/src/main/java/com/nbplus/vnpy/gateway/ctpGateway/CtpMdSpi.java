package com.nbplus.vnpy.gateway.ctpGateway;

import com.nbplus.vnpy.common.utils.Text;
import com.nbplus.vnpy.trader.VtErrorData;
import com.nbplus.vnpy.trader.VtSubscribeReq;
import ctp.thostmduserapi.*;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @Description
 * @Author gongtan
 * @Date 2019/11/28 11:12
 * @Version 1.0
 **/
public class CtpMdSpi extends CThostFtdcMdSpi {

    private CThostFtdcMdApi m_mdapi;

    private CtpGateway gateway; // gateway对象
    private String gatewayName; // gateway对象名称

    private String userID; // 账号
    private String password; // 密码
    private String brokerID; // 经纪商代码
    private String address; // 服务器地址

    private int reqID;
    private boolean connectionStatus; // 连接状态
    private boolean loginStatus; // 登录状态
    private Set<VtSubscribeReq> subscribedSymbols; // 已订阅合约代码


    public CtpMdSpi(CThostFtdcMdApi mdapi,VtSubscribeReq subscribeReq) {
        m_mdapi = mdapi;
        this.subscribedSymbols = new HashSet<VtSubscribeReq>(); // 已订阅合约代码
        this.gateway = gateway; // gateway对象
        //this.gatewayName = gateway.getGatewayName(); // gateway对象名称
        this.subscribedSymbols.add(subscribeReq);
        this.reqID = 0; // 操作请求编号

        this.connectionStatus = false; // 连接状态
        this.loginStatus = false; // 登录状态



        this.userID = ""; // 账号
        this.password = ""; // 密码
        this.brokerID = ""; // 经纪商代码
        this.address = ""; // 服务器地址
    }

    /**
     * @param
     * @return void
     * @Description 连接建立通知
     * @author gt_vv
     * @date 2019/11/28
     */
    @Override
    public void OnFrontConnected() {
        System.out.println("On Front Connected--登陆前连接");
        this.connectionStatus = true;
        System.out.println(Text.DATA_SERVER_CONNECTED);
        this.login();
    }

    /**
     * @param
     * @return void
     * @Description 登录
     * @author gt_vv
     * @date 2019/11/28
     */
    private void login() {
        CThostFtdcReqUserLoginField field = new CThostFtdcReqUserLoginField();
        field.setBrokerID(this.brokerID);
        field.setUserID(this.userID);
        field.setPassword(this.password);
        this.reqID += 1;
        //调取登录
        m_mdapi.ReqUserLogin(field, this.reqID);
    }

    /**
     * @param pRspUserLogin
     * @param pRspInfo
     * @param nRequestID
     * @param bIsLast
     * @return void
     * @Description 登录回报
     * @author gt_vv
     * @date 2019/11/28
     */
    @Override
    public void OnRspUserLogin(CThostFtdcRspUserLoginField pRspUserLogin, CThostFtdcRspInfoField pRspInfo,
                               int nRequestID, boolean bIsLast) {
        boolean isError = (pRspInfo != null) && (pRspInfo.getErrorID() != 0);
        if (!isError) {
            //更改登录状态
            this.loginStatus = true;
            System.out.println(Text.DATA_SERVER_LOGIN);
        } else {
            //打印错误信息
            System.out.printf("OnRspUserLogin ErrorMsg[%s]\n", pRspInfo.getErrorMsg());
        }
        //VtSubscribeReq vtSubscribeReq = new VtSubscribeReq();
        //vtSubscribeReq.setSymbol("MA001");//au1912
        for (VtSubscribeReq subscribedSymbol : this.subscribedSymbols) {
            this.subscribeMarket(subscribedSymbol);
        }

    }

    /**
     * @Description 订阅合约
     * @author gt_vv
     * @date 2019/11/28
     * @param
     * @return
     */

    public void subscribeMarket(VtSubscribeReq subscribeReq) {
        if(subscribeReq.getSymbol() == "au1912"){
            m_mdapi.SubscribeMarketData(new String[]{subscribeReq.getSymbol()}, 1);
            System.out.println("订阅");
        }

        if (this.loginStatus) {
            //调取订阅
            //new String[]{subscribeReq.getSymbol()}
            m_mdapi.SubscribeMarketData(new String[]{subscribeReq.getSymbol()}, 1);
            System.out.println("调用订阅");
        }else {
            //没有登录则保存订阅信息
            this.subscribedSymbols.add(subscribeReq);
        }
    }


    /**
     * @param pSpecificInstrument
     * @param pRspInfo
     * @param nRequestID
     * @param bIsLast
     * @return void
     * @Description 订阅合约回报
     * @author gt_vv
     * @date 2019/11/28
     */
    @Override
    public void OnRspSubMarketData(CThostFtdcSpecificInstrumentField pSpecificInstrument,
                                   CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {

        boolean isError = (pRspInfo != null) && (pRspInfo.getErrorID() != 0);
        if (isError) {
            VtErrorData err = new VtErrorData();
            err.setGatewayName(this.gatewayName);
            err.setErrorID(pRspInfo.getErrorID() + "");
            err.setErrorMsg(pRspInfo.getErrorMsg());
            this.gateway.onError(err);
            return;
        }
        System.out.println("订阅成功");
    }


    /**
     * @Description 行情推送
     * @author gt_vv
     * @date 2019/11/28
     * @param pDepthMarketData
     * @return void
     */
    @Override
    public void OnRtnDepthMarketData(CThostFtdcDepthMarketDataField pDepthMarketData) {
        if (pDepthMarketData != null) {
            String instrumentID = pDepthMarketData.getInstrumentID();
            System.out.printf(instrumentID + ":     " + "AskPrice1[%f]   BidPrice1[%f]\n",
                    pDepthMarketData.getAskPrice1(),pDepthMarketData.getBidPrice1());
        }
        else {
            System.out.printf("NULL obj\n");
        }

    }

}
