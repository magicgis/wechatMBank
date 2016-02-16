package com.yitong.weixin.front.receive.utils;

import com.yitong.weixin.common.utils.PropertiesLoader;


public class CF {

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

	public static String title_val1 = getConfig("jcqy");
	//解除签约
	public static String title_val2 = getConfig("dlwz");
	//网点查询
	public static String title_val3 = getConfig("welcome");
	//欢迎消息
	public static String title_val4 = getConfig("atm");
	//ATM查询
	public static String title_val5 = getConfig("qybd");
	//签约绑定
	public static String common_val1 = getConfig("czqbx2");
	//您要查找的全部消息如下：
	public static String common_val2 = getConfig("wqywxyh");
	//亲，您尚未签约绑定重庆三峡银行微信银行。轻松签约绑定微信，即可享受重庆三峡银行的便捷服务。
	public static String common_val3 = getConfig("wxyhktg");
	//微信银行可提供：
	public static String common_val4 = getConfig("template_id");
	
	public static String common_val5 = getConfig("qscdlwz");
	//请上传地理位置信息!
	//
	public static String error_val = getConfig("error_val");
	//亲，对您输入的内容我们无法检索到相关信息，我们将不断学习为您提供更优质的服务，请重新输入问题关键字！
	public static String picurl_val1 = getConfig("picurl_val1");
	public static String url_val1 = getConfig("url_val1");
	public static String picurl_val2 = getConfig("picurl_val2");
	public static String url_val2 = getConfig("url_val2");
	
	public static String description_val = "description_val";
	
	public static String accessTokenUrl = getConfig("accessTokenUrl");
	//"https://api.weixin.qq.com/cgi-bin/token";
	public static String publishUrl = getConfig("publishUrl");
	//"https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";
	public static String getUserInfoUrl = getConfig("getUserInfoUrl");
	//"https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s";
	public static String KeFuPostMsg = getConfig("KeFuPostMsg");
	//"https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=%s";
	public static String TemplateMsg = getConfig("TemplateMsg");
	//public static String TemplateMsg = "https://mp.weixin.qq.com/advanced/tmplmsg?action=faq&token=%s&lang=zh_CN";
	//"https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s";
	public static String QunFaPostMsg = getConfig("QunFaPostMsg");
	//"https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=%s";
	/**
	 * 属性文件加载对象
	 */
	private static PropertiesLoader propertiesLoader;
	/**
	 * 获取配置
	 */
	public static String getConfig(String key) {
		if (propertiesLoader == null) {
			propertiesLoader = new PropertiesLoader("wechat.properties","application-hsh.properties");
		}
		return propertiesLoader.getProperty(key);
	}
	
}
