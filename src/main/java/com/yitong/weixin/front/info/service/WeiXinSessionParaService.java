package com.yitong.weixin.front.info.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.yitong.weixin.common.service.CrudService;
import com.yitong.weixin.front.info.dao.WeiXinSessionParaDao;
import com.yitong.weixin.front.info.entity.WeiXinSessionPara;



@Service
@Transactional(readOnly = true)
public class WeiXinSessionParaService extends CrudService<WeiXinSessionParaDao, WeiXinSessionPara> {

	public WeiXinSessionPara get(String id) {
		return super.get(id);
	}
	
	@Transactional(readOnly = false)
	public void save(WeiXinSessionPara weixinSessionPara) {
		super.save(weixinSessionPara);
	}
	
	@Transactional(readOnly = false)
	public void deleteByOpenId(String openId) {
		dao.deleteByOpenId(openId);
	}
	
	@Transactional(readOnly = false)
	public WeiXinSessionPara findByMenuNo(String openId,String content) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("openId", openId);
		map.put("menuNo", content);
		List<WeiXinSessionPara> list = dao.findByMenuNo(map);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
}
