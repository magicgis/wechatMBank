package com.yitong.weixin.modules.wechat.model;

import java.util.List;

import com.yitong.weixin.modules.wechat.entity.WeixinGroup;
import com.yitong.weixin.modules.wechat.entity.WeixinUser;

public class WeixinUserBatUpdateModel {
	private String batUpdateGroupId;

	private List<WeixinUser> wxUserList;

	private WeixinGroup weixinGroup;

	private String groupIds;
	
	
	public WeixinUserBatUpdateModel() {
	}

	public WeixinUserBatUpdateModel(List<WeixinUser> wxUserList) {
		this.wxUserList = wxUserList;
	}

	public WeixinUserBatUpdateModel(String groupIds) {
		this.groupIds = groupIds;
	}

	public String getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(String groupIds) {
		this.groupIds = groupIds;
	}

	public WeixinGroup getWeixinGroup() {
		return weixinGroup;
	}

	public void setWeixinGroup(WeixinGroup weixinGroup) {
		this.weixinGroup = weixinGroup;
	}

	public String getBatUpdateGroupId() {
		return batUpdateGroupId;
	}

	public void setBatUpdateGroupId(String batUpdateGroupId) {
		this.batUpdateGroupId = batUpdateGroupId;
	}

	public List<WeixinUser> getWxUserList() {
		return wxUserList;
	}

	public void setWxUserList(List<WeixinUser> wxUserList) {
		this.wxUserList = wxUserList;
	}
}
