/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yitong.weixin.front.info.dao;

import com.yitong.weixin.common.persistence.CrudDao;
import com.yitong.weixin.common.persistence.annotation.MyBatisDao;
import com.yitong.weixin.front.info.entity.WeixinAccessTokenF;

/**
 * access_token配置DAO接口
 * @author hf
 * @version 2016-01-15
 */
@MyBatisDao
public interface WeixinAccessTokenFDao extends CrudDao<WeixinAccessTokenF> {
	public WeixinAccessTokenF getAccessTokenByOpenId(String AcctOpenId);
	
}