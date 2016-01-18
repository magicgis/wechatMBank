/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yitong.weixin.modules.wechat.dao;

import com.yitong.weixin.common.persistence.CrudDao;
import com.yitong.weixin.common.persistence.annotation.MyBatisDao;
import com.yitong.weixin.modules.wechat.entity.WeixinMenu;

import java.util.List;
import java.util.Map;

/**
 * 微信相关菜单配置DAO接口
 * @author fwy
 * @version 2015-12-30
 */
@MyBatisDao
public interface WeixinMenuDao extends CrudDao<WeixinMenu> {
	
	public List<WeixinMenu> getByPId(Map<String,String> map);

	public List<WeixinMenu> getByPId_(Map<String,String> map);

	public List<Map<String, Object>> menuChickStatsYesterday(Map<String,Object> map);
	
}