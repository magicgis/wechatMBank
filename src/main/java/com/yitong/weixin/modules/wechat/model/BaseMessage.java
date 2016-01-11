/**  
* @Title: BaseMessage.java
* @Package com.yitong.weixin.bean
* @Description: TODO
* @author ZhangPeiZhong
* @date 2014年4月25日 下午3:49:07
* @version V1.0  
*/ 
package com.yitong.weixin.modules.wechat.model;

import java.util.Calendar;

import javax.xml.bind.annotation.XmlElement;

/**
 * @ClassName: BaseMessage
 * @Description: 基本消息
 * @author ZhangPeiZhong
 * @date 2014年4月25日 下午3:49:07
 *
 */

public class BaseMessage {
	protected String fromUserName;	//发送方帐号（一个OpenID）
	protected String toUserName; 		//开发者微信号
	protected String createTime;		//消息创建时间 （整型）
	protected String msgType;
	
	public BaseMessage(){}
	public BaseMessage(String fromUserName, String toUserName, String msgType) {
		super();
		this.fromUserName = fromUserName;
		this.toUserName = toUserName;
		this.msgType = msgType;
		this.createTime = String.valueOf(Calendar.getInstance().getTime().getTime());
	}
	@XmlElement(name="FromUserName")
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	@XmlElement(name="ToUserName")
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	@XmlElement(name="CreateTime")
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@XmlElement(name="MsgType")
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	

}
