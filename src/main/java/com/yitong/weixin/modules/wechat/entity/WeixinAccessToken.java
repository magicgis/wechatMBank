/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yitong.weixin.modules.wechat.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.yitong.weixin.common.persistence.DataEntity;

/**
 * access_token配置Entity
 * @author hf
 * @version 2016-01-15
 */
public class WeixinAccessToken extends DataEntity<WeixinAccessToken> {
	
	private static final long serialVersionUID = 1L;
	private String acctOpenId;		// 微信号
	private String accessToken;		// access_token
	private Date expiresIn;		// 过期时间
	
	public WeixinAccessToken() {
		super();
	}

	public WeixinAccessToken(String id){
		super(id);
	}

	@Length(min=0, max=64, message="微信号长度必须介于 0 和 64 之间")
	public String getAcctOpenId() {
		return acctOpenId;
	}

	public void setAcctOpenId(String acctOpenId) {
		this.acctOpenId = acctOpenId;
	}
	
	@Length(min=0, max=64, message="access_token长度必须介于 0 和 64 之间")
	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Date expiresIn) {
		this.expiresIn = expiresIn;
	}
	
}