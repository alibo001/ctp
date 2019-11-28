package com.nbplus.vnpy.gateway.ctpGateway;

import com.nbplus.vnpy.event.Event;
import com.nbplus.vnpy.event.EventEngine;
import com.nbplus.vnpy.event.EventType;
import com.nbplus.vnpy.gson.CTPConnectBean;
import com.nbplus.vnpy.trader.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nbplus.vnpy.method.Method;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * @Description   ctp的接口 实例化接口
 * @Author gongtan
 * @Date 2019/11/27 16:53
 * @Version 1.0
 **/
public class CtpGateway  extends VtGateway{

    private CtpMdSpi mdSpi;// 行情SPI
    private CtpTdSpi tdSpi;// 交易API

    private CTPConnectBean ctpConnectBean;

    private boolean mdConnected;// 行情API连接状态，登录完成后为True
    private boolean tdConnected; // 交易API连接状态

    private String fileName;
    private String filePath;

    //方法的集合
    private ArrayList<Method> qryFunctionList;
    private int qryCount;// 查询触发倒计时
    private int qryTrigger;// 查询触发点
    private int qryNextFunction;// 上次运行的查询函数索引

    public CtpGateway(EventEngine eventEngine) {
        this(eventEngine, "CTP");
    }

    public CtpGateway(EventEngine eventEngine, String gatewayName) {
        super(eventEngine, gatewayName);
        //this.mdSpi = new CtpMdSpi(this); // 行情API
        this.tdSpi = new CtpTdSpi(this); // 交易API

        this.mdConnected = false; // 行情API连接状态，登录完成后为True
        this.tdConnected = false; // 交易API连接状态

        this.setQryEnabled(true); // 循环查询
        this.setGatewayType(VtConstant.GATEWAYTYPE_FUTURES);

        this.fileName = this.getGatewayName() + "_connect.json";
        this.filePath = "input/" + fileName;
    }




    @PostMapping("/connect")
    public void connect(@RequestBody CTPConnectBean connectBean){
        //调用 connect 方法进行登录验证
        //td 交易接口连接
        tdSpi.connect(connectBean.getUserID(),connectBean.getPassword(),connectBean.getBrokerID(),connectBean.getTdAddress(),connectBean.getAuthCode(),connectBean.getUserProductInfo());
        this.ctpConnectBean = connectBean;
        // 初始化并启动查询
        //this.initQuery();
    }



 /*
    // 初始化连续查询
    private void initQuery() {
        if (this.isQryEnabled()) {
            // 需要循环的查询函数列表
            this.qryFunctionList = new ArrayList<Method>();
            this.qryFunctionList.add(new Method(this, "qryAccount"));
            this.qryFunctionList.add(new Method(this, "qryPosition"));

            this.qryCount = 0;           // 查询触发倒计时
            this.qryTrigger = 2;         // 查询触发点
            this.qryNextFunction = 0;    // 上次运行的查询函数索引

            //this.startQuery();
        }
    }


    // 启动连续查询
    private void startQuery() {
        //注册计时器事件  1秒执行一下  query 方法 进行回调
        this.getEventEngine().register(EventType.EVENT_TIMER, new Method(this, "query", Event.class));
    }

    // 注册到事件处理引擎上的查询函数
    private void query(Event event) {
        this.qryCount += 1;

        //如果 qryCount 大于查询触发点则清空倒计时
        if (this.qryCount > this.qryTrigger) {
            // 清空倒计时
            this.qryCount = 0;

            // 执行查询函数
            Method function = this.qryFunctionList.get(this.qryNextFunction);
            function.invoke();

            // 计算下次查询函数的索引，如果超过了列表长度，则重新设为0
            this.qryNextFunction += 1;
            if (this.qryNextFunction == this.qryFunctionList.size()) {
                this.qryNextFunction = 0;
            }
        }
    }*/


    public CtpTdSpi getTdSpi() {
        return tdSpi;
    }

    public void setTdSpi(CtpTdSpi tdSpi) {
        this.tdSpi = tdSpi;
    }

    public CTPConnectBean getCtpConnectBean() {
        return ctpConnectBean;
    }

    public void setCtpConnectBean(CTPConnectBean ctpConnectBean) {
        this.ctpConnectBean = ctpConnectBean;
    }

    public boolean isMdConnected() {
        return mdConnected;
    }

    public void setMdConnected(boolean mdConnected) {
        this.mdConnected = mdConnected;
    }

    public boolean isTdConnected() {
        return tdConnected;
    }

    public void setTdConnected(boolean tdConnected) {
        this.tdConnected = tdConnected;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Method> getQryFunctionList() {
        return qryFunctionList;
    }

    public void setQryFunctionList(ArrayList<Method> qryFunctionList) {
        this.qryFunctionList = qryFunctionList;
    }

    public int getQryCount() {
        return qryCount;
    }

    public void setQryCount(int qryCount) {
        this.qryCount = qryCount;
    }

    public int getQryTrigger() {
        return qryTrigger;
    }

    public void setQryTrigger(int qryTrigger) {
        this.qryTrigger = qryTrigger;
    }

    public int getQryNextFunction() {
        return qryNextFunction;
    }

    public void setQryNextFunction(int qryNextFunction) {
        this.qryNextFunction = qryNextFunction;
    }
}
