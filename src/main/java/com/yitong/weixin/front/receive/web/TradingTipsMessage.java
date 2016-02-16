package com.yitong.weixin.front.receive.web;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.yitong.weixin.front.info.entity.SendTemplateLogs;
import com.yitong.weixin.front.info.service.SendTemplateLogsService;
import com.yitong.weixin.front.info.service.WeixinUserFService;
import com.yitong.weixin.front.receive.msgbean.TextMessage;
import com.yitong.weixin.front.receive.msgbean.TradingTips;
import com.yitong.weixin.front.receive.utils.CF;
import com.yitong.weixin.front.receive.utils.WeiXinUtils;

@Service
public class TradingTipsMessage {
	@Autowired
	private SendTemplateLogsService sendTemplateLogsService;
//	@Autowired
//	WeixinUserFService weixinUserFService;
	public void saveorupdateLogs(SendTemplateLogs sendTemplateLogs){
		sendTemplateLogsService.save(sendTemplateLogs);
	}

	public void pushMsg(String xml) throws Exception{
		TradingTips tipsMsg = null;
		try {
			JAXBContext context = JAXBContext.newInstance(TextMessage.class);
			Unmarshaller um = context.createUnmarshaller();
			ByteArrayInputStream is = new ByteArrayInputStream(xml.getBytes());
			tipsMsg = (TradingTips) um.unmarshal(is);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		pushMsg1(tipsMsg);
	}
	public void pushMsg1(TradingTips tips) throws Exception{
		/*String msgType = tips.getMsgType();
		if("trade".equals(msgType)){
			
		}*/
		//WeixinUser weixinUser = weixinUserService.findByAccNo(tips.getCustId());
		//String openId = weixinUser.getOpenId();
		
		String openId = "oKyyXjog-1LJ47vOzD6MB1HVTPZk";
		Map<String, Object> map = new HashMap<String, Object>(); 
		System.out.println("=======微信号： oKyyXjog-1LJ47vOzD6MB1HVTPZk");
		
		map.put("touser", openId);
		map.put("template_id", CF.common_val4);//wy2Jy9nFsVbo7V69oxHKMw7plFBtW0GUCHQEDhENqAQ
		map.put("url", "http://weixin.qq.com/download");
		map.put("topcolor", "#FF0000");
		
		/*	
		dataMap.put("title", "您好，您的信用卡收到1000元汇款。");
		dataMap.put("productType", "信用卡");
		dataMap.put("time", "2013年9月30日 17:58");
		dataMap.put("type", "收款");
		dataMap.put("number", "1000元");
		dataMap.put("remark", "备注：如有疑问，请拨打咨询热线123323。");
		*/
		Map title = new HashMap();
		title.put("value", "您好，您的借记卡转入1000.00元。");
		title.put("color", "#173177");
		Map productType = new HashMap();
		productType.put("value", "尾号：2783");
		productType.put("color", "#173177");
		Map time = new HashMap();
		time.put("value", "2013年9月30日 17:58");
		time.put("color", "#173177");
		Map type = new HashMap();
		type.put("value", "转入");
		type.put("color", "#173177");
		Map number = new HashMap();
		number.put("value", "1000.00元");
		number.put("color", "#173177");
		Map remark = new HashMap();
		remark.put("value", "备注：如有疑问，请拨打客服热线：023-96968。");
		remark.put("color", "#173177");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("title", title);
		dataMap.put("productType", productType);
		dataMap.put("time", time);
		dataMap.put("type", type);
		dataMap.put("number", number);
		dataMap.put("remark", remark);
		map.put("data", dataMap);

		String url = String.format(CF.TemplateMsg,WeiXinUtils.getAccessToken());
		
		String result = WeiXinUtils.postWeiXin(url, JSONObject.toJSONString(map));
		
		JSONObject jsonStr = JSONObject.parseObject(result);
		String msgid = jsonStr.getString("msgid");
		
		SendTemplateLogs sendTemplateLogs = new SendTemplateLogs(msgid, openId, "custId", "0", System.currentTimeMillis()/1000+"", "createBy");
		saveorupdateLogs(sendTemplateLogs);//保存发送日志
		System.out.println("pushMsg1-OK---------------->>");
	}
}
