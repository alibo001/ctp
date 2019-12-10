package com.nbplus.vnpy.gateway.ctpGateway;


import com.google.gson.Gson;
import com.nbplus.vnpy.common.utils.Text;
import com.nbplus.vnpy.event.EventEngine;
import com.nbplus.vnpy.gson.CTPConnectBean;
import com.nbplus.vnpy.method.AppException;
import com.nbplus.vnpy.trader.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @Description   ctp的接口 实例化接口
 * @Author gongtan
 * @Date 2019/11/27 16:53
 * @Version 1.0
 **/
public class CtpGateway extends VtGateway{

    // 行情SPI
    private mdSpi_CTP mdSpi;

    // 行情API连接状态，登录完成后为True
    private boolean mdConnected;

    public CtpGateway(EventEngine eventEngine) {
        this(eventEngine, "CTP");
    }

    /**
     * @Description  重写 CtpGateway  本类中调用
     * @author gt_vv
     * @date 2019/12/10
     * @param eventEngine
     * @param gatewayName
     * @return
     */
    public CtpGateway(EventEngine eventEngine, String gatewayName) {
        super(eventEngine, gatewayName);
        //行情Api
        mdSpi = new mdSpi_CTP(this);
        // 行情API连接状态，登录完成后为True
        this.mdConnected = false;
        // 循环查询
        this.setQryEnabled(true);
        this.setGatewayType(VtConstant.GATEWAYTYPE_FUTURES);
    }

  /*  // 连接
    @PostMapping("/login")
    public void connect(String userID,String password,String brokerID,String mdAddress, String authCode,String userProductInfo) {
        // 创建行情和交易接口对象
        this.mdSpi.connect(userID, password, brokerID, mdAddress);
       // this.tdSpi.connect(userID, password, brokerID, tdAddress, authCode, userProductInfo);
        // 初始化并启动查询
        // this.initQuery();
    }*/


    @Override
    public void connect() {
        String userID;
        String password;
        String brokerID;
        String tdAddress;
        String mdAddress;
        String authCode;
        String userProductInfo;

        // 创建行情和交易接口对象
        this.mdSpi.connect("153145", "Gongtan123", "9999", "180.168.146.187:10131");
    }

    public static void main(String[] args) {
        EventEngine eventEngine = new EventEngine();
        CtpGateway ctpGateway = new CtpGateway(eventEngine,"CTP");
        ctpGateway.connect();
    }
}
