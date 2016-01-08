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
import com.yitong.weixin.common.utils.CacheUtils;
import com.yitong.weixin.common.utils.StringUtils;
import com.yitong.weixin.common.web.BaseController;
import com.yitong.weixin.modules.wechat.entity.WeixinAccount;
import com.yitong.weixin.modules.wechat.service.WeixinAccountService;
import com.yitong.weixin.modules.wechat.utils.AcctUtils;

/**
 * 多公众号的维护Controller
 * @author fwy
 * @version 2015-12-29
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/weixinAccount")
public class WeixinAccountController extends BaseController {

	@Autowired
	private WeixinAccountService weixinAccountService;
	
	@ModelAttribute
	public WeixinAccount get(@RequestParam(required=false) String id) {
		WeixinAccount entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = weixinAccountService.get(id);
		}
		if (entity == null){
			entity = new WeixinAccount();
		}
		return entity;
	}
	
	/**
	 * 选择公众号
	 * @author Fwy
	 * @param openId
	 * @param response
	 */
	@RequestMapping(value = "select")
	@ResponseBody
	public String select(String openId, HttpServletResponse response){
		if (StringUtils.isNotBlank(openId)){
			CacheUtils.put(AcctUtils.CACHE_OPEN_ID, openId);
		}
		return null;
	}
	
	@RequiresPermissions("wechat:weixinAccount:view")
	@RequestMapping(value = {"list", ""})
	public String list(WeixinAccount weixinAccount, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WeixinAccount> page = weixinAccountService.findPage(new Page<WeixinAccount>(request, response), weixinAccount); 
		model.addAttribute("page", page);
		return "modules/wechat/weixinAccountList";
	}

	@RequiresPermissions("wechat:weixinAccount:view")
	@RequestMapping(value = "form")
	public String form(WeixinAccount weixinAccount, Model model) {
		model.addAttribute("weixinAccount", weixinAccount);
		return "modules/wechat/weixinAccountForm";
	}

	@RequiresPermissions("wechat:weixinAccount:edit")
	@RequestMapping(value = "save")
	public String save(WeixinAccount weixinAccount, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, weixinAccount)){
			return form(weixinAccount, model);
		}
		weixinAccountService.save(weixinAccount);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/weixinAccount/?repage";
	}
	
	@RequiresPermissions("wechat:weixinAccount:edit")
	@RequestMapping(value = "delete")
	public String delete(WeixinAccount weixinAccount, RedirectAttributes redirectAttributes) {
		weixinAccountService.delete(weixinAccount);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/weixinAccount/?repage";
	}

}