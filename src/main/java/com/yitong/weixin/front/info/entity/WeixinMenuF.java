/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yitong.weixin.front.info.entity;

import org.hibernate.validator.constraints.Length;

import com.yitong.weixin.common.persistence.DataEntity;

/**
 * 微信相关菜单配置Entity
 * @author fwy
 * @version 2015-12-30
 */
public class WeixinMenuF extends DataEntity<WeixinMenuF> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 功能编码
	private String name;		// 菜单名称
	private String acctOpenId;		// 微信公众号id
	private String type;		// 菜单的响应动作类型，目前有 0-click、1-view两种类型
	private String url;		// 连接地址
	private String pid;		// 父类菜单Id
	private String isHide;		// 0：不隐藏，1：隐藏
	private String orderN;		// 显示顺序
	
	public WeixinMenuF() {
		super();
	}

	public WeixinMenuF(String id){
		super(id);
	}
	
	@Override
	public void preInsert(){
		super.preInsert();
//		setAcctOpenId(WeixinAcctUtils.getOpenId());
	}
	
	@Override
	public void preUpdate(){
		super.preUpdate();
//		setAcctOpenId(WeixinAcctUtils.getOpenId());
	}

	@Length(min=0, max=32, message="功能编码长度必须介于 0 和 32 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=1, max=64, message="菜单名称长度必须介于 1 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=64, message="微信公众号id长度必须介于 0 和 64 之间")
	public String getAcctOpenId() {
		return acctOpenId;
	}

	public void setAcctOpenId(String acctOpenId) {
		this.acctOpenId = acctOpenId;
	}
	
	@Length(min=0, max=1, message="菜单的响应动作类型，目前有 0-click、1-view两种类型长度必须介于 0 和 1 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=256, message="连接地址长度必须介于 0 和 256 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Length(min=0, max=64, message="父类菜单Id长度必须介于 0 和 64 之间")
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
	
	@Length(min=0, max=1, message="0：不隐藏，1：隐藏长度必须介于 0 和 1 之间")
	public String getIsHide() {
		return isHide;
	}

	public void setIsHide(String isHide) {
		this.isHide = isHide;
	}
	
	@Length(min=0, max=8, message="显示顺序长度必须介于 0 和 8 之间")
	public String getOrderN() {
		return orderN;
	}

	public void setOrderN(String orderN) {
		this.orderN = orderN;
	}
	
}