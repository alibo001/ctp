package com.nbplus.vnpy.CTPservice.impl;

import ctp.thosttraderapi.CThostFtdcTraderApi;

/**
 * @Description
 * @Author gongtan
 * @Date 2019/11/23 14:10
 * @Version 1.0
 **/
public class TraderSpiImpl {

    private CThostFtdcTraderApi m_traderapi;

    public  CThostFtdcTraderApi getTraderapi(CThostFtdcTraderApi traderapi) {
        m_traderapi =  traderapi;
        return m_traderapi;
    }
}
