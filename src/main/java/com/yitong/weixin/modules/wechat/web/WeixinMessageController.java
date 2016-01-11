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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yitong.weixin.common.config.Global;
import com.yitong.weixin.common.persistence.Page;
import com.yitong.weixin.common.utils.StringUtils;
import com.yitong.weixin.common.web.BaseController;
import com.yitong.weixin.modules.wechat.entity.WeixinMessage;
import com.yitong.weixin.modules.wechat.service.WeixinMessageService;

/**
 * 微信消息存储表Controller
 * @author Fwy
 * @version 2016-01-07
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/weixinMessage")
public class WeixinMessageController extends BaseController {

	@Autowired
	private WeixinMessageService weixinMessageService;
	
	@ModelAttribute
	public WeixinMessage get(@RequestParam(required=false) String id) {
		WeixinMessage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = weixinMessageService.get(id);
		}
		if (entity == null){
			entity = new WeixinMessage();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(WeixinMessage message, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WeixinMessage> page = weixinMessageService.findPageGroupBy(new Page<WeixinMessage>(request, response), message); 
		model.addAttribute("page", page);
		model.addAttribute("messageModel", message);
		return "modules/wechat/wx_message_list";
	}
	
	@RequestMapping("/findPageByMsgId")
	@ResponseBody
	public Page<WeixinMessage> findPageByMsgId(String msgId, HttpServletRequest request, 
			HttpServletResponse response) {
		Page<WeixinMessage> page = new Page<WeixinMessage>(request, response);
		if(null == msgId) {
			return page;
		}
		return weixinMessageService.findPageByMsgId(page, msgId);
	}

	@RequiresPermissions("wechat:weixinMessage:view")
	@RequestMapping(value = "form")
	public String form(WeixinMessage weixinMessage, Model model) {
		model.addAttribute("weixinMessage", weixinMessage);
		return "modules/wechat/weixinMessageForm";
	}

	@RequiresPermissions("wechat:weixinMessage:edit")
	@RequestMapping(value = "save")
	public String save(WeixinMessage weixinMessage, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, weixinMessage)){
			return form(weixinMessage, model);
		}
		weixinMessageService.save(weixinMessage);
		addMessage(redirectAttributes, "保存消息成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/weixinMessage/?repage";
	}
	
	@RequiresPermissions("wechat:weixinMessage:edit")
	@RequestMapping(value = "delete")
	public String delete(WeixinMessage weixinMessage, RedirectAttributes redirectAttributes) {
		weixinMessageService.delete(weixinMessage);
		addMessage(redirectAttributes, "删除消息成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/weixinMessage/?repage";
	}

}