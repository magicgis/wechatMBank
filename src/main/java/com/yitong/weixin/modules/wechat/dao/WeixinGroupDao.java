/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yitong.weixin.modules.wechat.dao;

import java.util.List;
import java.util.Map;

import com.yitong.weixin.common.persistence.CrudDao;
import com.yitong.weixin.common.persistence.annotation.MyBatisDao;
import com.yitong.weixin.modules.wechat.entity.WeixinGroup;

/**
 * 微信分组DAO接口
 * @author fwy
 * @version 2015-12-31
 */
@MyBatisDao
public interface WeixinGroupDao extends CrudDao<WeixinGroup> {
	
	public List<WeixinGroup> getGroupList(Map<String,String> map);
	public int deleteAll(Map<String,String> map);
	
}