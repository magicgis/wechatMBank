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
import com.yitong.weixin.front.info.service.WeixinMessageFService;
import com.yitong.weixin.front.receive.func.Reply;
import com.yitong.weixin.front.receive.msgbean.LocationMessage;
import com.yitong.weixin.front.receive.utils.CF;


@Service
public class Webmsg02 {

	@Autowired
	private WeixinMessageFService weiXinMessageService;
//	@Autowired
//	private WeixinUserFService weiXinUserService;
//	@Autowired
//	private WeixinMenuFService weiXinMenuService;
//	@Autowired
//	private WeixinMenuListFService weiXinMenuListService;
	@Autowired
	private WeiXinSessionService weiXinSessionService;
	@Autowired
	private WeiXinSessionParaService weiXinSessionParaService;
	@Autowired
	private ArticleFService articleService;

	public String xmlToLocationClass(String xml){
		LocationMessage locationMsg = null;
		try {
			JAXBContext context = JAXBContext.newInstance(LocationMessage.class);
			Unmarshaller um = context.createUnmarshaller();
			ByteArrayInputStream is = new ByteArrayInputStream(xml.getBytes());
			locationMsg = (LocationMessage) um.unmarshal(is);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		String rspMsg = "";

		if(locationMsg!=null){
			WeixinMessageF weixinMessage = new WeixinMessageF(locationMsg);
			weiXinMessageService.save(weixinMessage);
			List<ArticleF> article = new ArrayList<ArticleF>();
			article.add(articleService.findByTitle(CF.title_val2));
			article.add(articleService.findByTitle(CF.title_val4));
			rspMsg += CF.common_val1; //"您要查找的全部消息如下："
			rspMsg = queryKeywords(rspMsg,article,weixinMessage);
		}
		//return Reply.rspTextMsg(locationMsg.getFromUserName(),locationMsg.getToUserName(),"地理位置识别结果接收体");
		return Reply.rspTextMsg(locationMsg.getFromUserName(),locationMsg.getToUserName(),rspMsg);
	}

	private String queryKeywords(String rspMsg,List<ArticleF> wML,WeixinMessageF wm){
		//判断session是否过期，过期清理表并返回菜单序号
		int menuNo = weiXinSessionService.getMenuNo(wm.getFromUser(),StringUtils.parseDate2SecondStr(wm.getCreateDate()));
		List<WeiXinSessionPara> listWsp = new ArrayList<WeiXinSessionPara>();

		for(int i=0;i<wML.size();i++){
			WeiXinSessionPara wsp = new WeiXinSessionPara();
			wsp.setOpenId(wm.getFromUser());
			wsp.setMenuNo(menuNo+i);
			if(CF.title_val2.equals(wML.get(i).getTitle()) || CF.title_val4.equals(wML.get(i).getTitle())){
				//特殊处理
				if(wm.getLocX() == null || "".equals(wm.getLocX())){
					wsp.setKey("0");
				}else{
					wsp.setKey(String.valueOf(wm.getLocX()+"-"+wm.getLocY()+"-"+wm.getLocScale()));
				}
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
