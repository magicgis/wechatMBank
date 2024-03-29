/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yitong.weixin.modules.wechat.entity;

import org.hibernate.validator.constraints.Length;

import com.yitong.weixin.common.persistence.DataEntity;

/**
 * 微信分组Entity
 * @author hf
 * @version 2016-02-18
 */
public class WeixinGroup extends DataEntity<WeixinGroup> {
	
	private static final long serialVersionUID = 1L;
	private String groupName;		// group_name
	private String groupComment;		// group_comment
	private Long groupTimes;		// group_times
	private String acctOpenId;		// acct_open_id
	private String groupId;		// group_id
	
	private String groupOldName;//修改分组旧名
	
	public String getGroupOldName() {
		return groupOldName;
	}

	public void setGroupOldName(String groupOldName) {
		this.groupOldName = groupOldName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public WeixinGroup() {
		super();
	}

	public WeixinGroup(String id){
		super(id);
	}

	@Length(min=0, max=255, message="group_name长度必须介于 0 和 255 之间")
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	@Length(min=0, max=255, message="group_comment长度必须介于 0 和 255 之间")
	public String getGroupComment() {
		return groupComment;
	}

	public void setGroupComment(String groupComment) {
		this.groupComment = groupComment;
	}
	
	public Long getGroupTimes() {
		return groupTimes;
	}

	public void setGroupTimes(Long groupTimes) {
		this.groupTimes = groupTimes;
	}
	
	@Length(min=0, max=64, message="acct_open_id长度必须介于 0 和 64 之间")
	public String getAcctOpenId() {
		return acctOpenId;
	}

	public void setAcctOpenId(String acctOpenId) {
		this.acctOpenId = acctOpenId;
	}
	
	@Length(min=0, max=16, message="group_id长度必须介于 0 和 16 之间")
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
}