/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yitong.weixin.front.info.dao;

import com.yitong.weixin.common.persistence.CrudDao;
import com.yitong.weixin.common.persistence.annotation.MyBatisDao;
import com.yitong.weixin.front.info.entity.WeixinMenuF;

import java.util.List;
import java.util.Map;

/**
 * 微信相关菜单配置DAO接口
 * @author fwy
 * @version 2015-12-30
 */
@MyBatisDao
public interface WeixinMenuFDao extends CrudDao<WeixinMenuF> {
	
	public List<WeixinMenuF> getByPId(Map<String,String> map);

	public List<WeixinMenuF> getByPId_(Map<String,String> map);

	public List<Map<String, Object>> menuChickStats(Map<String,Object> map);

	public List<Map<String, Object>> menuChickStatsByDay(Map<String,Object> map);

	public List<Map<String, Object>> menuChickStatsYesterday(Map<String,Object> map);
	
}