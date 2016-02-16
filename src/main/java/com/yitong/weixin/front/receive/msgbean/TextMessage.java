package com.yitong.weixin.front.receive.msgbean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author fang.wenyu
 * 微信接收文本消息
 */
@XmlRootElement(name="xml")
public class TextMessage {

	/**
	 * 开发者微信号
	 */
	private String ToUserName;
	
	/**
	 * 发送方帐号（一个OpenID）
	 */
	private String FromUserName;
	
	/**
	 * 消息创建时间 （整型）
	 */
	private String CreateTime;
	
	/**
	 * text
	 */
	private String MsgType;
	
	/**
	 * 文本消息内容
	 */
	private String Content;
	
	/**
	 * 消息id，64位整型
	 */
	private String MsgId;

	@XmlElement(name="ToUserName")
	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	@XmlElement(name="FromUserName")
	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	@XmlElement(name="CreateTime")
	public String getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}

	@XmlElement(name="MsgType")
	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	@XmlElement(name="Content")
	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	@XmlElement(name="MsgId")
	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String msgId) {
		MsgId = msgId;
	}
}
