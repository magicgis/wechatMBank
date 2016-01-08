/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yitong.weixin.modules.wechat.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yitong.weixin.common.config.Global;
import com.yitong.weixin.common.persistence.Page;
import com.yitong.weixin.common.utils.StringUtils;
import com.yitong.weixin.common.web.BaseController;
import com.yitong.weixin.modules.wechat.entity.WeixinGroup;
import com.yitong.weixin.modules.wechat.model.GroupModel;
import com.yitong.weixin.modules.wechat.service.WeixinGroupService;
import com.yitong.weixin.modules.wechat.utils.WeixinUtils;

/**
 * 微信分组Controller
 * @author fwy
 * @version 2015-12-31
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/weixinGroup")
public class WeixinGroupController extends BaseController {

	@Autowired
	private WeixinGroupService weixinGroupService;
	
	@ModelAttribute
	public WeixinGroup get(@RequestParam(required=false) String id) {
		WeixinGroup entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = weixinGroupService.get(id);
		}
		if (entity == null){
			entity = new WeixinGroup();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(WeixinGroup weixinGroup, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WeixinGroup> page = weixinGroupService.findPage(new Page<WeixinGroup>(request, response), weixinGroup); 
		model.addAttribute("page", page);
		return "modules/wechat/weixinGroupList";
	}

	@RequestMapping(value = "form")
	public String form(WeixinGroup weixinGroup, Model model) {
		model.addAttribute("weixinGroup", weixinGroup);
		return "modules/wechat/weixinGroupForm";
	}

	@RequestMapping(value = "save")
	public String save(WeixinGroup weixinGroup, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, weixinGroup)){
			return form(weixinGroup, model);
		}
		weixinGroupService.save(weixinGroup);
		addMessage(redirectAttributes, "保存微信分组成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/weixinGroup/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(WeixinGroup weixinGroup, RedirectAttributes redirectAttributes) {
		weixinGroupService.delete(weixinGroup);
		addMessage(redirectAttributes, "删除微信分组成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/weixinGroup/?repage";
	}
	
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(WeixinGroup weixinGroup, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<WeixinGroup> list = weixinGroupService.findList(weixinGroup);
		for (int i=0; i<list.size(); i++){
			WeixinGroup e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("name", e.getGroupName());
			mapList.add(map);
		}
		return mapList;
	}
	
	/**
	 * 从微信服务器下载数据到本地同步
	 * @param redirectAttributes
	 * @author Fwy
	 */
	@RequestMapping(value = "sync")
	public String sync(RedirectAttributes redirectAttributes) {
		try {
			String url=String.format("https://api.weixin.qq.com/cgi-bin/groups/get?access_token=%s",WeixinUtils.getAccessToken());
			String result = WeixinUtils.getWeiXin(url);//获取改公众号下的所有分组数据
			logger.debug("公众号下的所有分组为------->"+result);
			JSONObject jResult = JSON.parseObject(result);
			JSONArray groups = jResult.getJSONArray("groups");
			List<GroupModel> list = JSON.parseArray(JSON.toJSONString(groups), GroupModel.class);
			weixinGroupService.deleteAll();//先删除当前公众号下的所有分组数据
			for(GroupModel gm:list){
				WeixinGroup weixinGroup = new WeixinGroup();
				weixinGroup.setId(gm.getId());
				weixinGroup.setGroupName(gm.getName());
				weixinGroup.setGroupTimes(gm.getCount());
				weixinGroup.setIsNewRecord(true);
				weixinGroupService.save(weixinGroup);
			}
		} catch (Exception e) {
			addMessage(redirectAttributes, "同步微信分组失败");
			e.printStackTrace();
		}
		addMessage(redirectAttributes, "同步微信分组成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/weixinGroup/?repage";
	}
	
	
}