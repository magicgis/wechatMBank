package com.yitong.weixin.front.receive.utils;

public enum EnumMsgType {

	text,image,voice,video,location,link,event;
	
	public static EnumMsgType getEnumMsgType(String msgType){
		 return valueOf(msgType.toLowerCase());
	}
}
