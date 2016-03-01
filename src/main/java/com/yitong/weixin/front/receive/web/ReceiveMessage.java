package com.yitong.weixin.front.receive.web;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.yitong.weixin.front.info.entity.WeixinUserF;
import com.yitong.weixin.front.info.service.WeixinUserFService;
import com.yitong.weixin.front.receive.func.Reply;
import com.yitong.weixin.front.receive.msgbean.BaseMessageF;
import com.yitong.weixin.front.receive.msgbean.UserDetailInfo;
import com.yitong.weixin.front.receive.utils.EnumMsgType;
import com.yitong.weixin.front.receive.utils.WeiXinUtils;
import com.yitong.weixin.front.receive.wbank.Webmsg01;
import com.yitong.weixin.front.receive.wbank.Webmsg02;
import com.yitong.weixin.front.receive.wbank.Webmsg03;

@Controller
@RequestMapping(value = "${frontPath}/weixin")
public class ReceiveMessage {
	private Logger logger = LoggerFactory.getLogger(ReceiveMessage.class);

	@Autowired
	private Webmsg01 webmsg01;
	@Autowired
	private Webmsg02 webmsg02;
	@Autowired
	private Webmsg03 webmsg03;
//	@Autowired
//	private WeixinMessageFService weiXinMessageFService;
	@Autowired
	private WeixinUserFService weiXinUserFService;
//	@Autowired
//	private WeixinMenuFService weiXinMenuFService;
//	@Autowired
//	private WeixinMenuListFService weiXinMenuListFService;
//	@Autowired
//	private WeiXinSessionService weiXinSessionService;
//	@Autowired
//	private WeiXinSessionParaService weiXinSessionParaService;
//	@Autowired
//	private ArticleFService articleFService;
	
	DecimalFormat df = new DecimalFormat("###,###,###,##0.00");

	JAXBContext context;

	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public String weiXinMessage(@RequestParam("signature") String signature,
			@RequestParam("timestamp") String timestamp,
			@RequestParam("nonce") String nonce,
			@RequestParam("echostr") String echostr) {
		
		logger.debug("WEIXIN_URL======1======"+signature+"<>"+timestamp+"<>"+nonce);
		
		return echostr;
		
		/*if(WeiXinUtils.checkSignature(signature,timestamp,nonce)){
			return echostr;
		}else{
			return "";
		}*/
	}
	
	@RequestMapping(value = "")
	@ResponseBody
	public String weiXinMessage(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		logger.debug("WEIXIN_URL====2========"+request.getRequestURI());
		logger.debug("WEIXIN_URL====3========"+request.getRemoteAddr());
		logger.debug("WEIXIN_URL====4========"+request.getRemoteHost());
		logger.debug("WEIXIN_URL====5========"+request.getRemotePort());
		
		String xml = WeiXinUtils.readStreamParameter(request.getInputStream());
		//解析xml消息
		BaseMessageF baseMessage = getBaseMessage(xml);
		System.out.println(baseMessage.getToUserName()+"==============>"+baseMessage.getFromUserName()+"==============>\n");
		//检测签名信息
		if(WeiXinUtils.checkSignature(request, baseMessage.getToUserName())){
			System.out.println(baseMessage.getToUserName()+"==============>"+baseMessage.getFromUserName()+"==============>\n");
			if(baseMessage!=null){
				//这里不保存用户数据--在事件中保存
//				WeixinUser findTmp = weiXinUserService.findByOpenId(baseMessage.getFromUserName());
//				if(findTmp==null){
//					getUserInfo(baseMessage.getFromUserName(), baseMessage.getToUserName());
//				}
				//获取消息类型
				String msgType  = baseMessage.getMsgType();
				String result = "";
				switch (EnumMsgType.getEnumMsgType(msgType)) {
					case text:
						result = webmsg01.xmlToTextClass(xml);//文字消息处理
						break;
					case image:
						result = Reply.rspTextMsg(baseMessage.getFromUserName(),baseMessage.getToUserName(),"暂不支持图片信息交互！");
						break;
					case voice:
						result = Reply.rspTextMsg(baseMessage.getFromUserName(),baseMessage.getToUserName(),"暂不支持声音信息交互！");
						break;
					case video:
						result = Reply.rspTextMsg(baseMessage.getFromUserName(),baseMessage.getToUserName(),"暂不支持视频信息交互！");
						break;
					case location:
						result = webmsg02.xmlToLocationClass(xml);//位置信息处理
						break;
					case link:
						result = Reply.rspTextMsg(baseMessage.getFromUserName(),baseMessage.getToUserName(),"暂不支持链接信息交互！");
						break;
					case event:
						result = webmsg03.xmlToEventClass(xml);//事件信息处理
						break;
					default:
						result = Reply.rspTextMsg(baseMessage.getFromUserName(),baseMessage.getToUserName(),"暂不支持这种信息交互！");
						break;

				}
				print(response, result,baseMessage.getFromUserName(),baseMessage.getCreateTime());
				System.out.println("print-OK------------->>\n"+result);
				System.out.println("\n DateTime ==============>> "+new Date());
			}
		}
		return null;
	}
	

