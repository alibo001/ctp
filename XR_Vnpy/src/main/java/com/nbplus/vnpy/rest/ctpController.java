package com.nbplus.vnpy.rest;

import com.nbplus.vnpy.event.EventEngine;
import com.nbplus.vnpy.gateway.ctpGateway.CtpGateway;
import com.nbplus.vnpy.trader.VtCancelOrderReq;
import com.nbplus.vnpy.trader.VtGateway;
import com.nbplus.vnpy.trader.VtOrderReq;
import com.nbplus.vnpy.trader.VtSubscribeReq;
import ctp.thosttraderapi.thosttradeapiConstants;
import jdk.nashorn.internal.ir.annotations.Reference;
import lombok.experimental.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    /**
     * @Description 订阅行情
     * @author gt_vv
     * @date 2020/1/2
     * @param subscribeReq
     * @return void
     */
    @PostMapping("subscribe")
    public void subscribe(@RequestBody VtSubscribeReq subscribeReq){
        ctpGateway.subscribe(subscribeReq);
    }

    /**
     * @Description 退订行情
     * @author gt_vv
     * @date 2020/1/2
     * @param symbol
     * @return void
     */
    @PostMapping("unsubscribe")
    public void unsubscribe(@RequestParam(value = "symbol") String symbol){
        ctpGateway.unsubscribe(symbol);
    }


    /**
     * @Description  报单
     * @author gt_vv
     * @date 2020/1/2
     * @param
     * @return void
     */
    @PostMapping("/order")
    public void order(){
        VtOrderReq vtOrderReq = new VtOrderReq();
        vtOrderReq.setDirection("2");
        vtOrderReq.setSymbol("fu2005");
        vtOrderReq.setExchange("SHFE");
        vtOrderReq.setVtSymbol("fu2005.SHFE");
        vtOrderReq.setVolume(1);
        //THOST_FTDC_OPT_LimitPrice（限价）和THOST_FTDC_OPT_AnyPrice（市价）
        //vtOrderReq.setPriceType("FAK");
        vtOrderReq.setPrice(2270);
        ctpGateway.sendOrder(vtOrderReq);
    }

    /**
     * @Description  连接CTP服务
     * @author gt_vv
     * @date 2020/1/2
     * @param
     * @return void
     */
    @PostMapping("/con")
    public void con(){
        ctpGateway.connect();
    }

    /**
     * @Description  查询持仓
     * @author gt_vv
     * @date 2020/1/2
     * @param
     * @return void
     */
    @PostMapping("cancelOrder")
    public void cancelOrder(@RequestBody VtCancelOrderReq vtCancelOrderReq){
        ctpGateway.cancelOrder(vtCancelOrderReq);
    }

    /**
     * @Description  查询持仓
     * @author gt_vv
     * @date 2020/1/2
     * @param
     * @return void
     */
    @PostMapping("queryPosition")
    public void queryPosition(){
        ctpGateway.qryPosition();
    }


    /**
     * @Description 查询账户信息   // 循环查询 ---且主动  ctp内无实时推送线程
     * @author gt_vv
     * @date 2020/1/3
     * @return
     */
    @PostMapping("qryAccount")
    public void qryAccount(){
        ctpGateway.qryAccount();
    }

    /**
     * @Description  查询基础合约
     * @author gt_vv
     * @date 2020/1/3
     * @param
     * @return void
     */
    @PostMapping("qryContract")
    public void qryContract(){
        ctpGateway.qryContract();
    }

}
