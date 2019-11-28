package com.nbplus.vnpy.gateway.ctpGateway;

import com.nbplus.vnpy.common.utils.Text;
import com.nbplus.vnpy.trader.VtErrorData;
import com.nbplus.vnpy.trader.VtLogData;
import com.nbplus.vnpy.trader.VtPositionData;
import ctp.thosttraderapi.*;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * @Author gongtan
 * @Date 2019/11/27 16:22
 * @Version 1.0
 **/
public class CtpTdSpi extends CThostFtdcTraderSpi {

    private CtpGateway gateway; // gateway对象
    private String gatewayName; // gateway对象名称

    private int reqID; // 操作请求编号
    private int orderRef; // 订单编号

    private boolean connectionStatus; // 连接状态
    private boolean loginStatus; // 登录状态
    private boolean authStatus; // 验证状态
    private boolean loginFailed; // 登录失败（账号密码错误）

    private String userID; // 账号
    private String password; // 密码
    private String brokerID; // 经纪商代码
    private String address; // 服务器地址
    private String authCode;
    private String userProductInfo;

    private int frontID; // 前置机编号
    private int sessionID; // 会话编号

    private Map<String, VtPositionData> posDict;
    private Map<String, String> symbolExchangeDict; // 保存合约代码和交易所的印射关系
    private Map<String, Integer> symbolSizeDict; // 保存合约代码和合约大小的印射关系

    private boolean requireAuthentication;

    private CThostFtdcTraderApi tdApi;

    /**
     * @param gateway
     * @return
     * @Description
     * @author gt_vv
     * @date 2019/11/27
     */
    public CtpTdSpi(CtpGateway gateway) {
        this.gateway = gateway; // gateway对象
        this.gatewayName = gateway.getGatewayName(); // gateway对象名称

        this.reqID = 0; // 操作请求编号
        this.orderRef = 0; // 订单编号

        this.connectionStatus = false; // 连接状态
        this.loginStatus = false; // 登录状态
        this.authStatus = false; // 验证状态
        this.loginFailed = false; // 登录失败（账号密码错误）

        this.userID = ""; // 账号
        this.password = ""; // 密码
        this.brokerID = ""; // 经纪商代码
        this.address = ""; // 服务器地址

        this.frontID = 0; // 前置机编号
        this.sessionID = 0; // 会话编号

        this.posDict = new ConcurrentHashMap<String, VtPositionData>();
        this.symbolExchangeDict = new ConcurrentHashMap<String, String>(); // 保存合约代码和交易所的印射关系
        this.symbolSizeDict = new ConcurrentHashMap<String, Integer>(); // 保存合约代码和合约大小的印射关系

        this.requireAuthentication = false;
    }

    // 初始化连接
    public void connect(String userID, String password, String brokerID, String address, String authCode,
                        String userProductInfo) {
        this.userID = userID; // 账号
        this.password = password; // 密码
        this.brokerID = brokerID; // 经纪商代码
        this.address = address; // 服务器地址
        this.authCode = authCode; // 验证码
        this.userProductInfo = userProductInfo; // 产品信息

        // 如果尚未建立服务器连接，则进行连接
        if (!this.connectionStatus) {
            // 创建C++环境中的API对象，这里传入的参数是需要用来保存.con文件的文件夹路径
            File path = new File("temp/");
            if (!path.exists()) {
                path.mkdirs();
            }
            //创建 API 对象  指定生成流文件的路径
            this.tdApi = CThostFtdcTraderApi.CreateFtdcTraderApi(path.getPath() + System.getProperty("file.separator"));

            //注册一事件处理的实例
            this.tdApi.RegisterSpi(this);

            // 设置数据同步模式为推送从今日开始所有数据
            this.tdApi.SubscribePublicTopic(THOST_TE_RESUME_TYPE.THOST_TERT_RESTART);
            this.tdApi.SubscribePrivateTopic(THOST_TE_RESUME_TYPE.THOST_TERT_RESTART);

            // 注册服务器地址
            this.tdApi.RegisterFront(this.address);

            // 初始化连接，成功会调用onFrontConnected
            this.tdApi.Init();
        }
        // 若已经连接但尚未登录，则进行登录
        else {
            if (this.requireAuthentication && !this.authStatus) {
                this.authenticate();
            } else if (!this.loginStatus) {
                this.login();
            }
        }
    }

