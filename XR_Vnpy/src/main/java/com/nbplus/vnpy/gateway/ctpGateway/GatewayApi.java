package com.nbplus.vnpy.gateway.ctpGateway;

import com.nbplus.vnpy.domain.CTPLogin;
import com.nbplus.vnpy.trader.VtSubscribeReq;
import com.nbplus.vnpy.trader.VtTickData;

/**
 * @Description
 * @Author gongtan
 * @Date 2019/12/4 16:46
 * @Version 1.0
 **/
public interface GatewayApi {
    /**
     * 获取ID
     *
     * @return
     */
    String getGatewayId();

    /**
     * 获取名称
     *
     * @return
     */
    String getGatewayName();

    /**
     * 获取 登录信息 地址 等
     *
     * @param
     * @return com.nbplus.vnpy.domain.CTPLogin
     */
    CTPLogin getCtpLogin();

    /**
     * 断开
     */
    void disconnect();

    /**
     * 订阅
     *
     * @param
     */
    boolean subscribe(VtSubscribeReq vtSubscribeReq);

    /**
     * 获取日志字符串
     *
     * @return
     */
    String getLogInfo();



    /**
     * 广播Tick
     *
     * @param tick
     */
    void emitTick(VtTickData tick);

}
