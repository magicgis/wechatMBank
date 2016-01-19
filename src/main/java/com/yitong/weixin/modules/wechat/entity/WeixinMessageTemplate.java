/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yitong.weixin.modules.wechat.entity;

import org.hibernate.validator.constraints.Length;

import com.yitong.weixin.common.persistence.DataEntity;

/**
 * 微信模板消息存储表Entity
 * @author hf
 * @version 2016-01-18
 */
public class WeixinMessageTemplate extends DataEntity<WeixinMessageTemplate> {
	
	private static final long serialVersionUID = 1L;
	private String templateId;		// 模板ID
	private String templateCode;		// 模板编码
	private String title;		// 标题
	private String primaryIndustry;		// 主行业
	private String deputyIndustry;		// 副行业
	private String content;		// 内容
	private String example;		// 示例
	private String acctOpenId;		// 微信号
	
	public WeixinMessageTemplate() {
		super();
	}

	public WeixinMessageTemplate(String id){
		super(id);
	}

	@Length(min=0, max=128, message="模板ID长度必须介于 0 和 128 之间")
	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	
	@Length(min=0, max=32, message="模板编码长度必须介于 0 和 32 之间")
	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}
	
	@Length(min=0, max=32, message="标题长度必须介于 0 和 32 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=64, message="主行业长度必须介于 0 和 64 之间")
	public String getPrimaryIndustry() {
		return primaryIndustry;
	}

	public void setPrimaryIndustry(String primaryIndustry) {
		this.primaryIndustry = primaryIndustry;
	}
	
	@Length(min=0, max=64, message="副行业长度必须介于 0 和 64 之间")
	public String getDeputyIndustry() {
		return deputyIndustry;
	}

	public void setDeputyIndustry(String deputyIndustry) {
		this.deputyIndustry = deputyIndustry;
	}
	
	@Length(min=0, max=1024, message="内容长度必须介于 0 和 1024 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=1024, message="示例长度必须介于 0 和 1024 之间")
	public String getExample() {
		return example;
	}

	public void setExample(String example) {
		this.example = example;
	}
	
	@Length(min=0, max=32, message="微信号长度必须介于 0 和 32 之间")
	public String getAcctOpenId() {
		return acctOpenId;
	}

	public void setAcctOpenId(String acctOpenId) {
		this.acctOpenId = acctOpenId;
	}
	
}