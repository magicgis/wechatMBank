package com.yitong.weixin.front.info.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yitong.weixin.common.service.CrudService;
import com.yitong.weixin.front.info.dao.WeiXinSessionDao;
import com.yitong.weixin.front.info.entity.WeiXinSession;
import com.yitong.weixin.front.receive.utils.CF;


@Service
@Transactional(readOnly = true)
public class WeiXinSessionService extends CrudService<WeiXinSessionDao, WeiXinSession>{

	@Autowired
	private WeiXinSessionParaService weiXinSessionParaService;
	
	public WeiXinSession get(String id) {
		return super.get(id);
	}
	
	public WeiXinSession getByOpenId(String id) {
		List<WeiXinSession> list = dao.getByOpenId(id);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public int getMenuNo(String openId,String createTime){
		WeiXinSession weiXinSession= isSessionOver(openId,createTime);
		if(weiXinSession!=null){
			return weiXinSession.getNextMenuNo();
		}else{
			return 1;
		}
	}
	
	@Transactional(readOnly = false)
	public void save(WeiXinSession weixinSession) {
		super.save(weixinSession);
	}
	
	public WeiXinSession isSessionOver(String openId,String createTime){
		WeiXinSession weiXinSession= getByOpenId(openId);
		if(weiXinSession!=null){
			long time = Long.valueOf(createTime)-Long.valueOf(weiXinSession.getLastVisitTime());
			if(time>CF.SessionTime*60){
				weiXinSessionParaService.deleteByOpenId(openId);
				dao.delete(weiXinSession);
				return null;
			}
		}
		return weiXinSession;
	}
	
	public void clearSession(String openId){
		WeiXinSession weiXinSession= getByOpenId(openId);
		if(weiXinSession!=null){
			dao.delete(weiXinSession);
		}
		weiXinSessionParaService.deleteByOpenId(openId);
	}
}
