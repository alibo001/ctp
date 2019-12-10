package com.nbplus.vnpy.gateway.ctpGateway;

import com.nbplus.vnpy.disruptorEvent.FastEventService;
import com.nbplus.vnpy.domain.CTPLogin;
import com.nbplus.vnpy.trader.VtTickData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description    实现GatwayApi得抽象类
 * @Author gongtan
 * @Date 2019/12/5 10:46
 * @Version 1.0
 **/
public abstract class GatewayApiAbstract  implements GatewayApi{

    private static Logger log = LoggerFactory.getLogger(GatewayApiAbstract.class);

    protected String gatewayId;
    protected String gatewayName;
    protected String logInfo;

    // 事件引擎
    private FastEventService fastEventService;

    protected CTPLogin ctpLogin;

    @Override
    public String getLogInfo() {
        return this.logInfo;
    }


    @Override
    public void emitTick(VtTickData tick) {
        fastEventService.emitTick(tick);
    }

    @Override
    public CTPLogin getCtpLogin() {
        return ctpLogin;
    }
}
