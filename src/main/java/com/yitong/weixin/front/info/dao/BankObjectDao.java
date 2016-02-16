package com.yitong.weixin.front.info.dao;

import java.util.List;

import com.yitong.weixin.common.persistence.CrudDao;
import com.yitong.weixin.common.persistence.annotation.MyBatisDao;
import com.yitong.weixin.front.info.entity.BankObject;

/**
 * 站点DAO接口
 * @author ThinkGem
 * @version 2013-01-15
 */
@MyBatisDao
public interface BankObjectDao extends CrudDao<BankObject>{

	public List<BankObject> findByLocation(String type, double add);
	
}

