/**
 * 
 */
package com.yitong.weixin.modules.wechat.model;

import com.yitong.weixin.common.utils.SpringContextHolder;
import com.yitong.weixin.modules.cms.entity.Article;
import com.yitong.weixin.modules.cms.service.ArticleService;
import com.yitong.weixin.modules.wechat.entity.WeixinUser;
import com.yitong.weixin.modules.wechat.model.ReplyMessage.E_ReplyType;
import com.yitong.weixin.modules.wechat.utils.IWeixinMsgDataUtils;
import com.yitong.weixin.modules.wechat.utils.WxCommMsg;

/**
 * @author ZhangPeiZhong
 *
 */
public class ReplyMessageUtil {

	public static final String uploadMedia  = "http://api.weixin.qq.com/cgi-bin/media/upload?access_token=%s&type=image";
	public static final String newsUpload = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=%s";
	
	/**
	 * 发送被动消息
	 * @param replyMsg
	 * @param weiXinUser
	 * @return
	 */
	public static String toMsg(ReplyMessage replyMsg, WeixinUser weiXinUser){
		if(null == replyMsg)
			return null;

		ArticleService articleService = SpringContextHolder.getBean(ArticleService.class);
		IWeixinMsgDataUtils msgDataUtils = SpringContextHolder.getBean(IWeixinMsgDataUtils.class);
		
		String msg = null;
		E_ReplyType replyType = null;
		
		// 1 查找文章
		if(replyMsg.getReplyType() == E_ReplyType.artl)
		{
			Article article = articleService.get(replyMsg.getArticleId());
			msg = replyMsg.getMessage() + article.getDescription();
			
			// 判断文章的消息类型
			if(article.getMsgType() != 0)
				replyType = E_ReplyType.txt_h;
			else
				replyType = E_ReplyType.txt;
		}
		else
		{
			msg = replyMsg.getMessage();
			replyType = replyMsg.getReplyType();
		}
		
		StringBuilder sb = new StringBuilder(msg);
		sb.append(replyMsg.getAfterMessage());
		
		switch(replyType)
		{
		case txt:
			return WxCommMsg.rspTextTypeMsg(replyMsg.getFromUserName(), replyMsg.getToUserName()
					, msgDataUtils.replacePara(sb.toString(), weiXinUser, replyMsg.getCreateTime()));
		case txt_h:
			return WxCommMsg.rspOtherTypeMsg(replyMsg.getFromUserName(), replyMsg.getToUserName()
					, msgDataUtils.replacePara(sb.toString(), weiXinUser, replyMsg.getCreateTime()));
		}
		
		return null;
	}
	
}
