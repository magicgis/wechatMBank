/**  
* @Title: IWeixinMsgDataUtils.java
* @Package com.thinkgem.jeesite.modules.receive.web
* @Description: TODO
* @author ZhangPeiZhong
* @date 2014年4月28日 下午4:35:35
* @version V1.0  
*/ 
package com.yitong.weixin.modules.wechat.utils;

import com.yitong.weixin.modules.wechat.entity.WeixinUser;

/**
 * @ClassName: IWeixinMsgDataUtils
 * @Description: 微信消息数据处理工具接口
 * @author ZhangPeiZhong
 * @date 2014年4月28日 下午4:35:35
 *
 */

public interface IWeixinMsgDataUtils {
	/**
	 * 
	* @Title: replacePara
	* @Description: 替换模板参数
	* @param content
	* @param weiXinUser
	* @param createTime
	* @return
	* @return String
	 */
	public String replacePara(String content,WeixinUser weiXinUser,String createTime);
}
