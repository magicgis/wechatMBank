/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yitong.weixin.modules.wechat.dao;

import java.util.List;

import com.yitong.weixin.common.persistence.CrudDao;
import com.yitong.weixin.common.persistence.annotation.MyBatisDao;
import com.yitong.weixin.modules.wechat.entity.WeixinMessage;

/**
 * 微信消息存储表DAO接口
 * @author Fwy
 * @version 2016-01-07
 */
@MyBatisDao
public interface WeixinMessageDao extends CrudDao<WeixinMessage> {
	public List<WeixinMessage> findListGroupBy(WeixinMessage message);
}