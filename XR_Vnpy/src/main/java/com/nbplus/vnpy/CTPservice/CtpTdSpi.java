package com.nbplus.vnpy.CTPservice;

import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author gongtan
 * @Date 2019/11/21 16:18
 * @Version 1.0
 **/
@Service
public class CtpTdSpi {

    // ctp  状态
    private boolean connectionStatus; // 连接状态
    private boolean loginStatus; // 登录状态
    private boolean authStatus; // 验证状态
    private boolean loginFailed; // 登录失败（账号密码错误）
}
