/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.yitong.weixin.front.info.entity;

import com.yitong.weixin.common.persistence.DataEntity;


/**
 * 文章Entity
 * 
 * @author ThinkGem
 * @version 2013-05-15
 */
public class SendTemplateLogs extends DataEntity<SendTemplateLogs>{
	public SendTemplateLogs(String msgId, String openId, String custId, String status,
			String createDate, String createBy) {
		super();
	}
}
