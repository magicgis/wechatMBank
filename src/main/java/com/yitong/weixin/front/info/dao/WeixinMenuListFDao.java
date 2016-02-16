/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yitong.weixin.front.info.dao;

import java.util.List;
import java.util.Map;

import com.yitong.weixin.common.persistence.CrudDao;
import com.yitong.weixin.common.persistence.annotation.MyBatisDao;
import com.yitong.weixin.front.info.entity.WeixinMenuListF;

/**
 * 微信菜单规则DAO接口
 * @author Fwy
 * @version 2016-01-06
 */
@MyBatisDao
public interface WeixinMenuListFDao extends CrudDao<WeixinMenuListF> {
	public List<WeixinMenuListF> getByCode(Map<String,Object> map);
	
	public List<WeixinMenuListF> getByArticleId(String articleId);
}