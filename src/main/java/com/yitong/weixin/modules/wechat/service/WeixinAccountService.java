/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yitong.weixin.modules.wechat.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yitong.weixin.common.persistence.Page;
import com.yitong.weixin.common.service.CrudService;
import com.yitong.weixin.modules.wechat.entity.WeixinAccount;
import com.yitong.weixin.modules.wechat.dao.WeixinAccountDao;

/**
 * 多公众号的维护Service
 * @author fwy
 * @version 2015-12-29
 */
@Service
@Transactional(readOnly = true)
public class WeixinAccountService extends CrudService<WeixinAccountDao, WeixinAccount> {

	public WeixinAccount get(String id) {
		return super.get(id);
	}
	
	public List<WeixinAccount> findList(WeixinAccount weixinAccount) {
		return super.findList(weixinAccount);
	}
	
	public Page<WeixinAccount> findPage(Page<WeixinAccount> page, WeixinAccount weixinAccount) {
		return super.findPage(page, weixinAccount);
	}
	
	@Transactional(readOnly = false)
	public void save(WeixinAccount weixinAccount) {
		super.save(weixinAccount);
	}
	
	@Transactional(readOnly = false)
	public void delete(WeixinAccount weixinAccount) {
		super.delete(weixinAccount);
	}
	
}