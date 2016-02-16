package com.yitong.weixin.front.receive.msgbean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author fang.wenyu
 * 接收微信事件推送实体，除了地理位置
 */
@XmlRootElement(name="xml")
public class SubscribeEvent {

	private String ToUserName; 		//开发者微信号
	private String FromUserName;	//发送方帐号（一个OpenID）
	private String CreateTime;		//消息创建时间 （整型）
	private String MsgType;			//为Event
	private String Event;			//事件类型，subscribe(订阅)、unsubscribe(取消订阅)
	private String EventKey;		//事件KEY值，qrscene_为前缀，后面为二维码的参数值
	private String Ticket;			//二维码的ticket，可用来换取二维码图片
	private String Latitude;		//地理位置纬度
	private String Longitude;		//地理位置经度
	private String Precision;		//地理位置精度
	private String value;
	private String Status;
	private String MsgId;
	
	@XmlElement(name="ToUserName")
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	
	@XmlElement(name="FromUserName")
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	
	@XmlElement(name="CreateTime")
	public String getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}
	
	@XmlElement(name="MsgType")
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	
	@XmlElement(name="Event")
	public String getEvent() {
		return Event;
	}
	public void setEvent(String event) {
		Event = event;
	}
	
	@XmlElement(name="EventKey")
	public String getEventKey() {
		return EventKey;
	}
	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}
	
	@XmlElement(name="Ticket")
	public String getTicket() {
		return Ticket;
	}
	public void setTicket(String ticket) {
		Ticket = ticket;
	}
	
	@XmlElement(name="Latitude")
	public String getLatitude() {
		return Latitude;
	}
	public void setLatitude(String latitude) {
		Latitude = latitude;
	}
	
	@XmlElement(name="Longitude")
	public String getLongitude() {
		return Longitude;
	}
	public void setLongitude(String longitude) {
		Longitude = longitude;
	}
	
	@XmlElement(name="Precision")
	public String getPrecision() {
		return Precision;
	}
	public void setPrecision(String precision) {
		Precision = precision;
	}
	
	@XmlElement(name="Value")
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@XmlElement(name="Status")
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	@XmlElement(name="MsgID")
	public String getMsgId() {
		return MsgId;
	}
	public void setMsgId(String msgId) {
		MsgId = msgId;
	}
	
}
