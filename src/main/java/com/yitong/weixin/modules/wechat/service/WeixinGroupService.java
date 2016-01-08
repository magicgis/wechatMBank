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
import com.yitong.weixin.modules.wechat.dao.WeixinGroupDao;
import com.yitong.weixin.modules.wechat.entity.WeixinGroup;
import com.yitong.weixin.modules.wechat.utils.AcctUtils;

/**
 * 微信分组Service
 * @author fwy
 * @version 2015-12-31
 */
@Service
@Transactional(readOnly = true)
public class WeixinGroupService extends CrudService<WeixinGroupDao, WeixinGroup> {

	public WeixinGroup get(String id) {
		return super.get(id);
	}
	
	public List<WeixinGroup> findList(WeixinGroup weixinGroup) {
		weixinGroup.setAcctOpenId(AcctUtils.getOpenId());//多公众帐号
		return super.findList(weixinGroup);
	}
	
	public Page<WeixinGroup> findPage(Page<WeixinGroup> page, WeixinGroup weixinGroup) {
		weixinGroup.setAcctOpenId(AcctUtils.getOpenId());//多公众帐号
		return super.findPage(page, weixinGroup);
	}
	
	@Transactional(readOnly = false)
	public void save(WeixinGroup weixinGroup) {
		weixinGroup.setAcctOpenId(AcctUtils.getOpenId());
		super.save(weixinGroup);
	}
	
	@Transactional(readOnly = false)
	public void saveList(List<WeixinGroup> list) {
		for(WeixinGroup weixinGroup:list){
			weixinGroup.setAcctOpenId(AcctUtils.getOpenId());
			super.save(weixinGroup);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(WeixinGroup weixinGroup) {
		super.delete(weixinGroup);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		WeixinGroup weixinGroup = new WeixinGroup();
		weixinGroup.setId(id);
		super.delete(weixinGroup);
	}
	
	@Transactional(readOnly = false)
	public void deleteAll() {
		Map<String,String> map = Maps.newHashMap();
		map.put("acctOpenId", AcctUtils.getOpenId());
		dao.deleteAll(map);
	}
	
	public boolean hasGroup(String groupName){
		Map<String,String> map = Maps.newHashMap();
		map.put("groupName", groupName);
		map.put("acctOpenId", AcctUtils.getOpenId());
		List<WeixinGroup> list = dao.getGroupList(map);
		return list != null && list.size() > 0;
	}
	
}