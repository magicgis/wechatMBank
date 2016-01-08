/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yitong.weixin.modules.wechat.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yitong.weixin.common.config.Global;
import com.yitong.weixin.common.persistence.Page;
import com.yitong.weixin.common.utils.StringUtils;
import com.yitong.weixin.common.web.BaseController;
import com.yitong.weixin.modules.wechat.entity.WeixinUser;
import com.yitong.weixin.modules.wechat.model.UserInfo;
import com.yitong.weixin.modules.wechat.model.WeixinUserBatUpdateModel;
import com.yitong.weixin.modules.wechat.service.WeixinUserService;
import com.yitong.weixin.modules.wechat.utils.WeixinUtils;

/**
 * 微信用户Controller
 * @author Fwy
 * @version 2015-12-31
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/weixinUser")
public class WeixinUserController extends BaseController {

	@Autowired
	private WeixinUserService weixinUserService;
	
	@ModelAttribute
	public WeixinUser get(@RequestParam(required=false) String id) {
		WeixinUser entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = weixinUserService.get(id);
		}
		if (entity == null){
			entity = new WeixinUser();
		}
		return entity;
	}
	
	/**
	 * @author Fwy
	 * @return
	 */
	@RequestMapping(value = {"list", ""})
	public String list(WeixinUser weixinUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WeixinUser> page = weixinUserService.findPage(new Page<WeixinUser>(request, response), weixinUser); 
		model.addAttribute("page", page);
		return "modules/wechat/weixinUserList";
	}

	@RequiresPermissions("wechat:weixinUser:view")
	@RequestMapping(value = "form")
	public String form(WeixinUser weixinUser, Model model) {
		model.addAttribute("weixinUser", weixinUser);
		return "modules/wechat/weixinUserForm";
	}

	@RequiresPermissions("wechat:weixinUser:edit")
	@RequestMapping(value = "save")
	public String save(WeixinUser weixinUser, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, weixinUser)){
			return form(weixinUser, model);
		}
		weixinUserService.save(weixinUser);
		addMessage(redirectAttributes, "保存微信用户成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/weixinUser/?repage";
	}
	
	@RequiresPermissions("wechat:weixinUser:edit")
	@RequestMapping(value = "delete")
	public String delete(WeixinUser weixinUser, RedirectAttributes redirectAttributes) {
		weixinUserService.delete(weixinUser);
		addMessage(redirectAttributes, "删除微信用户成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/weixinUser/?repage";
	}
	
	@RequestMapping(value = "/batUpdateUsersGroup/{groupIds}")
	public String batUpdateUsersGroup(@PathVariable String groupIds, Model model) {
		WeixinUserBatUpdateModel batUpdateModel = new WeixinUserBatUpdateModel(
				groupIds);
		model.addAttribute("wxuBatUpdateModel", batUpdateModel);
		model.addAttribute("groupIds", groupIds);
		return "modules/wechat/userBatUpdateForm";
	}
	
	@RequestMapping(value = "/updateBatUserGroup", method = RequestMethod.POST)
	public String updateBatUserGroup(WeixinUserBatUpdateModel wxuBatUpdateModel, Model model, RedirectAttributes redirectAttributes) {
		String groupId = "0";
		if (wxuBatUpdateModel.getBatUpdateGroupId() != null) {
			groupId = wxuBatUpdateModel.getBatUpdateGroupId();
		}
		weixinUserService.batUserGroupUpdate(groupId, wxuBatUpdateModel.getGroupIds());
		addMessage(redirectAttributes, "批量修改用户分组成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/weixinUser/?repage";
	}

	/**
	 * 从微信服务器下载数据到本地同步
	 * @param redirectAttributes
	 * @author Fwy
	 */
	@RequestMapping(value = "sync")
	public String sync(RedirectAttributes redirectAttributes) {
		try {
			String url=String.format("https://api.weixin.qq.com/cgi-bin/user/get?access_token=%s",WeixinUtils.getAccessToken());
			String result = WeixinUtils.getWeiXin(url);//获取改公众号下的所有分组数据
			getNextList(result);
			logger.debug("公众号下的所有用户openId为------->"+result);
		} catch (Exception e) {
			addMessage(redirectAttributes, "同步微信用户失败");
			e.printStackTrace();
		}
		addMessage(redirectAttributes, "同步微信用户成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/weixinGroup/?repage";
	}
	
	public void getNextList(String result) throws Exception{
		String urlPart = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=%s&next_openid=%s";
		JSONObject jResult = JSON.parseObject(result);
		int count = jResult.getIntValue("count");
		if(count>0){
			String nextOpenId = jResult.getString("next_openid");
			JSONObject data = jResult.getJSONObject("data");
			getDetailUserInfo(data);
			
			String url=String.format(urlPart,WeixinUtils.getAccessToken(),nextOpenId);
			String nextResult = WeixinUtils.getWeiXin(url);//获取改公众号下的所有分组数据
			getNextList(nextResult);
		}else{
			return;
		}
	}
	
	/**
	 *获取具体的用户明细信息
	 *@author Fwy
	 * @throws Exception 
	 */
	public void getDetailUserInfo(JSONObject data) throws Exception{
		JSONArray openids = data.getJSONArray("openid");
		String[] openid = openids.toArray(new String[openids.size()]);
		for(int i=0;i<openid.length;i++){
			getUserInfo(openid[i]);
		}
	}
	
	private void getUserInfo(String openId) throws Exception{
		String userInfoStr = WeixinUtils.getUserDetailInfo(openId);
		UserInfo userInfo = JSONObject.parseObject(userInfoStr, UserInfo.class);
		WeixinUser findTmp = weixinUserService.findByOpenId(openId);
		logger.debug("user--->"+userInfoStr);
		if(findTmp==null){
			findTmp = new WeixinUser(userInfo);
			weixinUserService.save(findTmp);
		}else{
			findTmp.updateWeixinUser(userInfo);
			weixinUserService.save(findTmp);
		}
	}
}