    /**
     * @param
     * @return void
     * @Description 验证没有登陆后   此方法会被调用 进行登录
     * @author gt_vv
     * @date 2019/11/27
     */
    private void login() {
        // 如果之前有过登录失败，则不再进行尝试
        if (this.loginFailed) {
            return;
        }
        // 如果填入了用户名密码等，则登录
        if ((this.userID != null && !"".equals(this.userID.trim()))
                && (this.password != null && !"".equals(this.password.trim()))
                && (this.brokerID != null && !"".equals(this.brokerID.trim()))) {
            CThostFtdcReqUserLoginField req = new CThostFtdcReqUserLoginField();
            req.setUserID(this.userID);
            req.setPassword(this.password);
            req.setBrokerID(this.brokerID);
            this.reqID += 1;
            //调用   ReqUserLogin 登录
            this.tdApi.ReqUserLogin(req, this.reqID);
        }
    }

    private void authenticate() {
        if ((this.userID != null && !"".equals(this.userID.trim()))
                && (this.brokerID != null && !"".equals(this.brokerID.trim()))
                && (this.authCode != null && !"".equals(this.authCode.trim()))
                && (this.userProductInfo != null && !"".equals(this.userProductInfo.trim()))) {
            CThostFtdcReqAuthenticateField req = new CThostFtdcReqAuthenticateField();
            req.setUserID(this.userID);
            req.setBrokerID(this.brokerID);
            req.setAuthCode(this.authCode);
            req.setUserProductInfo(this.userProductInfo);
            this.reqID += 1;
            //调用api的登录前验证
            this.tdApi.ReqAuthenticate(req, this.reqID);
        }
    }


    // 发出日志
    private void writeLog(String content) {
        VtLogData log = new VtLogData();
        log.setGatewayName(this.gatewayName);
        log.setLogContent(content);
        this.gateway.onLog(log);
    }

    /**
     * @param
     * @return void
     * @Description 当客户端与交易托管系统建立起通信连接，客户端需要进行登录    连接前 所执行的 方法
     * @author gt_vv
     * @date 2019/11/25
     */
    @Override
    public void OnFrontConnected() {
        //连接后  才会调用此方法  更改连接状态
        this.connectionStatus = true;
        System.out.println("On Front Connected");
        if (this.requireAuthentication) {
            this.authenticate();
        } else {
            this.login();
        }
        System.out.println("Send ReqAuthenticate ok");
    }


    // 从写验证客户端的回报 验证客户端回报
    @Override
    public void OnRspAuthenticate(CThostFtdcRspAuthenticateField pRspAuthenticateField, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
        boolean isError = (pRspInfo != null) && (pRspInfo.getErrorID() != 0);
        if (!isError) {
            this.authStatus = true;
            //交易服务器验证
            this.writeLog(Text.TRADING_SERVER_AUTHENTICATED);
            this.login();
        } else {
            VtErrorData err = new VtErrorData();
            err.setGatewayName(this.gatewayName);
            err.setErrorID(pRspInfo.getErrorID()+"");
            err.setErrorMsg(pRspInfo.getErrorMsg());
            this.gateway.onError(err);
        }
    }



    // 登陆回报
    @Override
    public void OnRspUserLogin(CThostFtdcRspUserLoginField pRspUserLogin, CThostFtdcRspInfoField pRspInfo,
                               int nRequestID, boolean bIsLast) {
        boolean isError = (pRspInfo != null) && (pRspInfo.getErrorID() != 0);
        // 如果登录成功，推送日志信息
        if (!isError) {
            this.frontID = pRspUserLogin.getFrontID();
            this.sessionID = pRspUserLogin.getSessionID();
            this.loginStatus = true;

            this.gateway.setTdConnected(true);
            //交易服务器登录完成
            this.writeLog(Text.TRADING_SERVER_LOGIN);

            // 确认结算信息
            CThostFtdcSettlementInfoConfirmField req = new CThostFtdcSettlementInfoConfirmField();
            req.setBrokerID(this.brokerID);
            req.setInvestorID(this.userID);
            this.reqID += 1;
            this.tdApi.ReqSettlementInfoConfirm(req, this.reqID);
        }
        // 否则，推送错误信息
        else {
            VtErrorData err = new VtErrorData();
            err.setGatewayName(this.gatewayName);
            err.setErrorID(pRspInfo.getErrorID()+"");
            err.setErrorMsg(pRspInfo.getErrorMsg());
            this.gateway.onError(err);

            // 标识登录失败，防止用错误信息连续重复登录
            this.loginFailed = true;
        }
    }
}
