package com.yitong.weixin.front.receive.wbank;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.yitong.weixin.front.info.entity.ArticleF;
import com.yitong.weixin.front.info.entity.WeixinMenuListF;
import com.yitong.weixin.front.info.entity.WeixinUserF;
import com.yitong.weixin.front.info.service.ArticleFService;
import com.yitong.weixin.front.info.service.WeixinMenuListFService;
import com.yitong.weixin.front.info.service.WeixinUserFService;
import com.yitong.weixin.front.receive.func.Reply;
import com.yitong.weixin.front.receive.msgbean.SubscribeEvent;
import com.yitong.weixin.front.receive.msgbean.UserDetailInfo;
import com.yitong.weixin.front.receive.utils.CF;
import com.yitong.weixin.front.receive.utils.WeiXinUtils;
import com.yitong.weixin.front.receive.web.ReceiveMessage;

@Service
public class Webmsg0301 {
	private Logger logger = LoggerFactory.getLogger(ReceiveMessage.class);
//	@Autowired
//	private WeixinMessageFService weiXinMessageFService;
	@Autowired
	private WeixinUserFService weiXinUserFService;
//	@Autowired
//	private WeixinMenuFService weiXinMenuFService;
	@Autowired
	private WeixinMenuListFService weiXinMenuListFService;
//	@Autowired
//	private WeiXinSessionService weiXinSessionFService;
//	@Autowired
//	private WeiXinSessionParaService weiXinSessionParaService;
	@Autowired
	private ArticleFService articleService;
//	@Autowired
//	private WeiXinTokenService weiXinTokenService;
	/**
	 * 菜单点击事件
	 * @param subEvent
	 * @return
	 */
	public String pushClickMessage(SubscribeEvent subEvent){
		String rspMsg = "";
		try {
			if(subEvent.getEventKey() == null){
				return "";
			}
			WeixinMenuListF weiXinList = weiXinMenuListFService.getByCode(subEvent.getEventKey());
			if(weiXinList == null){
				//error
				rspMsg = CF.error_val;
				return Reply.rspTextMsg(subEvent.getFromUserName(), subEvent.getToUserName(), rspMsg);
			}
			System.out.println(" weiXinList.getCode()====================>"+weiXinList.getCode());
			String actType = weiXinList.getActionType();
			if(actType.equals("0")){
				//显示子功能列表,然后从3级菜单中找出code等于sub_code的子菜单
				return getMessgeToList(subEvent,weiXinList.getMessage());
				
			}else if(actType.equals("1")){
			    //显示消息内容
				return getClickToContext(subEvent, weiXinList);
				
			}else if(actType.equals("2")){
				//调用接口
				return getClickToUrl(subEvent, weiXinList);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getClickToUrl(SubscribeEvent subEvent, WeixinMenuListF weiXinList) {
		return "";
	}

	private String getClickToContext(SubscribeEvent subEvent, WeixinMenuListF weiXinList) {
		String rspMsg = "";
		//WeixinUser weiXinUser =  weiXinUserService.findByOpenId(subEvent.getFromUserName());
		if(weiXinList.getCode().equals("query11")){
			return pushSubscibeMessage(subEvent);
		}
		
		else{
			ArticleF article = articleService.get(weiXinList.getArticleId());
			//判断签约
			if(weiXinList.getIsBinded() == null || weiXinList.getIsBinded().equals("0")){
				if(article!=null){//返回相应的规则回复
					String descript = article.getDescription();
					rspMsg = replacePara(descript, subEvent.getFromUserName(), subEvent.getCreateTime());
					return Reply.rspUnknownMsg(subEvent.getFromUserName(), subEvent.getToUserName(), rspMsg);
				}else{
					//error
					rspMsg = CF.error_val;
					return Reply.rspTextMsg(subEvent.getFromUserName(), subEvent.getToUserName(), rspMsg);
				}
			}
			if("1".equals(weiXinList.getIsBinded())){
				String isBindMsg = "";
				/*if(!isBindMsg.equals("")){
					return Reply.rspTextMsg(subEvent.getFromUserName(), subEvent.getToUserName(), isBindMsg);
				}*/
				if(article!=null){//返回相应的规则回复
					String descript = article.getDescription();
					rspMsg = replacePara(descript, subEvent.getFromUserName(), subEvent.getCreateTime());
					return Reply.rspUnknownMsg(subEvent.getFromUserName(), subEvent.getToUserName(), rspMsg);
				}else{
					//error
					rspMsg = CF.error_val;
					return Reply.rspTextMsg(subEvent.getFromUserName(), subEvent.getToUserName(), rspMsg);
				}

			}
		}
		return "";
	}

	private String getMessgeToList(SubscribeEvent subEvent,String message){
		return "";
	}

	private String replacePara(String content,String opendId,String createTime){
    	WeixinUserF weiXinUser = weiXinUserFService.findByOpenId(opendId);
    	if(weiXinUser!=null){//处理相关的参数
    		content = queryWinxinToken(content, opendId, createTime);
    		if(weiXinUser.getUserName()!=null){
    			String name = weiXinUser.getUserName();
				try {
					name = URLEncoder.encode(name,"UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				content = content.replaceAll("%USERNAME%", name);
    		}
//    	}else{
//    		weiXinUserFService.clearCache();	
    	}
    	return content;
    }

	private String isBind(SubscribeEvent subEvent){
		String rspMsg = "";
		WeixinUserF weiXinUser =  weiXinUserFService.findByOpenId(subEvent.getFromUserName());
		if(weiXinUser!=null){
			// 签约标记
			String mark = weiXinUser.getCustId();
			if(mark == null || mark.equals("")){
				//String val = C.common_val2+C.common_val3;
				rspMsg += CF.common_val2;
				/*rspMsg +="\n<a href=\""+C.ip+"/api/bind.do?" +
						"USERNAME=%USERNAME%&OPENID=%OPENID%"+
								"&MSGID=%MSGID%&"+"getToken(subEvent.getFromUserName())"+"\">点击这里  立即签约</a>";
								*/
				rspMsg +="\n<a href=\""+CF.ip+"/app/16/001.jsp?" +
						"USERNAME=%USERNAME%&OPENID=%OPENID%"+
						"&MSGID=%MSGID%&"+WeiXinUtils.getToken(subEvent.getFromUserName())+"\">点击这里  立即签约</a>";
				rspMsg = replacePara(rspMsg, subEvent.getFromUserName(), subEvent.getCreateTime());
			} 
		}
		return rspMsg;
	}
	/** 
	 * 用户订阅消息
	 * @param subEvent
	 * @return
	 */
	public String pushSubscibeMessage(SubscribeEvent subEvent) {
		String rspMsg = "";
		getUserInfo(subEvent);
		ArticleF article = articleService.findByTitle(CF.title_val3); //根据“欢迎消息” titile 匹配 
		String descript = article.getDescription().replaceAll("%TOKEN%", WeiXinUtils.getToken(subEvent.getFromUserName()));
		rspMsg = replacePara(descript, subEvent.getFromUserName(), subEvent.getCreateTime());
		return Reply.rspUnknownMsg(subEvent.getFromUserName(), subEvent.getToUserName(),descript);
	}
	
	/** 
	 * 用户取消订阅消息
	 * @param subEvent
	 * @return
	 */
	public void pushUnSubscibeMessage(SubscribeEvent subEvent) {
		String rspMsg = "";
		String openId = subEvent.getFromUserName();
		WeixinUserF findTmp = weiXinUserFService.findByOpenId(openId);
//		logger.info("ddddddddddddddddddd查询本地是否存在记录findTmp======="+findTmp);
		if(null != findTmp){
			weiXinUserFService.updateSubTimeByOpenId(subEvent.getCreateTime(), openId);
		}
	}
	
	/**
	 * 	1.重新设置订阅时间（当前系统时间）
	 *	2.删除取消订阅时间
	 *	3.设置默认分组
	 * @param openId
	 */
	private void getUserInfo(SubscribeEvent subEvent){
		String openId = subEvent.getFromUserName();
		String userInfoStr = WeiXinUtils.getUserDetailInfo(openId);
		
			UserDetailInfo userInfo = JSONObject.parseObject(userInfoStr, UserDetailInfo.class);
			WeixinUserF findTmp = weiXinUserFService.findByOpenId(openId);
			logger.info("b当前微信用户openId======="+openId);
			logger.info("b微信接口返回信息======="+userInfoStr);
			//{"subscribe":1,"openid":"oBZNGuEBtp-4BLwhY3Zo9QfEabXE","nickname":"彭大 it","sex":0,
			//"language":"zh_CN","city":"","province":"","country":"","headimgurl":"","subscribe_time":1412927356,"remark":""}
//			logger.info("aaaaaaaaaaaaaaaa获取用户UserDetailInfo======="+userInfo);
//			logger.info("aaaaaaaaaaaaaaaa获取用户UserDetailInfo======="+userInfo.getOpenId()+"<<<<>>>>>"+userInfo.getNickname());
//			logger.info("aaaaaaaaaaaaaaaa查询本地是否存在记录findTmp======="+findTmp);
			
			if(findTmp==null){
//				logger.info("bbbbbbbbbbbbbbbbbb本地无记录userInfo======="+userInfo);
//				logger.info("bbbbbbbbbbbbbbbbbb本地无记录获取用户UserDetailInfo======="+userInfo);
//				logger.info("bbbbbbbbbbbbbbbbbb本地无记录获取用户UserDetailInfo======="+userInfo.getOpenId()+"<<<<>>>>>"+userInfo.getNickname());
				if(null != userInfo && null != userInfo.getOpenid()){
					WeixinUserF weiXinUser = new WeixinUserF(userInfo);
					weiXinUserFService.save(weiXinUser);
				}
			}else{
//				logger.info("cccccccccccccccccccccccc本地有记录userInfo======="+userInfo);
//				logger.info("cccccccccccccccccccccccc本地有记录获取用户UserDetailInfo======="+userInfo);
//				logger.info("cccccccccccccccccccccccc本地有记录获取用户UserDetailInfo======="+userInfo.getOpenId()+"<<<<>>>>>"+userInfo.getNickname());
				if(null != userInfo && null != userInfo.getOpenid()){
					WeixinUserF weiXinUser = new WeixinUserF(userInfo);
					weiXinUserFService.updateWeixinUserByOpenId(weiXinUser);
				}else{
					weiXinUserFService.updateSubTimeByOpenId("",openId);
				}
			}
	}

	private String queryWinxinToken(String content, String opendId, String createTime){
		String rspMsg = "";
		content = content.replaceAll("%OPENID%", opendId);
		content = content.replaceAll("%MSGID%", createTime);
//		try {
//			String[] sArray=content.split("%TOKEN%");
//			int menuNo = 0;
//			//判断session是否过期，过期清理表并返回菜单序号>> start
//			long outtime = (long)CF.SessionTime*60*1000;//5分钟
//			long time =0;
//			WeiXinToken weiXinToken = weiXinTokenService.getByOpenId(opendId);
//			if(weiXinToken != null){
//				time = Long.valueOf(createTime)-Long.valueOf(String.valueOf(weiXinToken.getCreateDate()));
//				if(time<outtime){
//					menuNo = Integer.valueOf(weiXinToken.getLevelId());
//				}else{
//					weiXinTokenService.deleteByOpenId(opendId);
//				}
//			}
//			
//			//判断session是否过期，过期清理表并返回菜单序号>> end
//			WeiXinToken wsp = new WeiXinToken();
//			for(int i=0; i < sArray.length-1; i++){
//				wsp.setOpenId(opendId);
//				wsp.setLevelId(String.valueOf(menuNo+i));
//				wsp.setToken(WeiXinUtils.getToken(opendId, menuNo+i));
//				rspMsg +=sArray[i] + "LEVELID=" + wsp.getLevelId() + "&" + wsp.getToken();
//			}
//			rspMsg += sArray[sArray.length-1];
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return rspMsg;
	}
}
