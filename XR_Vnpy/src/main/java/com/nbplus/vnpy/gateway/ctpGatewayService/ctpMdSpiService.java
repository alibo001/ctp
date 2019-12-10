package com.nbplus.vnpy.gateway.ctpGatewayService;

import com.nbplus.vnpy.gateway.ctpGateway.CtpMdSpi;
import com.nbplus.vnpy.trader.VtSubscribeReq;
import ctp.thostmduserapi.CThostFtdcMdApi;
import org.springframework.stereotype.Service;

/**
 * @Description  订阅行情
 * @Author gongtan
 * @Date 2019/11/28 14:23
 * @Version 1.0
 **/
@Service
public class ctpMdSpiService {

    private CThostFtdcMdApi mdApi = CThostFtdcMdApi.CreateFtdcMdApi();
    //final static String ctp1_MdAddress = "tcp://180.168.146.187:10131";
    final static String ctp1_MdAddress = "tcp://180.168.146.187:10110";
    CtpMdSpi pMdspiImpl = new CtpMdSpi(mdApi);

    public void getMarketData(VtSubscribeReq subscribeReq){
        mdApi.RegisterSpi(pMdspiImpl);

        mdApi.RegisterFront(ctp1_MdAddress);
        mdApi.Init();
    }
    public void test(VtSubscribeReq subscribeReq) {
        pMdspiImpl.subscribeMarket(subscribeReq);
    }
}
