/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yitong.weixin.modules.wechat.dao;

import com.yitong.weixin.common.persistence.CrudDao;
import com.yitong.weixin.common.persistence.annotation.MyBatisDao;
import com.yitong.weixin.modules.wechat.entity.WeixinMessageTemplate;

/**
 * 微信模板消息存储表DAO接口
 * @author hf
 * @version 2016-01-14
 */
@MyBatisDao
public interface WeixinMessageTemplateDao extends CrudDao<WeixinMessageTemplate> {
	
}