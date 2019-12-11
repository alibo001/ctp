package com.nbplus.vnpy.gateway.ctpGateway;


import com.nbplus.vnpy.event.EventEngine;
import com.nbplus.vnpy.trader.VtConstant;
import com.nbplus.vnpy.trader.VtGateway;

/**
 * @Description   ctp的接口 实例化接口
 * @Author gongtan
 * @Date 2019/11/27 16:53
 * @Version 1.0
 **/
public class CtpGateway extends VtGateway {

    // 行情SPI
    private mdSpi_CTP mdSpi;

    // 交易SPI
    private tdSpi_CTP tdSpi;

    // 行情API连接状态，登录完成后为True
    private boolean mdConnected;

    // 行情API连接状态，登录完成后为True
    private boolean tdConnected;


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

    public CtpGateway(EventEngine eventEngine) {
        this(eventEngine, "CTP");
    }

    /**
     * @Description  重写 CtpGateway  本类中调用
     * @author gt_vv
     * @date 2019/12/10
     * @param eventEngine
     * @param gatewayName
     * @return
     */
    public CtpGateway(EventEngine eventEngine, String gatewayName) {
        super(eventEngine, gatewayName);
        //行情Api
        mdSpi = new mdSpi_CTP(this);
        tdSpi = new tdSpi_CTP(this);
        // 行情API连接状态，登录完成后为True
        this.mdConnected = false;
        this.tdConnected = false;
        // 循环查询
        this.setQryEnabled(true);
        this.setGatewayType(VtConstant.GATEWAYTYPE_FUTURES);
    }

  /*  // 连接
    @PostMapping("/login")
    public void connect(String userID,String password,String brokerID,String mdAddress, String authCode,String userProductInfo) {
        // 创建行情和交易接口对象
        this.mdSpi.connect(userID, password, brokerID, mdAddress);
       // this.tdSpi.connect(userID, password, brokerID, tdAddress, authCode, userProductInfo);
        // 初始化并启动查询
        // this.initQuery();
    }*/


    @Override
    public void connect() {
        String userID;
        String password;
        String brokerID;
        String tdAddress;
        String mdAddress;
        String authCode;
        String userProductInfo;

        // 创建行情和交易接口对象
        this.mdSpi.connect("153145", "Gongtan123", "9999", "tcp://180.168.146.187:10131");
        //tcp://180.168.146.187:10131   7*24 模拟环境
        //tcp://180.168.146.187:10110   真实环境
        this.tdSpi.connect("153145","Gongtan123","9999",
                "tcp://180.168.146.187:10130","0000000000000000","simnow_client_test");
        //String userID, String password, String brokerID, String address, String authCode,
        //                        String userProductInfo
        //tcp://180.168.146.187:10130  7*24交易CTP
    }

    /**
     * @Description  测试main方法
     * @author gt_vv
     * @date 2019/12/11
     * @param args
     * @return void
     */
    public static void main(String[] args) {
        EventEngine eventEngine = new EventEngine();
        eventEngine.start(false);
        CtpGateway ctpGateway = new CtpGateway(eventEngine,"CTP");
        ctpGateway.connect();
    }

}
