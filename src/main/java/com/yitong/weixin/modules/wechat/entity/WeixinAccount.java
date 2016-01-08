/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yitong.weixin.modules.wechat.entity;

import org.hibernate.validator.constraints.Length;
import com.yitong.weixin.modules.sys.entity.User;

import com.yitong.weixin.common.persistence.DataEntity;

/**
 * 多公众号的维护Entity
 * @author fwy
 * @version 2015-12-29
 */
public class WeixinAccount extends DataEntity<WeixinAccount> {
	
	private static final long serialVersionUID = 1L;
	private String acctName;		// 公众号名
	private String acctOpenId;		// 公众号OpenId
	private String appId;		// app_id
	private String appSercet;		// app_sercet
	private String token;		// token
	private String aeskey;		// aeskey
	private User user;		// 用户名
	
	public WeixinAccount() {
		super();
	}

	public WeixinAccount(String id){
		super(id);
	}

	@Length(min=0, max=255, message="公众号名长度必须介于 0 和 255 之间")
	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}
	
	@Length(min=0, max=64, message="公众号OpenId长度必须介于 0 和 64 之间")
	public String getAcctOpenId() {
		return acctOpenId;
	}

	public void setAcctOpenId(String acctOpenId) {
		this.acctOpenId = acctOpenId;
	}
	
	@Length(min=0, max=64, message="app_id长度必须介于 0 和 64 之间")
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	@Length(min=0, max=64, message="app_sercet长度必须介于 0 和 64 之间")
	public String getAppSercet() {
		return appSercet;
	}

	public void setAppSercet(String appSercet) {
		this.appSercet = appSercet;
	}
	
	@Length(min=0, max=64, message="token长度必须介于 0 和 64 之间")
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	@Length(min=0, max=64, message="aeskey长度必须介于 0 和 64 之间")
	public String getAeskey() {
		return aeskey;
	}

	public void setAeskey(String aeskey) {
		this.aeskey = aeskey;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}