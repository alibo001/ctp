package com.nbplus.vnpy.rest;

import com.nbplus.vnpy.event.EventEngine;
import com.nbplus.vnpy.gateway.ctpGateway.CtpGateway;
import com.nbplus.vnpy.trader.VtGateway;
import com.nbplus.vnpy.trader.VtOrderReq;
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
        System.out.println(111111);
        VtOrderReq vtOrderReq = new VtOrderReq();
        vtOrderReq.setDirection("2");
        vtOrderReq.setSymbol("m2005");
        vtOrderReq.setExchange("DCE");
        vtOrderReq.setVtSymbol("m2005.DCE");
        vtOrderReq.setPrice(2741);
        ctpGateway.sendOrder(vtOrderReq);
    }

    @PostMapping("/con")
    public void con(){
        ctpGateway.connect();
    }
}
