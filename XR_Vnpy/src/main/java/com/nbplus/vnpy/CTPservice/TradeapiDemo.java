package com.nbplus.vnpy.CTPservice;

import ctp.thosttraderapi.*;

;

class TraderSpiImpl extends CThostFtdcTraderSpi {
	//final static String m_BrokerId = "9999";
	//final static String m_UserId = "070624";
	final static String m_BrokerId = "9999";
	final static String m_UserId = "110208";
	final static String m_PassWord = "thorp";
	final static String m_InvestorId = "110208";
	final static String m_TradingDay = "20191122";
	final static String m_AccountId = "110208";
	final static String m_CurrencyId = "CNY";
	final static String m_AppId = "simnow_client_test";
	final static String m_AuthCode = "0000000000000000";
	TraderSpiImpl(CThostFtdcTraderApi traderapi)
	{
		m_traderapi =  traderapi;
	}
	
	@Override
	public void OnFrontConnected(){
		System.out.println("On Front Connected");
		CThostFtdcReqAuthenticateField field = new CThostFtdcReqAuthenticateField();
		field.setBrokerID(m_BrokerId);
		field.setUserID(m_UserId);
		field.setAppID(m_AppId);
		field.setAuthCode(m_AuthCode);
    	m_traderapi.ReqAuthenticate(field, 0);
		System.out.println("Send ReqAuthenticate ok");
	}
	
	public void OnFrontDisconnected(int nReason) {
		System.out.printf("OnFrontDisconnected nReason[%d]\n", nReason);
	};
	
	@Override
	public void OnRspAuthenticate(CThostFtdcRspAuthenticateField pRspAuthenticateField, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast)
	{
		System.out.println(pRspInfo.getErrorID());
		if (pRspInfo != null && pRspInfo.getErrorID() != 0)
        {
             System.out.printf("Login ErrorID[%d] ErrMsg[%s]\n", pRspInfo.getErrorID(), pRspInfo.getErrorMsg());

             return;
        }
        System.out.println("OnRspAuthenticate success!!!");
		CThostFtdcReqUserLoginField field = new CThostFtdcReqUserLoginField();
        field.setBrokerID(m_BrokerId);
        field.setUserID(m_UserId);
        field.setPassword(m_PassWord);
        m_traderapi.ReqUserLogin(field,0);
        System.out.println("Send login ok");
	}
	
	@Override
	public void OnRspUserLogin(CThostFtdcRspUserLoginField pRspUserLogin, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast)
	{
		if (pRspInfo != null && pRspInfo.getErrorID() != 0)
		{
			System.out.printf("Login ErrorID[%d] ErrMsg[%s]\n", pRspInfo.getErrorID(), pRspInfo.getErrorMsg());

			return;
		}
		System.out.println("Login success!!!");
		CThostFtdcQryTradingAccountField qryTradingAccount = new CThostFtdcQryTradingAccountField();
		qryTradingAccount.setBrokerID(m_BrokerId);
		qryTradingAccount.setCurrencyID(m_CurrencyId);
		qryTradingAccount.setInvestorID(m_InvestorId);
		//m_traderapi.ReqQryTradingAccount(qryTradingAccount, 1);

		CThostFtdcQrySettlementInfoField qrysettlement = new CThostFtdcQrySettlementInfoField();
		qrysettlement.setBrokerID(m_BrokerId);
		qrysettlement.setInvestorID(m_InvestorId);
		qrysettlement.setTradingDay(m_TradingDay);
		qrysettlement.setAccountID(m_AccountId);
		qrysettlement.setCurrencyID(m_CurrencyId);
		m_traderapi.ReqQrySettlementInfo(qrysettlement, 2);
		System.out.println("Query success!!!");
	}

	//请求查询资金账户响应。当客户端发出请求查询资金账户指令后，交易托管 系统返回响应时，该方法会被调用。
	@Override
	public void OnRspQryTradingAccount(CThostFtdcTradingAccountField pTradingAccount, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast)
	{
		if (pRspInfo != null && pRspInfo.getErrorID() != 0)
		{
			System.out.printf("OnRspQryTradingAccount ErrorID[%d] ErrMsg[%s]\n", pRspInfo.getErrorID(), pRspInfo.getErrorMsg());

			return;
		}
		
		if (pTradingAccount != null)
		{
			System.out.printf("Balance[%f]Available[%f]WithdrawQuota[%f]Credit[%f]\n",
				pTradingAccount.getBalance(), pTradingAccount.getAvailable(), pTradingAccount.getWithdrawQuota(),
				pTradingAccount.getCredit());
		}
		else
		{
			System.out.printf("NULL obj\n");
		}
	}

	//请求查询投资者结算结果响应。当客户端发出请求查询投资者结算结果指令 后，交易托管系统返回响应时，该方法会被调用
	public void  OnRspQrySettlementInfo(CThostFtdcSettlementInfoField pSettlementInfo, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast)
	{
		if (pRspInfo != null && pRspInfo.getErrorID() != 0)
		{
			System.out.printf("OnRspQrySettlementInfo ErrorID[%d] ErrMsg[%s]\n", pRspInfo.getErrorID(), pRspInfo.getErrorMsg());

			return;
		}
		
		if (pSettlementInfo != null)
		{
			System.out.printf("%s",pSettlementInfo.getContent());
		}
		else
		{
			System.out.printf("NULL obj\n");
		}
	}
	
	private CThostFtdcTraderApi m_traderapi;
}

public class TradeapiDemo {

	static{
		System.loadLibrary("thosttraderapi_se");
		System.loadLibrary("thosttraderapi_wrap");
	}
	final static String ctp1_TradeAddress = "tcp://180.168.146.187:10130";
	//final static String ctp1_TradeAddress = "tcp://172.24.125.199:50233";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println(System.getProperty("java.library.path"));
		CThostFtdcTraderApi traderApi = CThostFtdcTraderApi.CreateFtdcTraderApi("td");
		TraderSpiImpl pTraderSpi = new TraderSpiImpl(traderApi);
		traderApi.RegisterSpi(pTraderSpi);
		traderApi.RegisterFront("tcp://127.0.0.1:17001");
		traderApi.SubscribePublicTopic(THOST_TE_RESUME_TYPE.THOST_TERT_RESTART);
		traderApi.SubscribePrivateTopic(THOST_TE_RESUME_TYPE.THOST_TERT_RESTART);
		traderApi.Init();
		traderApi.Join();
		return;
	}
}