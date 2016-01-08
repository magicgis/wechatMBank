/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yitong.weixin.modules.wechat.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.yitong.weixin.common.persistence.Page;
import com.yitong.weixin.common.service.CrudService;
import com.yitong.weixin.modules.wechat.dao.WeixinUserDao;
import com.yitong.weixin.modules.wechat.entity.WeixinUser;
import com.yitong.weixin.modules.wechat.utils.AcctUtils;

/**
 * 微信用户Service
 * @author Fwy
 * @version 2015-12-31
 */
@Service
@Transactional(readOnly = true)
public class WeixinUserService extends CrudService<WeixinUserDao, WeixinUser> {

	public WeixinUser get(String id) {
		return super.get(id);
	}
	
	public List<WeixinUser> findList(WeixinUser weixinUser) {
		return super.findList(weixinUser);
	}
	
	public Page<WeixinUser> findPage(Page<WeixinUser> page, WeixinUser weixinUser) {
		weixinUser.setAcctOpenId(AcctUtils.getOpenId());
		return super.findPage(page, weixinUser);
	}
	
	public Page<WeixinUser> findPageByAcctOpenId(Page<WeixinUser> page, WeixinUser weixinUser) {
		Map<String,Object> map = Maps.newHashMap();
		map.put("userName", weixinUser.getUserName());
		map.put("acctOpenId", AcctUtils.getOpenId());
		if(weixinUser.getGroup()!=null)
			map.put("groupId", weixinUser.getGroup().getId());
		if(page!=null)
			map.put("pageOrderBy", page.getOrderBy());
		page.setList(dao.findListByAcctOpenId(map));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(WeixinUser weixinUser) {
		super.save(weixinUser);
	}
	
	@Transactional(readOnly = false)
	public void delete(WeixinUser weixinUser) {
		super.delete(weixinUser);
	}
	
	public WeixinUser findByOpenId(String openId){
		Map<String,String> map = Maps.newHashMap();
		map.put("openId", openId);
		map.put("acctOpenId", AcctUtils.getOpenId());
		return dao.findByOpenId(map);
	}
	
	@Transactional(readOnly = false)
	public void batUserGroupUpdate(String groupId,String userIds){
		String[] ids = userIds.split(",");
		String[] idStr = new String[ids.length];
		for (int i = 0; i < ids.length; i++) {
			String[] users = ids[i].split("=");
			idStr[i] = users[0];
		}
		Map<String,Object> map = Maps.newHashMap();
		map.put("groupId", groupId);
		map.put("userIds", idStr);
		dao.updateByUserIds(map);
	}
}