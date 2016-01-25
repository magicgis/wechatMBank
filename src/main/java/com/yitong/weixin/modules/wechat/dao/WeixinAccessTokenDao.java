/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yitong.weixin.modules.wechat.dao;

import com.yitong.weixin.common.persistence.CrudDao;
import com.yitong.weixin.common.persistence.annotation.MyBatisDao;
import com.yitong.weixin.modules.wechat.entity.WeixinAccessToken;

/**
 * access_token配置DAO接口
 * @author hf
 * @version 2016-01-15
 */
@MyBatisDao
public interface WeixinAccessTokenDao extends CrudDao<WeixinAccessToken> {
	public WeixinAccessToken getAccessTokenByOpenId(String acctOpenId);

	public String getCurrentTime();
	
}