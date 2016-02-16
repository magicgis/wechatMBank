package com.yitong.weixin.front.receive.msgbean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author he.huang
 * 地理位置识别结果接收体
 */
@XmlRootElement(name="xml")
public class LocationMessage {

	private String ToUserName; 		//开发者微信号
	private String FromUserName;	//发送方帐号（一个OpenID）
	private String CreateTime;		//消息创建时间 （整型）
	private String MsgType;			//地理位置为location
	private String Location_X;		//地理位置维度
	private String Location_Y;		//地理位置经度
	private String Scale;			//地图缩放大小

//	private double Latitude;		//地理位置纬度
//	private double Longitude;		//地理位置经度
//	private double Precision;		//地理位置精度
	private String Label;			//地理位置信息
	private String MsgID;			//消息id，64位整型
/*
	@XmlElement(name="Location_X")
	public Double getLatitude() {
		return Double.valueOf(Latitude);
	}
	public void setLatitude(String latitude) {
		Latitude = Double.valueOf(latitude);
	}
	@XmlElement(name="Location_Y")
	public Double getLongitude() {
		return Double.valueOf(Longitude);
	}
	public void setLongitude(String longitude) {
		Longitude = Double.valueOf(longitude);
	}
	@XmlElement(name="Scale")
	public Double getPrecision() {
		return Double.valueOf(Precision);
	}
	public void setPrecision(String precision) {
		Precision = Double.valueOf(precision);
	}
	*/
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
	@XmlElement(name="Location_X")
	public String getLocation_X() {
		return Location_X;
	}
	public void setLocation_X(String location_X) {
		Location_X = location_X;
	}
	@XmlElement(name="Location_Y")
	public String getLocation_Y() {
		return Location_Y;
	}
	public void setLocation_Y(String location_Y) {
		Location_Y = location_Y;
	}
	@XmlElement(name="Scale")
	public String getScale() {
		return Scale;
	}
	public void setScale(String scale) {
		Scale = scale;
	}
	@XmlElement(name="Label")
	public String getLabel() {
		return Label;
	}
	public void setLabel(String label) {
		Label = label;
	}
	@XmlElement(name="MsgID")
	public String getMsgID() {
		return MsgID;
	}
	public void setMsgID(String msgID) {
		MsgID = msgID;
	}
}
