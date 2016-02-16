package com.yitong.weixin.front.info.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yitong.weixin.common.service.CrudService;
import com.yitong.weixin.front.info.dao.WeiXinTokenDao;
import com.yitong.weixin.front.info.entity.WeiXinToken;


@Service
@Transactional(readOnly = true)
public class WeiXinTokenService extends CrudService<WeiXinTokenDao, WeiXinToken> {

	public WeiXinToken get(String id) {
		return super.get(id);
	}
	
	@Transactional(readOnly = false)
	public void deleteByOpenId(String openId) {
		dao.deleteByOpenId(openId);
	}
	
	@Transactional(readOnly = false)
	public WeiXinToken findByMenuNo(String openId,String content) {
		List<WeiXinToken> list = dao.findByMenuNo(openId,content);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Transactional(readOnly = false)
	public WeiXinToken getByOpenId(String openId) {
		List<WeiXinToken> list = dao.getByOpenId(openId);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
}
