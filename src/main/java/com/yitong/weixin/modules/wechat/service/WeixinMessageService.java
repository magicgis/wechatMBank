/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yitong.weixin.modules.wechat.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yitong.weixin.common.persistence.Page;
import com.yitong.weixin.common.service.CrudService;
import com.yitong.weixin.common.utils.DateUtils;
import com.yitong.weixin.common.utils.StringUtils;
import com.yitong.weixin.modules.wechat.dao.WeixinMessageDao;
import com.yitong.weixin.modules.wechat.entity.WeixinMessage;
import com.yitong.weixin.modules.wechat.entity.WeixinUser;

/**
 * 微信消息存储表Service
 * @author Fwy
 * @version 2016-01-07
 */
@Service
@Transactional(readOnly = true)
public class WeixinMessageService extends CrudService<WeixinMessageDao, WeixinMessage> {

	public WeixinMessage get(String id) {
		return super.get(id);
	}
	
	public List<WeixinMessage> findList(WeixinMessage weixinMessage) {
		return super.findList(weixinMessage);
	}
	
	public Page<WeixinMessage> findPage(Page<WeixinMessage> page, WeixinMessage weixinMessage) {
		return super.findPage(page, weixinMessage);
	}
	
	public Page<WeixinMessage> findPageGroupBy(Page<WeixinMessage> page, WeixinMessage weixinMessage) {
		Date beginDate = DateUtils.parseDate(weixinMessage.getBeginCreateDate());
		if (beginDate == null){
			beginDate = DateUtils.addDays(new Date(), -2);
			weixinMessage.setBeginCreateDate(beginDate);
		}
		Date endDate = DateUtils.parseDate(weixinMessage.getEndCreateDate());
		if(endDate != null){
			endDate = DateUtils.addDays(endDate, 1);
		}
		if (endDate == null){
			endDate = new Date();
			weixinMessage.setEndCreateDate(endDate);
		}
		if(weixinMessage.getWeixinUser()==null){
			WeixinUser weixinUser = new WeixinUser();
			weixinUser.setUserName("");
			weixinMessage.setWeixinUser(weixinUser);
		}
		if(weixinMessage.getContent()==null){
			weixinMessage.setContent("");
		}
			
		weixinMessage.setPage(page);
		page.setList(dao.findListGroupBy(weixinMessage));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(WeixinMessage weixinMessage) {
		super.save(weixinMessage);
	}
	
	@Transactional(readOnly = false)
	public void delete(WeixinMessage weixinMessage) {
		super.delete(weixinMessage);
	}
	
}