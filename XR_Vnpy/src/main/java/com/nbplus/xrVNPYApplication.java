package com.nbplus;

import ctp.thostmduserapi.CThostFtdcMdApi;
import ctp.thosttraderapi.CThostFtdcTraderApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @Description
 * @Author gongtan
 * @Date 2019/11/20 15:13
 * @Version 1.0
 **/
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class xrVNPYApplication {
    public static void main(String[] args) {
        SpringApplication.run(xrVNPYApplication.class,args);
    }

    /**
     * @Description 引入动态资源库
     * @author gt_vv
     * @date 2019/11/22
     * @param null
     * @return
     */
    static{
        //资源目录library.path
        System.out.println(System.getProperty("java.library.path"));
        //加载动态库
        //交易dll
        System.loadLibrary("thosttraderapi_se");
        System.loadLibrary("thosttraderapi_wrap");
        //行情dll
        System.loadLibrary("thostmduserapi_se");
        System.loadLibrary("thostmduserapi_wrap");
    }

    /**
     * @Description 实例化Bean
     * @author gt_vv
     * @date 2019/11/22
     * @param
     * @return ctp.thosttraderapi.CThostFtdcTraderApi
     */
    @Bean
    public CThostFtdcTraderApi traderApi() {
        return CThostFtdcTraderApi.CreateFtdcTraderApi();
    }

    @Bean
    public CThostFtdcMdApi mduserApi() {
        return CThostFtdcMdApi.CreateFtdcMdApi();

    }
}
