/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yitong.weixin.front.info.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yitong.weixin.common.persistence.Page;
import com.yitong.weixin.common.service.CrudService;
import com.yitong.weixin.front.info.dao.WeixinMessageFDao;
import com.yitong.weixin.front.info.entity.WeixinMessageF;

/**
 * 微信消息存储表Service
 * @author hf
 * @version 2016-01-07
 */
@Service
@Transactional(readOnly = true)
public class WeixinMessageFService extends CrudService<WeixinMessageFDao, WeixinMessageF> {

	public WeixinMessageF get(String id) {
		return super.get(id);
	}
	
	public List<WeixinMessageF> findList(WeixinMessageF weixinMessage) {
		return super.findList(weixinMessage);
	}
	
	public Page<WeixinMessageF> findPage(Page<WeixinMessageF> page, WeixinMessageF weixinMessage) {
		return super.findPage(page, weixinMessage);
	}
	
//	public Page<WeixinMessageF> findPageGroupBy(Page<WeixinMessageF> page, WeixinMessageF weixinMessage) {
//		Date beginDate = DateUtils.parseDate(weixinMessage.getBeginCreateDate());
//		if (beginDate == null){
//			beginDate = DateUtils.addDays(new Date(), -2);
//			weixinMessage.setBeginCreateDate(beginDate);
//		}
//		Date endDate = DateUtils.parseDate(weixinMessage.getEndCreateDate());
//		if(endDate != null){
//			endDate = DateUtils.addDays(endDate, 1);
//		}
//		if (endDate == null){
//			endDate = new Date();
//			weixinMessage.setEndCreateDate(endDate);
//		}
//		if(weixinMessage.getWeixinUser()==null){
//			WeixinUserF weixinUser = new WeixinUserF();
//			weixinUser.setUserName("");
//			weixinMessage.setWeixinUser(weixinUser);
//		}
//		if(weixinMessage.getContent()==null){
//			weixinMessage.setContent("");
//		}
//			
//		weixinMessage.setPage(page);
//		page.setList(dao.findListGroupBy(weixinMessage));
//		return page;
//	}
//	
//	public Page<WeixinMessageF> findPageByMsgId(Page<WeixinMessageF> page, String msgId) {
//		WeixinMessageF msg = dao.get(msgId);
//		if(null == msg) {
//			throw new IllegalArgumentException(String.format("查询不到%s对应的消息", msgId));
//		}
//		msg.setPage(page);
//		page.setList(dao.findPageByMsgId(msg));
//		return page;
//	}
	
	@Transactional(readOnly = false)
	public void save(WeixinMessageF weixinMessage) {
		super.save(weixinMessage);
	}
	
	@Transactional(readOnly = false)
	public void delete(WeixinMessageF weixinMessage) {
		super.delete(weixinMessage);
	}
	
}