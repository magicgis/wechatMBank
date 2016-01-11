/**  
* @Title: WxMsgUtils.java
* @Package com.yitong.weixin.utils
* @Description: TODO
* @author zpz
* @date 2014年4月1日 上午9:22:51
* @version V1.0  
*/ 
package com.yitong.weixin.modules.wechat.utils;

/**
 * @ClassName: WxMsgUtils
 * @Description: 微信消息拼接
 * @author zpz
 * @date 2014年4月1日 上午9:22:51
 *
 */

public class WxCommMsg {
	
	/**
	 * 
	* @Title: responseSubMsg
	* @Description: 用户关注后，消息反馈
	* @param toUser
	* @param fromUser
	* @return
	* @return String
	 */
	public static String responseSubMsg(String toUser,String fromUser){
		return rspTextTypeMsg(toUser, fromUser, "谢谢关注，敬请期待！");
	}
	
	/**
	 * 
	* @Title: rspOtherTypeMsg
	* @Description: 消息
	* @param toUser
	* @param fromUser
	* @param message
	* @return
	* @return String
	 */
	public static String rspTextTypeMsg(String toUser,String fromUser,String message){
		StringBuilder buf = new StringBuilder();
		
		buf.append("<xml>")
			.append("<ToUserName><![CDATA[").append(toUser).append("]]></ToUserName>")
			.append("<FromUserName><![CDATA[").append(fromUser).append("]]></FromUserName>")
			.append("<CreateTime>").append(System.currentTimeMillis()/1000).append("</CreateTime>")
			.append("<MsgType><![CDATA[text]]></MsgType>")
			.append("<Content><![CDATA[").append(message).append("]]></Content>")
			.append("</xml>");
		return buf.toString();
	}
	public static String rspOtherTypeMsg(String toUser,String fromUser,String message){
		StringBuilder buf = new StringBuilder();
		
		buf.append("<xml>")
			.append("<ToUserName><![CDATA[").append(toUser).append("]]></ToUserName>")
			.append("<FromUserName><![CDATA[").append(fromUser).append("]]></FromUserName>")
			.append("<CreateTime>").append(System.currentTimeMillis()/1000).append("</CreateTime>")
			//.append("<MsgType><![CDATA[text]]></MsgType>")
			.append(message)
			.append("</xml>");
		return buf.toString();
	}
	
	public static String rspImageTextMsg(String toUser,String fromUser){
		StringBuilder buf = new StringBuilder();
		buf.append("<xml>")
			.append("<ToUserName><![CDATA[").append(toUser).append("]]></ToUserName>")
			.append("<FromUserName><![CDATA[").append(fromUser).append("]]></FromUserName>")
			.append("<CreateTime>").append(System.currentTimeMillis()/1000).append("</CreateTime>")
			.append("<MsgType><![CDATA[news]]></MsgType>")
			.append("<ArticleCount>3</ArticleCount>")
			.append("<Articles>")
			.append("<item>")
			.append("<Title><![CDATA[手机银行客户端下载]]></Title> ")
			.append("<Description><![CDATA[手机银行客户端下载]]></Description>")
			.append("<PicUrl><![CDATA[http://www.yitong.com.cn/weixin/userfiles/15/images/app_0.png]]></PicUrl>")
			.append("<Url><![CDATA[www.baidu.com]]></Url>")
			.append("</item>")
			.append("<item>")
			.append("<Title><![CDATA[iPhone 客户端下载]]></Title>")
			.append("<Description><![CDATA[iPhone 客户端下载]]></Description>")
			.append("<PicUrl><![CDATA[http://www.yitong.com.cn/weixin/userfiles/15/images/app_1.png]]></PicUrl>")
			.append("<Url><![CDATA[https://itunes.apple.com/cn/app/hai-kou-lian-he-nong-shang/id718021934?mt=8]]></Url>")
			.append("</item>")
			.append("<item>")
			.append("<Title><![CDATA[Android 客户端下载（敬请关注）]]></Title>")
			.append("<Description><![CDATA[Android 客户端下载]]></Description>")
			.append("<PicUrl><![CDATA[http://www.yitong.com.cn/weixin/userfiles/15/images/app_2.png]]></PicUrl>")
			.append("<Url><![CDATA[www.sina.com]]></Url>")
			.append("</item>")
			.append("</Articles>")
			.append("</xml> ");
		return buf.toString();
	}
}
