/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yitong.weixin.front.info.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yitong.weixin.common.persistence.Page;
import com.yitong.weixin.common.service.CrudService;
import com.yitong.weixin.front.info.dao.WeixinAccessTokenFDao;
import com.yitong.weixin.front.info.entity.WeixinAccessTokenF;
import com.yitong.weixin.modules.wechat.entity.WeixinAccessToken;
import com.yitong.weixin.modules.wechat.dao.WeixinAccessTokenDao;

/**
 * access_token配置Service
 * @author hf
 * @version 2016-01-15
 */
@Service
@Transactional(readOnly = true)
public class WeixinAccessTokenFService extends CrudService<WeixinAccessTokenFDao, WeixinAccessTokenF> {

	public WeixinAccessTokenF get(String id) {
		return super.get(id);
	}
	
	public List<WeixinAccessTokenF> findList(WeixinAccessTokenF weixinAccessToken) {
		return super.findList(weixinAccessToken);
	}
	
	public Page<WeixinAccessTokenF> findPage(Page<WeixinAccessTokenF> page, WeixinAccessTokenF weixinAccessToken) {
		return super.findPage(page, weixinAccessToken);
	}
	
	@Transactional(readOnly = false)
	public void save(WeixinAccessTokenF weixinAccessToken) {
		super.save(weixinAccessToken);
	}
	
	@Transactional(readOnly = false)
	public void delete(WeixinAccessTokenF weixinAccessToken) {
		super.delete(weixinAccessToken);
	}
	
	public WeixinAccessTokenF getAccessTokenByOpenId(String AcctOpenId) {
		return dao.getAccessTokenByOpenId(AcctOpenId);
	}
	
}