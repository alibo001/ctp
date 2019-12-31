package com.nbplus.vnpy.rest;

import com.nbplus.vnpy.event.EventEngine;
import com.nbplus.vnpy.gateway.ctpGateway.CtpGateway;
import com.nbplus.vnpy.trader.VtGateway;
import com.nbplus.vnpy.trader.VtOrderReq;
import ctp.thosttraderapi.thosttradeapiConstants;
import jdk.nashorn.internal.ir.annotations.Reference;
import lombok.experimental.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author gongtan
 * @Date 2019/12/21 13:31
 * @Version 1.0
 **/
@RestController
@RequestMapping("/javactp")
public class ctpController {

    @Reference
    private CtpGateway ctpGateway = new CtpGateway(new EventEngine(),"CTP");

    @PostMapping("/order")
    public void order(){
        VtOrderReq vtOrderReq = new VtOrderReq();
        vtOrderReq.setDirection("1");
        vtOrderReq.setSymbol("MA001");
        vtOrderReq.setExchange("CZCE");
        vtOrderReq.setVtSymbol("MA001.CZCE");
        vtOrderReq.setVolume(10);
        //THOST_FTDC_OPT_LimitPrice（限价）和THOST_FTDC_OPT_AnyPrice（市价）
        vtOrderReq.setPriceType("FOK");
        vtOrderReq.setPrice(2111);
        ctpGateway.sendOrder(vtOrderReq);
    }

    @PostMapping("/con")
    public void con(){
        ctpGateway.connect();
    }

    @PostMapping("queryPosition")
    public void queryPosition(){
        ctpGateway.qryPosition();
    }
}
