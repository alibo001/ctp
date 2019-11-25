package com.nbplus.vnpy.CTPservice.serviceImpl;


import com.nbplus.vnpy.CTPservice.CtpTdSpi;
import com.nbplus.vnpy.domain.CTPLogin;
import ctp.thosttraderapi.CThostFtdcTraderApi;
import ctp.thosttraderapi.THOST_TE_RESUME_TYPE;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author gongtan
 * @Date 2019/11/25 17:26
 * @Version 1.0
 **/
@Service
public class tdCtpImpl {

    /**
     * @Description  连接ctp的    登录暴露服务层
     * @author gt_vv
     * @date 2019/11/25
     * @param ctpLogin
     * @return void
     */
    public void login(CTPLogin ctpLogin){
        CThostFtdcTraderApi traderApi = CThostFtdcTraderApi.CreateFtdcTraderApi();
        CtpTdSpi ctpTdSpi = new CtpTdSpi(traderApi,ctpLogin);
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
