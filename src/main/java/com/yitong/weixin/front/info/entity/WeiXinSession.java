package com.yitong.weixin.front.info.entity;

/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
import org.hibernate.validator.constraints.Length;

import com.yitong.weixin.common.persistence.DataEntity;

/**
 * session存储表Entity
 * @author hf
 * @version 2016-01-26
 */
public class WeiXinSession extends DataEntity<WeiXinSession> {
	
	private static final long serialVersionUID = 1L;
	private String openId;		// open_id
	private int lastMenuNo;		// 上次选中的菜单项编号
	private int nextMenuNo;		// 下一个菜单项编号
	private String lastVisitTime;		// 上次访问时间
	private String lastMsgid;		// 上次访问时的消息ID
	private int errorNum;		// 错误次数
	private String msgId;		// msg_id
	private String msgType;		// msg_type
	private String msgResult;		// msg_result
	private String msgStatus;		// msg_status
	
	public WeiXinSession() {
		super();
	}

	public WeiXinSession(String id){
		super(id);
	}

	@Length(min=0, max=64, message="open_id长度必须介于 0 和 64 之间")
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	public int getLastMenuNo() {
		return lastMenuNo;
	}

	public void setLastMenuNo(int lastMenuNo) {
		this.lastMenuNo = lastMenuNo;
	}
	
	public int getNextMenuNo() {
		return nextMenuNo;
	}

	public void setNextMenuNo(int nextMenuNo) {
		this.nextMenuNo = nextMenuNo;
	}
	
	@Length(min=0, max=64, message="上次访问时间长度必须介于 0 和 64 之间")
	public String getLastVisitTime() {
		return lastVisitTime;
	}

	public void setLastVisitTime(String lastVisitTime) {
		this.lastVisitTime = lastVisitTime;
	}
	
	@Length(min=0, max=64, message="上次访问时的消息ID长度必须介于 0 和 64 之间")
	public String getLastMsgid() {
		return lastMsgid;
	}

	public void setLastMsgid(String lastMsgid) {
		this.lastMsgid = lastMsgid;
	}
	
	public int getErrorNum() {
		return errorNum;
	}

	public void setErrorNum(int errorNum) {
		this.errorNum = errorNum;
	}
	
	@Length(min=0, max=64, message="msg_id长度必须介于 0 和 64 之间")
	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
	@Length(min=0, max=16, message="msg_type长度必须介于 0 和 16 之间")
	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	@Length(min=0, max=2048, message="msg_result长度必须介于 0 和 2048 之间")
	public String getMsgResult() {
		return msgResult;
	}

	public void setMsgResult(String msgResult) {
		this.msgResult = msgResult;
	}
	
	@Length(min=0, max=8, message="msg_status长度必须介于 0 和 8 之间")
	public String getMsgStatus() {
		return msgStatus;
	}

	public void setMsgStatus(String msgStatus) {
		this.msgStatus = msgStatus;
	}
	
}