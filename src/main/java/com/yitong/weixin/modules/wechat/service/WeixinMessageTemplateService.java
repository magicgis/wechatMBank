/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yitong.weixin.modules.wechat.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yitong.weixin.common.persistence.Page;
import com.yitong.weixin.common.service.CrudService;
import com.yitong.weixin.modules.wechat.entity.WeixinMessageTemplate;
import com.yitong.weixin.modules.wechat.dao.WeixinMessageTemplateDao;

/**
 * 微信模板消息存储表Service
 * @author hf
 * @version 2016-01-18
 */
@Service
@Transactional(readOnly = true)
public class WeixinMessageTemplateService extends CrudService<WeixinMessageTemplateDao, WeixinMessageTemplate> {

	public WeixinMessageTemplate get(String id) {
		return super.get(id);
	}
	
	public List<WeixinMessageTemplate> findList(WeixinMessageTemplate weixinMessageTemplate) {
		return super.findList(weixinMessageTemplate);
	}
	
	public Page<WeixinMessageTemplate> findPage(Page<WeixinMessageTemplate> page, WeixinMessageTemplate weixinMessageTemplate) {
		return super.findPage(page, weixinMessageTemplate);
	}
	
	@Transactional(readOnly = false)
	public void save(WeixinMessageTemplate weixinMessageTemplate) {
		super.save(weixinMessageTemplate);
	}
	
	@Transactional(readOnly = false)
	public void delete(WeixinMessageTemplate weixinMessageTemplate) {
		super.delete(weixinMessageTemplate);
	}
	
}