package com.yitong.weixin.front.info.entity;

/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
import org.hibernate.validator.constraints.Length;

import com.yitong.weixin.common.persistence.DataEntity;

/**
 * session参数表Entity
 * @author hf
 * @version 2016-01-26
 */
public class WeiXinSessionPara extends DataEntity<WeiXinSessionPara> {
	
	private static final long serialVersionUID = 1L;
	private String openId;		// 微信用户OPENID
	private int menuNo;		// 菜单项编号
	private String key;		// 下一个菜单项编号或变量ID
	private String value;		// 菜单名称或变量取值
	private String type;		// 类型
	
	public WeiXinSessionPara() {
		super();
	}

	public WeiXinSessionPara(String id){
		super(id);
	}

	@Length(min=0, max=64, message="微信用户OPENID长度必须介于 0 和 64 之间")
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	public int getMenuNo() {
		return menuNo;
	}

	public void setMenuNo(int menuNo) {
		this.menuNo = menuNo;
	}
	
	@Length(min=0, max=128, message="下一个菜单项编号或变量ID长度必须介于 0 和 128 之间")
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	@Length(min=0, max=256, message="菜单名称或变量取值长度必须介于 0 和 256 之间")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Length(min=0, max=8, message="类型长度必须介于 0 和 8 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}