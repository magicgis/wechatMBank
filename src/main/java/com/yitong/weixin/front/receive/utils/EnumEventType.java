package com.yitong.weixin.front.receive.utils;

public enum EnumEventType {
	subscribe,unsubscribe,scan,location,click,templatesendjobfinish,view;
	//TEMPLATESENDJOBFINISH  模版消息
	public static EnumEventType getEnumEventType(String msgType){
		 return valueOf(msgType.toLowerCase());
	}

}
