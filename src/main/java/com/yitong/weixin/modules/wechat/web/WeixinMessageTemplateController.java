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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yitong.weixin.common.config.Global;
import com.yitong.weixin.common.persistence.Page;
import com.yitong.weixin.common.web.BaseController;
import com.yitong.weixin.common.utils.StringUtils;
import com.yitong.weixin.modules.wechat.entity.WeixinMessageTemplate;
import com.yitong.weixin.modules.wechat.service.WeixinMessageTemplateService;

/**
 * 微信模板消息存储表Controller
 * @author hf
 * @version 2016-01-14
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/weixinMessageTemplate")
public class WeixinMessageTemplateController extends BaseController {

	@Autowired
	private WeixinMessageTemplateService weixinMessageTemplateService;
	
	@ModelAttribute
	public WeixinMessageTemplate get(@RequestParam(required=false) String id) {
		WeixinMessageTemplate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = weixinMessageTemplateService.get(id);
		}
		if (entity == null){
			entity = new WeixinMessageTemplate();
		}
		return entity;
	}
	
	@RequiresPermissions("wechat:weixinMessageTemplate:view")
	@RequestMapping(value = {"list", ""})
	public String list(WeixinMessageTemplate weixinMessageTemplate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WeixinMessageTemplate> page = weixinMessageTemplateService.findPage(new Page<WeixinMessageTemplate>(request, response), weixinMessageTemplate); 
		model.addAttribute("page", page);
		return "modules/wechat/weixinMessageTemplateList";
	}

	@RequiresPermissions("wechat:weixinMessageTemplate:view")
	@RequestMapping(value = "form")
	public String form(WeixinMessageTemplate weixinMessageTemplate, Model model) {
		model.addAttribute("weixinMessageTemplate", weixinMessageTemplate);
		return "modules/wechat/weixinMessageTemplateForm";
	}

	@RequiresPermissions("wechat:weixinMessageTemplate:edit")
	@RequestMapping(value = "save")
	public String save(WeixinMessageTemplate weixinMessageTemplate, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, weixinMessageTemplate)){
			return form(weixinMessageTemplate, model);
		}
		weixinMessageTemplateService.save(weixinMessageTemplate);
		addMessage(redirectAttributes, "保存模板消息成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/weixinMessageTemplate/?repage";
	}
	
	@RequiresPermissions("wechat:weixinMessageTemplate:edit")
	@RequestMapping(value = "delete")
	public String delete(WeixinMessageTemplate weixinMessageTemplate, RedirectAttributes redirectAttributes) {
		weixinMessageTemplateService.delete(weixinMessageTemplate);
		addMessage(redirectAttributes, "删除模板消息成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/weixinMessageTemplate/?repage";
	}

}