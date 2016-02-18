/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yitong.weixin.front.info.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.yitong.weixin.common.persistence.Page;
import com.yitong.weixin.common.service.CrudService;
import com.yitong.weixin.front.info.dao.WeixinUserFDao;
import com.yitong.weixin.front.info.entity.WeixinUserF;

/**
 * 微信用户Service
 * @author Fwy
 * @version 2015-12-31
 */
@Service
@Transactional(readOnly = true)
public class WeixinUserFService extends CrudService<WeixinUserFDao, WeixinUserF> {

	public WeixinUserF get(String id) {
		return super.get(id);
	}
	
	public List<WeixinUserF> findList(WeixinUserF weixinUserF) {
		return super.findList(weixinUserF);
	}
	
	public Page<WeixinUserF> findPage(Page<WeixinUserF> page, WeixinUserF weixinUser) {
		return super.findPage(page, weixinUser);
	}
	
//	public Page<WeixinUserF> findPageByAcctOpenId(Page<WeixinUserF> page, WeixinUserF weixinUser) {
//		Map<String,Object> map = Maps.newHashMap();
//		map.put("userName", weixinUser.getUserName());
////		map.put("acctOpenId", WeixinAcctUtils.getOpenId());
//		if(weixinUser.getGroup()!=null)
//			map.put("groupId", weixinUser.getGroup().getId());
//		if(page!=null)
//			map.put("pageOrderBy", page.getOrderBy());
//		page.setList(dao.findListByAcctOpenId(map));
//		return page;
//	}
	
	@Transactional(readOnly = false)
	public void save(WeixinUserF weixinUser) {
		super.save(weixinUser);
	}
	
	@Transactional(readOnly = false)
	public void delete(WeixinUserF weixinUser) {
		super.delete(weixinUser);
	}
	
	public WeixinUserF findByOpenId(String openId){
		Map<String,String> map = Maps.newHashMap();
		map.put("openId", openId);
		List<WeixinUserF> list = dao.findByOpenId(map);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
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
	
	@Transactional(readOnly = false)
	public void updateCustIdByOpenId(String str, String openId){
		Map<String,Object> map = Maps.newHashMap();
		map.put("custId", str);
		map.put("openId", openId);
		dao.updateCustIdByOpenId(map);
	}

//	public void clearCache() {
//		dao.clearCache();
//	}

	public void updateSubTimeByOpenId(String createTime, String openId) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("createTime", createTime);
		map.put("openId", openId);
		dao.updateSubTimeByOpenId(map);
	}

	public void updateWeixinUserByOpenId(Map<String, Object> map) {
		dao.updateWeixinUserByOpenId(map);
	}

	public void updateWeixinUserByOpenId2(Map<String, Object> map) {
		dao.updateWeixinUserByOpenId2(map);
	}
}