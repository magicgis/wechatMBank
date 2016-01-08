/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.yitong.weixin.modules.wechat.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yitong.weixin.common.service.BaseService;
import com.yitong.weixin.common.utils.CacheUtils;
import com.yitong.weixin.common.utils.SpringContextHolder;
import com.yitong.weixin.common.utils.StringUtils;
import com.yitong.weixin.modules.sys.utils.UserUtils;
import com.yitong.weixin.modules.wechat.dao.WeixinAccountDao;
import com.yitong.weixin.modules.wechat.entity.WeixinAccount;

/**
 * 用户工具类
 * @author ThinkGem
 * @version 2013-5-29
 */
public class AcctUtils extends BaseService {
	
	private static WeixinAccountDao AcctDao = SpringContextHolder.getBean(WeixinAccountDao.class);
	public static final String CACHE_OPEN_ID = "openId";
	
	public static WeixinAccount getAcct(){
		//获取当前用户的id
		String userId= String.valueOf(UserUtils.getUser().getId());
		//获取当前选择的公众号openidO
		String openId = (String)CacheUtils.get(CACHE_OPEN_ID);
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("userId", userId);
		
		WeixinAccount acct = new WeixinAccount();
		if (openId == null || StringUtils.isBlank(openId)){
			List<WeixinAccount> acctList = AcctDao.findByUserId(map);
			if(acctList!=null && acctList.size()>0){
				acct = acctList.get(0);
				openId = acctList.get(0).getAcctOpenId();
			}
			CacheUtils.put(CACHE_OPEN_ID, openId);
		}else{
			map.put("acctOpenId", openId);
			List<WeixinAccount> acctList = AcctDao.findByUserAndAcctId(map);
			if(acctList!=null && acctList.size()>0){
				acct = acctList.get(0);
				openId = acctList.get(0).getAcctOpenId();
			}
			CacheUtils.put(CACHE_OPEN_ID, openId);
		}
		return acct;
	}
	
	public static String getOpenId(){
		String openId = (String)CacheUtils.get(CACHE_OPEN_ID);
		if (openId != null && StringUtils.isBlank(openId)){
			return openId;
		}else{
			return openId;
		}
	}

	public static List<WeixinAccount> getAcctList(){
		List<WeixinAccount> acctList;;
		acctList = AcctDao.findAllList(new WeixinAccount());
		return acctList;
	}
	
	public static List<WeixinAccount> getCurrtAcct(){
		List<WeixinAccount> acctList;
		Map<String,String> map = new HashMap<String,String>();
		String userId= String.valueOf(UserUtils.getUser().getId());
		map.put("userId", userId);
		acctList = AcctDao.findByUserId(map);
		return acctList;
	}
}
