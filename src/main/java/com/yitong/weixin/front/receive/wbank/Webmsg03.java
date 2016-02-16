package com.yitong.weixin.front.receive.wbank;

import java.io.ByteArrayInputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yitong.weixin.front.info.service.SendTemplateLogsService;
import com.yitong.weixin.front.receive.func.Reply;
import com.yitong.weixin.front.receive.msgbean.SubscribeEvent;
import com.yitong.weixin.front.receive.utils.EnumEventType;


@Service
public class Webmsg03 {

//	@Autowired
//	private WeixinMessageFService weiXinMessageService;
//	@Autowired
//	private WeixinUserFService weiXinUserService;
//	@Autowired
//	private WeixinMenuFService weiXinMenuService;
//	@Autowired
//	private WeixinMenuListFService weiXinMenuListService;
//	@Autowired
//	private WeiXinSessionService weiXinSessionService;
//	@Autowired
//	private WeiXinSessionParaService weiXinSessionParaService;
//	@Autowired
//	private ArticleFService articleService;
//	@Autowired
//	private BankObjectService bankObjectService;
	@Autowired
	private SendTemplateLogsService sendTemplateLogsService;
	@Autowired
	private Webmsg0301 webmsg0301;

	public String xmlToEventClass(String xml) {
		SubscribeEvent subEvent = null;
		try {
			JAXBContext context = JAXBContext.newInstance(SubscribeEvent.class);
			Unmarshaller um = context.createUnmarshaller();
			ByteArrayInputStream is = new ByteArrayInputStream(xml.getBytes());
			subEvent = (SubscribeEvent) um.unmarshal(is);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		if(subEvent!=null){
			System.out.println(" EnumEventType.getEnumEventType(subEvent.getEvent()) = [" + EnumEventType.getEnumEventType(subEvent.getEvent()) + "]");
			switch (EnumEventType.getEnumEventType(subEvent.getEvent())) {
			case subscribe://用户订阅公众账号
				
				return webmsg0301.pushSubscibeMessage(subEvent);
			case scan: 	   //用户已关注公众账号，又重新扫描二维码推送事件
				
				break;
			case location: //同意上报位置信息后，微信服务器发送信息到手机
				
				break;
			case click:    //菜单点击事件
				return webmsg0301.pushClickMessage(subEvent);
			case templatesendjobfinish:	//推送模版
				sendTemplateLogsService.updateStatus(subEvent.getMsgId(), subEvent.getStatus());
				break;
			case unsubscribe://用户取消订阅公众账号
				webmsg0301.pushUnSubscibeMessage(subEvent);
				break;
			case view:
				break;
			}
			return Reply.responseSubMsg(subEvent.getFromUserName(), subEvent.getToUserName());
		}
		return null;
	}
}
