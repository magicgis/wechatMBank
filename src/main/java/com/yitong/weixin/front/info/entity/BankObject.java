/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.yitong.weixin.front.info.entity;


import com.yitong.weixin.common.persistence.DataEntity;


/**
 * 文章Entity
 * @author ThinkGem
 * @version 2013-05-15
 */
public class BankObject extends DataEntity<BankObject> {
	
	private static final long serialVersionUID = 1L;
	
	private long bankId;
	private String bankName;
	private String bankType;	
	private String atmType;	
	private String areaId;
	private String posX;
	private String posY;	
	private String telephone;
	private String address;	
	private String uptDate;	
	private String pushDate;	
	private String uptOper;	
	private String chkOper;	
	
	private String zipcode;	
	private String worktime;	

	public BankObject() {
		
	}
	
	public BankObject(long bankId, String bankName, String bankType,
			String atmType, String areaId, String posX, String posY,
			String telephone, String address, String uptDate, String pushDate,
			String uptOper, String chkOper, String zipcode, String worktime) {
		this.bankId = bankId;
		this.bankName = bankName;
		this.bankType = bankType;
		this.atmType = atmType;
		this.areaId = areaId;
		this.posX = posX;
		this.posY = posY;
		this.telephone = telephone;
		this.address = address;
		this.uptDate = uptDate;
		this.pushDate = pushDate;
		this.uptOper = uptOper;
		this.chkOper = chkOper;
		this.zipcode = zipcode;
		this.worktime = worktime;
	}


	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getWorktime() {
		return worktime;
	}

	public void setWorktime(String worktime) {
		this.worktime = worktime;
	}

	public long getBankId() {
		return bankId;
	}

	public void setBankId(long bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public String getAtmType() {
		return atmType;
	}

	public void setAtmType(String atmType) {
		this.atmType = atmType;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getPosX() {
		return posX;
	}

	public void setPosX(String posX) {
		this.posX = posX;
	}

	public String getPosY() {
		return posY;
	}

	public void setPosY(String posY) {
		this.posY = posY;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUptDate() {
		return uptDate;
	}

	public void setUptDate(String uptDate) {
		this.uptDate = uptDate;
	}

	public String getPushDate() {
		return pushDate;
	}

	public void setPushDate(String pushDate) {
		this.pushDate = pushDate;
	}

	public String getUptOper() {
		return uptOper;
	}

	public void setUptOper(String uptOper) {
		this.uptOper = uptOper;
	}

	public String getChkOper() {
		return chkOper;
	}

	public void setChkOper(String chkOper) {
		this.chkOper = chkOper;
	}
	
}


