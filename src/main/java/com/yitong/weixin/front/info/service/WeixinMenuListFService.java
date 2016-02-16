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
import com.yitong.weixin.front.info.dao.WeixinMenuListFDao;
import com.yitong.weixin.front.info.entity.WeixinMenuListF;

/**
 * 微信菜单规则Service
 * @author Fwy
 * @version 2016-01-06
 */
@Service
@Transactional(readOnly = true)
public class WeixinMenuListFService extends CrudService<WeixinMenuListFDao, WeixinMenuListF> {

	public WeixinMenuListF get(String id) {
		return super.get(id);
	}
	
	public List<WeixinMenuListF> findList(WeixinMenuListF weixinMenuList) {
//		weixinMenuList.setAcctOpenId(WeixinAcctUtils.getOpenId());
		return super.findList(weixinMenuList);
	}
	
	public Page<WeixinMenuListF> findPage(Page<WeixinMenuListF> page, WeixinMenuListF weixinMenuList) {
//		weixinMenuList.setAcctOpenId(WeixinAcctUtils.getOpenId());
		return super.findPage(page, weixinMenuList);
	}
	
	@Transactional(readOnly = false)
	public void save(WeixinMenuListF weixinMenuList) {
//		weixinMenuList.setAcctOpenId(WeixinAcctUtils.getOpenId());
		super.save(weixinMenuList);
	}
	
	@Transactional(readOnly = false)
	public void delete(WeixinMenuListF weixinMenuList) {
		super.delete(weixinMenuList);
	}
	
	/**
	 * 通过规则编码查询规则详情
	 * @param code 规则编码
	 * @return
	 */
	public WeixinMenuListF getByCode(String code){
		Map<String,Object> map = Maps.newHashMap();
		map.put("code", code);
		List<WeixinMenuListF> list = dao.getByCode(map);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public WeixinMenuListF getByArticleId(String articleId){
		List<WeixinMenuListF> list = dao.getByArticleId(articleId);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
}