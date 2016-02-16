/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.yitong.weixin.front.info.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yitong.weixin.common.service.CrudService;
import com.yitong.weixin.front.info.dao.SendTemplateLogsDao;
import com.yitong.weixin.front.info.entity.SendTemplateLogs;


/**
 * 文章Service
 * @author ThinkGem
 * @version 2013-05-15
 */
@Service
@Transactional(readOnly = true)
public class SendTemplateLogsService extends CrudService<SendTemplateLogsDao, SendTemplateLogs> {

	public SendTemplateLogs findById(Long id){
		return null;
	}
	
	@Transactional(readOnly = false)
	public void updateStatus(String msgId, String status) {
	}
	
}
