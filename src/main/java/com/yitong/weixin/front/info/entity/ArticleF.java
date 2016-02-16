/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yitong.weixin.front.info.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion.User;
import com.yitong.weixin.common.persistence.DataEntity;

/**
 * 文章Entity
 * @author Fwy
 * @version 2016-01-05
 */
public class ArticleF extends DataEntity<ArticleF> {

    public static final String DEFAULT_TEMPLATE = "frontViewArticle";
	
	private static final long serialVersionUID = 1L;
//	private CategoryF category;// 分类编号
	private String title;	// 标题
	private String image;	// 文章图片
	private String keywords;// 关键字
	private String description;// 描述、摘要
	private Integer hits;	// 点击数
	private ArticleDataF articleData;	//文章副表
	private Date beginDate;	// 开始时间
	private Date endDate;	// 结束时间
	
	private User user;
	
	private int msgType;	// 消息类型，0：文本消息，1：单图文消息， 2：多图文消息
	private String desJson;
    
	public ArticleF() {
		super();
		this.hits = 0;
	}

	public ArticleF(String id){
		this();
		this.id = id;
	}
	
//	public ArticleF(CategoryF category){
//		this();
//		this.category = category;
//	}

	public void prePersist(){
		articleData.setId(this.id);
	}
	
//	public CategoryF getCategory() {
//		return category;
//	}

//	public void setCategory(CategoryF category) {
//		this.category = category;
//	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Length(min=0, max=255)
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
        this.image = image;//CmsUtils.formatImageSrcToDb(image);
	}

	@Length(min=0, max=255)
	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getDescription() {
		return description;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getHits() {
		return hits;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}

	public ArticleDataF getArticleData() {
		return articleData;
	}

	public void setArticleData(ArticleDataF articleData) {
		this.articleData = articleData;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

//   	public String getImageSrc() {
//        return CmsFUtils.formatImageSrcToWeb(this.image);
//   	}

	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public String getDesJson() {
		return desJson;
	}

	public void setDesJson(String desJson) {
		this.desJson = desJson;
	}
}


