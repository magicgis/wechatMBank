/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yitong.weixin.front.info.dao;

import java.util.List;
import java.util.Map;

import com.yitong.weixin.common.persistence.CrudDao;
import com.yitong.weixin.common.persistence.annotation.MyBatisDao;
import com.yitong.weixin.front.info.entity.WeixinAccountF;

/**
 * 多公众号的维护DAO接口
 * @author fwy
 * @version 2015-12-29
 */
@MyBatisDao
public interface WeixinAccountFDao extends CrudDao<WeixinAccountF> {
	
	public List<WeixinAccountF> findAll();
	public List<WeixinAccountF> findByUserId(Map<String,String> map);
	public List<WeixinAccountF> findByUserAndAcctId(Map<String,String> map);
	public WeixinAccountF findByAcctId(String accOpenId);
	
}