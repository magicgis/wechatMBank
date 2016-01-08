/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yitong.weixin.modules.wechat.entity;

import org.hibernate.validator.constraints.Length;

import com.yitong.weixin.common.persistence.DataEntity;

/**
 * 微信菜单规则Entity
 * @author Fwy
 * @version 2016-01-06
 */
public class WeixinMenuList extends DataEntity<WeixinMenuList> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 功能编码
	private String name;		// 列表功能名称
	private String superCode;		// 所属上级功能编码
	private String orderN;		// 显示顺序
	private String actionType;		// 响应类型，0-显示子功能列表，1-显示消息内容，2-调用接口，3-外部链接
	private String articleId;		// 应答消息id
	private String url;		// 网页链接
	private String isHide;		// 是否隐藏
	private String message;		// 选中后显示的消息头
	private String msgType;		// 消息类型（0：文本；1：图文；2：自定义）
	private String msgClass;		// 自定义消息处理类，msgType=2 使用此类
	private Long isBinded;		// 绑定检查，0：不检查，1：检查
	private String keywords;		// keywords
	private String acctOpenId;
	
	public WeixinMenuList() {
		super();
	}

	public WeixinMenuList(String id){
		super(id);
	}

	@Length(min=0, max=64, message="功能编码长度必须介于 0 和 64 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=256, message="列表功能名称长度必须介于 0 和 256 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=32, message="所属上级功能编码长度必须介于 0 和 32 之间")
	public String getSuperCode() {
		return superCode;
	}

	public void setSuperCode(String superCode) {
		this.superCode = superCode;
	}
	
	@Length(min=0, max=8, message="显示顺序长度必须介于 0 和 8 之间")
	public String getOrderN() {
		return orderN;
	}

	public void setOrderN(String orderN) {
		this.orderN = orderN;
	}
	
	@Length(min=0, max=1, message="响应类型，0-显示子功能列表，1-显示消息内容，2-调用接口，3-外部链接长度必须介于 0 和 1 之间")
	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	
	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	
	@Length(min=0, max=256, message="网页链接长度必须介于 0 和 256 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Length(min=0, max=1, message="是否隐藏长度必须介于 0 和 1 之间")
	public String getIsHide() {
		return isHide;
	}

	public void setIsHide(String isHide) {
		this.isHide = isHide;
	}
	
	@Length(min=0, max=1024, message="选中后显示的消息头长度必须介于 0 和 1024 之间")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Length(min=0, max=16, message="消息类型（0：文本；1：图文；2：自定义）长度必须介于 0 和 16 之间")
	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	@Length(min=0, max=32, message="自定义消息处理类，msgType=2 使用此类长度必须介于 0 和 32 之间")
	public String getMsgClass() {
		return msgClass;
	}

	public void setMsgClass(String msgClass) {
		this.msgClass = msgClass;
	}
	
	public Long getIsBinded() {
		return isBinded;
	}

	public void setIsBinded(Long isBinded) {
		this.isBinded = isBinded;
	}
	
	@Length(min=0, max=128, message="keywords长度必须介于 0 和 128 之间")
	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getAcctOpenId() {
		return acctOpenId;
	}

	public void setAcctOpenId(String acctOpenId) {
		this.acctOpenId = acctOpenId;
	}
}