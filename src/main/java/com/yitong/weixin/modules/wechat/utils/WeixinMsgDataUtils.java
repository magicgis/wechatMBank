/**  
* @Title: WeixinMsgDataUtils.java
* @Package com.thinkgem.jeesite.modules.receive.web
* @Description: TODO
* @author ZhangPeiZhong
* @date 2014年4月15日 上午11:00:49
* @version V1.0  
*/ 
package com.yitong.weixin.modules.wechat.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.stereotype.Component;

import com.yitong.weixin.modules.wechat.entity.WeixinUser;

/**
 * @ClassName: WeixinMsgDataUtils
 * @Description: 1 替换模板参数，2 判断是否绑定银行卡
 * @author ZhangPeiZhong
 * @date 2014年4月15日 上午11:00:49
 *
 */
@Component(value="weixinMsgDataUtils")
public class WeixinMsgDataUtils implements IWeixinMsgDataUtils{
	
	public String replacePara(String content,WeixinUser weiXinUser,String createTime){
		if(null == content)
			return null;
    	if(weiXinUser!=null){//处理相关的参数
    		content = content.replaceAll("%OPENID%", weiXinUser.getOpenId());
    		content = content.replaceAll("%MSGID%", createTime);
    		if(weiXinUser.getUserName()!=null){
    			String name = weiXinUser.getUserName();
				try {
					name = URLEncoder.encode(name,"UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			content = content.replaceAll("%USERNAME%", name);
    		}
    		if(weiXinUser.getBankcardId1()!=null)
    			content = content.replaceAll("%EBUSER_NO%", weiXinUser.getBankcardId1());
    		
    		content = content.replaceAll("%TOKEN%", "");
    	}
		return content;
	}
}
