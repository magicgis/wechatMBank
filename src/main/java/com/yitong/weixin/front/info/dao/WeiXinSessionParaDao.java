/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.yitong.weixin.front.info.dao;

import java.util.List;
import java.util.Map;

import com.yitong.weixin.common.persistence.CrudDao;
import com.yitong.weixin.common.persistence.annotation.MyBatisDao;
import com.yitong.weixin.front.info.entity.WeiXinSession;
import com.yitong.weixin.front.info.entity.WeiXinSessionPara;


/**
 * 区域DAO接口
 * @author ThinkGem
 * @version 2013-01-15
 */
@MyBatisDao
public interface WeiXinSessionParaDao extends CrudDao<WeiXinSessionPara> {
	
	public int deleteByOpenId(String id);
	
	public List<WeiXinSessionPara> findByMenuNo(Map<String, Object> map);
}

/**
 * DAO自定义接口
 * @author ThinkGem
 */
interface WeiXinSessionParaDaoCustom {

}

