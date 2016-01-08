package com.yitong.weixin.modules.wechat.model;

import java.util.List;

public class SubMenuModel {
	private String id;
	private String code;// 功能编码
	private String name;// 菜单名称
	private String type;// 菜单的响应动作类型，目前有 0-click、1-view两种类型
	private String url;
	private String pid;// 父类菜单Id
	private String isHide;// 是否隐藏menu,0代表显示，1代表隐藏
	private String order;// 显示顺序
	private Integer contentType;// 内容类型
	private String message;// 内容
	private List<SubMenuModelList> modules;

	public List<SubMenuModelList> getModules() {
		return modules;
	}

	public void setModules(List<SubMenuModelList> modules) {
		this.modules = modules;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getIsHide() {
		return isHide;
	}

	public void setIsHide(String isHide) {
		this.isHide = isHide;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Integer getContentType() {
		return contentType;
	}

	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
