/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yitong.weixin.modules.wechat.dao;

import java.util.List;
import java.util.Map;

import com.yitong.weixin.common.persistence.CrudDao;
import com.yitong.weixin.common.persistence.annotation.MyBatisDao;
import com.yitong.weixin.modules.wechat.entity.WeixinUser;

/**
 * 微信用户DAO接口
 * @author Fwy
 * @version 2015-12-31
 */
@MyBatisDao
public interface WeixinUserDao extends CrudDao<WeixinUser> {
	public WeixinUser findByOpenId(Map<String,String> map);
	public List<WeixinUser> findListByAcctOpenId(Map<String,Object> map);
	public int updateByUserIds(Map<String,Object> map);
}