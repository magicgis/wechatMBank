/**  
* @Title: ReplyMessage.java
* @Package com.yitong.weixin.bean
* @Description: TODO
* @author ZhangPeiZhong
* @date 2014年4月25日 下午3:54:56
* @version V1.0  
*/ 
package com.yitong.weixin.modules.wechat.model;

/**
 * @ClassName: ReplyMessage
 * @Description: 回复消息
 * @author ZhangPeiZhong
 * @date 2014年4月25日 下午3:54:56
 *
 */

public class ReplyMessage extends BaseMessage{
	private E_ReplyType replyType;	// 回复消息类型，0：文本消息，1:文本复杂消息，2：文章消息
	private String message = "";	// 消息头
	private String articleId;
	private String afterMessage;
	
	public static enum E_ReplyType{txt,txt_h, artl};
	
	public ReplyMessage() {}
	
	/**
	 * 
	* <p>Title: </p>
	* <p>Description: </p>
	* @param replyType
	* @param message
	 */
	public ReplyMessage(E_ReplyType replyType, String message) {
		super();
		this.replyType = replyType;
		this.message = null == message ? "" : message;
	}
	/**
	 * 
	* <p>Title: </p>
	* <p>Description: </p>
	* @param replyType
	* @param message
	* @param afterMessage
	 */
	/*public ReplyMessage(E_ReplyType replyType, String message, String afterMessage) {
		this(replyType, message);
		this.afterMessage = null == afterMessage? "" : afterMessage;
	}*/
	/**
	 * 
	* <p>Title: </p>
	* <p>Description: </p>
	* @param replyType
	* @param message
	* @param afterMessage
	* @param articleId
	 */
	public ReplyMessage(E_ReplyType replyType, String message, String afterMessage, String articleId) {
		this(replyType, message, afterMessage);
		this.articleId = articleId;
	}
	/**
	 * 
	* <p>Title: </p>
	* <p>Description: </p>
	* @param replyType
	* @param afterMessage
	* @param articleId
	 */
	public ReplyMessage(E_ReplyType replyType, String afterMessage, String articleId) {
		super();
		this.replyType = replyType;
		this.afterMessage = null == afterMessage ? "" : afterMessage;
		this.articleId = articleId;
	}
	
	public void iniMsg(BaseMessage bm)
	{
		this.toUserName = bm.getToUserName();
		this.fromUserName = bm.getFromUserName();
		this.msgType = bm.getMsgType();
		this.createTime = bm.getCreateTime();
	}
	public E_ReplyType getReplyType() {
		return replyType;
	}
	public void setReplyType(E_ReplyType replyType) {
		this.replyType = replyType;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = null == message ? "" : message;
	}
	public String getArticleId() {
		return articleId;
	}
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	public String getAfterMessage() {
		return afterMessage;
	}
	public void setAfterMessage(String afterMessage) {
		this.afterMessage = null == afterMessage ? "" : afterMessage;
	}
	
}
