/**
 * 
 */
package com.yitong.weixin.modules.wechat.web;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.support.json.JSONUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yitong.weixin.common.utils.StringUtils;
import com.yitong.weixin.modules.cms.service.ArticleService;
import com.yitong.weixin.modules.wechat.entity.WeixinMessage;
import com.yitong.weixin.modules.wechat.entity.WeixinUser;
import com.yitong.weixin.modules.wechat.model.BaseMessage;
import com.yitong.weixin.modules.wechat.model.ReplyMessage;
import com.yitong.weixin.modules.wechat.model.ReplyMessage.E_ReplyType;
import com.yitong.weixin.modules.wechat.model.ReplyMessageUtil;
import com.yitong.weixin.modules.wechat.service.WeixinMessageService;
import com.yitong.weixin.modules.wechat.service.WeixinUserService;
import com.yitong.weixin.modules.wechat.utils.AcctUtils;
import com.yitong.weixin.modules.wechat.utils.WeixinUtils;

/**
 * 客户消息、及群发消息
 * @author ZhangPeiZhong
 *
 */
@Controller
@RequestMapping(value = "${frontPath}/custom")
public class CustomMsgController {
	public static final String KeFuPostMsg = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=%s";
	public static final String newsMsg = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=%s";
	@Autowired
	private WeixinUserService weiXinUserService;
    @Resource
	private WeixinMessageService weiXinMessageService;
    @Resource
    private ArticleService articleService;
	
	@ResponseBody
	@RequestMapping(value = "hight")
	public String sendCust(@RequestParam("openId") String openId
			,@RequestParam(value="artId", required=false) String articleId
			,@RequestParam(value="msg", required=false) String msg)
	{
		WeixinUser weiXinUser =  weiXinUserService.findByOpenId(openId);
		
		if(null == weiXinUser)
		{
			return "{\"STATUS\":\"2\",\"MSG\":\"用户不存在\"}";
		}

//		weiXinSessionService.clearSession(openId);
		
		String sendMsg = null;
		String content = null;
		// 如果msg不为空，发送msg
		if(StringUtils.isNotBlank(msg))
		{
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("content", msg);
			map.put("touser", openId);
			map.put("msgtype", "text");
			map.put("text", map1);
			sendMsg = JSONUtils.toJSONString(map);
			content = msg;
		}
		else if(articleId != null)
		{
			// 如果是图文消息，发送图文消息
			ReplyMessage replyMsg = new ReplyMessage(E_ReplyType.artl, null, articleId);
			BaseMessage baseMsg = new BaseMessage(openId, AcctUtils.getOpenId(),"news");
			replyMsg.iniMsg(baseMsg );
			String rtMsg = ReplyMessageUtil.toMsg(replyMsg, weiXinUser);
			sendMsg = msg2(rtMsg);
			content = rtMsg;
		}
		else
			return "{\"STATUS\":\"2\",\"MSG\":\"请检查参数\"}";
		
		if(null == sendMsg)
			return "{\"STATUS\":\"2\",\"MSG\":\"发送失败\"}";
		
		try {
			String url = String.format(KeFuPostMsg,WeixinUtils.getAccessToken());
			String s1 = WeixinUtils.postWeiXin(url, sendMsg);
			System.out.println(sendMsg);
			System.out.println(s1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{\"STATUS\":\"2\",\"MSG\":\"发送失败\"}";
		}
		
		// 保存回复消息
		WeixinMessage saveMsg = new WeixinMessage();
		saveMsg.setCreateDate(new Date());
		saveMsg.setFromUser(AcctUtils.getOpenId());
		saveMsg.setToUser(openId);
		saveMsg.setContent(content);
		saveMsg.setMsgType("text");
		weiXinMessageService.save(saveMsg);
		
		return "{\"STATUS\":\"1\",\"MSG\":\"已发送\"}";
	}
	
	
	/*
	 * 发送客服消息
	 * 是图文消息时，需要把json数据转换成消息格式
	*/
	private String msg2(String msg)
	{
		// 转换后的内容
		SAXReader reader = new SAXReader();
		InputStream in;
		try {
			in = new ByteArrayInputStream(msg.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
		try {
			Document doc = reader.read(in);
			Element root = doc.getRootElement();
			
			HashMap<String, Object> rsMap = Maps.newHashMap();
			
			rsMap.put(NewsMsg_2Const.touser, root.element(NewsMsg_1Const.ToUserName).getText());
			rsMap.put(NewsMsg_2Const.msgtype, root.element(NewsMsg_1Const.MsgType).getText());
			
			HashMap<String, Object> newsMap = Maps.newHashMap();
			List<HashMap<String, String>> articles = Lists.newArrayList();
			
			Iterator itItem = root.element(NewsMsg_1Const.Articles).elementIterator();
			for(; itItem.hasNext();)
			{
				Element item = (Element)itItem.next();
				List<Element> eList = item.elements();
				
				HashMap<String, String> art = Maps.newHashMap();
				for(Element e : eList)
				{
					String name = e.getQName().getName();
					if(name.equals(NewsMsg_1Const.Title))
						art.put(NewsMsg_2Const.title, e.getTextTrim());
					else if(name.equals(NewsMsg_1Const.Description))
						art.put(NewsMsg_2Const.description, e.getTextTrim());
					else if(name.equals(NewsMsg_1Const.PicUrl))
						art.put(NewsMsg_2Const.picurl, e.getTextTrim());
					else if(name.equals(NewsMsg_1Const.Url))
						art.put(NewsMsg_2Const.url, e.getTextTrim());
				}
				articles.add(art);
			}
			newsMap.put(NewsMsg_2Const.articles, articles);
			rsMap.put(NewsMsg_2Const.news, newsMap);
			
			return JSONUtils.toJSONString(rsMap);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	static class NewsMsg_1Const{
		public static final String ToUserName = "ToUserName";
		public static final String FromUserName = "FromUserName";
		public static final String CreateTime = "CreateTime";
		public static final String MsgType = "MsgType";
		public static final String Articles = "Articles";
		
		public static final String item = "item";
		public static final String Title = "Title";
		public static final String Description = "Description";
		public static final String PicUrl = "PicUrl";
		public static final String Url = "Url";
	}
	static class NewsMsg_2Const{
		public static final String touser = "touser";
		public static final String msgtype = "msgtype";
		public static final String news = "news";
		
		public static final String articles = "articles";
		public static final String title = "title";
		public static final String description = "description";
		public static final String url = "url";
		public static final String picurl = "picurl";
		
	}
	
}
