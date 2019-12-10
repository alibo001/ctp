package com.nbplus.vnpy.gateway.ctpGatewayService;

import org.springframework.stereotype.Service;

/**
 * @Description  订阅行情
 * @Author gongtan
 * @Date 2019/11/28 14:23
 * @Version 1.0
 **/
@Service
public class ctpMdSpiService {

    /*private CThostFtdcMdApi mdApi = CThostFtdcMdApi.CreateFtdcMdApi();
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
    }*/
   /* CThostFtdcTraderApi traderApi = CThostFtdcTraderApi.CreateFtdcTraderApi("td");
    CtpTdSpi ctpTdSpi = new CtpTdSpi(traderApi,ctpLogin);
    //注册一事件处理的实例    参数：实现了 CThostFtdcTraderSpi 接口的实例指针
        traderApi.RegisterSpi(ctpTdSpi);
    //设置交易托管系统的网络通讯地址    目前写死
        traderApi.RegisterFront("tcp://180.168.146.187:10100");
    //订阅私有流
        traderApi.SubscribePublicTopic(THOST_TE_RESUME_TYPE.THOST_TERT_RESTART);
    //订阅公共流
        traderApi.SubscribePrivateTopic(THOST_TE_RESUME_TYPE.THOST_TERT_RESTART);
    //使客户端开始与交易托管系统建立连接，连接成功后可以进行登陆。
        traderApi.Init();



    CThostFtdcMdApi mdApi = CThostFtdcMdApi.CreateFtdcMdApi("md",true,true);
    //CtpMdSpi pMdspiImpl = new CtpMdSpi(mdApi);
    //mdApi.RegisterSpi(pMdspiImpl);
        mdApi.RegisterFront(ctp1_MdAddress);
        mdApi.Init();
        mdApi.Join();
    //客户端等待一个接口实例线程的结束。
        traderApi.Join();
        return;*/
}
