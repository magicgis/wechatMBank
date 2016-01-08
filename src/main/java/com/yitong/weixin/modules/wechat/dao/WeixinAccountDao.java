/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yitong.weixin.modules.wechat.dao;

import java.util.List;
import java.util.Map;

import com.yitong.weixin.common.persistence.CrudDao;
import com.yitong.weixin.common.persistence.annotation.MyBatisDao;
import com.yitong.weixin.modules.wechat.entity.WeixinAccount;

/**
 * 多公众号的维护DAO接口
 * @author fwy
 * @version 2015-12-29
 */
@MyBatisDao
public interface WeixinAccountDao extends CrudDao<WeixinAccount> {
	
	public List<WeixinAccount> findAll();
	public List<WeixinAccount> findByUserId(Map<String,String> map);
	public List<WeixinAccount> findByUserAndAcctId(Map<String,String> map);
	
}