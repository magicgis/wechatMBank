package com.yitong.weixin.front.info.dao;

import java.util.List;
import java.util.Map;

import com.yitong.weixin.common.persistence.CrudDao;
import com.yitong.weixin.common.persistence.annotation.MyBatisDao;
import com.yitong.weixin.front.info.entity.WeixinMessageF;

/**
 * 微信消息存储表DAO接口
 * @author hf
 * @version 2016-01-07
 */
@MyBatisDao
public interface WeixinMessageFDao extends CrudDao<WeixinMessageF>{

	public List<WeixinMessageF> findListGroupBy(WeixinMessageF message);

	public List<WeixinMessageF> findPageByMsgId(WeixinMessageF message);

	public List<Map<String, Object>> findMessageStatsList(Map<String,Object> map);
}
