/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yitong.weixin.modules.wechat.web;

import java.util.HashMap;
import java.util.Map;

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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yitong.weixin.common.config.Global;
import com.yitong.weixin.common.persistence.Page;
import com.yitong.weixin.common.web.BaseController;
import com.yitong.weixin.common.utils.StringUtils;
import com.yitong.weixin.modules.wechat.entity.WeixinMessageTemplate;
import com.yitong.weixin.modules.wechat.service.WeixinMessageTemplateService;
import com.yitong.weixin.modules.wechat.utils.AcctUtils;
import com.yitong.weixin.modules.wechat.utils.WeixinUtils;

/**
 * 微信模板消息存储表Controller
 * @author hf
 * @version 2016-01-18
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
		try{
			String result = syncDeleteWeixinTemplate(weixinMessageTemplate);
			if(WeixinUtils.parseWeixinResult(result)){
				weixinMessageTemplateService.delete(weixinMessageTemplate);
				addMessage(redirectAttributes, "删除模板消息成功");
			}else{
				addMessage(redirectAttributes, "删除模板消息失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			addMessage(redirectAttributes, "删除模板消息失败");
			return "redirect:"+Global.getAdminPath()+"/wechat/weixinMessageTemplate/?repage";
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/weixinMessageTemplate/?repage";
	}
	
	/**
	 * 从微信服务器下载数据到本地同步
	 * @param redirectAttributes
	 * @author hf
	 */
	@RequestMapping(value = "sync")
	public String sync(RedirectAttributes redirectAttributes) {
		try {
			String url=String.format("https://api.weixin.qq.com/cgi-bin/template/get_all_private_template?access_token=%s",WeixinUtils.getAccessToken());
			logger.debug("公众号下的所有模板消息url为------->"+url);
			String result = WeixinUtils.getWeiXin(url);//获取改公众号下的所有分组数据
			logger.debug("公众号下的所有模板消息为------->"+result);
			getList(result);
		} catch (Exception e) {
			addMessage(redirectAttributes, "同步微信模板消息失败");
			e.printStackTrace();
		}
		addMessage(redirectAttributes, "同步微信模板消息成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/weixinMessageTemplate/?repage";
	}
	
	public void getList(String result) throws Exception{
		JSONObject jResult = JSON.parseObject(result);
		JSONArray templateList = jResult.getJSONArray("template_list");
		String[] list = templateList.toArray(new String[templateList.size()]);
		for(int i = 0; i < list.length; i++){
			JSONObject template = JSONObject.parseObject(list[i]);
			saveMessageTemplate(template);
		}
	}
	
	public void saveMessageTemplate(JSONObject template){
		WeixinMessageTemplate weixinMessageTemplate = new WeixinMessageTemplate();
		weixinMessageTemplate.setTemplateId(template.getString("template_id"));
		weixinMessageTemplate.setTitle(template.getString("title"));
		weixinMessageTemplate.setPrimaryIndustry(template.getString("primary_industry"));
		weixinMessageTemplate.setDeputyIndustry(template.getString("deputy_industry"));
		weixinMessageTemplate.setContent(template.getString("content"));
		weixinMessageTemplate.setExample(template.getString("example"));
		weixinMessageTemplateService.save(weixinMessageTemplate);
	}
	
	private String syncDeleteWeixinTemplate(WeixinMessageTemplate weixinMessageTemplate) throws Exception{
		String url=String.format("https://api,weixin.qq.com/cgi-bin/template/del_private_template?access_token=%s",WeixinUtils.getAccessToken());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("template_id", weixinMessageTemplate.getTemplateId());
		String content = JSON.toJSONString(map);
		String result = WeixinUtils.postWeiXin(url, content);//向微信服务器删除模板信息
		logger.debug("向微信服务器删除模板信息返回结果为------->"+result);
		return result;
	}

}