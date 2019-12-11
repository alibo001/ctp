package com.nbplus.vnpy.gateway.ctpGateway;

import com.nbplus.vnpy.trader.VtPositionData;
import ctp.thosttraderapi.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * @Author gongtan
 * @Date 2019/12/11 14:39
 * @Version 1.0
 **/
public class tdSpi_CTP extends CThostFtdcTraderSpi {

    private final static Logger logger = LoggerFactory.getLogger(tdSpi_CTP.class);
    private String logInfo;

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

    private String tradingDay;//交易日

    //撤单所需变量
    private int frontID; // 前置机编号
    private int sessionID; // 会话编号

    private Map<String, VtPositionData> posDict;
    private Map<String, String> symbolExchangeDict; // 保存合约代码和交易所的印射关系
    private Map<String, Integer> symbolSizeDict; // 保存合约代码和合约大小的印射关系

    private boolean requireAuthentication;

    private CThostFtdcTraderApi tdApi;

    /**
     * @Description  创建单例时 初始化
     * @author gt_vv
     * @date 2019/12/11
     * @param ctpGateway
     * @return
     */
    public tdSpi_CTP(CtpGateway gateway){
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
            //创建  tdApi（单例）
            this.tdApi = CThostFtdcTraderApi.CreateFtdcTraderApi(path.getPath()+System.getProperty("file.separator"));
            //注册一事件处理的实例    参数：实现了 CThostFtdcTraderSpi 接口的实例指针
            this.tdApi.RegisterSpi(this);

            // 设置数据同步模式为推送从今日开始所有数据
            this.tdApi.SubscribePublicTopic(THOST_TE_RESUME_TYPE.THOST_TERT_RESTART);
            this.tdApi.SubscribePrivateTopic(THOST_TE_RESUME_TYPE.THOST_TERT_RESTART);

            // 注册服务器地址
            this.tdApi.RegisterFront(this.address);

            // 初始化连接，成功会调用onFrontConnected
            this.tdApi.Init();
            this.tdApi.Join();
        }
        // 若已经连接但尚未登录，则进行登录
        else {
            //是否需要验证和验证状态
            if (this.requireAuthentication && !this.authStatus) {
                this.authenticate();
            } else if (!this.loginStatus) {
                this.login();
            }
        }
    }

    // 连接服务器
    private void login() {
        // 如果之前有过登录失败，则不再进行尝试
        if (this.loginFailed){
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
            //ReqUserLogin调用后ctp执行前置机连接
            this.tdApi.ReqUserLogin(req, this.reqID);
        }
    }


    /**
     * @Description 申请验证
     * @author gt_vv
     * @date 2019/12/11
     * @param
     * @return void
     */
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
            logger.warn("交易接口申请验证");
            System.out.println("OnRspAuthenticate success!!!");
            int i = this.tdApi.ReqAuthenticate(req, this.reqID);
            logger.warn("交易接口发送登录成功");
        }
    }

    /**
     * @Description 前置机联机回报
     * @author gt_vv
     * @date 2019/12/11
     * @param
     * @return void
     */
    @Override
    public void OnFrontConnected() {
        try {
            logger.warn("{}交易接口前置机已连接", logInfo);
            // 修改前置机连接状态
            connectionStatus = true;
            //调用认证
            this.authenticate();
        } catch (Throwable t) {
            logger.error("{}OnFrontConnected Exception", logInfo, t);
        }
    }



    /**
     * @Description 验证客户端回报
     * @author gt_vv
     * @date 2019/12/11
     * @param pRspAuthenticateField
     * @param pRspInfo
     * @param nRequestID
     * @param bIsLast
     * @return void
     */
    @Override
    public void OnRspAuthenticate(CThostFtdcRspAuthenticateField pRspAuthenticateField, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
        try {
            if (pRspInfo != null) {
                if (pRspInfo.getErrorID() == 0) {
                    logger.warn(logInfo + "交易接口客户端验证成功");
                    CThostFtdcReqUserLoginField reqUserLoginField = new CThostFtdcReqUserLoginField();
                    reqUserLoginField.setBrokerID(this.brokerID);
                    reqUserLoginField.setUserID(this.userID);
                    reqUserLoginField.setPassword(this.password);
                    tdApi.ReqUserLogin(reqUserLoginField, reqID);
                } else {
                    logger.error("{}交易接口客户端验证失败 错误ID:{},错误信息:{}", logInfo, pRspInfo.getErrorID(), pRspInfo.getErrorMsg());
                    loginFailed = true;
                }
            } else {
                loginFailed = true;
                logger.error("{}处理交易接口客户端验证回报错误,回报信息为空", logInfo);
            }
        } catch (Throwable t) {
            loginFailed = true;
            logger.error("{}处理交易接口客户端验证回报异常", logInfo, t);
        }
    }

    /**
     * @Description  登录回报
     * @author gt_vv
     * @date 2019/12/11
     * @param pRspUserLogin
     * @param pRspInfo
     * @param nRequestID
     * @param bIsLast
     * @return void
     */
    @Override
    public void OnRspUserLogin(CThostFtdcRspUserLoginField pRspUserLogin, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
        try {
            if (pRspInfo.getErrorID() == 0) {
                logger.warn("{}交易接口登录成功 TradingDay:{},SessionID:{},BrokerID:{},UserId:{}", logInfo, pRspUserLogin.getTradingDay(), pRspUserLogin.getSessionID(),
                        pRspUserLogin.getBrokerID(), pRspUserLogin.getUserID());
                sessionID = pRspUserLogin.getSessionID();
                frontID = pRspUserLogin.getFrontID();
                // 修改登录状态为true
                loginStatus = true;
                tradingDay = pRspUserLogin.getTradingDay();
                logger.warn("{}交易接口获取到的交易日为{}", logInfo, tradingDay);

                // 确认结算单
                CThostFtdcSettlementInfoConfirmField settlementInfoConfirmField = new CThostFtdcSettlementInfoConfirmField();
                settlementInfoConfirmField.setBrokerID(brokerID);
                settlementInfoConfirmField.setInvestorID(userID);
                tdApi.ReqSettlementInfoConfirm(settlementInfoConfirmField, reqID);

            } else {
                logger.error("{}交易接口登录回报错误 错误ID:{},错误信息:{}", logInfo, pRspInfo.getErrorID(), pRspInfo.getErrorMsg());
                loginFailed = true;
            }
        } catch (Throwable t) {
            logger.error("{}交易接口处理登录回报异常", logInfo, t);
            loginFailed = true;
        }

    }
}
