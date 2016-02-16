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
import com.yitong.weixin.front.info.dao.WeixinMenuFDao;
import com.yitong.weixin.front.info.entity.WeixinMenuF;

/**
 * 微信相关菜单配置Service
 * @author fwy
 * @version 2015-12-30
 */
@Service
@Transactional(readOnly = true)
public class WeixinMenuFService extends CrudService<WeixinMenuFDao, WeixinMenuF> {

	public WeixinMenuF get(String id) {
		return super.get(id);
	}
	
	public List<WeixinMenuF> findList(WeixinMenuF weixinMenu) {
		return super.findList(weixinMenu);
	}
	
	public Page<WeixinMenuF> findPage(Page<WeixinMenuF> page, WeixinMenuF weixinMenu) {
		return super.findPage(page, weixinMenu);
	}
	
	@Transactional(readOnly = false)
	public void save(WeixinMenuF weixinMenu) {
		super.save(weixinMenu);
	}
	
	@Transactional(readOnly = false)
	public void delete(WeixinMenuF weixinMenu) {
		super.delete(weixinMenu);
	}
	
	
	public List<WeixinMenuF> getByPId_(String pid){
		Map<String,String> map = Maps.newHashMap();
		map.put("pid", pid);
		List<WeixinMenuF> list = dao.getByPId_(map);
		return list;
	}
	
	public List<WeixinMenuF> getByPId(){
		Map<String,String> map = Maps.newHashMap();
		List<WeixinMenuF> list = dao.getByPId(map);
		return list;
	}
	
	@Transactional(readOnly = false)
	public void deleteById(String id){
		WeixinMenuF weixinMenu = new WeixinMenuF();
		weixinMenu.setId(id);
		super.delete(weixinMenu);
	}
	
	@Transactional(readOnly = false)
	public void deleteList(List<WeixinMenuF> list){
		for(WeixinMenuF weixinMenu:list){
			super.delete(weixinMenu);
		}
	}
}