package com.yitong.weixin.front.receive.wbank;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yitong.weixin.front.info.entity.ArticleF;
import com.yitong.weixin.front.info.entity.WeiXinToken;
import com.yitong.weixin.front.info.entity.WeixinMenuListF;
import com.yitong.weixin.front.info.entity.WeixinUserF;
import com.yitong.weixin.front.info.service.ArticleFService;
import com.yitong.weixin.front.info.service.BankObjectService;
import com.yitong.weixin.front.info.service.WeixinMenuListFService;
import com.yitong.weixin.front.info.service.WeixinUserFService;
import com.yitong.weixin.front.receive.func.Reply;
import com.yitong.weixin.front.receive.msgbean.SubscribeEvent;
import com.yitong.weixin.front.receive.utils.CF;
import com.yitong.weixin.front.receive.utils.WeiXinUtils;

@Service
public class Webmsg0101{

//	@Autowired
//	private WeixinMessageFService weiXinMessageFService;
	@Autowired
	private WeixinUserFService weiXinUserFService;
//	@Autowired
//	private WeixinMenuFService weiXinMenuFService;
	@Autowired
	private WeixinMenuListFService weiXinMenuListFService;
//	@Autowired
//	private WeiXinSessionService weiXinSessionService;
//	@Autowired
//	private WeiXinSessionParaService weiXinSessionParaService;
	@Autowired
	private ArticleFService articleFService;
	@Autowired
	private BankObjectService bankObjectService;

//	@Autowired
//	private WeiXinTokenService weiXinTokenService;
	
	
	public String pushClickMessage(SubscribeEvent subEvent){
		String rspMsg ="";
		try {
			if(CF.title_val2.equals(subEvent.getValue()) || CF.title_val4.equals(subEvent.getValue())){//网点查询 和 ATM
				//特殊处理 标题 关键字 
				//rspMsg = ">>地理位置"+System.currentTimeMillis()/1000;
				if("0".equals(subEvent.getEventKey())){//未上传地理位置信息 请上传
					rspMsg = CF.common_val5;
					return Reply.rspTextMsg(subEvent.getFromUserName(), subEvent.getToUserName(), rspMsg);
				}else{//已经存在地址位置信息
					String [] strs= subEvent.getEventKey().split("-");
					String loc_X = strs[0];
					String loc_Y = strs[1];
					String mark = "";
					if(CF.title_val2.equals(subEvent.getValue())){//网点查询
						mark = "1";
					}
					else if(CF.title_val4.equals(subEvent.getValue())){//ATM查询
						mark = "2";
					}
					List<Map> list = bankObjectService.findByLocation(mark, loc_X, loc_Y);
					rspMsg = Reply.topMsg(subEvent.getFromUserName(), subEvent.getToUserName());
					rspMsg += "<MsgType><![CDATA[news]]></MsgType>"
							+ "<ArticleCount>5</ArticleCount>"
							+ "<Articles>"
							+ Reply.rspLocationMsg("发送位置查找附近网点/ATM", "", CF.picurl_val1, CF.url_val1);//网点atm图片、访问页面  
					for (Map map : list) {
						String name = map.get("name").toString();
						String address = map.get("address").toString();
						String len = map.get("len").toString();
						String posx = map.get("posx").toString();
						String posy = map.get("posy").toString();
						String title = name+"\n" + "地址： " + address + "\n" + "距离： " + len +" 公里";
						rspMsg += Reply.rspLocationMsg(title, "", CF.picurl_val2, CF.url_val2+"?loc_X="+posx+"&loc_Y="+posy);//网点atm图片、访问页面 
					}
					
					//TODO: 网点token处理
					return rspMsg +"</Articles>" + "</xml>";
				}
			}else if(CF.title_val1.equals(subEvent.getValue())){//解除签约
				//特殊处理  标题 关键字 
				//rspMsg = ">>解除签约"+System.currentTimeMillis()/1000;
				weiXinUserFService.updateCustIdByOpenId("", subEvent.getFromUserName());
				ArticleF art = articleFService.findByTitle(CF.title_val1);
				rspMsg = art.getDescription();
				return Reply.rspUnknownMsg(subEvent.getFromUserName(), subEvent.getToUserName(), rspMsg);
			}else if(CF.title_val5.equals(subEvent.getValue())){//签约绑定
				WeixinUserF weiXinUser =  weiXinUserFService.findByOpenId(subEvent.getFromUserName());
				if(weiXinUser!=null){
					if(null !=weiXinUser.getCustId() && !"".equals(weiXinUser.getCustId())){//已签约
						rspMsg= "<xml>"+
						"<ToUserName><![CDATA["+subEvent.getFromUserName()+"]]></ToUserName>"+
						"<FromUserName><![CDATA["+subEvent.getToUserName()+"]]></FromUserName>"+
						"<CreateTime>"+subEvent.getCreateTime()+"</CreateTime>"+
						"<MsgType><![CDATA[text]]></MsgType>"+
						"<Content><![CDATA[亲，您已签约绑定重庆三峡银行微信银行！]]></Content>"+
						"</xml>";
					}else{//未签约
						// 签约标记
						rspMsg += CF.common_val2;
//						rspMsg +="\n<a href=\""+CF.ip+"/phoneDown/003.html?" +
//								"USERNAME=%USERNAME%&OPENID=%OPENID%"+
//								"&MSGID=%MSGID%&TOKEN=%TOKEN%&tag=1\">点击这里  立即签约</a>";
						//TODO:处理签约token
						rspMsg = replacePara(rspMsg, subEvent.getFromUserName(), subEvent.getCreateTime());
						return Reply.rspTextMsg(subEvent.getFromUserName(), subEvent.getToUserName(), rspMsg);
					}
				}
				return rspMsg;
			}
			
			//其他消息处理
			
			WeixinMenuListF weiXinList = weiXinMenuListFService.getByArticleId(subEvent.getEventKey() );
			if(weiXinList == null){
				ArticleF article = articleFService.get(subEvent.getEventKey());
				if(article != null){
//					String descript = article.getDescription().replaceAll("%TOKEN%", WeiXinUtils.getToken(subEvent.getFromUserName()));
					String descript = article.getDescription();
					rspMsg = replacePara(descript, subEvent.getFromUserName(), subEvent.getCreateTime());
					return Reply.rspUnknownMsg(subEvent.getFromUserName(), subEvent.getToUserName(), rspMsg);//进行普通消息或者图文消息发送
				}else{
					//error  
					rspMsg = CF.error_val;
					return Reply.rspTextMsg(subEvent.getFromUserName(), subEvent.getToUserName(), rspMsg);
				}
			}
			else{
				return getClickToContext(subEvent, weiXinList);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String replacePara(String content,String opendId,String createTime){
    	WeixinUserF weiXinUser = weiXinUserFService.findByOpenId(opendId);
    	if(weiXinUser!=null){//处理相关的参数
    		content = queryWinxinToken(content, opendId, createTime);//处理token
    		content = content.replaceAll("%OPENID%", opendId);
    		content = content.replaceAll("%MSGID%", createTime);
    		if(weiXinUser.getUserName()!=null){
    			String name = weiXinUser.getUserName();
				try {
					name = URLEncoder.encode(name,"UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				content = content.replaceAll("%USERNAME%", name);
    		}
    	}
    	return content;
    }

	private String getClickToContext(SubscribeEvent subEvent, WeixinMenuListF weiXinList) {
		String rspMsg = "";
		//WeixinUser weiXinUser =  weiXinUserService.findByOpenId(subEvent.getFromUserName());
		
		ArticleF article = articleFService.get(weiXinList.getArticleId());
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
		return "";
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
//				rspMsg +="\n<a href=\""+CF.ip+"/app/16/001.jsp?" +
//						"USERNAME=%USERNAME%&OPENID=%OPENID%"+
//						"&MSGID=%MSGID%&"+WeiXinUtils.getToken(subEvent.getFromUserName())+"\">点击这里  立即签约</a>";
				
				//TODO:处理签约token
				rspMsg = replacePara(rspMsg, subEvent.getFromUserName(), subEvent.getCreateTime());
			} 
		}
		return rspMsg;
	}

	private String queryWinxinToken(String content, String opendId, String createTime){
		String rspMsg = "";
		content = content.replaceAll("%OPENID%", opendId);
		content = content.replaceAll("%MSGID%", createTime);
		try {
			String[] sArray=content.split("%TOKEN%");
			int menuNo = 0;
			//超时token 在服务端进行判断删除
			//判断session是否过期，过期清理表并返回菜单序号>> start  
			//long outtime = (long)C.SessionTime*60*1000;//5分钟
			//long time =0;
//			WeiXinToken weiXinToken = weiXinTokenService.getByOpenId(opendId);
//			if(weiXinToken != null){
//				time = Long.valueOf(createTime)-Long.valueOf(weiXinToken.getCreateDate());
//				if(time<outtime){//超过5分钟清空url token
//					menuNo = Integer.valueOf(weiXinToken.getLevelId());
//				}else{
//					weiXinTokenService.deleteByOpenId(opendId);
//				}
//			}
			
			//判断session是否过期，过期清理表并返回菜单序号>> end
			//List<WeiXinToken> listWsp = new ArrayList<WeiXinToken>();
			WeiXinToken wsp = new WeiXinToken();
			for(int i=0; i < sArray.length-1; i++){
				wsp.setOpenId(opendId);
				wsp.setLevelId(String.valueOf(menuNo+i));
				wsp.setToken(WeiXinUtils.getToken(opendId, menuNo+i));
				rspMsg +=sArray[i] + "LEVELID=" + wsp.getLevelId() + "&" + wsp.getToken();
			}
			rspMsg += sArray[sArray.length-1];
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rspMsg;
	}
	
	public static void main(String[] args) {
		long time = (long)CF.SessionTime*60+5;
		System.out.println(time);
	}
}
