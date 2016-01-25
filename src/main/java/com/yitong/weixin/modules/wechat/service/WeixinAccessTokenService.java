/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yitong.weixin.modules.wechat.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yitong.weixin.common.persistence.Page;
import com.yitong.weixin.common.service.CrudService;
import com.yitong.weixin.modules.wechat.entity.WeixinAccessToken;
import com.yitong.weixin.modules.wechat.dao.WeixinAccessTokenDao;

/**
 * access_token配置Service
 * @author hf
 * @version 2016-01-15
 */
@Service
@Transactional(readOnly = true)
public class WeixinAccessTokenService extends CrudService<WeixinAccessTokenDao, WeixinAccessToken> {

	public WeixinAccessToken get(String id) {
		return super.get(id);
	}
	
	public List<WeixinAccessToken> findList(WeixinAccessToken weixinAccessToken) {
		return super.findList(weixinAccessToken);
	}
	
	public Page<WeixinAccessToken> findPage(Page<WeixinAccessToken> page, WeixinAccessToken weixinAccessToken) {
		return super.findPage(page, weixinAccessToken);
	}
	
	@Transactional(readOnly = false)
	public void save(WeixinAccessToken weixinAccessToken) {
		super.save(weixinAccessToken);
	}
	
	@Transactional(readOnly = false)
	public void delete(WeixinAccessToken weixinAccessToken) {
		super.delete(weixinAccessToken);
	}
	
	public WeixinAccessToken getAccessTokenByOpenId(String acctOpenId) {
		return dao.getAccessTokenByOpenId(acctOpenId);
	}
	
	public String getCurrentTime() {
		return dao.getCurrentTime();
	}
	
}