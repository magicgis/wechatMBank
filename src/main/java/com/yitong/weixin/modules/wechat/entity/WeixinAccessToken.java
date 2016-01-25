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
 * @version 2016-01-22
 */
public class WeixinAccessToken extends DataEntity<WeixinAccessToken> {
	
	private static final long serialVersionUID = 1L;
	private String acctOpenId;		// acct_open_id
	private String accessToken;		// access_token
	private String expriresIn;		// exprires_in
	private Date lastDate;		// last_date
	
	public WeixinAccessToken() {
		super();
	}

	public WeixinAccessToken(String id){
		super(id);
	}

	@Length(min=0, max=64, message="acct_open_id长度必须介于 0 和 64 之间")
	public String getAcctOpenId() {
		return acctOpenId;
	}

	public void setAcctOpenId(String acctOpenId) {
		this.acctOpenId = acctOpenId;
	}
	
	@Length(min=0, max=512, message="access_token长度必须介于 0 和 512 之间")
	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	@Length(min=0, max=16, message="exprires_in长度必须介于 0 和 16 之间")
	public String getExpriresIn() {
		return expriresIn;
	}

	public void setExpriresIn(String expriresIn) {
		this.expriresIn = expriresIn;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}
	
}