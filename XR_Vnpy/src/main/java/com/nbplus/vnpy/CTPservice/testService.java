package com.nbplus.vnpy.CTPservice;

import ctp.thosttraderapi.CThostFtdcTraderApi;
import ctp.thosttraderapi.THOST_TE_RESUME_TYPE;

/**
 * @Description
 * @Author gongtan
 * @Date 2019/11/23 14:16
 * @Version 1.0
 **/
public class testService {

    public static void main(String[] args) {
        CThostFtdcTraderApi traderApi = CThostFtdcTraderApi.CreateFtdcTraderApi();
        CtpTdSpi ctpTdSpi = new CtpTdSpi(traderApi);
        //注册一事件处理的实例    参数：实现了 CThostFtdcTraderSpi 接口的实例指针
        traderApi.RegisterSpi(ctpTdSpi);
        //设置交易托管系统的网络通讯地址
        traderApi.RegisterFront("tcp://180.168.146.187:10130");
        //订阅私有流
        traderApi.SubscribePublicTopic(THOST_TE_RESUME_TYPE.THOST_TERT_RESTART);
        //订阅公共流
        traderApi.SubscribePrivateTopic(THOST_TE_RESUME_TYPE.THOST_TERT_RESTART);
        //使客户端开始与交易托管系统建立连接，连接成功后可以进行登陆。
        traderApi.Init();
        //客户端等待一个接口实例线程的结束。
        traderApi.Join();
    }

    public void  tet(){

        CThostFtdcTraderApi traderApi = CThostFtdcTraderApi.CreateFtdcTraderApi();
        CtpTdSpi ctpTdSpi = new CtpTdSpi(traderApi);
        //注册一事件处理的实例    参数：实现了 CThostFtdcTraderSpi 接口的实例指针
        traderApi.RegisterSpi(ctpTdSpi);
        //设置交易托管系统的网络通讯地址
        traderApi.RegisterFront("tcp://180.168.146.187:10130");
        //订阅私有流
        traderApi.SubscribePublicTopic(THOST_TE_RESUME_TYPE.THOST_TERT_RESTART);
        //订阅公共流
        traderApi.SubscribePrivateTopic(THOST_TE_RESUME_TYPE.THOST_TERT_RESTART);
        //使客户端开始与交易托管系统建立连接，连接成功后可以进行登陆。
        traderApi.Init();
        //客户端等待一个接口实例线程的结束。
        traderApi.Join();
    }
}
