package com.nbplus.vnpy.gateway;

import com.nbplus.vnpy.CTPservice.serviceImpl.tdCtpImpl;
import com.nbplus.vnpy.common.msg.ObjectRestResponse;
import com.nbplus.vnpy.domain.CTPLogin;
import com.nbplus.vnpy.gateway.ctpGateway.CtpMdSpi;
import com.nbplus.vnpy.gateway.ctpGatewayService.ctpMdSpiService;
import com.nbplus.vnpy.trader.VtSubscribeReq;
import ctp.thostmduserapi.CThostFtdcMdApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description
 * @Author gongtan
 * @Date 2019/11/21 15:31
 * @Version 1.0
 **/
@RestController
@RequestMapping("/tdCTP")
public class CTPController {

    @Autowired
    private tdCtpImpl tdCtp;

    @Autowired
    private ctpMdSpiService spiService;


    @PostMapping("/login")
    public ObjectRestResponse login(@RequestBody CTPLogin ctpLogin){
        tdCtp.login(ctpLogin);
        return null;
    }

    /**
     * @Description  订阅行情服务器连接
     * @author gt_vv
     * @date 2019/11/28
     * @param subscribeReq
     * @return void
     */
    @PostMapping("/subscribeCon")
    public void connect (@RequestBody VtSubscribeReq subscribeReq){
       spiService.getMarketData(subscribeReq);
    }

    /**
     * @Description  行情订阅
     * @author gt_vv
     * @date 2019/11/29
     * @param subscribeReq
     * @return void
     */
    @PostMapping("/subscribeMarket")
    public void subscribeDate (@RequestBody VtSubscribeReq subscribeReq){
        spiService.test(subscribeReq);
    }
}
