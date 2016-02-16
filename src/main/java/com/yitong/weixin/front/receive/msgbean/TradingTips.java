package com.yitong.weixin.front.receive.msgbean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author he.huang
 * socket接收文本消息
 */
@XmlRootElement(name="xml")
public class TradingTips {
	
	/*
	您好，您的信用卡收到1000元汇款。
	帐号类型：信用卡
	尾号：1758 4545
	交易时间：2013年9月30日 17:58
	交易类型：收款
	交易金额：1000元
	备注：如有疑问，请拨打咨询热线123323。
	*/
	private String msgType;		//发送类型
	private String custNum;		//客户号
	private String AccNo;		//卡号
	private String AccType;  	//卡类型
	private String tranDate;	//交易时间
	private String tranType;	//交易类型
	private String transAmount;	//交易金额
	
	public TradingTips() {
		
	}
	
	public TradingTips(String msgType, String custNum, String accNo, String accType,
			String tranDate, String tranType, String transAmount) {
		super();
		this.msgType = msgType;
		this.custNum = custNum;
		AccNo = accNo;
		AccType = accType;
		this.tranDate = tranDate;
		this.tranType = tranType;
		this.transAmount = transAmount;
	}

	@XmlElement(name="msgType")
	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	@XmlElement(name="custNum")
	public String getCustNum() {
		return custNum;
	}
	public void setCustNum(String custNum) {
		this.custNum = custNum;
	}

	@XmlElement(name="accNo")
	public String getAccNo() {
		return AccNo;
	}
	public void setAccNo(String accNo) {
		AccNo = accNo;
	}
	@XmlElement(name="accType")
	public String getAccType() {
		return AccType;
	}
	public void setAccType(String accType) {
		AccType = accType;
	}
	@XmlElement(name="tranDate")
	public String getTranDate() {
		return tranDate;
	}
	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}
	@XmlElement(name="tranType")
	public String getTranType() {
		return tranType;
	}
	public void setTranType(String tranType) {
		this.tranType = tranType;
	}
	@XmlElement(name="transAmount")
	public String getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(String transAmount) {
		this.transAmount = transAmount;
	}
}


