package com.yitong.weixin.front.receive.wbank;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yitong.weixin.common.utils.StringUtils;
import com.yitong.weixin.front.info.entity.ArticleF;
import com.yitong.weixin.front.info.entity.WeiXinSession;
import com.yitong.weixin.front.info.entity.WeiXinSessionPara;
import com.yitong.weixin.front.info.entity.WeixinMessageF;
import com.yitong.weixin.front.info.service.ArticleFService;
import com.yitong.weixin.front.info.service.WeiXinSessionParaService;
import com.yitong.weixin.front.info.service.WeiXinSessionService;
import com.yitong.weixin.front.info.service.WeixinMenuFService;
import com.yitong.weixin.front.info.service.WeixinMenuListFService;
import com.yitong.weixin.front.info.service.WeixinMessageFService;
import com.yitong.weixin.front.info.service.WeixinUserFService;
import com.yitong.weixin.front.receive.func.Reply;
import com.yitong.weixin.front.receive.msgbean.SubscribeEvent;
import com.yitong.weixin.front.receive.msgbean.TextMessage;
import com.yitong.weixin.front.receive.utils.CF;
import com.yitong.weixin.front.receive.utils.WeiXinUtils;
import com.yitong.weixin.front.receive.web.TradingTipsMessage;

@Service
public class Webmsg01{

	@Autowired
	private WeixinMessageFService weiXinMessageFService;
//	@Autowired
//	private WeixinUserFService weiXinUserFService;
//	@Autowired
//	private WeixinMenuFService weiXinMenuFService;
//	@Autowired
//	private WeixinMenuListFService weiXinMenuListFService;
	@Autowired
	private WeiXinSessionService weiXinSessionService;
	@Autowired
	private WeiXinSessionParaService weiXinSessionParaService;
	@Autowired
	private ArticleFService articleFService;
//	@Autowired
//	private TradingTipsMessage tradingTipsMessage;
	@Autowired
	private Webmsg0101 webmsg0101;
	
	public String xmlToTextClass(String xml) {
		TextMessage textMsg = null;
		try {
			JAXBContext context = JAXBContext.newInstance(TextMessage.class);
			Unmarshaller um = context.createUnmarshaller();
			ByteArrayInputStream is = new ByteArrayInputStream(xml.getBytes());
			textMsg = (TextMessage) um.unmarshal(is);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		String rspMsg = "";
		try {
			if(textMsg == null){
				return null;
			}else{
				
				/*TradingTips tips = null;
				tradingTipsMessage.pushMsg1(tips);
				System.out.println("xmlToTextClass-OK-------------------->>");
				*/
				
				WeixinMessageF weixinMessage = new WeixinMessageF(textMsg);
				
				weixinMessage.setContent(textMsg.getContent().trim());
				weiXinMessageFService.save(weixinMessage);
				if(WeiXinUtils.isNumeric(weixinMessage.getContent())){//判断用户输入信息是否是数字
					//首先判断session是否过期
					weiXinSessionService.isSessionOver(weixinMessage.getFromUser(),StringUtils.parseDate2SecondStr(weixinMessage.getCreateDate()));;
					WeiXinSessionPara wxsp = weiXinSessionParaService.findByMenuNo(weixinMessage.getFromUser(),weixinMessage.getContent());
					if(wxsp!=null){ //session_para中查询到相应的code
						SubscribeEvent subEvent = new SubscribeEvent();
						subEvent.setEventKey(wxsp.getKey());
						subEvent.setValue(wxsp.getValue());
						subEvent.setCreateTime(StringUtils.parseDate2SecondStr(weixinMessage.getCreateDate()));
						subEvent.setFromUserName(weixinMessage.getFromUser());
						subEvent.setToUserName(weixinMessage.getToUser());
						return webmsg0101.pushClickMessage(subEvent);//推送信息给用户
					}
				}
				//用户输入信息不是数字 匹配消息 显示给用户
				
				List<ArticleF> article = articleFService.findByDescription(weixinMessage.getContent());
				
				if(article == null || article.size()==0){//未匹配到相关信息
					//error
					rspMsg = CF.error_val;
					return Reply.rspTextMsg(weixinMessage.getFromUser(), weixinMessage.getToUser(), rspMsg);
				}else if(article.size()>0){//匹配到相关信息
					rspMsg += CF.common_val1; //"您要查找的全部消息如下："
					rspMsg = queryKeywords(rspMsg,article,weixinMessage);//生成带编号消息
					return Reply.rspTextMsg(weixinMessage.getFromUser(), weixinMessage.getToUser(), rspMsg);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String queryKeywords(String rspMsg,List<ArticleF> wML,WeixinMessageF wm){
		//判断session是否过期，过期清理表并返回菜单序号
		int menuNo = weiXinSessionService.getMenuNo(wm.getFromUser(),StringUtils.parseDate2SecondStr(wm.getCreateDate()));
		List<WeiXinSessionPara> listWsp = new ArrayList<WeiXinSessionPara>();

		for(int i=0;i<wML.size();i++){
			WeiXinSessionPara wsp = new WeiXinSessionPara();
			wsp.setOpenId(wm.getFromUser());
			wsp.setMenuNo(menuNo+i);
			if(CF.title_val2.equals(wML.get(i).getTitle()) || CF.title_val4.equals(wML.get(i).getTitle())){//网点查询 和 ATM
				//特殊处理
				if(wm.getLocX() == null || "".equals(wm.getLocX())){//判断是否需要用户输入当前位置信息 key值为0 需要用户输入当前地理位置信息
					wsp.setKey("0");
				}else{//用户已经上传过当前地理位置信息
					wsp.setKey(String.valueOf(wm.getLocX()+"-"+wm.getLocY()+"-"+wm.getLocScale()));
				}
			}else{//正常处理其他消息
				wsp.setKey(String.valueOf(wML.get(i).getId()));
			}
			wsp.setValue(wML.get(i).getTitle());
			weiXinSessionParaService.save(wsp);
			listWsp.add(wsp);
		}
		WeiXinSession wxs = weiXinSessionService.getByOpenId(wm.getFromUser());
		if(wxs!=null){
			wxs.setNextMenuNo(menuNo+wML.size());
			wxs.setLastVisitTime(StringUtils.parseDate2SecondStr(wm.getCreateDate()));
		}else{
			wxs = new WeiXinSession();
			wxs.setOpenId(wm.getFromUser());
			wxs.setLastVisitTime(StringUtils.parseDate2SecondStr(wm.getCreateDate()));
			wxs.setNextMenuNo(wML.size()+1);
			wxs.setErrorNum(0);
		}
		weiXinSessionService.save(wxs);
		
		for(WeiXinSessionPara tmp:listWsp){
			rspMsg += "\n【"+tmp.getMenuNo()+"】"+tmp.getValue();
		}
		return rspMsg;
	}

}
