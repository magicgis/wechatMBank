/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yitong.weixin.front.info.dao;

import com.yitong.weixin.common.persistence.CrudDao;
import com.yitong.weixin.common.persistence.annotation.MyBatisDao;
import com.yitong.weixin.front.info.entity.WeixinUserF;

import java.util.List;
import java.util.Map;

/**
 * 微信用户DAO接口
 * @author Fwy
 * @version 2015-12-31
 */
@MyBatisDao
public interface WeixinUserFDao extends CrudDao<WeixinUserF> {

	public List<WeixinUserF> findByOpenId(Map<String,String> map);

	public List<WeixinUserF> findListByAcctOpenId(Map<String,Object> map);

	public int updateByUserIds(Map<String,Object> map);

	public List<Map<String, Object>> findUserListByDay(Map<String,Object> map);

	public List<Map<String, Object>> fingUserNumByTime(Map<String,Object> map);
	
	public void updateCustIdByOpenId(Map<String, Object> map);

	public void clearCache();

	public void updateSubTimeByOpenId(Map<String, Object> map);

	public void updateWeixinUserByOpenId(Map<String, Object> map);

	public void updateWeixinUserByOpenId2(Map<String, Object> map);
}