	//向请求端发送返回数据  
    public void print(HttpServletResponse response,String content,String opendId,String createTime){  
    	WeixinUserF weiXinUser = weiXinUserFService.findByOpenId(opendId);
    	if(weiXinUser!=null){//处理相关的参数
    		/*content = content.replaceAll("%NAME%", weiXinUser.getUserName()).
    		replaceAll("%OPENID%", weiXinUser.getOpenId()).
    		replaceAll("%sex%", weiXinUser.getSex().equals("1")?"男":"女").
    		replaceAll("%city%", weiXinUser.getCity()).
    		replaceAll("%country%", weiXinUser.getCountry()).
    		replaceAll("%province%", weiXinUser.getProvince());*/
    		
    		content = content.replaceAll("%OPENID%", weiXinUser.getOpenId());
    		content = content.replaceAll("%MSGID%", createTime);
    		if(weiXinUser.getUserName()!=null){
    			String name = weiXinUser.getUserName();
				try {
					name = URLEncoder.encode(name,"UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			content = content.replaceAll("%USERNAME%", name);
    		}
    		if(weiXinUser.getBankcardId1()!=null)
    			content = content.replaceAll("%EBUSER_NO%", weiXinUser.getBankcardId1());
    	}
    	
        try{  
        	response.setCharacterEncoding("UTF-8");
        	response.getWriter().print(content);  
        	response.getWriter().flush();  
        	response.getWriter().close();  
        }catch(Exception e){  
              
        }  
    } 
	private void getUserInfo(String openId, String accOpenId){
		String userInfoStr = WeiXinUtils.getUserDetailInfo(openId, accOpenId);
		UserDetailInfo userInfo = JSONObject.parseObject(userInfoStr, UserDetailInfo.class);
		WeixinUserF findTmp = weiXinUserFService.findByOpenId(openId);
		//重新设置订阅时间（当前系统时间）
		userInfo.setSubscribeTime(""+System.currentTimeMillis()/1000);
		logger.info("a当前微信用户openId======="+openId);
		logger.info("a微信接口返回信息======="+userInfoStr);
		if(findTmp==null){
			if(null != userInfo && null != userInfo.getOpenid()){
				WeixinUserF weiXinUser = new WeixinUserF(userInfo); 
				weiXinUserFService.save(weiXinUser);
			}
		}else{
			if(null != userInfo && null != userInfo.getOpenid()){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userName", userInfo.getNickname());
				map.put("sex", userInfo.getSex());
				map.put("city", userInfo.getCity());
				map.put("country", userInfo.getCountry());
				map.put("headImgUrl", userInfo.getHeadimgurl());
				map.put("openId", openId);
				map.put("cancelSubscribeTime", "");
				
				weiXinUserFService.updateWeixinUserByOpenId2(map);
			}
		}
	}

	/**
	 * 在完整解析xml消息时,先提前获取消息类型
	 * @param xmlStr
	 * @return
	 */
	private BaseMessageF getBaseMessage(String xmlStr){
		if (context == null) {
				try {
					context = JAXBContext.newInstance(BaseMessageF.class);
				} catch (JAXBException e) {
					e.printStackTrace();
				}
		}
		Unmarshaller um;
		try {
			um = context.createUnmarshaller();
			ByteArrayInputStream is = new ByteArrayInputStream(
					xmlStr.getBytes());
			BaseMessageF baseMsg = (BaseMessageF) um.unmarshal(is);
			return baseMsg;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
}
