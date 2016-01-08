/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yitong.weixin.modules.wechat.entity;

import org.hibernate.validator.constraints.Length;

import com.yitong.weixin.common.persistence.DataEntity;
import com.yitong.weixin.modules.wechat.model.UserInfo;
import com.yitong.weixin.modules.wechat.utils.AcctUtils;

/**
 * 微信用户Entity
 * @author Fwy
 * @version 2015-12-31
 */
public class WeixinUser extends DataEntity<WeixinUser> {
	
	private static final long serialVersionUID = 1L;
	private String openId;		// 微信用户OPENID
	private String userName;		// 微信用户昵称
	private String acctOpenId;		// acct_open_id
	private WeixinGroup group;		// 所属类别
	private String lastActiveTime;		// 上次访问时间
	private String sex;		// 用户的性别，1-男性，2-女性，0-未知
	private String city;		// 所在城市
	private String country;		// 所在国家
	private String province;		// 所在省份
	private String headImgUrl;		// 微信用户图像
	private String subscribeTime;		// 订阅服务号的时间
	private String cancelSubscribeTime;		// 取消订阅服务号的时间
	private String bankcardId1;		// 是否绑定银行卡  1、已绑定 0、未绑定
	private String bankcardId2;		// 绑定银行卡号2
	private String bankcardId3;		// 绑定银行卡号3
	private String custId;		// 客户标识
	private String unionId;		// 微信用户 unionid
	
	public WeixinUser() {
		super();
	}

	public WeixinUser(String id){
		super(id);
	}
	
	public WeixinUser(UserInfo userInfo){
		super();
		openId = userInfo.getOpenid();
		userName = userInfo.getNickname();
		acctOpenId = AcctUtils.getOpenId();
		group = new WeixinGroup(userInfo.getGroupid());
		sex = userInfo.getSex();
		city = userInfo.getCity();
		country = userInfo.getCountry();
		province = userInfo.getProvince();
		headImgUrl = userInfo.getHeadimgurl();
		subscribeTime = userInfo.getSubscribeTime();
		unionId = userInfo.getUnionid();
	}
	
	public void updateWeixinUser(UserInfo userInfo){
		openId = userInfo.getOpenid();
		userName = userInfo.getNickname();
		acctOpenId = AcctUtils.getOpenId();
		group = new WeixinGroup(userInfo.getGroupid());
		sex = userInfo.getSex();
		city = userInfo.getCity();
		country = userInfo.getCountry();
		province = userInfo.getProvince();
		headImgUrl = userInfo.getHeadimgurl();
		subscribeTime = userInfo.getSubscribeTime();
		unionId = userInfo.getUnionid();
	}

	@Length(min=0, max=64, message="微信用户OPENID长度必须介于 0 和 64 之间")
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	@Length(min=0, max=255, message="微信用户昵称长度必须介于 0 和 255 之间")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Length(min=0, max=64, message="acct_open_id长度必须介于 0 和 64 之间")
	public String getAcctOpenId() {
		return acctOpenId;
	}

	public void setAcctOpenId(String acctOpenId) {
		this.acctOpenId = acctOpenId;
	}
	
	public WeixinGroup getGroup() {
		return group;
	}
	public void setGroup(WeixinGroup group) {
		this.group = group;
	}
	@Length(min=0, max=64, message="上次访问时间长度必须介于 0 和 64 之间")
	public String getLastActiveTime() {
		return lastActiveTime;
	}

	public void setLastActiveTime(String lastActiveTime) {
		this.lastActiveTime = lastActiveTime;
	}
	
	@Length(min=0, max=8, message="用户的性别，1-男性，2-女性，0-未知长度必须介于 0 和 8 之间")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Length(min=0, max=64, message="所在城市长度必须介于 0 和 64 之间")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Length(min=0, max=64, message="所在国家长度必须介于 0 和 64 之间")
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	@Length(min=0, max=64, message="所在省份长度必须介于 0 和 64 之间")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@Length(min=0, max=512, message="微信用户图像长度必须介于 0 和 512 之间")
	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	
	@Length(min=0, max=64, message="订阅服务号的时间长度必须介于 0 和 64 之间")
	public String getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(String subscribeTime) {
		this.subscribeTime = subscribeTime;
	}
	
	@Length(min=0, max=64, message="取消订阅服务号的时间长度必须介于 0 和 64 之间")
	public String getCancelSubscribeTime() {
		return cancelSubscribeTime;
	}

	public void setCancelSubscribeTime(String cancelSubscribeTime) {
		this.cancelSubscribeTime = cancelSubscribeTime;
	}
	
	@Length(min=0, max=64, message="是否绑定银行卡  1、已绑定 0、未绑定长度必须介于 0 和 64 之间")
	public String getBankcardId1() {
		return bankcardId1;
	}

	public void setBankcardId1(String bankcardId1) {
		this.bankcardId1 = bankcardId1;
	}
	
	@Length(min=0, max=64, message="绑定银行卡号2长度必须介于 0 和 64 之间")
	public String getBankcardId2() {
		return bankcardId2;
	}

	public void setBankcardId2(String bankcardId2) {
		this.bankcardId2 = bankcardId2;
	}
	
	@Length(min=0, max=64, message="绑定银行卡号3长度必须介于 0 和 64 之间")
	public String getBankcardId3() {
		return bankcardId3;
	}

	public void setBankcardId3(String bankcardId3) {
		this.bankcardId3 = bankcardId3;
	}
	
	@Length(min=0, max=255, message="客户标识长度必须介于 0 和 255 之间")
	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}
	
	@Length(min=0, max=64, message="微信用户 unionid长度必须介于 0 和 64 之间")
	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
	
}