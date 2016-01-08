package com.yitong.weixin.modules.wechat.utils;

import com.yitong.weixin.common.config.Global;
import com.yitong.weixin.common.utils.PropertiesLoader;


public class C {

	public final static String Text = "text";         //文本消息msgType
	
	public final static String Image = "image";		  //图片消息msgType
	
	public final static String Voice = "voice";		  //语音和语音识别结果消息msgType
	
	public final static String Video = "video";		  //视频消息msgType
	
	public final static String Location = "location"; //地理位置消息msgType
	
	public final static String Link = "link";		  //连接消息msgType
	public final static float SessionTime = 5f;          //整数单位为分
	public final static int moreNum = 10;  //更多明细查询
	
	public static String AccessToken="";
	
	public static String appId = getConfig("appId");//测试公众账号
	public static String appsecret = getConfig("appsecret");//测试公众
	public static String ip = getConfig("webIp");//外网跳转地址
	public static String lanIp = getConfig("lanIp");//内网接口访问地址
	public static String Token = getConfig("token");//微信验证所需token
	
	public static String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token";
	public static String publishUrl = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";
	public static String getUserInfoUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s";
	
	public static String KeFuPostMsg = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=%s";
	
	/**
	 * 获取配置
	 */
	public static String getConfig(String key) {
		return Global.getConfig(key);
	}
}
