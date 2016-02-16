/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yitong.weixin.front.info.entity;

import java.beans.Transient;
import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.yitong.weixin.common.persistence.DataEntity;
import com.yitong.weixin.common.utils.DateUtils;
import com.yitong.weixin.common.utils.StringUtils;
import com.yitong.weixin.common.utils.excel.annotation.ExcelField;
import com.yitong.weixin.front.receive.msgbean.LocationMessage;
import com.yitong.weixin.front.receive.msgbean.TextMessage;

/**
 * 微信消息存储表Entity
 * @author Fwy
 * @version 2016-01-07
 */
public class WeixinMessageF extends DataEntity<WeixinMessageF> {
	
	private static final long serialVersionUID = 1L;
	private String toUser;		// 接收微信用户所在分组
	private String fromUser; 	// 发送微信用户OPENID
	private WeixinUserF weixinUser;		// 发送微信用户OPENID
	private String msgType;		// 消息类型，说见微信接口 API
	private String content;		// 微信内容
	private String url;		// 链接
	private String title;		// 链接标题
	private String description;		// 链接说明
	private String mediaId;		// 媒体ID
	private String mediaFormat;		// 媒体格式
	private String thumbmediaId;		// 缩图ID
	private Double locX;		// 地点坐标 X
	private Double locY;		// 地点坐标 Y
	private Double locScale;		// 地点地图缩放级别
	private Double locLable;		// 地点标签
	private String functionCode;		// 功能编码
	private Date createDate;
	private Date beginCreateDate;		// 开始 创建时间
	private Date endCreateDate;		// 结束 创建时间
	
	public WeixinMessageF() {
		super();
	}

	public WeixinMessageF(String id){
		super(id);
	}
	
	public WeixinMessageF(TextMessage tm) {
		this.toUser = tm.getToUserName();
		this.fromUser = tm.getFromUserName();
		this.msgType = tm.getMsgType();
		this.content = tm.getContent();
		this.createDate = StringUtils.parseSecondStr2Date(tm.getCreateTime());
	}
	
	public WeixinMessageF(LocationMessage lm) {
		this.toUser = lm.getToUserName();
		this.fromUser = lm.getFromUserName();
		this.createDate = StringUtils.parseSecondStr2Date(lm.getCreateTime());
		this.msgType = lm.getMsgType();
		
		this.locX = Double.valueOf(lm.getLocation_X());
		this.locY = Double.valueOf(lm.getLocation_Y());
		this.locScale = Double.valueOf(lm.getScale());
	}

	@Length(min=0, max=32, message="接收微信用户所在分组长度必须介于 0 和 32 之间")
	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	
	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public WeixinUserF getWeixinUser() {
		return weixinUser;
	}

	public void setWeixinUser(WeixinUserF weixinUser) {
		this.weixinUser = weixinUser;
	}

	@Length(min=0, max=16, message="消息类型，说见微信接口 API长度必须介于 0 和 16 之间")
	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	@Length(min=0, max=2048, message="微信内容长度必须介于 0 和 2048 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=128, message="链接长度必须介于 0 和 128 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Length(min=0, max=128, message="链接标题长度必须介于 0 和 128 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=128, message="链接说明长度必须介于 0 和 128 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Length(min=0, max=64, message="媒体ID长度必须介于 0 和 64 之间")
	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
	@Length(min=0, max=64, message="媒体格式长度必须介于 0 和 64 之间")
	public String getMediaFormat() {
		return mediaFormat;
	}

	public void setMediaFormat(String mediaFormat) {
		this.mediaFormat = mediaFormat;
	}
	
	@Length(min=0, max=64, message="缩图ID长度必须介于 0 和 64 之间")
	public String getThumbmediaId() {
		return thumbmediaId;
	}

	public void setThumbmediaId(String thumbmediaId) {
		this.thumbmediaId = thumbmediaId;
	}
	
	public Double getLocX() {
		return locX;
	}

	public void setLocX(Double locX) {
		this.locX = locX;
	}
	
	public Double getLocY() {
		return locY;
	}

	public void setLocY(Double locY) {
		this.locY = locY;
	}
	
	public Double getLocScale() {
		return locScale;
	}

	public void setLocScale(Double locScale) {
		this.locScale = locScale;
	}
	
	public Double getLocLable() {
		return locLable;
	}

	public void setLocLable(Double locLable) {
		this.locLable = locLable;
	}
	
	@Length(min=0, max=32, message="功能编码长度必须介于 0 和 32 之间")
	public String getFunctionCode() {
		return functionCode;
	}

	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}
	
	public String getBeginCreateDate() {
		if(null != beginCreateDate) {
			return DateUtils.formatDate(beginCreateDate, "yyyy-MM-dd");
		}
		return null;
	}

	public Date getBeginDate() {
		return this.beginCreateDate;
	}
	
	public void setBeginCreateDate(Date beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}
	
	public String getEndCreateDate() {
		if(null != endCreateDate) {
			return DateUtils.formatDate(endCreateDate, "yyyy-MM-dd");
		}
		return null;
	}

	public Date getEndDate() {
		return this.endCreateDate;
	}
	
	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Transient
	@ExcelField(title="更新时间", type=1, align=2, sort=40)
	public String getCreateDateFomat() {
		if(null != createDate) {
			return DateUtils.formatDate(createDate, "yyyy-MM-dd HH:mm:ss");
		}
		return null;
	}
}