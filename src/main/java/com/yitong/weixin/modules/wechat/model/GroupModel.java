package com.yitong.weixin.modules.wechat.model;

public class GroupModel {

	private String id;
	private String name;// 分组名称
	private Long count;// 分组人数
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
}
