/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yitong.weixin.modules.wechat.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yitong.weixin.common.config.Global;
import com.yitong.weixin.common.persistence.Page;
import com.yitong.weixin.common.utils.StringUtils;
import com.yitong.weixin.common.web.BaseController;
import com.yitong.weixin.modules.cms.entity.Article;
import com.yitong.weixin.modules.cms.service.ArticleService;
import com.yitong.weixin.modules.wechat.entity.WeixinMenuList;
import com.yitong.weixin.modules.wechat.service.WeixinMenuListService;

/**
 * 微信菜单规则Controller
 * @author Fwy
 * @version 2016-01-06
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/weixinMenuList")
public class WeixinMenuListController extends BaseController {

	@Autowired
	private WeixinMenuListService weixinMenuListService;
	
	@Autowired
    private ArticleService articleService;
	
	@ModelAttribute
	public WeixinMenuList get(@RequestParam(required=false) String id) {
		WeixinMenuList entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = weixinMenuListService.get(id);
		}
		if (entity == null){
			entity = new WeixinMenuList();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(WeixinMenuList weixinMenuList, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WeixinMenuList> page = weixinMenuListService.findPage(new Page<WeixinMenuList>(request, response), weixinMenuList); 
		model.addAttribute("page", page);
		return "modules/wechat/ruleList";
	}

	@RequestMapping(value = "form")
	public String form(WeixinMenuList weixinMenuList, Model model) {
		model.addAttribute("weixinMenuList", weixinMenuList);
		return "modules/wechat/ruleForm";
	}

	@RequestMapping(value = "save")
	public String save(WeixinMenuList weixinMenuList, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, weixinMenuList)){
			return form(weixinMenuList, model);
		}
		if(weixinMenuList.getId()==null || "".equals(weixinMenuList.getId())){//ID为空是新增
			WeixinMenuList cur = weixinMenuListService.getByCode(weixinMenuList.getCode());
			if(cur!=null){
				addMessage(model, "规则编码不能重复，请重新录入");
				return form(weixinMenuList, model);
			}
		}
		
		weixinMenuListService.save(weixinMenuList);
		addMessage(redirectAttributes, "保存菜单规则成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/weixinMenuList/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(WeixinMenuList weixinMenuList, RedirectAttributes redirectAttributes) {
		weixinMenuListService.delete(weixinMenuList);
		addMessage(redirectAttributes, "删除菜单规则成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/weixinMenuList/?repage";
	}

	 /**
     * 文章选择列表
     * 只能单选
     */
    @RequestMapping(value = "/selectListRadio")
    public String selectListRadio(Article article, HttpServletRequest request, HttpServletResponse response, Model model) {
    	Page<Article> page = null;
    	page = articleService.findPageByMsgType(new Page<Article>(request, response), article, true);
        model.addAttribute("page", page);
        return "modules/wechat/articleSelectListRadio";
    }
}