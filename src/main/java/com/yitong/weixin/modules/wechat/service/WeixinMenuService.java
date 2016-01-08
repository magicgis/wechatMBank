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
import com.yitong.weixin.modules.wechat.dao.WeixinMenuDao;
import com.yitong.weixin.modules.wechat.entity.WeixinMenu;
import com.yitong.weixin.modules.wechat.utils.AcctUtils;

/**
 * 微信相关菜单配置Service
 * @author fwy
 * @version 2015-12-30
 */
@Service
@Transactional(readOnly = true)
public class WeixinMenuService extends CrudService<WeixinMenuDao, WeixinMenu> {

	public WeixinMenu get(String id) {
		return super.get(id);
	}
	
	public List<WeixinMenu> findList(WeixinMenu weixinMenu) {
		return super.findList(weixinMenu);
	}
	
	public Page<WeixinMenu> findPage(Page<WeixinMenu> page, WeixinMenu weixinMenu) {
		return super.findPage(page, weixinMenu);
	}
	
	@Transactional(readOnly = false)
	public void save(WeixinMenu weixinMenu) {
		super.save(weixinMenu);
	}
	
	@Transactional(readOnly = false)
	public void delete(WeixinMenu weixinMenu) {
		super.delete(weixinMenu);
	}
	
	
	public List<WeixinMenu> getByPId_(String pid){
		Map<String,String> map = Maps.newHashMap();
		map.put("acctOpenId", AcctUtils.getOpenId());
		map.put("pid", pid);
		List<WeixinMenu> list = dao.getByPId_(map);
		return list;
	}
	
	public List<WeixinMenu> getByPId(){
		Map<String,String> map = Maps.newHashMap();
		map.put("acctOpenId", AcctUtils.getOpenId());
		List<WeixinMenu> list = dao.getByPId(map);
		return list;
	}
	
	@Transactional(readOnly = false)
	public void deleteById(String id){
		WeixinMenu weixinMenu = new WeixinMenu();
		weixinMenu.setId(id);
		super.delete(weixinMenu);
	}
	
	@Transactional(readOnly = false)
	public void deleteList(List<WeixinMenu> list){
		for(WeixinMenu weixinMenu:list){
			super.delete(weixinMenu);
		}
	}
}