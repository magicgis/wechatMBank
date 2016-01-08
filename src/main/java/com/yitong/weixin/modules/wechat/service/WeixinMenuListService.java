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
import com.yitong.weixin.modules.wechat.dao.WeixinMenuListDao;
import com.yitong.weixin.modules.wechat.entity.WeixinMenuList;
import com.yitong.weixin.modules.wechat.utils.AcctUtils;

/**
 * 微信菜单规则Service
 * @author Fwy
 * @version 2016-01-06
 */
@Service
@Transactional(readOnly = true)
public class WeixinMenuListService extends CrudService<WeixinMenuListDao, WeixinMenuList> {

	public WeixinMenuList get(String id) {
		return super.get(id);
	}
	
	public List<WeixinMenuList> findList(WeixinMenuList weixinMenuList) {
		weixinMenuList.setAcctOpenId(AcctUtils.getOpenId());
		return super.findList(weixinMenuList);
	}
	
	public Page<WeixinMenuList> findPage(Page<WeixinMenuList> page, WeixinMenuList weixinMenuList) {
		weixinMenuList.setAcctOpenId(AcctUtils.getOpenId());
		return super.findPage(page, weixinMenuList);
	}
	
	@Transactional(readOnly = false)
	public void save(WeixinMenuList weixinMenuList) {
		weixinMenuList.setAcctOpenId(AcctUtils.getOpenId());
		super.save(weixinMenuList);
	}
	
	@Transactional(readOnly = false)
	public void delete(WeixinMenuList weixinMenuList) {
		super.delete(weixinMenuList);
	}
	
	/**
	 * 通过规则编码查询规则详情
	 * @param code 规则编码
	 * @return
	 */
	public WeixinMenuList getByCode(String code){
		Map<String,Object> map = Maps.newHashMap();
		map.put("acctOpenId", AcctUtils.getOpenId());
		map.put("code", code);
		List<WeixinMenuList> list = dao.getByCode(map);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
}