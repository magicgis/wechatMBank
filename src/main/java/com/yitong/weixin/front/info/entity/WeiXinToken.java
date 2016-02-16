/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.yitong.weixin.front.info.entity;

import java.io.Serializable;


import com.yitong.weixin.common.persistence.DataEntity;

/**
 * 文章Entity
 * 
 * @author ThinkGem
 * @version 2013-05-15
 */
public class WeiXinToken extends DataEntity<WeiXinToken> {

	private static final long serialVersionUID = 1L;

	private long lineId;
	private String openId;
	private String levelId;
	private String token;
	private String status; // 状态
//	private String createDate; // 创建时间
//	private String createBy; // 创建者

	public WeiXinToken() {

	}

	public long getLineId() {
		return lineId;
	}

	public void setLineId(long lineId) {
		this.lineId = lineId;
	}

	public String getLevelId() {
		return levelId;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


}