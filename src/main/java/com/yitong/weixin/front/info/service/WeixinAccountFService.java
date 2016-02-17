/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yitong.weixin.front.info.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yitong.weixin.common.persistence.Page;
import com.yitong.weixin.common.service.CrudService;
import com.yitong.weixin.front.info.dao.WeixinAccountFDao;
import com.yitong.weixin.front.info.entity.WeixinAccountF;

/**
 * 
 * @author hefan
 *
 */
@Service
@Transactional(readOnly = true)
public class WeixinAccountFService extends CrudService<WeixinAccountFDao, WeixinAccountF> {

	public WeixinAccountF get(String id) {
		return super.get(id);
	}
	
	public List<WeixinAccountF> findList(WeixinAccountF weixinAccount) {
		return super.findList(weixinAccount);
	}
	
	public Page<WeixinAccountF> findPage(Page<WeixinAccountF> page, WeixinAccountF weixinAccount) {
		return super.findPage(page, weixinAccount);
	}
	
	@Transactional(readOnly = false)
	public void save(WeixinAccountF weixinAccount) {
		super.save(weixinAccount);
	}
	
	@Transactional(readOnly = false)
	public void delete(WeixinAccountF weixinAccount) {
		super.delete(weixinAccount);
	}
	
	public WeixinAccountF findByAcctId(String accOpenId) {
		return dao.findByAcctId(accOpenId);
	}
	
}